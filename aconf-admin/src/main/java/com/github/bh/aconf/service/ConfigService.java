package com.github.bh.aconf.service;

import com.github.bh.aconf.common.constants.FilterBelongType;
import com.github.bh.aconf.common.constants.ValidStatus;
import com.github.bh.aconf.common.constants.ValueType;
import com.github.bh.aconf.common.utils.BeanUtils;
import com.github.bh.aconf.common.utils.JsonUtils;
import com.github.bh.aconf.common.utils.VersionConverter;
import com.github.bh.aconf.constants.BusinessStatus;
import com.github.bh.aconf.domain.Page;
import com.github.bh.aconf.domain.ServerResponse;
import com.github.bh.aconf.domain.StaffInfo;
import com.github.bh.aconf.domain.command.ConditionCommandV2;
import com.github.bh.aconf.domain.command.ConfigCommand;
import com.github.bh.aconf.domain.command.ConfigCommandV2;
import com.github.bh.aconf.domain.command.FilterCommandV2;
import com.github.bh.aconf.domain.dtgrid.Pager;
import com.github.bh.aconf.domain.node.Node;
import com.github.bh.aconf.domain.vo.ConditionVo;
import com.github.bh.aconf.domain.vo.ConfigVo;
import com.github.bh.aconf.domain.vo.ConfigVoV2;
import com.github.bh.aconf.domain.vo.FilterVo;
import com.github.bh.aconf.filter.BoundaryUtils;
import com.github.bh.aconf.filter.Filter;
import com.github.bh.aconf.filter.FilterContainer;
import com.github.bh.aconf.mapper.ConditionMapper;
import com.github.bh.aconf.mapper.ConfigMapper;
import com.github.bh.aconf.mapper.FilterMapper;
import com.github.bh.aconf.mapper.MetaMapper;
import com.github.bh.aconf.persist.base.mapper.BssMetaMapper;
import com.github.bh.aconf.persist.base.mapper.ConfigHistoryMetaMapper;
import com.github.bh.aconf.persist.base.mapper.ConfigMetaMapper;
import com.github.bh.aconf.persist.base.mapper.FilterMetaMapper;
import com.github.bh.aconf.persist.base.model.*;
import com.github.bh.aconf.utils.AuthUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author xiaobenhai
 * Date: 2016/11/3
 * Time: 15:53
 */
@Service
public class ConfigService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigService.class);
    private static final String DEFAULT_ORDER = "create_time desc";
    @Autowired
    private ConfigHistoryMetaMapper configHistoryMetaMapper;
    @Autowired
    private ConfigMetaMapper configMetaMapper;
    @Autowired
    private FilterMetaMapper filterMetaMapper;
    @Autowired
    private BssMetaMapper bssMetaMapper;
    @Autowired
    private ResourceService resourceService;

    @Autowired
    private ConditionService conditionService;
    @Autowired
    private FilterService filterService;
    @Autowired
    private FilterContainer filterContainer;
    /**
     * 创建或更新配置项
     *
     * @param command 配置请求
     * @return 视图对象
     */
    @Transactional
    public ConfigVo createOrUpdateConfig(ConfigCommand command) {
        long currentVersion = getBssVersion(command.getBssId());
        ConfigMeta meta = ConfigMapper.INSTANCE.getConfigMeta(command);
        meta.setVersion(currentVersion + 1);
        if (meta.getId() != null) {
            configMetaMapper.updateByPrimaryKeySelective(meta);
        } else {
            configMetaMapper.insertSelective(meta);
        }

        ConfigMeta result = configMetaMapper.selectByPrimaryKey(meta.getId());
        configHistoryMetaMapper.insert(MetaMapper.INSTANCE.getHistoryMeta(result));
        increaseBssVersion(meta.getBssId(), currentVersion);
        return ConfigMapper.INSTANCE.getConfigVo(result);
    }

    /**
     * 创建Config
     *
     * @param command
     * @return
     */
    public ServerResponse createConfig(ConfigCommand command) {
        ServerResponse response = new ServerResponse();
        if (command.getId() != null) {
            return response.state(BusinessStatus.NO_NEED_PARAMS);
        }
        ConfigMeta meta = ConfigMapper.INSTANCE.getConfigMeta(command);
        meta.setVersion(1L);
        try {
            configMetaMapper.insert(meta);
        } catch (Exception e) {
            LOGGER.info("create config error", e);
            return response.state(BusinessStatus.DUPLICATE_KEY);
        }
        ConfigVo vo = ConfigMapper.INSTANCE.getConfigVo(meta);
        return response.state(BusinessStatus.SUCCESS).data(vo);
    }

    /**
     * 创建Config，包括Config的所有条件分支以及条件分支所包含的各项拦截器
     * 由于一次执行了大量的数据库操作，为了保证数据一致性，加了事务操作
     *
     * @param bssId  业务ID
     * @param config 前端返回的配置项信息
     * @return 执行结果
     * @since 0.0.2
     */
    @Transactional
    public ServerResponse createConfigV2(long bssId, ConfigCommandV2 config, HttpServletRequest request) {
        ServerResponse response = new ServerResponse();
        if (config == null) {
            return response.state(BusinessStatus.INVALID_PARAMS);
        }
        
        //拦截器检查
        if(!CollectionUtils.isEmpty(config.getConditions())){
        	for (ConditionCommandV2 condition : config.getConditions()) {
        		if(!CollectionUtils.isEmpty(condition.getFilters())){
        			for (FilterCommandV2 filter : condition.getFilters()) {
        				Filter f = filterContainer.get(filter.getBasis(), null);
        				//boundary为空或者数字转换失败的问题
        				if(filter.getBoundary()==null || filter.getBoundary().length() < 1){
        					return response.state(BusinessStatus.INVALID_PARAMS)
        							.addMessage(f==null?filter.getBasis():f.getAlias()).addMessage(condition.getName());
        				}else if(  "eq".equals(filter.getOperator()) 
        						|| "neq".equals(filter.getOperator())
        						|| "match".equals(filter.getOperator())){
        					List<String> elems = BoundaryUtils.splitJson(filter.getBoundary());
        					for(String elem : elems){
        						if(elem==null || elem.length()<1){
        							return response.state(BusinessStatus.INVALID_PARAMS)
        									.addMessage(f==null?filter.getBasis():f.getAlias()).addMessage(condition.getName());
        						}
        					}
        				}else if("version".equals(filter.getBasis())  || "osversion".equals(filter.getBasis())) {
        					List<String> elems = BoundaryUtils.splitJson(filter.getBoundary());
        					for(String elem : elems){
        						try{
        							if(VersionConverter.convert(elem) == 0){
            							return response.state(BusinessStatus.INVALID_PARAMS)
            									.addMessage(f==null?filter.getBasis():f.getAlias()).addMessage(condition.getName());        								
        							}
        						}catch(Exception e){
        							LOGGER.warn("invalid version :"+elem+" "+e.getMessage(),e);
        							return response.state(BusinessStatus.INVALID_PARAMS)
        									.addMessage(f==null?filter.getBasis():f.getAlias()).addMessage(condition.getName());
        						}        						
        					}       
        				}else if("datetime".equals(filter.getBasis())) {
        					List<String> elems = BoundaryUtils.splitJson(filter.getBoundary());
        					for(String elem : elems){
        						try{
        							SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        							fm.parse(elem);
        						}catch(Exception e){
        							LOGGER.warn("invalid datetime :"+elem+" "+e.getMessage(),e);
        							return response.state(BusinessStatus.INVALID_PARAMS)
        									.addMessage(f==null?filter.getBasis():f.getAlias()).addMessage(condition.getName());
        						}        						
        					}      
        				}		
        				else{ //number check
        					List<Long> elems = BoundaryUtils.parseForLongList(filter.getBoundary());
        					if(elems.isEmpty()){
        						return response.state(BusinessStatus.INVALID_PARAMS)
        								.addMessage(f==null?filter.getBasis():f.getAlias()).addMessage(condition.getName());
        					}
        				}
        			}
        		}
        	}
        }
        
        
        ConfigMeta configMeta = ConfigMapper.INSTANCE.getConfigMeta(config);
        saveConfigMeta(bssId, configMeta, request);
        List<ConditionCommandV2> conditions = config.getConditions();
        if (CollectionUtils.isEmpty(conditions)) {
            return response.state(BusinessStatus.SUCCESS);
        }
        for (ConditionCommandV2 condition : conditions) {
            ConditionMeta conditionMeta = ConditionMapper.INSTANCE.getMeta(condition);
            conditionService.saveCondition(configMeta.getId(), conditionMeta, request);
            List<FilterCommandV2> filters = condition.getFilters();
            if (CollectionUtils.isEmpty(filters)) {
                return response.state(BusinessStatus.SUCCESS);
            }
            for (FilterCommandV2 filter : filters) {
                FilterMeta filterMeta = FilterMapper.INSTANCE.getFilterMeta(filter);
                filterService.saveFilter(conditionMeta.getId(), FilterBelongType.CONDITION, filterMeta, request);
            }
        }
        return response.state(BusinessStatus.SUCCESS);
    }


    public ServerResponse updateConfigV2(long bssId, long configId, ConfigCommandV2 config, HttpServletRequest request) {
        ServerResponse response = new ServerResponse();
        if (config == null) {
            return response.state(BusinessStatus.INVALID_PARAMS);
        }
        
        //拦截器检查
        if(!CollectionUtils.isEmpty(config.getConditions())){
        	for (ConditionCommandV2 condition : config.getConditions()) {
        		if(!CollectionUtils.isEmpty(condition.getFilters())){
        			for (FilterCommandV2 filter : condition.getFilters()) {
        				Filter f = filterContainer.get(filter.getBasis(), null);
        				//boundary为空或者数字转换失败的问题
        				if(filter.getBoundary()==null || filter.getBoundary().length() < 1){
        					return response.state(BusinessStatus.INVALID_PARAMS)
        							.addMessage(f==null?filter.getBasis():f.getAlias()).addMessage(condition.getName());
        				}else if(  "eq".equals(filter.getOperator()) 
        						|| "neq".equals(filter.getOperator())
        						|| "match".equals(filter.getOperator())){
        					List<String> elems = BoundaryUtils.splitJson(filter.getBoundary());
        					for(String elem : elems){
        						if(elem==null || elem.length()<1){
        							return response.state(BusinessStatus.INVALID_PARAMS)
        									.addMessage(f==null?filter.getBasis():f.getAlias()).addMessage(condition.getName());
        						}
        					}
        				}else if("version".equals(filter.getBasis()) || "osversion".equals(filter.getBasis())) {
        					List<String> elems = BoundaryUtils.splitJson(filter.getBoundary());
        					for(String elem : elems){
        						try{
        							if(VersionConverter.convert(elem) == 0){
            							return response.state(BusinessStatus.INVALID_PARAMS)
            									.addMessage(f==null?filter.getBasis():f.getAlias()).addMessage(condition.getName());        								
        							}
        						}catch(Exception e){
        							LOGGER.warn("invalid version :"+elem+" "+e.getMessage(),e);
        							return response.state(BusinessStatus.INVALID_PARAMS)
        									.addMessage(f==null?filter.getBasis():f.getAlias()).addMessage(condition.getName());
        						}        						
        					}        					
        				}else if("datetime".equals(filter.getBasis())) {
        					List<String> elems = BoundaryUtils.splitJson(filter.getBoundary());
        					for(String elem : elems){
        						try{
        							SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        							fm.parse(elem);
        						}catch(Exception e){
        							LOGGER.warn("invalid datetime :"+elem+" "+e.getMessage(),e);
        							return response.state(BusinessStatus.INVALID_PARAMS)
        									.addMessage(f==null?filter.getBasis():f.getAlias()).addMessage(condition.getName());
        						}        						
        					}        					
        				} else{ //number check
        					List<Long> elems = BoundaryUtils.parseForLongList(filter.getBoundary());
        					if(elems.isEmpty()){
        						return response.state(BusinessStatus.INVALID_PARAMS)
        								.addMessage(f==null?filter.getBasis():f.getAlias()).addMessage(condition.getName());
        					}
        				}
        			}
        		}
        	}
        }
        
        ConfigMeta configMeta = ConfigMapper.INSTANCE.getConfigMeta(config);
        updateConfigMeta(configId, configMeta, request);

        conditionService.deleteConditions(config.getDeletedConditionIds());
        filterService.deleteFilters(config.getDeletedFilterIds());

        List<ConditionCommandV2> conditions = config.getConditions();
        if (CollectionUtils.isEmpty(conditions)) {
            return response.state(BusinessStatus.SUCCESS);
        }
        for (ConditionCommandV2 condition : conditions) {
            ConditionMeta conditionMeta = ConditionMapper.INSTANCE.getMeta(condition);
            conditionService.mergeCondition(configMeta.getId(), conditionMeta, request);
            List<FilterCommandV2> filters = condition.getFilters();
            if (CollectionUtils.isEmpty(filters)) {
                return response.state(BusinessStatus.SUCCESS);
            }
            for (FilterCommandV2 filter : filters) {
                FilterMeta filterMeta = FilterMapper.INSTANCE.getFilterMeta(filter);
                filterService.mergeFilter(conditionMeta.getId(), FilterBelongType.CONDITION, filterMeta, request);
            }
        }
        return response.state(BusinessStatus.SUCCESS);
    }

    /**
     * 更新配置项元信息
     *
     * @param id
     * @param configMeta
     */
    private void updateConfigMeta(long id, ConfigMeta configMeta, HttpServletRequest request) {
        configMeta.setId(id);
        configMeta.setCreateTime(new Date());
        StaffInfo user = AuthUtils.getAdminUser(request);
        if (user != null) {
            configMeta.setCreator(user.getPassport());
        }
        configMetaMapper.updateByPrimaryKeySelective(configMeta);
    }

    /**
     * 保存配置项元信息
     *
     * @param bssId
     * @param configMeta
     */
    private void saveConfigMeta(long bssId, ConfigMeta configMeta, HttpServletRequest request) {
        ResourceMeta res = null;
        String value = configMeta.getValue();
        if (value != null && value.startsWith("http")) {
            res = resourceService.getRousourceMetaByUrl(value);
        }

        configMeta.setBssId(bssId);
        StaffInfo user = AuthUtils.getAdminUser(request);
        if (user != null) {
            configMeta.setCreator(user.getPassport());
        }
        configMeta.setValid(ValidStatus.VALID.getValue());
        configMeta.setCreateTime(new Date());
        configMeta.setVersion(1L);
        if (res != null) {
            configMeta.setValueType(ValueType.VALUE.getValue());
        }
        configMetaMapper.insert(configMeta);

        resourceService.supplementConfigId(res, configMeta.getId());
        // TODO: 2017/1/18 存入历史记录（考虑AOP）
    }

    /**
     * 更新Config，和创建分离，更易于控制，防止业务方调用时由于误操作导致的覆盖当前已存在key的配置值
     *
     * @param command
     * @return
     */
    public ServerResponse updateConfig(ConfigCommand command) {
        ServerResponse response = new ServerResponse();
        if (command.getId() == null) {
            return response.state(BusinessStatus.INVALID_CONFIG_ID);
        }
        long currentVersion = getBssVersion(command.getBssId());
        ConfigMeta meta = ConfigMapper.INSTANCE.getConfigMeta(command);
        meta.setVersion(currentVersion + 1);
        configMetaMapper.updateByPrimaryKeySelective(meta);
        ConfigVo vo = ConfigMapper.INSTANCE.getConfigVo(meta);
        return response.state(BusinessStatus.SUCCESS).data(vo);
    }

    private void increaseBssVersion(Long bssId, long currentVersion) {
        BssMeta meta = new BssMeta();
        meta.setId(bssId);
        meta.setVersion(currentVersion + 1);
        bssMetaMapper.updateByPrimaryKeySelective(meta);
    }

    /**
     * 获取当前业务的最新版本
     */
    private long getBssVersion(long bssId) {
        BssMeta meta = bssMetaMapper.selectByPrimaryKey(bssId);
        if (meta == null) {
            return 0;
        }
        Long version = meta.getVersion();
        if (version == null) {
            return 0;
        }
        return version;
    }

    public ServerResponse getConfig(Long id) {
        ServerResponse response = new ServerResponse();
        ConfigVoV2 configVoV2 = getConfigVo(id);
        if (configVoV2 == null) {
            return response.state(BusinessStatus.NO_THIS_BSS);
        }
        return response.state(BusinessStatus.SUCCESS).data(configVoV2);
    }

    public ServerResponse getConfigNode(Long id) {
        ServerResponse response = new ServerResponse();
        ConfigVoV2 vo = getConfigVo(id);
        if (vo == null) {
            return response.state(BusinessStatus.NO_THIS_BSS);
        }
        Node config = new Node();
        config.setType("config");
        config.setId(vo.getId());
        config.setName(vo.getKey());
        List<ConditionVo> conditions = vo.getConditions();
        List<Node> conditionNodes = getConditionNodes(conditions);
        config.setChildren(conditionNodes);
        return response.state(BusinessStatus.SUCCESS).data(config);
    }

    private List<Node> getConditionNodes(List<ConditionVo> conditions) {
        List<Node> conditionNodes = null;
        if (CollectionUtils.isNotEmpty(conditions)) {
            conditionNodes = Lists.newArrayList();
            for (ConditionVo condition : conditions) {
                Node conditionNode = new Node();
                conditionNode.setType("condition");
                conditionNode.setName(condition.getName());
                conditionNode.setId(condition.getId());
                List<FilterVo> filters = condition.getFilters();
                List<Node> filterNodes = getFilterNodes(filters);
                conditionNode.setChildren(filterNodes);
                conditionNodes.add(conditionNode);
            }
        }
        return conditionNodes;
    }

    private List<Node> getFilterNodes(List<FilterVo> filters) {
        List<Node> filterNodes = null;
        if (CollectionUtils.isNotEmpty(filters)) {
            filterNodes = Lists.newArrayList();
            for (FilterVo filter : filters) {
                Node filterNode = new Node();
                filterNode.setType("filter");
                String boundary = filter.getBoundary();
                if (boundary.length() >= 25) {
                    boundary = boundary.substring(0, 25);
                }
                filterNode.setName(filter.getBasis() + " " + filter.getOperator() + " " + boundary);
                filterNode.setId(filter.getId());
                filterNodes.add(filterNode);
            }
        }
        return filterNodes;
    }

    private ConfigVoV2 getConfigVo(Long id) {
        ConfigMeta configMeta = configMetaMapper.selectByPrimaryKey(id);
        if (configMeta == null) {
            return null;
        }
        ConfigVoV2 configVoV2 = ConfigMapper.INSTANCE.getConfigVoV2(configMeta);
        fillCondition(configVoV2);
        return configVoV2;
    }

    /**
     * 批量获取配置项，通过bssId找到相应的配置项，配置项信息中将包含相应的过滤器信息
     *
     * @param bssId
     * @param limit
     * @param offset
     * @param order
     * @return
     */
    public List<ConfigVo> listConfigs(long bssId, int limit, int offset, String order) {
        List<ConfigMeta> list = getConfigMetas(bssId, limit, offset, order);
        List<ConfigVo> result = ConfigMapper.INSTANCE.getConfigVos(list);
        for (ConfigVo vo : result) {
            long id = vo.getId();
            FilterMetaExample filterMetaExample = new FilterMetaExample();
            filterMetaExample.createCriteria().andBelongIdEqualTo(id)
                    .andBelongTypeEqualTo(FilterBelongType.ITEM.getValue())
                    .andValidEqualTo(ValidStatus.VALID.getValue());
            List<FilterMeta> filters = filterMetaMapper.selectByExample(filterMetaExample);
            List<FilterVo> filterVo = FilterMapper.INSTANCE.getFilterVo(filters);
            vo.setFilters(filterVo);
        }
        return result;
    }

    /**
     * 伪删，将valid置为0;配置版本和业务版本更新
     * 可能的返回值：
     * NO_THIS_CONFIG 查无此配置
     *
     * @return
     */
    @Transactional
    public ServerResponse deleteConfig(long id, HttpServletRequest request) {
        ServerResponse response = new ServerResponse();
        ConfigMeta config = configMetaMapper.selectByPrimaryKey(id);
        if (config == null) {
            return response.state(BusinessStatus.NO_THIS_CONFIG);
        }

        long currentVersion = getBssVersion(config.getBssId());
        config.setValid(ValidStatus.INVALID.getValue());
        config.setVersion(currentVersion + 1);
        config.setCreateTime(new Date());
        configMetaMapper.deleteByPrimaryKey(id);

        configHistoryMetaMapper.insert(MetaMapper.INSTANCE.getHistoryMeta(config));
        increaseBssVersion(config.getBssId(), currentVersion);
        ConfigVo configVo = ConfigMapper.INSTANCE.getConfigVo(config);
        return response.state(BusinessStatus.SUCCESS).data(configVo);
    }

    public int countAll(long bssId) {
        ConfigMetaExample example = new ConfigMetaExample();
        example.createCriteria().andBssIdEqualTo(bssId).andValidEqualTo(ValidStatus.VALID.getValue());
        return configMetaMapper.countByExample(example);
    }

    public int countAll(ConfigCommand command) {
        ConfigMetaExample example = buildExample(command);
        return configMetaMapper.countByExample(example);
    }

    public List<ConfigVo> query(ConfigCommand command, int curPage, int pageSize) {
        if (command == null) {
            return Lists.newArrayList();
        }
        ConfigMetaExample example = buildExample(command);
        example.setOffset((curPage - 1) * pageSize);
        example.setLimit(pageSize);
        example.setOrderByClause("valid desc,id asc");

        List<ConfigMeta> appMetas = configMetaMapper.selectByExample(example);
        return ConfigMapper.INSTANCE.getConfigVos(appMetas);
    }

    private ConfigMetaExample buildExample(ConfigCommand command) {
        ConfigMetaExample example = new ConfigMetaExample();
        ConfigMetaExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotEmpty(command.getKey())) {
            criteria.andNameEqualTo(command.getKey());
        }
        if (command.getBssId() != null) {
            criteria.andBssIdEqualTo(command.getBssId());
        }
        return example;
    }

    /**
     * @param commands 配置项列表
     * @return 1--成功,0--失败
     */
    public int batchMerge(List<ConfigCommand> commands) {
        if (CollectionUtils.isEmpty(commands)) {
            return 0;
        }
        Map<Long, List<ConfigCommand>> configMap = Maps.newConcurrentMap();
        for (ConfigCommand command : commands) {
            if (command.getId() != null) {
                List<ConfigCommand> commandList = configMap.get(command.getBssId());
                if (CollectionUtils.isEmpty(commandList)) {
                    commandList = Lists.newArrayList();
                    configMap.put(command.getBssId(), commandList);
                }
                commandList.add(command);
            }
        }
        batchMergeByBss(configMap);
        return 1;
    }

    public void batchMergeByBss(Map<Long, List<ConfigCommand>> configMap) {
        for (Map.Entry<Long, List<ConfigCommand>> entry : configMap.entrySet()) {
            Long bssId = entry.getKey();
            ConfigHistoryMetaExample example = new ConfigHistoryMetaExample();
            example.setLimit(1);
            example.setOffset(0);
            example.setOrderByClause("version desc");
            example.createCriteria().andBssIdEqualTo(bssId);
            List<ConfigHistoryMeta> metas = configHistoryMetaMapper.selectByExample(example);
            long version;
            if (CollectionUtils.isNotEmpty(metas)) {
                ConfigHistoryMeta meta = metas.get(0);
                version = meta.getVersion();
            } else {
                version = 0;
            }

            List<ConfigCommand> value = entry.getValue();
            for (ConfigCommand command : value) {
                ConfigMeta meta = ConfigMapper.INSTANCE.getConfigMeta(command);
                meta.setVersion(version);
                ConfigHistoryMeta history = MetaMapper.INSTANCE.getHistoryMeta(meta);

                configHistoryMetaMapper.insert(history);
                if (configMetaMapper.selectByPrimaryKey(meta.getId()) == null) {
                    configMetaMapper.insert(meta);
                } else {
                    configMetaMapper.updateByPrimaryKeySelective(meta);
                }
            }
        }
    }

    public ServerResponse listConfigsV2(long bssId, int limit, int offset, String order) {
        ServerResponse response = new ServerResponse();
        List<ConfigMeta> list = getConfigMetas(bssId, limit, offset, order);
        List<ConfigVoV2> result = ConfigMapper.INSTANCE.getConfigVosV2(list);
        fillCondition(result);
        int count = countAll(bssId);
        Page<ConfigVoV2> page = new Page<>();
        page.setTotalSize(count);
        page.setList(result);
        return response.state(BusinessStatus.SUCCESS).data(page);
    }

    private void fillCondition(List<ConfigVoV2> result) {
        for (ConfigVoV2 vo : result) {
            fillCondition(vo);
        }
    }

    private void fillCondition(ConfigVoV2 vo) {
        long id = vo.getId();
        List<ConditionMeta> conditionMetas = conditionService.getConditionMetasByConfigId(id);
        List<ConditionVo> conditionVos = ConditionMapper.INSTANCE.getVos(conditionMetas);
        fillFilter(conditionVos);
        vo.setConditions(conditionVos);
    }

    private void fillFilter(List<ConditionVo> conditionVos) {
        if (CollectionUtils.isEmpty(conditionVos)) {
            return;
        }
        for (ConditionVo condition : conditionVos) {
            Long id = condition.getId();
            FilterMetaExample example = new FilterMetaExample();
            example.createCriteria().andBelongIdEqualTo(id)
                    .andBelongTypeEqualTo(FilterBelongType.CONDITION.getValue())
                    .andValidEqualTo(ValidStatus.VALID.getValue());
            List<FilterMeta> filterMetas = filterMetaMapper.selectByExample(example);
            List<FilterVo> filters = FilterMapper.INSTANCE.getFilterVo(filterMetas);
            condition.setFilters(filters);
        }
    }

    private List<ConfigMeta> getConfigMetas(long bssId, int limit, int offset, String order) {
        return this.getConfigMetas(bssId, ValidStatus.VALID, null, null, limit, offset, order);
    }

    private List<ConfigMeta> getConfigMetas(long bssId, ValidStatus status, Long configId, String name, int limit, int offset, String order) {
        ConfigMetaExample example = new ConfigMetaExample();
        ConfigMetaExample.Criteria criteria = example.createCriteria();
        if (configId != null && configId != 0) {
            criteria.andIdEqualTo(configId);
        }
        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike(name);
        }
        if (limit != 0) {
            example.setLimit(limit);
            example.setOffset(offset);
        }
        if (StringUtils.isNotEmpty(order)) {
            example.setOrderByClause(order);
        }
        criteria.andBssIdEqualTo(bssId).andValidEqualTo(status.getValue());
        return configMetaMapper.selectByExample(example);
    }

    public Pager searchConfig(String gridPager) {
        Pager pager = JsonUtils.fromJson(gridPager, Pager.class);
        Map<String, Object> params = pager.getParameters();
        Long bssId = NumberUtils.toLong((String) params.get("bssid"), 0);
        Long configId = NumberUtils.toLong((String) params.get("config_id"));
        String name = (String) params.get("name");
        Byte state = NumberUtils.toByte((String) params.get("state"), ValidStatus.VALID.getValue());

        int pageSize = pager.getPageSize();
        int startRecord = pager.getStartRecord();
        try {
            List<ConfigMeta> list = getConfigMetas(bssId, ValidStatus.find(state), configId, name, pageSize, startRecord, DEFAULT_ORDER);
            List<ConfigVoV2> result = ConfigMapper.INSTANCE.getConfigVosV2(list);
            fillCondition(result);
            int count = countAll(bssId);
            pager.setRecordCount(count);
            int pageCount = count / pageSize + (count % pageSize > 0 ? 1 : 0);
            pager.setPageCount(pageCount);
            pager.setExhibitDatas(BeanUtils.batchConvertBean(result));
            pager.setIsSuccess(true);
        } catch (Exception e) {
            pager.setIsSuccess(false);
            LOGGER.error("searchBss has an error:{}", e);
        }
        return pager;
    }
}

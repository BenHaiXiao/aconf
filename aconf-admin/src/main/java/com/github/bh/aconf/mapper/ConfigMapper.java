package com.github.bh.aconf.mapper;

import com.github.bh.aconf.domain.command.ConfigCommand;
import com.github.bh.aconf.domain.command.ConfigCommandV2;
import com.github.bh.aconf.domain.vo.ConfigVo;
import com.github.bh.aconf.domain.vo.ConfigVoV2;
import com.github.bh.aconf.persist.base.model.ConfigMeta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author xiaobenhai
 * Date: 2016/11/3
 * Time: 16:16
 * 配置项映射对象
 * 详情见<a href="http://mapstruct.org/">MapStruct</a>.
 */
@Mapper(uses = {CreatorMapper.class, ValueTypeMapper.class})
public interface ConfigMapper {
    ConfigMapper INSTANCE = Mappers.getMapper(ConfigMapper.class);

    @Mappings({
            @Mapping(target = "key", source = "name"),
            @Mapping(target = "updateTime", source = "createTime")
    })
    ConfigVo getConfigVo(ConfigMeta meta);

    @Mappings({
            @Mapping(target = "key", source = "name"),
            @Mapping(target = "updateTime", source = "createTime"),
            @Mapping(target = "creator", source = "creator", qualifiedBy = {CreatorMapper.CreatorTranslator.class, CreatorMapper.Creator.class})
    })
    ConfigVoV2 getConfigVoV2(ConfigMeta meta);

    /**
     * 获取ConfigMeta对象
     *
     * @param command 注意ConfigCommand中没有version，需要手动指定
     * @return
     */
    @Mappings({
            @Mapping(target = "createTime", expression = "java(new java.util.Date())"),
            @Mapping(target = "name", source = "key"),
            @Mapping(target = "valid", constant = "1"),
            @Mapping(target = "value", source = "value", qualifiedBy = {ValueTypeMapper.ValueTranslator.class, ValueTypeMapper.Value.class}),
            @Mapping(target = "valueType", source = "value", qualifiedBy = {ValueTypeMapper.TypeTranslator.class, ValueTypeMapper.Value.class})
    })
    ConfigMeta getConfigMeta(ConfigCommand command);

    List<ConfigVo> getConfigVos(List<ConfigMeta> list);

    List<ConfigVoV2> getConfigVosV2(List<ConfigMeta> list);

    @Mappings({
            @Mapping(target = "name", source = "key"),
            @Mapping(target = "value", source = "value", qualifiedBy = {ValueTypeMapper.ValueTranslator.class, ValueTypeMapper.Value.class}),
            @Mapping(target = "valueType", source = "value", qualifiedBy = {ValueTypeMapper.TypeTranslator.class, ValueTypeMapper.Value.class})
    })
    ConfigMeta getConfigMeta(ConfigCommandV2 config);
}

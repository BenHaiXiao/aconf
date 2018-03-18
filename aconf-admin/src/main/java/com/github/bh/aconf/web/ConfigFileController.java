package com.github.bh.aconf.web;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.github.bh.aconf.domain.vo.ConfigVoV2;
import com.github.bh.aconf.domain.vo.FilterVo;
import com.github.bh.aconf.utils.MDStrBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.bh.aconf.domain.vo.ConditionVo;
import com.github.bh.aconf.service.ConfigService;
import com.github.bh.aconf.utils.JsonFormatUtils;

/**
 * 业务配置项文件传输控制器。
 * Created by Shildon on 2017/3/24.
 */
@Controller
@RequestMapping("/")
public class ConfigFileController {

    @Autowired
    private ConfigService configService;

    public static final Logger LOGGER = LoggerFactory.getLogger(ConfigFileController.class);

    /**
     * 下载配置项文件
     * @param configId 配置项id
     * @return
     */
    @RequestMapping(value = "configfile/download/{configId}")
    public ResponseEntity<byte[]> downloadConfigFile(@PathVariable("configId") long configId) {
        Object obj = configService.getConfig(configId);
        if (null != obj) {
            ConfigVoV2 config = (ConfigVoV2) configService.getConfig(configId).getData();
            String fileName = null;
            try {
                fileName = new String((config.getKey() + ".md").getBytes("utf-8"), "iso8859-1");
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                httpHeaders.setContentDispositionFormData("attachment", fileName);
                String content = parse(config);
                byte[] buf = content.getBytes();
                return new ResponseEntity<>(buf, httpHeaders, HttpStatus.CREATED);
            } catch (UnsupportedEncodingException e) {
                LOGGER.error("encode filename error! filename={}", fileName);
            }
        }
        return null;
    }

    /**
     * 处理业务配置项，转化成MD格式的字符串
     * @param config 配置项
     * @return MD格式字符串
     */
    private String parse(ConfigVoV2 config) {
        String cfgKey = config.getKey();
        String cfgValue = JsonFormatUtils.format(config.getValue());
        List<ConditionVo> conditions = config.getConditions();
        MDStrBuilder builder = MDStrBuilder.newBuilder().h1(cfgKey).codeBlock(cfgValue);
        for (ConditionVo condition : conditions) {
            String cdtName = condition.getName();
            String cdtValue = JsonFormatUtils.format(condition.getValue());
            builder.unorderedList(cdtName);
            List<String> filters = new ArrayList<String>();
            for(FilterVo f: condition.getFilters()){
            	filters.add(f.getBasis()+" "+f.getOperator()+" "+f.getBoundary());
            }
            builder.codeBlocks(filters);
            builder.quotingBlock(cdtValue);
        }
        return builder.build();
    }

}

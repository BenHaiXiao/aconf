package com.github.bh.aconf.service.utils;

import com.github.bh.aconf.persist.base.model.ConfigMeta;

/**
 * @author xiaobenhai
 * Date: 2017/1/12
 * Time: 11:41
 */
public final class ConfigUtils {

    private ConfigUtils() {
        //no-op
    }

    public static ConfigMeta createConfigMetaWithValue(ConfigMeta meta, String value, Byte valueType) {
        ConfigMeta newMeta = new ConfigMeta();
        newMeta.setId(meta.getId());
        newMeta.setBssId(meta.getBssId());
        newMeta.setCreator(meta.getCreator());
        newMeta.setName(meta.getName());
        newMeta.setCreateTime(meta.getCreateTime());
        newMeta.setDescription(meta.getDescription());
        newMeta.setValid(meta.getValid());
        newMeta.setVersion(meta.getVersion());
        newMeta.setSendDefault(meta.getSendDefault());
        newMeta.setValue(value);
        newMeta.setValueType(valueType);
        return newMeta;
    }

}

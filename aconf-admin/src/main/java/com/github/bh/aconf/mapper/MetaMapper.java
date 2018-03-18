package com.github.bh.aconf.mapper;

import com.github.bh.aconf.persist.base.model.ConfigHistoryMeta;
import com.github.bh.aconf.persist.base.model.ConfigMeta;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author xiaobenhai
 * Date: 2016/11/10
 * Time: 16:21
 */
@Mapper
public interface MetaMapper {
    MetaMapper INSTANCE = Mappers.getMapper(MetaMapper.class);

    ConfigMeta getConfigMeta(ConfigHistoryMeta meta);

    List<ConfigMeta> getConfigMetaList(List<ConfigHistoryMeta> meta);

    ConfigHistoryMeta getHistoryMeta(ConfigMeta meta);

    List<ConfigHistoryMeta> getHistoryMetaList(List<ConfigMeta> meta);
}

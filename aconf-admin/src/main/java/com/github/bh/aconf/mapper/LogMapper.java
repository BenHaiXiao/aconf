package com.github.bh.aconf.mapper;

import com.github.bh.aconf.domain.vo.LogVo;
import com.github.bh.aconf.persist.base.model.OperationLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author xiaobenhai
 * Date: 2017/3/1
 * Time: 15:36
 */
@Mapper(uses = {CreatorMapper.class})
public interface LogMapper {
    LogMapper INSTANCE = Mappers.getMapper(LogMapper.class);

    @Mappings({
            @Mapping(target = "face", source = "type"),
            @Mapping(target = "creator", source = "passport", qualifiedBy = {CreatorMapper.CreatorTranslator.class, CreatorMapper.Creator.class})
    })
    LogVo getLogVo(OperationLog meta);

    List<LogVo> getLogVoList(List<OperationLog> metas);
}

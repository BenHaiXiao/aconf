package com.github.bh.aconf.mapper;

import com.github.bh.aconf.domain.command.PushCommand;
import com.github.bh.aconf.domain.vo.PushDetailVo;
import com.github.bh.aconf.domain.vo.PushVo;
import com.github.bh.aconf.persist.base.model.PushMeta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author xiaobenhai
 * Date: 2016/11/16
 * Time: 15:35
 */
@Mapper(uses = CreatorMapper.class)
public interface PushMapper {
    PushMapper INSTANCE = Mappers.getMapper(PushMapper.class);

    PushVo getPushVo(PushMeta meta);

    List<PushVo> getPushVo(List<PushMeta> metas);

    @Mapping(target = "creator", source = "creator", qualifiedBy = {CreatorMapper.CreatorTranslator.class, CreatorMapper.Creator.class})
    PushDetailVo getPushDetailVo(PushMeta meta);

    List<PushDetailVo> getPushDetailVo(List<PushMeta> metas);

    @Mappings({
            @Mapping(target = "createTime", expression = "java(new java.util.Date())"),
            @Mapping(target = "updateTime", expression = "java(new java.util.Date())"),
            @Mapping(target = "effectiveTime", expression = "java(new java.util.Date(command.getEffectiveTime()))")
    })
    PushMeta getPushMetaForCreate(PushCommand command);

    @Mappings({
            @Mapping(target = "createTime", ignore = true),
            @Mapping(target = "updateTime", expression = "java(new java.util.Date())"),
            @Mapping(target = "effectiveTime", expression = "java(new java.util.Date(command.getEffectiveTime()))")
    })
    PushMeta getPushMetaForUpdate(PushCommand command);
}

package com.github.bh.aconf.mapper;

import com.github.bh.aconf.domain.command.FilterCommand;
import com.github.bh.aconf.domain.command.FilterCommandV2;
import com.github.bh.aconf.domain.vo.FilterVo;
import com.github.bh.aconf.persist.base.model.FilterMeta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author xiaobenhai
 * Date: 2016/11/15
 * Time: 17:15
 */
@Mapper(uses = CreatorMapper.class)
public interface FilterMapper {
    FilterMapper INSTANCE = Mappers.getMapper(FilterMapper.class);

    @Mappings({
            @Mapping(target = "creator", source = "creator", qualifiedBy = {CreatorMapper.CreatorTranslator.class, CreatorMapper.Creator.class}),
            @Mapping(target = "type", source = "basisType")
    })
    FilterVo getFilterVo(FilterMeta meta);

    List<FilterVo> getFilterVo(List<FilterMeta> metas);

    @Mappings({
            @Mapping(target = "createTime", expression = "java(new java.util.Date())"),
            @Mapping(target = "updateTime", expression = "java(new java.util.Date())"),
            @Mapping(target = "valid", constant = "1")
    })
    FilterMeta getFilterMetaForCreate(FilterCommand command);

    @Mappings({
            @Mapping(target = "updateTime", expression = "java(new java.util.Date())"),
            @Mapping(target = "valid", constant = "1")
    })
    FilterMeta getFilterMetaForUpdate(FilterCommand command);

    @Mapping(target = "basisType", source = "type")
    FilterMeta getFilterMeta(FilterCommandV2 filter);

    @Mappings({
            @Mapping(target = "createTime", expression = "java(new java.util.Date())"),
            @Mapping(target = "updateTime", expression = "java(new java.util.Date())"),
            @Mapping(target = "valid", constant = "1")
    })
    FilterMeta getFilterMetaForCreate(FilterCommandV2 filterCommandV2);

    @Mappings({
            @Mapping(target = "updateTime", expression = "java(new java.util.Date())"),
    })
    FilterMeta getFilterMetaForUpdate(FilterCommandV2 filterCommandV2);
}

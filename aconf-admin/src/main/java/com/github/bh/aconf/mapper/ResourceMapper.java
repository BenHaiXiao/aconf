package com.github.bh.aconf.mapper;

import com.github.bh.aconf.domain.vo.ResourceVo;
import com.github.bh.aconf.persist.base.model.ResourceMeta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author xiaobenhai
 * Date: 2017/3/10
 * Time: 17:42
 */
@Mapper(uses = {CreatorMapper.class})
public interface ResourceMapper {
    ResourceMapper INSTANCE = Mappers.getMapper(ResourceMapper.class);

    @Mapping(target = "creator", source = "creator", qualifiedBy = {CreatorMapper.CreatorTranslator.class, CreatorMapper.Creator.class})
    ResourceVo getVo(ResourceMeta meta);

    List<ResourceVo> getVoList(List<ResourceMeta> metas);
}

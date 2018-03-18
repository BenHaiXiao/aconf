package com.github.bh.aconf.mapper;

import com.github.bh.aconf.domain.command.ConditionCommandV2;
import com.github.bh.aconf.domain.command.ConditionCommand;
import com.github.bh.aconf.domain.vo.ConditionVo;
import com.github.bh.aconf.persist.base.model.ConditionMeta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author xiaobenhai
 * Date: 2017/1/9
 * Time: 17:36
 */
@Mapper(uses = {ValueTypeMapper.class})
public interface ConditionMapper {
    ConditionMapper INSTANCE = Mappers.getMapper(ConditionMapper.class);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createTime", expression = "java(new java.util.Date())"),
            @Mapping(target = "updateTime", expression = "java(new java.util.Date())"),
            @Mapping(target = "state", constant = "101"),
            @Mapping(target = "configId", source = "configId"),
            @Mapping(target = "value", source = "command.value", qualifiedBy = {ValueTypeMapper.ValueTranslator.class, ValueTypeMapper.Value.class}),
            @Mapping(target = "valueType", source = "command.value", qualifiedBy = {ValueTypeMapper.TypeTranslator.class, ValueTypeMapper.Value.class})
    })
    ConditionMeta getMetaForCreate(Long configId, ConditionCommand command);

    ConditionVo getVo(ConditionMeta meta);

    List<ConditionVo> getVos(List<ConditionMeta> metas);

    @Mappings({
            @Mapping(target = "id", source = "conditionId"),
            @Mapping(target = "updateTime", expression = "java(new java.util.Date())"),
            @Mapping(target = "state", ignore = true),
            @Mapping(target = "configId", ignore = true),
            @Mapping(target = "value", source = "command.value", qualifiedBy = {ValueTypeMapper.ValueTranslator.class, ValueTypeMapper.Value.class}),
            @Mapping(target = "valueType", source = "command.value", qualifiedBy = {ValueTypeMapper.TypeTranslator.class, ValueTypeMapper.Value.class})
    })
    ConditionMeta getMetaForUpdate(Long conditionId, ConditionCommand command);

    @Mappings({
            @Mapping(target = "state", ignore = true),
            @Mapping(target = "configId", ignore = true),
            @Mapping(target = "value", source = "value", qualifiedBy = {ValueTypeMapper.ValueTranslator.class, ValueTypeMapper.Value.class}),
            @Mapping(target = "valueType", source = "value", qualifiedBy = {ValueTypeMapper.TypeTranslator.class, ValueTypeMapper.Value.class})
    })
    ConditionMeta getMeta(ConditionCommandV2 condition);
}

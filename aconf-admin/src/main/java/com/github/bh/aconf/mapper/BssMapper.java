package com.github.bh.aconf.mapper;

import com.github.bh.aconf.domain.command.BssCodeCommand;
import com.github.bh.aconf.domain.command.BssCommand;
import com.github.bh.aconf.domain.vo.BssVo;
import com.github.bh.aconf.persist.base.model.BssArchivedMeta;
import com.github.bh.aconf.persist.base.model.BssMeta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author xiaobenhai
 * Date: 2016/11/3
 * Time: 14:32
 * bss映射对象
 * 详情见<a href="http://mapstruct.org/">MapStruct</a>.
 */
@Mapper
public interface BssMapper {
    BssMapper INSTANCE = Mappers.getMapper(BssMapper.class);

    @Mappings({
            @Mapping(target = "createTime", expression = "java(new java.util.Date())"),
            @Mapping(target = "updateTime", expression = "java(new java.util.Date())"),
            @Mapping(target = "effectiveTime", expression = "java(com.github.bh.aconf.utils.DateUtils.parse(command.getEffectiveTime()))"),
            @Mapping(target = "failureTime", expression = "java(com.github.bh.aconf.utils.DateUtils.parse(command.getFailureTime()))"),
            @Mapping(target = "version", constant = "1"),
            @Mapping(target = "valid", defaultValue = "1")
    })
    BssMeta getBssMetaForCreate(BssCommand command);

    // FIXME: 2017/2/28 暂时去掉了起止时间，后续完善
    @Mappings({
            @Mapping(target = "updateTime", expression = "java(new java.util.Date())"),
            @Mapping(target = "effectiveTime", ignore = true),
            @Mapping(target = "failureTime", ignore = true),
            @Mapping(target = "code", ignore = true)
    })
    BssMeta getBssMetaForUpdate(BssCommand command);

    @Mappings({
            @Mapping(target = "effectiveTime", expression = "java(com.github.bh.aconf.utils.DateUtils.format(bssMeta.getEffectiveTime()))"),
            @Mapping(target = "failureTime", expression = "java(com.github.bh.aconf.utils.DateUtils.format(bssMeta.getFailureTime()))")
    })
    BssVo getBssVo(BssMeta bssMeta);

    List<BssVo> getBssVos(List<BssMeta> list);

    BssArchivedMeta getArchivedMeta(BssMeta bss);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "appId", ignore = true),
            @Mapping(target = "valid", constant = "1")
    })
    BssCommand getBssCommand(BssCodeCommand command);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "updateTime", expression = "java(new java.util.Date())"),
            @Mapping(target = "effectiveTime", expression = "java(new java.util.Date(command.getEffectiveTime()))"),
            @Mapping(target = "failureTime", expression = "java(new java.util.Date(command.getFailureTime()))"),
    })
    BssMeta getBssMeta(BssCodeCommand command);

    BssVo getBssVo(BssArchivedMeta meta);

    List<BssVo> getBssVosByArchivedMetas(List<BssArchivedMeta> metas);

    BssMeta getBssMetaByArchived(BssArchivedMeta delMeta);

    List<BssVo> getBssMetaByArchived(List<BssArchivedMeta> delMetas);
}

package com.github.bh.aconf.mapper;

import com.github.bh.aconf.domain.command.AppCommand;
import com.github.bh.aconf.domain.vo.AppVo;
import com.github.bh.aconf.domain.vo.AppPageVo;
import com.github.bh.aconf.persist.base.model.AppMeta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author xiaobenhai
 * Date: 2016/11/9
 * Time: 19:52
 */
@Mapper
public interface AppMapper {
    AppMapper INSTANCE = Mappers.getMapper(AppMapper.class);

    @Mappings({
            @Mapping(target = "createTime", expression = "java(new java.util.Date())"),
            @Mapping(target = "updateTime", expression = "java(new java.util.Date())")
    })
    AppMeta getAppMetaForCreate(AppCommand appCommand);

    @Mappings({
            @Mapping(target = "updateTime", expression = "java(new java.util.Date())")
    })
    AppMeta getAppMetaForUpdate(AppCommand appCommand);

    AppVo getAppVo(AppMeta appMeta);

    List<AppVo> getAppVoList(List<AppMeta> appMetas);

    AppPageVo getAppPageVo(AppMeta app, int bssNum);
}

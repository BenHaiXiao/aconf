package com.github.bh.aconf.mapper;

import com.github.bh.aconf.domain.command.AppRegisterCommand;
import com.github.bh.aconf.persist.base.model.AppRegisterMeta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author xiaobenhai
 * Date: 2016/12/28
 * Time: 10:05
 */
@Mapper
public interface AppRegisterMapper {
    AppRegisterMapper INSTANCE = Mappers.getMapper(AppRegisterMapper.class);

    @Mappings({
            @Mapping(target = "rnum", expression = "java(org.apache.commons.lang3.RandomUtils.nextInt(0, 9))"),
            @Mapping(target = "valid", constant = "1")
    })
    AppRegisterMeta getMeta(AppRegisterCommand command);
}

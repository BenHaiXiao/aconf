package com.github.bh.aconf.mapper;

import com.github.bh.aconf.domain.StaffInfo;
import com.github.bh.aconf.domain.command.AdminUserCommand;
import com.github.bh.aconf.domain.vo.UserVo;
import com.github.bh.aconf.persist.base.model.UserAuthMeta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author xiaobenhai
 * Date: 2017/2/9
 * Time: 12:58
 */
@Mapper(uses = {UserRoleMapper.class})
public interface StaffInfoMapper {
    StaffInfoMapper INSTANCE = Mappers.getMapper(StaffInfoMapper.class);

    @Mappings({
            @Mapping(target = "nickName", source = "name"),
            @Mapping(target = "realName", source = "name"),
            @Mapping(target = "deptName", ignore = true),
            @Mapping(target = "role", expression = "java(com.github.bh.aconf.constants.UserRole.find(userAuthMeta.getRole()).roleName())"),
            @Mapping(target = "userId", source = "id")
    })
    StaffInfo getStaffInfo(UserAuthMeta userAuthMeta);


    @Mappings({
            @Mapping(target = "name", source = "realName"),
            @Mapping(target = "role", ignore = true),
            @Mapping(target = "telephone", ignore = true)
    })
    UserAuthMeta getUserAuthMeta(StaffInfo staffInfo);

    @Mappings({
            @Mapping(target = "role", source = "role", qualifiedBy = {UserRoleMapper.RoleTranslator.class, UserRoleMapper.Role.class})
    })
    UserVo getUserVo(UserAuthMeta meta);

    List<UserVo> getUserVoList(List<UserAuthMeta> metas);

    @Mappings({
            @Mapping(target = "role", source = "role", qualifiedBy = {UserRoleMapper.RoleTranslator.class, UserRoleMapper.RoleMeta.class}),
            @Mapping(target = "passport", ignore = true)
    })
    UserAuthMeta getUserAuthMeta(AdminUserCommand command);
}

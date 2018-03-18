package com.github.bh.aconf.mapper;

import com.github.bh.aconf.constants.UserRole;
import org.mapstruct.Mapper;
import org.mapstruct.Qualifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author xiaobenhai
 * Date: 2017/2/20
 * Time: 11:48
 */
@Mapper
public class UserRoleMapper {
    @Role
    public Integer convertRoles(String role) {
        UserRole userRole = UserRole.find(role);
        return userRole.code();
    }

    @RoleMeta
    public String getRole(Integer roleCode) {
        UserRole userRole = UserRole.find(roleCode);
        if(UserRole.CREATOR.role().equalsIgnoreCase(userRole.role())){
        	return userRole.role()+","+UserRole.USER.role(); 
        }
        return userRole.role();
    }

    @Qualifier
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.SOURCE)
    public @interface RoleTranslator {
    }

    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    public @interface Role {
    }

    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    public @interface RoleMeta {
    }
}

package com.github.bh.aconf.mapper;

import com.github.bh.aconf.domain.StaffInfo;
import com.github.bh.aconf.domain.vo.CreatorVo;
import com.github.bh.aconf.utils.AuthUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Qualifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author xiaobenhai
 * Date: 2017/2/9
 * Time: 15:39
 */
@Mapper
public class CreatorMapper {
    @Creator
    public CreatorVo getRealName(String passport) {
        CreatorVo vo = new CreatorVo();
        if (passport == null || !passport.startsWith("dw_")) {
            vo.setName(passport);
            return vo;
        }
        StaffInfo user = AuthUtils.getAdminUser(passport);
        vo.setName(user.getRealName());
        vo.setAvatar(user.getAvatar());
        vo.setEmail(user.getEmail());
        vo.setPhone(user.getTelephone());
        return vo;
    }

    @Qualifier
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.SOURCE)
    public @interface CreatorTranslator {
    }

    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    public @interface Creator {
    }
}

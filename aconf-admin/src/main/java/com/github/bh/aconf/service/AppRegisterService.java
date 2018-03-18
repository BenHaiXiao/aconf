package com.github.bh.aconf.service;

import com.github.bh.aconf.constants.BusinessStatus;
import com.github.bh.aconf.domain.ServerResponse;
import com.github.bh.aconf.domain.command.AppRegisterCommand;
import com.github.bh.aconf.domain.vo.AppRegisterVo;
import com.github.bh.aconf.mapper.AppRegisterMapper;
import com.github.bh.aconf.persist.base.mapper.AppRegisterMetaMapper;
import com.github.bh.aconf.persist.base.model.AppRegisterMeta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xiaobenhai
 * Date: 2016/12/28
 * Time: 9:51
 */
@Service
public class AppRegisterService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppRegisterService.class);
    @Autowired
    private AppRegisterMetaMapper registerMapper;
    /**
     * 注册方法
     *
     * @param command
     * @return
     */
    public ServerResponse register(AppRegisterCommand command) {
        ServerResponse response = new ServerResponse();
        AppRegisterMeta meta = AppRegisterMapper.INSTANCE.getMeta(command);
        registerMapper.insert(meta);
        try {
            AppRegisterVo vo = new AppRegisterVo();
            vo.setAppid(meta.getId());
            vo.setName(meta.getName());
            response.state(BusinessStatus.SUCCESS).data(vo);
        } catch (Exception e) {
            LOGGER.error("can not register new app, command={}", command, e);
            response.state(BusinessStatus.APP_REGISTER_ERROR);
        }
        return response;
    }

    /**
     * 验证方法
     *
     * @param appId
     * @param encrypt
     * @return
     */
    public boolean verify(Long appId, String encrypt) {
        try {
            return true;
        } catch (Exception e) {
            LOGGER.error("can not verify appToken, appid={}, encrypt={}", appId, encrypt, e);
        }
        return false;
    }
}

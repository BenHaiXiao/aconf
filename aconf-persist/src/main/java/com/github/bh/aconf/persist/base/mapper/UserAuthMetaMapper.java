package com.github.bh.aconf.persist.base.mapper;

import com.github.bh.aconf.persist.base.model.UserAuthMeta;
import com.github.bh.aconf.persist.base.model.UserAuthMetaExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserAuthMetaMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_auth
     *
     * @mbggenerated
     */
    int countByExample(UserAuthMetaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_auth
     *
     * @mbggenerated
     */
    int deleteByExample(UserAuthMetaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_auth
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_auth
     *
     * @mbggenerated
     */
    int insert(UserAuthMeta record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_auth
     *
     * @mbggenerated
     */
    int insertSelective(UserAuthMeta record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_auth
     *
     * @mbggenerated
     */
    List<UserAuthMeta> selectByExample(UserAuthMetaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_auth
     *
     * @mbggenerated
     */
    UserAuthMeta selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_auth
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") UserAuthMeta record, @Param("example") UserAuthMetaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_auth
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") UserAuthMeta record, @Param("example") UserAuthMetaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_auth
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(UserAuthMeta record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_auth
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(UserAuthMeta record);
}
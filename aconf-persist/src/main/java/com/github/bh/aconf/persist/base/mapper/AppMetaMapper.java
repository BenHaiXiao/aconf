package com.github.bh.aconf.persist.base.mapper;

import com.github.bh.aconf.persist.base.model.AppMeta;
import com.github.bh.aconf.persist.base.model.AppMetaExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AppMetaMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app
     *
     * @mbggenerated
     */
    int countByExample(AppMetaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app
     *
     * @mbggenerated
     */
    int deleteByExample(AppMetaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app
     *
     * @mbggenerated
     */
    int insert(AppMeta record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app
     *
     * @mbggenerated
     */
    int insertSelective(AppMeta record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app
     *
     * @mbggenerated
     */
    List<AppMeta> selectByExample(AppMetaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app
     *
     * @mbggenerated
     */
    AppMeta selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") AppMeta record, @Param("example") AppMetaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") AppMeta record, @Param("example") AppMetaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(AppMeta record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(AppMeta record);
}
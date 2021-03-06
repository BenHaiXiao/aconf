package com.github.bh.aconf.persist.base.mapper;

import com.github.bh.aconf.persist.base.model.PushMeta;
import com.github.bh.aconf.persist.base.model.PushMetaExample;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface PushMetaMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table push
     *
     * @mbggenerated
     */
    int countByExample(PushMetaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table push
     *
     * @mbggenerated
     */
    int deleteByExample(PushMetaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table push
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table push
     *
     * @mbggenerated
     */
    int insert(PushMeta record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table push
     *
     * @mbggenerated
     */
    int insertSelective(PushMeta record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table push
     *
     * @mbggenerated
     */
    List<PushMeta> selectByExample(PushMetaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table push
     *
     * @mbggenerated
     */
    PushMeta selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table push
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") PushMeta record, @Param("example") PushMetaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table push
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") PushMeta record, @Param("example") PushMetaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table push
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(PushMeta record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table push
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(PushMeta record);
}
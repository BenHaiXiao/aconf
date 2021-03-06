package com.github.bh.aconf.persist.base.mapper;

import com.github.bh.aconf.persist.base.model.ResourceMeta;
import com.github.bh.aconf.persist.base.model.ResourceMetaExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ResourceMetaMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table resource
     *
     * @mbggenerated
     */
    int countByExample(ResourceMetaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table resource
     *
     * @mbggenerated
     */
    int deleteByExample(ResourceMetaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table resource
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table resource
     *
     * @mbggenerated
     */
    int insert(ResourceMeta record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table resource
     *
     * @mbggenerated
     */
    int insertSelective(ResourceMeta record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table resource
     *
     * @mbggenerated
     */
    List<ResourceMeta> selectByExample(ResourceMetaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table resource
     *
     * @mbggenerated
     */
    ResourceMeta selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table resource
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") ResourceMeta record, @Param("example") ResourceMetaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table resource
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") ResourceMeta record, @Param("example") ResourceMetaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table resource
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(ResourceMeta record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table resource
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(ResourceMeta record);
}
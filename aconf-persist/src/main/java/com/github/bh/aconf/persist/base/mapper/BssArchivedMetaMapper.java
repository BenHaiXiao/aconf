package com.github.bh.aconf.persist.base.mapper;

import com.github.bh.aconf.persist.base.model.BssArchivedMeta;
import com.github.bh.aconf.persist.base.model.BssArchivedMetaExample;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface BssArchivedMetaMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bss_archived
     *
     * @mbggenerated
     */
    int countByExample(BssArchivedMetaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bss_archived
     *
     * @mbggenerated
     */
    int deleteByExample(BssArchivedMetaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bss_archived
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bss_archived
     *
     * @mbggenerated
     */
    int insert(BssArchivedMeta record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bss_archived
     *
     * @mbggenerated
     */
    int insertSelective(BssArchivedMeta record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bss_archived
     *
     * @mbggenerated
     */
    List<BssArchivedMeta> selectByExample(BssArchivedMetaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bss_archived
     *
     * @mbggenerated
     */
    BssArchivedMeta selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bss_archived
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") BssArchivedMeta record, @Param("example") BssArchivedMetaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bss_archived
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") BssArchivedMeta record, @Param("example") BssArchivedMetaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bss_archived
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(BssArchivedMeta record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bss_archived
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(BssArchivedMeta record);
}
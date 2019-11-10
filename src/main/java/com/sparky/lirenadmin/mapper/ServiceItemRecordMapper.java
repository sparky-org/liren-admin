package com.sparky.lirenadmin.mapper;

import com.sparky.lirenadmin.entity.ServiceItemRecord;
import com.sparky.lirenadmin.entity.ServiceItemRecordExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ServiceItemRecordMapper {
    long countByExample(ServiceItemRecordExample example);

    int deleteByExample(ServiceItemRecordExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ServiceItemRecord record);

    int insertSelective(ServiceItemRecord record);

    List<ServiceItemRecord> selectByExample(ServiceItemRecordExample example);

    ServiceItemRecord selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ServiceItemRecord record, @Param("example") ServiceItemRecordExample example);

    int updateByExample(@Param("record") ServiceItemRecord record, @Param("example") ServiceItemRecordExample example);

    int updateByPrimaryKeySelective(ServiceItemRecord record);

    int updateByPrimaryKey(ServiceItemRecord record);
}
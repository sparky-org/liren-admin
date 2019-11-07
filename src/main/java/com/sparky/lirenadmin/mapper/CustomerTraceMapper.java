package com.sparky.lirenadmin.mapper;

import com.sparky.lirenadmin.entity.CustomerTrace;
import com.sparky.lirenadmin.entity.CustomerTraceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CustomerTraceMapper {
    long countByExample(CustomerTraceExample example);

    int deleteByExample(CustomerTraceExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CustomerTrace record);

    int insertSelective(CustomerTrace record);

    List<CustomerTrace> selectByExample(CustomerTraceExample example);

    CustomerTrace selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CustomerTrace record, @Param("example") CustomerTraceExample example);

    int updateByExample(@Param("record") CustomerTrace record, @Param("example") CustomerTraceExample example);

    int updateByPrimaryKeySelective(CustomerTrace record);

    int updateByPrimaryKey(CustomerTrace record);
}
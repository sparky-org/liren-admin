package com.sparky.lirenadmin.mapper;

import com.sparky.lirenadmin.entity.ServiceItem;
import com.sparky.lirenadmin.entity.ServiceItemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceItemMapper {
    long countByExample(ServiceItemExample example);

    int deleteByExample(ServiceItemExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ServiceItem record);

    int insertSelective(ServiceItem record);

    List<ServiceItem> selectByExample(ServiceItemExample example);

    ServiceItem selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ServiceItem record, @Param("example") ServiceItemExample example);

    int updateByExample(@Param("record") ServiceItem record, @Param("example") ServiceItemExample example);

    int updateByPrimaryKeySelective(ServiceItem record);

    int updateByPrimaryKey(ServiceItem record);
}
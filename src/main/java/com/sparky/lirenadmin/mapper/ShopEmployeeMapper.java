package com.sparky.lirenadmin.mapper;

import com.sparky.lirenadmin.entity.ShopEmployee;
import com.sparky.lirenadmin.entity.ShopEmployeeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopEmployeeMapper {
    long countByExample(ShopEmployeeExample example);

    int deleteByExample(ShopEmployeeExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ShopEmployee record);

    int insertSelective(ShopEmployee record);

    List<ShopEmployee> selectByExample(ShopEmployeeExample example);

    ShopEmployee selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ShopEmployee record, @Param("example") ShopEmployeeExample example);

    int updateByExample(@Param("record") ShopEmployee record, @Param("example") ShopEmployeeExample example);

    int updateByPrimaryKeySelective(ShopEmployee record);

    int updateByPrimaryKey(ShopEmployee record);
}
package com.sparky.lirenadmin.mapper;

import com.sparky.lirenadmin.entity.ShopConfig;
import com.sparky.lirenadmin.entity.ShopConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopConfigMapper {
    long countByExample(ShopConfigExample example);

    int deleteByExample(ShopConfigExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ShopConfig record);

    int insertSelective(ShopConfig record);

    List<ShopConfig> selectByExample(ShopConfigExample example);

    ShopConfig selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ShopConfig record, @Param("example") ShopConfigExample example);

    int updateByExample(@Param("record") ShopConfig record, @Param("example") ShopConfigExample example);

    int updateByPrimaryKeySelective(ShopConfig record);

    int updateByPrimaryKey(ShopConfig record);
}
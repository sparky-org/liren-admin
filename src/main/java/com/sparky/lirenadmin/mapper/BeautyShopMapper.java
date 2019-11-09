package com.sparky.lirenadmin.mapper;

import com.sparky.lirenadmin.entity.BeautyShop;
import com.sparky.lirenadmin.entity.BeautyShopExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BeautyShopMapper {
    long countByExample(BeautyShopExample example);

    int deleteByExample(BeautyShopExample example);

    int deleteByPrimaryKey(Long id);

    int insert(BeautyShop record);

    int insertSelective(BeautyShop record);

    List<BeautyShop> selectByExample(BeautyShopExample example);

    BeautyShop selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") BeautyShop record, @Param("example") BeautyShopExample example);

    int updateByExample(@Param("record") BeautyShop record, @Param("example") BeautyShopExample example);

    int updateByPrimaryKeySelective(BeautyShop record);

    int updateByPrimaryKey(BeautyShop record);
}
package com.sparky.lirenadmin.mapper;

import com.sparky.lirenadmin.entity.ShopJob;
import com.sparky.lirenadmin.entity.ShopJobExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopJobMapper {
    long countByExample(ShopJobExample example);

    int deleteByExample(ShopJobExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ShopJob record);

    int insertSelective(ShopJob record);

    List<ShopJob> selectByExample(ShopJobExample example);

    ShopJob selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ShopJob record, @Param("example") ShopJobExample example);

    int updateByExample(@Param("record") ShopJob record, @Param("example") ShopJobExample example);

    int updateByPrimaryKeySelective(ShopJob record);

    int updateByPrimaryKey(ShopJob record);
}
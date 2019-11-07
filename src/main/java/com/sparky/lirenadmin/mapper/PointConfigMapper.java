package com.sparky.lirenadmin.mapper;

import com.sparky.lirenadmin.entity.PointConfig;
import com.sparky.lirenadmin.entity.PointConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PointConfigMapper {
    long countByExample(PointConfigExample example);

    int deleteByExample(PointConfigExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PointConfig record);

    int insertSelective(PointConfig record);

    List<PointConfig> selectByExample(PointConfigExample example);

    PointConfig selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PointConfig record, @Param("example") PointConfigExample example);

    int updateByExample(@Param("record") PointConfig record, @Param("example") PointConfigExample example);

    int updateByPrimaryKeySelective(PointConfig record);

    int updateByPrimaryKey(PointConfig record);
}
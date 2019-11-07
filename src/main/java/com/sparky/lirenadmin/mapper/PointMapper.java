package com.sparky.lirenadmin.mapper;

import com.sparky.lirenadmin.entity.Point;
import com.sparky.lirenadmin.entity.PointExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PointMapper {
    long countByExample(PointExample example);

    int deleteByExample(PointExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Point record);

    int insertSelective(Point record);

    List<Point> selectByExample(PointExample example);

    Point selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Point record, @Param("example") PointExample example);

    int updateByExample(@Param("record") Point record, @Param("example") PointExample example);

    int updateByPrimaryKeySelective(Point record);

    int updateByPrimaryKey(Point record);
}
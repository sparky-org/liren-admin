package com.sparky.lirenadmin.mapper;

import com.sparky.lirenadmin.entity.SalesPerformance;
import com.sparky.lirenadmin.entity.SalesPerformanceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesPerformanceMapper {
    long countByExample(SalesPerformanceExample example);

    int deleteByExample(SalesPerformanceExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SalesPerformance record);

    int insertSelective(SalesPerformance record);

    List<SalesPerformance> selectByExample(SalesPerformanceExample example);

    SalesPerformance selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SalesPerformance record, @Param("example") SalesPerformanceExample example);

    int updateByExample(@Param("record") SalesPerformance record, @Param("example") SalesPerformanceExample example);

    int updateByPrimaryKeySelective(SalesPerformance record);

    int updateByPrimaryKey(SalesPerformance record);
}
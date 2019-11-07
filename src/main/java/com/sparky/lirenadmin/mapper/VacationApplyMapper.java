package com.sparky.lirenadmin.mapper;

import com.sparky.lirenadmin.entity.VacationApply;
import com.sparky.lirenadmin.entity.VacationApplyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VacationApplyMapper {
    long countByExample(VacationApplyExample example);

    int deleteByExample(VacationApplyExample example);

    int deleteByPrimaryKey(Long id);

    int insert(VacationApply record);

    int insertSelective(VacationApply record);

    List<VacationApply> selectByExample(VacationApplyExample example);

    VacationApply selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") VacationApply record, @Param("example") VacationApplyExample example);

    int updateByExample(@Param("record") VacationApply record, @Param("example") VacationApplyExample example);

    int updateByPrimaryKeySelective(VacationApply record);

    int updateByPrimaryKey(VacationApply record);
}
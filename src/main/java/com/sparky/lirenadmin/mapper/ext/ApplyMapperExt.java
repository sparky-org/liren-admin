package com.sparky.lirenadmin.mapper.ext;

import com.sparky.lirenadmin.bo.cond.QueryApplyCond;
import com.sparky.lirenadmin.entity.Apply;
import com.sparky.lirenadmin.mapper.ApplyMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplyMapperExt extends ApplyMapper {
    Integer countByQueryCond(@Param("cond") QueryApplyCond cond);

    List<Apply> pagingQueryApply(@Param("cond") QueryApplyCond cond);
}
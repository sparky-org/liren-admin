package com.sparky.lirenadmin.mapper;

import com.sparky.lirenadmin.entity.ApplyDtl;
import com.sparky.lirenadmin.entity.ApplyDtlExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplyDtlMapper {
    long countByExample(ApplyDtlExample example);

    int deleteByExample(ApplyDtlExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ApplyDtl record);

    int insertSelective(ApplyDtl record);

    List<ApplyDtl> selectByExample(ApplyDtlExample example);

    ApplyDtl selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ApplyDtl record, @Param("example") ApplyDtlExample example);

    int updateByExample(@Param("record") ApplyDtl record, @Param("example") ApplyDtlExample example);

    int updateByPrimaryKeySelective(ApplyDtl record);

    int updateByPrimaryKey(ApplyDtl record);
}
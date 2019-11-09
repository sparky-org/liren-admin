package com.sparky.lirenadmin.mapper.ext;

import com.sparky.lirenadmin.entity.po.PointRankPO;
import com.sparky.lirenadmin.mapper.RewardRecordMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RewardRecordMapperExt extends RewardRecordMapper {

    List<PointRankPO> findPointRank(@Param("empNo") Long empNo, @Param("date") Date date);
}
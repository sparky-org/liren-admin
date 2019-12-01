package com.sparky.lirenadmin.mapper.ext;

import com.sparky.lirenadmin.entity.RewardRecord;
import com.sparky.lirenadmin.entity.po.PointRankPO;
import com.sparky.lirenadmin.entity.po.PointTablePO;
import com.sparky.lirenadmin.mapper.RewardRecordMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RewardRecordMapperExt extends RewardRecordMapper {

    List<PointRankPO> findPointRank(@Param("empNo") Long empNo, @Param("date") Date date);

    int countRewardRecord(String shopNo);

    List<RewardRecord> pagingQueryRewardRecord(String shopNo, int start, Integer pageSize);

    Integer countPointTable(Long empNo, Long shopNo, String interval, String beginDate, String endDate);

    List<PointTablePO> getPointTable(Long empNo, Long shopNo, String interval, String beginDate, String endDate, Integer start, Integer pageSize);

    Integer countPointDetail(Long empNo, Long shopNo, String interval, String beginDate, String endDate);

    List<RewardRecord> queryPointDetail(Long empNo, Long shopNo, String interval, String beginDate, String endDate, Integer start, Integer pageSize);
}
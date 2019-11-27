package com.sparky.lirenadmin.mapper.ext;

import com.sparky.lirenadmin.entity.DailyRecord;
import com.sparky.lirenadmin.mapper.DailyRecordMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DailyRecordMapperExt extends DailyRecordMapper {


    Integer countDailyRecord(Long reporter, Long auditor);

    List<DailyRecord> queryDailyRecord(Long reporter, Long auditor, Integer startIndex, Integer pageSize);
}
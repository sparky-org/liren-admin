package com.sparky.lirenadmin.bo;

import com.sparky.lirenadmin.entity.DailyRecord;

import java.util.List;

public interface DailyRecordBO {

    void createDailyRecord(DailyRecord dailyRecord);

    List<DailyRecord> queryDailyRecord(Long reporter, Long auditor);

    Integer countDailyRecord(Long reporter, Long auditor);

    List<DailyRecord> queryDailyRecord(Long empNo, Long auditor, Integer startIndex, Integer pageSize);
}

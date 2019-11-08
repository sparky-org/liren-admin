package com.sparky.lirenadmin.component.impl;

import com.sparky.lirenadmin.bo.TaskBO;
import com.sparky.lirenadmin.bo.TaskRecordBO;
import com.sparky.lirenadmin.component.ApplyApprovedHandler;
import com.sparky.lirenadmin.entity.TaskRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName ApplyApproved4TaskRecordComponent
 * @Description TODO
 * @Author 86186
 * @Date 2019/11/8
 * @Version v0.0.1
 */
@Service
public class ApplyApproved4TaskRecordComponent implements ApplyApprovedHandler {

    @Autowired
    private TaskRecordBO taskRecordBO;

    @Override
    public void afterApplyApproved(Long originNo) {
        taskRecordBO.rewardTask(originNo);
    }
}

package com.sparky.lirenadmin.component.impl;

import com.sparky.lirenadmin.bo.TaskRecordBO;
import com.sparky.lirenadmin.component.ApplyApprovedHandler;
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
    public void afterApplyApproved(String origin, Long originNo) {
        if (!"TASK_RECORD".equals(origin)){
            return;
        }
        taskRecordBO.rewardTask(originNo);
    }
}

package com.sparky.lirenadmin.component.impl;

import com.sparky.lirenadmin.bo.TaskRecordBO;
import com.sparky.lirenadmin.component.ApplyApprovedHandler;
import com.sparky.lirenadmin.constant.ApplyTypeEnum;
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
        if (!ApplyTypeEnum.TASK_RECORD.getCode().equals(origin)){
            return;
        }
        taskRecordBO.rewardTask(originNo);
    }
}

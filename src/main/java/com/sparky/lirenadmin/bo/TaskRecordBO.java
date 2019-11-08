package com.sparky.lirenadmin.bo;

import com.sparky.lirenadmin.entity.TaskRecord;

public interface TaskRecordBO {

    /**
     * 完成任务创任务完成记录：
     * 1. 创建任务完成记录
     * 2. 创建申请单
     *
    * @MethodName:
    * @Description: TODO
    * @Param:
    * @Return:
    * @Author:
    * @Time: 2019/11/8
    */
    void createTaskRecord(TaskRecord record);

    /**
     * 申请单审批通过自动发放奖励流程
     * 1. 调用任务完成奖励组件申请增加积分
     * 2. 积分增加完成更新自身状态为已发放奖励
    * @MethodName:
    * @Description: TODO
    * @Param:
    * @Return:
    * @Author:
    * @Time: 2019/11/8
    */
    void rewardTask(Long recordNo);

}

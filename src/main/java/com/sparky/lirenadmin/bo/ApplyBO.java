package com.sparky.lirenadmin.bo;

import com.sparky.lirenadmin.entity.Apply;

import java.util.List;

public interface ApplyBO {

    /**
     *
    * @MethodName:
    * @Description: TODO
    * @Param:
    * @Return:
    * @Author:
    * @Time: 2019/11/8
    */
    void createApply(Apply apply);

    /**
     * 审批通过
     * 调用通知组件通知业务方继续之前的流程
     *
    * @MethodName:
    * @Description: TODO
    * @Param:
    * @Return:
    * @Author:
    * @Time: 2019/11/8
    */
    void approve(Apply apply);

    List<Apply> queryApprovalPendingTasks(Long id);

    Apply buildApply(String origin, Long originId, String content, Long applyEmp, Long approvalEmp, Long creator, Long shopNo);

    Apply buildApply(String origin, Long originId, String content, Long applyEmp, Long creator, Long shopNo);

    Apply queryApplyByOrigin(String origin, Long originNo);
}

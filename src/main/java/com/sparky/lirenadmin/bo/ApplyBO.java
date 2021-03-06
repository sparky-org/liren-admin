package com.sparky.lirenadmin.bo;

import com.sparky.lirenadmin.bo.cond.QueryApplyCond;
import com.sparky.lirenadmin.entity.Apply;
import com.sparky.lirenadmin.entity.ApplyDtl;

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
    void createApply(Apply apply, List<ApplyDtl> dtls);

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

    void refuse(Apply apply);

    List<Apply> queryApprovalPendingTasks(Long id);

    Apply buildApply(String origin, Long originId, String content, Long applyEmp, Long approvalEmp, Long creator, Long shopNo);

    Apply buildApply(String origin, Long originId, String content, Long applyEmp, Long creator, Long shopNo);

    Apply queryApplyByOrigin(String origin, Long originNo);

    Integer countApply(QueryApplyCond buildQueryApplyCond);

    List<Apply> pagingQueryApply(QueryApplyCond cond);

    Apply getApply(Long id);

    void revert(Apply apply);
}

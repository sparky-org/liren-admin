package com.sparky.lirenadmin.bo;

import com.sparky.lirenadmin.entity.Apply;

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

}

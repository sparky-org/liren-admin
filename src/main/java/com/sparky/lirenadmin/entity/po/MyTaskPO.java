package com.sparky.lirenadmin.entity.po;

import java.util.Date;

/**
 * 员工查询我的任务返回对象，
 * 如果是管理员在管理员也是只可以看到他范围内的
 * 该对象包含待任务和已完成任务
 * 待办任务：该员工可见+未完成的任务//+完成人数未清零//
 * 已完成任务：该员工已完成的任务
 *
 * @ClassName MyTaskPO
 * @Description TODO
 * @Author 86186
 * @Date 2019/11/8
 * @Version v0.0.1
 */
public class MyTaskPO {
    private Long id;

    private Long empNo;

    private String status;

    private String title;

    private String content;

    private Long pointNo;

    private Integer joinLimit;

    private String scope;

    private Long shopNo;

    private Long creator;

    private Boolean isValid;

    private Date gmtCreate;

    private Date gmtModify;
}

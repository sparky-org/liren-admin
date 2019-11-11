package com.sparky.lirenadmin.constant;

public enum ApplyTypeEnum {
    TASK_RECORD("TASK_RECORD", "任务完成申请"),
    SAL_PERF("SAL_PERF", "业绩完成申请"),
    SERVICE_ITEM("SERVICE_ITEM", "项目完成申请"),
    VACATION("VACATION", "请假申请");

    private String code;
    private String desc;

    ApplyTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}

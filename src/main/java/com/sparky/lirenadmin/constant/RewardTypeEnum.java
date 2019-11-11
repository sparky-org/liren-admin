package com.sparky.lirenadmin.constant;

public enum RewardTypeEnum {

    TASK_RECORD("TASK_RECORD", "任务奖励"),
    SAL_PERF("SAL_PERF", "业绩奖励"),
    SERVICE_ITEM("SERVICE_ITEM", "项目完成奖励"),
    DAILY_RECORD("DAILY_RECORD", "日记奖励"),
    APPOINTMENT("APPOINTMENT", "预约客户奖励"),
    ATTENDANCE("ATTENDANCE", "按时打卡奖励"),
    REWARD_PUNISH("REWARD_PUNISH", "员工奖罚");

    private String code;
    private String desc;

    RewardTypeEnum(String code, String desc) {
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

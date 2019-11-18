package com.sparky.lirenadmin.constant;

public enum PointTypeEnum {
    CHARACTER("CHARACTER", "品德积分"),
    ACTION("ACTION", "行为积分"),
    DAILY_RECORD("DAILY_RECORD", "日记奖励"),
    APPOINTMENT("APPOINTMENT", "预约客户奖励"),
    ATTENDANCE("ATTENDANCE", "按时打卡奖励");

    private String code;
    private String desc;

    PointTypeEnum(String code, String desc) {
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

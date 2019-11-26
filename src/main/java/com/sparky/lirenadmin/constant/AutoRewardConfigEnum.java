package com.sparky.lirenadmin.constant;

public enum AutoRewardConfigEnum {

    DAILY_RECORD("DAILY_RECORD", "日记奖励"),
    ATTENDANCE("ATTENDANCE", "按时打卡奖励");

    private String code;
    private String desc;

    AutoRewardConfigEnum(String code, String desc) {
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

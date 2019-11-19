package com.sparky.lirenadmin.constant;

public enum AttendanceStatus {
    BE_LATE("BE_LATE", "迟到"),
    LEAVE_EARLY("LEAVE_EARLY", "早退"),
    ABSENTEEISM("ABSENTEEISM", "旷工"),
    NORMAL("NORMAL", "正常");

    private String code;
    private String desc;

    AttendanceStatus(String code, String desc) {
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

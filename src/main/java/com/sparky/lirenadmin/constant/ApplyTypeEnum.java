package com.sparky.lirenadmin.constant;

import com.alibaba.fastjson.JSONArray;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    public static String listCodes(){
        List<String> list = Arrays.stream(ApplyTypeEnum.values()).map(ApplyTypeEnum::getCode).collect(Collectors.toList());
        return JSONArray.toJSONString(list);
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}

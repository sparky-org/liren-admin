package com.sparky.lirenadmin.constant;

import com.alibaba.fastjson.JSONArray;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum ApplyStatusEnum {
    NEW("NEW", "待审批"),
    REVERTED("REVERTED", "已撤回"),
    PASSED("PASSED", "审批通过"),
    REFUSED("REFUSED", "审批拒绝");

    private String code;
    private String desc;

    ApplyStatusEnum(String code, String desc) {
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

package com.sparky.lirenadmin.constant;

public enum ShopConfigTypeEnum {

    SYSTEM("SYSTEM", "公司制度"),
    NOTICE("NOTICE", "公告"),
    POSTER("POSTER", "店内海报"),
    @Deprecated
    REWARD_CONFIG("REWARD_CONFIG", "积分奖励配置");

    private String code;
    private String desc;

    ShopConfigTypeEnum(String code, String desc) {
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

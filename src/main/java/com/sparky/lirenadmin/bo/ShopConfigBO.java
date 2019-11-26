package com.sparky.lirenadmin.bo;

import com.sparky.lirenadmin.entity.ShopConfig;

public interface ShopConfigBO {
    void createModifyConfig(ShopConfig buildConfig);

    ShopConfig getShopConfig(Long shopNo, String code);
}

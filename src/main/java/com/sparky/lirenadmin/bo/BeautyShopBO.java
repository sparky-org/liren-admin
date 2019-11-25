package com.sparky.lirenadmin.bo;

import com.sparky.lirenadmin.entity.BeautyShop;

import java.util.List;

public interface BeautyShopBO {
    void createShop(BeautyShop shop);

    List<BeautyShop> getAllShop();

    List<BeautyShop> getShopByIdList(List<Long> shopNoList);
}

package com.sparky.lirenadmin.bo.impl;

import com.sparky.lirenadmin.bo.BeautyShopBO;
import com.sparky.lirenadmin.entity.BeautyShop;
import com.sparky.lirenadmin.mapper.BeautyShopMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BeautyShopBOImpl implements BeautyShopBO {

    @Autowired
    private BeautyShopMapper beautyShopMapper;

    @Override
    public void createShop(BeautyShop shop) {
        shop.setIsValid(true);
        shop.setGmtCreate(new Date());
        shop.setGmtModify(new Date());
        beautyShopMapper.insertSelective(shop);
    }
}

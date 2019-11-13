package com.sparky.lirenadmin.bo.impl;

import com.sparky.lirenadmin.bo.ShopJobBO;
import com.sparky.lirenadmin.entity.ShopJob;
import com.sparky.lirenadmin.mapper.ShopJobMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName ShopJobBOImpl
 * @Description TODO
 * @Author 86186
 * @Date 2019/11/13
 * @Version v0.0.1
 */
@Service
public class ShopJobBOImpl implements ShopJobBO {
    @Autowired
    private ShopJobMapper shopJobMapper;

    @Override
    public ShopJob getShopJob(Long jobNo) {
        return shopJobMapper.selectByPrimaryKey(jobNo);
    }
}

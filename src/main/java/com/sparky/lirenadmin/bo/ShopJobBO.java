package com.sparky.lirenadmin.bo;

import com.sparky.lirenadmin.entity.ShopJob;

import java.util.List;

public interface ShopJobBO {

    void createShopJob(ShopJob shopJob);

    ShopJob getShopJob(Long jobNo);

    List<ShopJob> getShopJobByShop(Long shopNo);

    void deleteJob(Long jobNo);
}

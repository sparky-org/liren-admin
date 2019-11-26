package com.sparky.lirenadmin.bo.impl;

import com.sparky.lirenadmin.bo.ShopJobBO;
import com.sparky.lirenadmin.entity.ShopJob;
import com.sparky.lirenadmin.entity.ShopJobExample;
import com.sparky.lirenadmin.mapper.ShopJobMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
    public void createShopJob(ShopJob shopJob) {
        shopJob.setIsValid(true);
        shopJob.setGmtCreate(new Date());
        shopJob.setGmtModify(new Date());
        shopJobMapper.insertSelective(shopJob);
    }

    @Override
    public ShopJob getShopJob(Long jobNo) {
        return shopJobMapper.selectByPrimaryKey(jobNo);
    }

    @Override
    public List<ShopJob> getShopJobByShop(Long shopNo) {
        ShopJobExample example = new ShopJobExample();
        example.createCriteria().andIsValidEqualTo(true).andShopNoEqualTo(shopNo);
        return shopJobMapper.selectByExample(example);
    }

    @Override
    public void deleteJob(Long jobNo) {
        ShopJob job = new ShopJob();
        job.setId(jobNo);
        job.setIsValid(false);
        job.setGmtModify(new Date());
        shopJobMapper.updateByPrimaryKeySelective(job);
    }
}

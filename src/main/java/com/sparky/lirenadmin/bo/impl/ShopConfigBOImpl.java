package com.sparky.lirenadmin.bo.impl;

import com.sparky.lirenadmin.bo.ShopConfigBO;
import com.sparky.lirenadmin.entity.ShopConfig;
import com.sparky.lirenadmin.entity.ShopConfigExample;
import com.sparky.lirenadmin.mapper.ShopConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * @ClassName ShopConfigBOImpl
 * @Description TODO
 * @Author 86186
 * @Date 2019/11/26
 * @Version v0.0.1
 */
@Service
public class ShopConfigBOImpl implements ShopConfigBO {

    @Autowired
    private ShopConfigMapper shopConfigMapper;

    @Override
    public void createModifyConfig(ShopConfig config) {
        if(config.getId() == null){
            doCreateConfig(config);
        }else{
            doModifyConfig(config);
        }
    }

    @Override
    public ShopConfig getShopConfig(Long shopNo, String code) {
        ShopConfigExample example = new ShopConfigExample();
        example.createCriteria().andShopNoEqualTo(shopNo).andConfigTypeEqualTo(code);
        List<ShopConfig> configList = shopConfigMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(configList)){
            return null;
        }
        return configList.iterator().next();
    }

    private void doModifyConfig(ShopConfig config) {
        config.setGmtModify(new Date());
        shopConfigMapper.updateByPrimaryKeySelective(config);
    }

    private void doCreateConfig(ShopConfig config) {
        config.setGmtCreate(new Date());
        config.setGmtModify(new Date());
        config.setIsValid(true);
        shopConfigMapper.insertSelective(config);
    }
}

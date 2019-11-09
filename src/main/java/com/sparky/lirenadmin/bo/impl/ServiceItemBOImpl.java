package com.sparky.lirenadmin.bo.impl;

import com.sparky.lirenadmin.bo.ServiceItemBO;
import com.sparky.lirenadmin.entity.ServiceItem;
import com.sparky.lirenadmin.mapper.ServiceItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ServiceItemBOImpl implements ServiceItemBO {

    @Autowired
    private ServiceItemMapper serviceItemMapper;

    @Override
    public void createUpdateServiceItem(ServiceItem serviceItem) {
        if (serviceItem == null){
            throw new RuntimeException("参数为空");
        }
        if (serviceItem.getId() == null){
            doCreate(serviceItem);
        }else{
            doModify(serviceItem);
        }
    }

    @Override
    public ServiceItem getServiceItem(Long id) {
        return serviceItemMapper.selectByPrimaryKey(id);
    }

    private void doModify(ServiceItem serviceItem) {
        serviceItem.setGmtModify(new Date());
        serviceItemMapper.updateByPrimaryKeySelective(serviceItem);
    }

    private void doCreate(ServiceItem serviceItem) {
        serviceItem.setIsValid(true);
        serviceItem.setGmtCreate(new Date());
        serviceItem.setGmtModify(new Date());
        serviceItemMapper.insertSelective(serviceItem);
    }
}

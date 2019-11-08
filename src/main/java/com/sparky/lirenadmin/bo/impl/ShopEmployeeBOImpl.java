package com.sparky.lirenadmin.bo.impl;

import com.sparky.lirenadmin.bo.ShopEmployeeBO;
import com.sparky.lirenadmin.entity.ShopEmployee;
import com.sparky.lirenadmin.entity.ShopEmployeeExample;
import com.sparky.lirenadmin.mapper.ShopEmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class ShopEmployeeBOImpl implements ShopEmployeeBO {

    @Autowired
    private ShopEmployeeMapper shopEmployeeMapper;

    @Override
    public ShopEmployee getEmployee(Long empNo) {
        return shopEmployeeMapper.selectByPrimaryKey(empNo);
    }

    @Override
    public ShopEmployee getShopAdmin(Long empNo) {
        ShopEmployee employee = shopEmployeeMapper.selectByPrimaryKey(empNo);
        if (employee.getIsAdmin()){
            return employee;
        }
        ShopEmployee admin = getShopAdmin(employee.getShopNo(), true);
        return admin;
    }

    private ShopEmployee getShopAdmin(Long shopNo, boolean isAdmin){
        ShopEmployeeExample example = new ShopEmployeeExample();
        example.createCriteria().andShopNoEqualTo(shopNo).andIsAdminEqualTo(isAdmin).andIsValidEqualTo(true);
        List<ShopEmployee> employeeList = shopEmployeeMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(employeeList)){
            return null;
        }
        return employeeList.iterator().next();
    }
}

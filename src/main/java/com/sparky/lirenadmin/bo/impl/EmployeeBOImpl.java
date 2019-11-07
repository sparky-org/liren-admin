package com.sparky.lirenadmin.bo.impl;

import com.sparky.lirenadmin.bo.EmployeeBO;
import com.sparky.lirenadmin.entity.ShopEmployee;
import com.sparky.lirenadmin.mapper.ShopEmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeBOImpl implements EmployeeBO {

    @Autowired
    private ShopEmployeeMapper employeeMapper;

    @Override
    public ShopEmployee getEmployee(Long empNo) {
        return employeeMapper.selectByPrimaryKey(empNo);
    }
}

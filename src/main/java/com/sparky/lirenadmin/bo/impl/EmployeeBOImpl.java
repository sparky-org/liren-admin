package com.sparky.lirenadmin.bo.impl;

import com.sparky.lirenadmin.bo.EmployeeBO;
import com.sparky.lirenadmin.entity.ShopEmployee;
import com.sparky.lirenadmin.entity.ShopEmployeeExample;
import com.sparky.lirenadmin.mapper.ShopEmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class EmployeeBOImpl implements EmployeeBO {

    @Autowired
    private ShopEmployeeMapper employeeMapper;

    @Override
    public ShopEmployee getEmployee(Long empNo) {
        return employeeMapper.selectByPrimaryKey(empNo);
    }

    @Override
    public ShopEmployee getEmployeeByPrimaryPhone(String phone) {
        ShopEmployeeExample example = new ShopEmployeeExample();
        example.createCriteria().andPhoneEqualTo(phone).andIsValidEqualTo(true);
        List<ShopEmployee> employees = employeeMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(employees)){
            return null;
        }
        return employees.iterator().next();
    }
}

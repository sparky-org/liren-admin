package com.sparky.lirenadmin.bo.impl;

import com.sparky.lirenadmin.bo.ShopEmployeeBO;
import com.sparky.lirenadmin.entity.ShopEmployee;
import com.sparky.lirenadmin.entity.ShopEmployeeExample;
import com.sparky.lirenadmin.mapper.ShopEmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

@Service
public class ShopEmployeeBOImpl implements ShopEmployeeBO {

    @Autowired
    private ShopEmployeeMapper shopEmployeeMapper;

    @Override
    public ShopEmployee getEmployee(Long empNo) {
        ShopEmployee employee = shopEmployeeMapper.selectByPrimaryKey(empNo);
        if (employee == null || !employee.getIsValid()){
            return null;
        }
        return employee;
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

    @Override
    public void createEmployee(ShopEmployee employee) {
        if (employee == null){
            throw new RuntimeException("要保存的员工不存在");
        }
        if (employee.getAvatar() == null){
            setDefaultAvatar(employee);
        }
        employee.setIsValid(true);
        employee.setGmtCreate(new Date());
        employee.setGmtModify(new Date());
        shopEmployeeMapper.insertSelective(employee);
    }

    @Override
    public void modify(ShopEmployee shopEmployee) {
        shopEmployee.setGmtModify(new Date());
        shopEmployeeMapper.updateByPrimaryKeySelective(shopEmployee);
    }

    @Override
    public List<ShopEmployee> listEmploy(List<Long> idList) {
        ShopEmployeeExample example = new ShopEmployeeExample();
        example.createCriteria().andIsValidEqualTo(true).andIdIn(idList);
        return shopEmployeeMapper.selectByExample(example);
    }

    @Override
    public List<ShopEmployee> listEmployByJobList(List<Long> jobList) {
        ShopEmployeeExample example = new ShopEmployeeExample();
        example.createCriteria().andIsValidEqualTo(true).andJobNoIn(jobList);
        return shopEmployeeMapper.selectByExample(example);
    }

    @Override
    public Integer countTotalEmployee(Long shopNo) {
        ShopEmployeeExample example = new ShopEmployeeExample();
        example.createCriteria().andIsValidEqualTo(true).andShopNoEqualTo(shopNo);
        Long total = shopEmployeeMapper.countByExample(example);
        return total == null ? 0 : total.intValue();
    }

    private void setDefaultAvatar(ShopEmployee employee) {
        //TODO 设置默认图片
        employee.setAvatar("/lr-resource/17/58/175880acb23807a7f93ef366fd7e9ac4");
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

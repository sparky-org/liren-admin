package com.sparky.lirenadmin.bo.impl;

import com.sparky.lirenadmin.bo.CustomerBO;
import com.sparky.lirenadmin.bo.EmployeeBO;
import com.sparky.lirenadmin.entity.CustomerInfo;
import com.sparky.lirenadmin.entity.ShopEmployee;
import com.sparky.lirenadmin.mapper.CustomerInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CustomerBOImpl implements CustomerBO {

    @Autowired
    private EmployeeBO employeeBO;


    @Autowired
    private CustomerInfoMapper customerInfoMapper;

    @Override
    public void createCustomer(CustomerInfo customerInfo) {
        customerInfo.setIsValid(true);
        customerInfo.setGmtCreate(new Date());
        customerInfo.setGmtModify(new Date());
        customerInfoMapper.insertSelective(customerInfo);
    }

    /**
     * 管理员可以直接修改，其他员工修改会生成工作流报送老板娘
     *
     * @param customerInfo
     */
    @Override
    public void modifyCustomer(CustomerInfo customerInfo, Boolean managerApproved) {
        if (null == customerInfo.getCreator()){
            throw new RuntimeException("修改顾客信息操作者不能为空");
        }
        ShopEmployee employee = employeeBO.getEmployee(customerInfo.getCreator());
        if (null == employee){
            throw new RuntimeException("修改顾客信息操作者不存在");
        }
        if (employee.getIsAdmin()){
            doUpdate(customerInfo);
        }else{
            if (null != managerApproved && managerApproved){
                doUpdate(customerInfo);
            }else{
                submitModifyApply(customerInfo);
            }
        }
    }

    @Override
    public void deleteCustomer(Long customerNo) {

    }

    @Override
    public CustomerInfo getCustomer(Long customerNo) {
        return customerInfoMapper.selectByPrimaryKey(customerNo);
    }

    @Override
    public int countCustomer(Long shopNo, Long empNo) {
        return 0;
    }

    @Override
    public List<CustomerInfo> queryCustomer(Long shopNo, Long empNo) {
        return null;
    }

    private void doUpdate(CustomerInfo customerInfo){
        customerInfo.setGmtModify(new Date());
        customerInfoMapper.updateByPrimaryKeySelective(customerInfo);
    }

    //创建修改申请
    private void submitModifyApply(CustomerInfo customerInfo) {

    }
}

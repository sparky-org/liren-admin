package com.sparky.lirenadmin.bo.impl;

import com.sparky.lirenadmin.bo.CustomerBO;
import com.sparky.lirenadmin.bo.EmployeeBO;
import com.sparky.lirenadmin.bo.cond.QueryCustomerCond;
import com.sparky.lirenadmin.entity.CustomerInfo;
import com.sparky.lirenadmin.entity.CustomerInfoExample;
import com.sparky.lirenadmin.entity.ShopEmployee;
import com.sparky.lirenadmin.entity.po.CustomerGrowthStatisticsPO;
import com.sparky.lirenadmin.mapper.ext.CustomerInfoMapperExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

@Service
public class CustomerBOImpl implements CustomerBO {
    private static final Logger logger = LoggerFactory.getLogger(CustomerBOImpl.class);

    @Autowired
    private EmployeeBO employeeBO;

    @Autowired
    private CustomerInfoMapperExt customerInfoMapper;

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

    @Override
    public CustomerInfo getCustomerByPhone(String customerPhone) {
        CustomerInfoExample example = new CustomerInfoExample();
        example.createCriteria().andPhoneEqualTo(customerPhone).andIsValidEqualTo(true);
        List<CustomerInfo> customerInfos = customerInfoMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(customerInfos)){
            return null;
        }
        if (customerInfos.size() > 1){
            logger.warn("系统中存在相同手机号的顾客。手机号：" + customerPhone);
        }
        return customerInfos.iterator().next();
    }

    @Override
    public CustomerGrowthStatisticsPO getGrowthStatistics(Long empNo) {
        ShopEmployee employee = employeeBO.getEmployee(empNo);
        Long queryEmpNo = null;
        if (!employee.getIsAdmin()){
            queryEmpNo = empNo;
        }
        int today = customerInfoMapper.countGrowthOfSpecialInterval(employee.getShopNo(),queryEmpNo,"DAY");
        int thisMonth = customerInfoMapper.countGrowthOfSpecialInterval(employee.getShopNo(),queryEmpNo,"MONTH");
        int thisQuarter = customerInfoMapper.countGrowthOfSpecialInterval(employee.getShopNo(),queryEmpNo,"QUARTER");
        int thisYear = customerInfoMapper.countGrowthOfSpecialInterval(employee.getShopNo(),queryEmpNo,"YEAR");
        CustomerGrowthStatisticsPO statisticsPO = new CustomerGrowthStatisticsPO();
        statisticsPO.setToday(today);
        statisticsPO.setThisMonth(thisMonth);
        statisticsPO.setThisSeason(thisQuarter);
        statisticsPO.setThisYear(thisYear);
        return statisticsPO;
    }

    @Override
    public Integer countCustomerByCond(QueryCustomerCond cond) {
        return customerInfoMapper.countCustomerByCond(cond);
    }

    @Override
    public List<CustomerInfo> pagingQueryCustomerByCond(QueryCustomerCond cond) {
        return customerInfoMapper.pagingQueryCustomerByCond(cond);
    }

    private void doUpdate(CustomerInfo customerInfo){
        customerInfo.setGmtModify(new Date());
        customerInfoMapper.updateByPrimaryKeySelective(customerInfo);
    }

    //创建修改申请
    private void submitModifyApply(CustomerInfo customerInfo) {

    }
}

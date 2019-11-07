package com.sparky.lirenadmin.bo;

import com.sparky.lirenadmin.entity.CustomerInfo;
import com.sparky.lirenadmin.entity.ShopEmployee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@MapperScan("com.sparky.lirenadmin.mapper")
@RunWith(SpringRunner.class)
@Transactional
@Rollback(true)
public class CustomerBOTest {

    @Autowired
    private CustomerBO customerBO;

    @MockBean
    private EmployeeBO employeeBO;

    @Test
    public void testInsert(){
        CustomerInfo info = new CustomerInfo();
        info.setName("张三");
        info.setSex("FEMALE");
        info.setPhone("13611111110");
        info.setShopNo(1l);
        info.setCreator(1l);
        customerBO.createCustomer(info);
    }

    @Test
    public void testModify(){

        //case 1: 管理员修改
        Mockito.doReturn(getAdminEmployee()).when(employeeBO).getEmployee(any());
        CustomerInfo info = customerBO.getCustomer(1l);
        String favor = "[\"唱歌\",\"跳舞\"]";
        info.setFavor(favor);
        customerBO.modifyCustomer(info, false);
        info = customerBO.getCustomer(1l);
        Assert.isTrue(info.getFavor().equals(favor));

        //case 1: 普通员工修改
        Mockito.doReturn(getAdminEmployee()).when(employeeBO).getEmployee(any());
        customerBO.modifyCustomer(info, false);
        info = customerBO.getCustomer(1l);
        Assert.isTrue(info.getFavor().equals(favor));
    }

    ShopEmployee getAdminEmployee(){
        ShopEmployee employee = new ShopEmployee();
        employee.setId(1l);
        employee.setIsAdmin(true);
        return employee;
    }

}

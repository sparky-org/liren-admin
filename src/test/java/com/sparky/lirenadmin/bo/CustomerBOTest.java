package com.sparky.lirenadmin.bo;

import com.sparky.lirenadmin.controller.request.CreateAppointmentDTO;
import com.sparky.lirenadmin.entity.BeautyShop;
import com.sparky.lirenadmin.entity.CustomerInfo;
import com.sparky.lirenadmin.entity.ServiceItem;
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

import java.util.Date;

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
        CustomerInfo info = new CustomerInfo();
        info.setName("张三");
        info.setSex("FEMALE");
        info.setPhone("13611111110");
        info.setShopNo(1l);
        info.setCreator(1l);
        customerBO.createCustomer(info);

        //case 1: 管理员修改
        Mockito.doReturn(getAdminEmployee()).when(employeeBO).getEmployee(any());
        String favor = "[\"唱歌\",\"跳舞\"]";
        info.setFavor(favor);
        customerBO.modifyCustomer(info, false);
        info = customerBO.getCustomer(info.getId());
        Assert.isTrue(info.getFavor().equals(favor));

        //case 1: 普通员工修改
        Mockito.doReturn(getAdminEmployee()).when(employeeBO).getEmployee(any());
        customerBO.modifyCustomer(info, false);
        info = customerBO.getCustomer(info.getId());
        Assert.isTrue(info.getFavor().equals(favor));
    }

    ShopEmployee getAdminEmployee(){
        ShopEmployee employee = new ShopEmployee();
        employee.setId(1l);
        employee.setIsAdmin(true);
        return employee;
    }

    private CustomerInfo initCustomer(ShopEmployee employee) {
        CustomerInfo info = new CustomerInfo();
        info.setName("王灵儿");
        info.setSex("FEMALE");
        info.setPhone("13611111110");
        info.setShopNo(employee.getShopNo());
        info.setCreator(employee.getId());
        return info;
    }

    private CreateAppointmentDTO initCreateAppointmentDTO(ShopEmployee employee, CustomerInfo customer, ServiceItem serviceItem) {
        CreateAppointmentDTO dto = new CreateAppointmentDTO();
        dto.setAppointDate(new Date());
        dto.setCustomerPhone(customer.getPhone());
        dto.setEmpNo(employee.getId());
        dto.setServiceItemNo(serviceItem.getId());
        dto.setRemark("VIP客户，做好服务");
        dto.setOperator(employee.getId());
        return dto;
    }

    private ServiceItem initServiceItem(ShopEmployee admin){
        ServiceItem item = new ServiceItem();
        item.setShopNo(admin.getShopNo());
        item.setCreator(admin.getId());
        item.setDuration(60);
        item.setItemName("脸部护理");
        item.setItemDesc("护理手法要求：护理时间要求：");
        item.setRewardPoint(30);
        return item;
    }

    private BeautyShop initShop() {
        BeautyShop shop = new BeautyShop();
        shop.setName("美容院X");
        shop.setJoinDate(new Date());
        return shop;
    }

    private ShopEmployee initEmployee(BeautyShop shop) {
        ShopEmployee admin = new ShopEmployee();
        admin.setIsAdmin(true);
        admin.setJobNo(1l);
        admin.setShopNo(shop.getId());
        admin.setPassword("1111");
        admin.setName("张三");
        admin.setManagerNo(0l);
        admin.setPhone("13011111111");
        return admin;
    }

}

package com.sparky.lirenadmin.bo;

import com.sparky.lirenadmin.entity.ShopEmployee;

public interface EmployeeBO {

    ShopEmployee getEmployee(Long empNo);

    /**
     * 该方法要求员工手机号全局唯一，如果一个老板由多个店铺
     * 且都选用同一个手机号作为登陆账号，默认登陆第一个。
     * 以后可以优化，让他选择登陆哪个。
    * @MethodName:
    * @Description: TODO
    * @Param:
    * @Return:
    * @Author:
    * @Time: 2019/11/8
    */
    ShopEmployee getEmployeeByPrimaryPhone(String phone);
}

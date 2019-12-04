package com.sparky.lirenadmin.controller;

import com.sparky.lirenadmin.bo.AppointmentBO;
import com.sparky.lirenadmin.bo.CustomerBO;
import com.sparky.lirenadmin.bo.ServiceItemBO;
import com.sparky.lirenadmin.bo.ShopEmployeeBO;
import com.sparky.lirenadmin.bo.cond.QueryAppointmentCond;
import com.sparky.lirenadmin.controller.request.CreateAppointmentDTO;
import com.sparky.lirenadmin.controller.response.AppointmentInfo;
import com.sparky.lirenadmin.controller.response.BaseResponseWrapper;
import com.sparky.lirenadmin.controller.response.PagingResponseWrapper;
import com.sparky.lirenadmin.entity.Appointment;
import com.sparky.lirenadmin.entity.CustomerInfo;
import com.sparky.lirenadmin.entity.ServiceItem;
import com.sparky.lirenadmin.entity.ShopEmployee;
import com.sparky.lirenadmin.utils.DateUtils;
import com.sparky.lirenadmin.utils.PagingUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName MyAppointmentController
 * @Description TODO
 * @Author 86186
 * @Date 2019/11/14
 * @Version v0.0.1
 */
@Api(tags = "我的预约")
@Controller
@RequestMapping("/myAppointment")
public class MyAppointmentController {
    private static final Logger logger = LoggerFactory.getLogger(MyApplyController.class);

    @Autowired
    private AppointmentBO appointmentBO;
    @Autowired
    private ShopEmployeeBO shopEmployeeBO;
    @Autowired
    private CustomerBO customerBO;
    @Autowired
    private ServiceItemBO serviceItemBO;

    @ApiOperation("创建预约")
    @RequestMapping(value = "/createAppointment",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseWrapper createAppointment(@RequestBody CreateAppointmentDTO dto){
        try {
            appointmentBO.createAppointment(buildAppointment(dto));
            return BaseResponseWrapper.success(null);
        } catch (Exception e) {
            logger.error("创建预约失败", e);
            return BaseResponseWrapper.fail(null, e.getMessage());
        }
    }

    /**
     * 管理员看到本店所有预约，员工只可以看到自己的预约
     *
     * @param empNo
     * @param month
     * @return
     */
    @ApiOperation("查询预约当月预约日期")
    @RequestMapping(value = "/queryAppointmentDays",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseWrapper<List<String>> queryAppointmentDays(@RequestParam @ApiParam Long empNo,
                                                                  @RequestParam @ApiParam("月份：yyyy-MM") String month){
        try {
            Date month1 = DateUtils.getMonth(month);
            Date monthBegin = DateUtils.getMonthBegining(month1);
            Date monthEnd = DateUtils.getMonthEnding(month1);
            ShopEmployee employee = shopEmployeeBO.getEmployee(empNo);
            if (null == employee){
                throw new RuntimeException(String.format("员工[%s]不存在", empNo));
            }
            List<Appointment> appointments = new ArrayList<>();
            if(employee.getIsAdmin()){
                appointments = appointmentBO.queryAppointment(new QueryAppointmentCond(employee.getShopNo(), null, monthBegin, monthEnd));
            }else{
                appointments = appointmentBO.queryAppointment(new QueryAppointmentCond(employee.getShopNo(), empNo, monthBegin, monthEnd));

            }
            if (appointments == null){
                return BaseResponseWrapper.success(new ArrayList<>());
            }
            List<String> days = appointments.stream().map(a -> com.sparky.lirenadmin.utils.DateUtils.formatDate(a.getAppointTime(),"yyyy-MM-dd")).collect(Collectors.toList());
            return BaseResponseWrapper.success(days);
        } catch (RuntimeException e) {
            logger.error("查询预约日期异常", e);
            return BaseResponseWrapper.fail(null, e.getMessage());
        }
    }

    /**
     * 上级可以查看下级的预约，老板娘可以查看全店的预约
    * @MethodName:
    * @Description: TODO
    * @Param:
    * @Return:
    * @Author:
    * @Time: 2019/11/14
    */
    @ApiOperation("查询预约")
    @RequestMapping(value = "/queryAppointment",method = RequestMethod.POST)
    @ResponseBody
    public PagingResponseWrapper<List<AppointmentInfo>> queryAppointment(@RequestParam @ApiParam Long empNo,
                                                                         @RequestParam @ApiParam("日期：yyyy-MM-dd") String date,
                                                                         @RequestParam @ApiParam Integer currentPage,
                                                                         @RequestParam @ApiParam Integer pageSize){
        try {
            Date date1 = DateUtils.getDate(date);
            Date monthBegin = DateUtils.getDayBegining(date1);
            Date monthEnd = DateUtils.getDayEnding(date1);
            ShopEmployee employee = shopEmployeeBO.getEmployee(empNo);
            if (null == employee){
                throw new RuntimeException(String.format("员工[%s]不存在", empNo));
            }
            List<Appointment> appointments;
            Integer total;
            QueryAppointmentCond cond;
            if(employee.getIsAdmin()){
                cond = new QueryAppointmentCond(employee.getShopNo(), null, monthBegin, monthEnd);
            }else {
                cond = new QueryAppointmentCond(employee.getShopNo(), empNo, monthBegin, monthEnd);
            }
            total = appointmentBO.countAppointCustomer(cond);
            if(total < 1){
                return PagingResponseWrapper.success(new ArrayList<>(),0);
            }
            Integer start = PagingUtils.getStartIndex(total, currentPage, pageSize);
            appointments = appointmentBO.pagingQueryAppointment(cond, start, pageSize);

            List<AppointmentInfo> infos = appointments.stream().map(e -> convertToAppointmentInfo(e, employee)).collect(Collectors.toList());
            return PagingResponseWrapper.success(infos, total);
        } catch (RuntimeException e) {
            logger.error("查询预约异常", e);
            return PagingResponseWrapper.fail1(null, e.getMessage());
        }
    }

    private AppointmentInfo convertToAppointmentInfo(Appointment appointment, ShopEmployee employee) {
        AppointmentInfo info = new AppointmentInfo();
        info.setTime(appointment.getAppointTime());
        CustomerInfo customer = customerBO.getCustomerByPhone(appointment.getCustomerPhone());
        StringBuffer content = new StringBuffer();
        if (customer != null){
            content.append(customer.getName()).append(customer.getSex().equals("MALE") ? "先生" : "女士");
        }
        ServiceItem item = serviceItemBO.getServiceItem(appointment.getServiceNo());
        if (item != null) {
            content.append("预约了").append(item.getItemName()).append(",");
        }
        if (employee.getIsAdmin() && appointment.getAppointEmpNo() != null) {
            ShopEmployee emp = shopEmployeeBO.getEmployee(appointment.getAppointEmpNo());
            if (emp!=null){
                content.append("接待美容师是").append(emp.getName());
            }
        }
        info.setInfo(content.toString());
        return info;
    }

    private Appointment buildAppointment(CreateAppointmentDTO dto) {
        Appointment appointment = new Appointment();
        appointment.setCustomerPhone(dto.getCustomerPhone());
        appointment.setServiceNo(dto.getServiceItemNo());
        appointment.setAppointTime(DateUtils.getDateTime(dto.getAppointDate()));
        appointment.setRemark(dto.getRemark());
        appointment.setServiceEmpNo(dto.getEmpNo());
        appointment.setIsRewarded(false);
        appointment.setAppointEmpNo(dto.getOperator());
        appointment.setCreator(dto.getOperator());
        appointment.setRewardPoint(30);
        ShopEmployee employee = shopEmployeeBO.getEmployee(dto.getEmpNo());
        if (employee == null){
            throw new RuntimeException(String.format("员工[%d]不存在", dto.getEmpNo()));
        }
        appointment.setShopNo(employee.getShopNo());
        return appointment;
    }
}

package com.sparky.lirenadmin.controller;

import com.sparky.lirenadmin.bo.AppointmentBO;
import com.sparky.lirenadmin.controller.request.CreateAppointmentDTO;
import com.sparky.lirenadmin.controller.response.BaseResponseWrapper;
import com.sparky.lirenadmin.entity.Appointment;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    private Appointment buildAppointment(CreateAppointmentDTO dto) {
        Appointment appointment = new Appointment();

        return appointment;
    }

    @ApiOperation("查询预约当月预约日期")
    @RequestMapping(value = "/queryAppointmentDays",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseWrapper queryAppointmentDays(@RequestParam @ApiParam Long empNo,
                                                    @RequestParam @ApiParam("月份：yyyy-MM") String month){

        return BaseResponseWrapper.success(null);
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
    public BaseResponseWrapper queryAppointment(@RequestParam @ApiParam Long empNo,
                                                @RequestParam @ApiParam("日期：yyyy-MM-dd") String month,
                                                @RequestParam @ApiParam Integer currentPage,
                                                @RequestParam @ApiParam Integer pageSize){

        return BaseResponseWrapper.success(null);
    }



}

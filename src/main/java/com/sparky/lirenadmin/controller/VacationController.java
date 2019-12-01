package com.sparky.lirenadmin.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sparky.lirenadmin.bo.*;
import com.sparky.lirenadmin.constant.AutoRewardConfigEnum;
import com.sparky.lirenadmin.constant.ShopConfigTypeEnum;
import com.sparky.lirenadmin.controller.request.SetAttendanceDTO;
import com.sparky.lirenadmin.controller.response.BaseResponseWrapper;
import com.sparky.lirenadmin.controller.response.MyAttendanceInfo;
import com.sparky.lirenadmin.entity.*;
import com.sparky.lirenadmin.entity.po.AttendanceStatisticsPO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.http.client.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Api(tags = "考勤接口")
@Controller
@RequestMapping("/vacation")
public class VacationController {

    @Autowired
    private ClockInLogBO clockInLogBO;
    @Autowired
    private ShopEmployeeBO shopEmployeeBO;
    @Autowired
    private AttendanceRecordBO attendanceRecordBO;
    @Autowired
    private AttendanceConfigBO attendanceConfigBO;
    @Autowired
    private ShopConfigBO shopConfigBO;
    /**
     * 查询月信息
     * @param empNo
     * @param month
     * @return
     */
    @ApiOperation("按月查询考勤情况")
    @RequestMapping(value = "/myVacation", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseWrapper<MyAttendanceInfo> myVacation(@RequestParam @ApiParam Long empNo,
                                          @RequestParam @ApiParam String month){
        try {
            ShopEmployee employee = shopEmployeeBO.getEmployee(empNo);
            if (employee == null){
                throw new RuntimeException("员工不存在");
            }
            MyAttendanceInfo info = new MyAttendanceInfo();
            //迟到、早退、旷工
            AttendanceStatisticsPO po = attendanceRecordBO.getStatistics(empNo, com.sparky.lirenadmin.utils.DateUtils.getMonth(month));
            if (po == null){
                return BaseResponseWrapper.success(null);
            }
            info.setLate(po.getLate());
            info.setLeaveEarly(po.getLeaveEarly());
            info.setAbsenteeism(po.getAbsenteeism());
            //考勤异常日期列表
            List<AttendanceRecord> exceptRecord = attendanceRecordBO.getExceptRecord(empNo, com.sparky.lirenadmin.utils.DateUtils.getMonth(month));
            if (CollectionUtils.isEmpty(exceptRecord)){
                List<String> exceptDateList = exceptRecord.stream().map(e -> DateUtils.formatDate(e.getAttendanceDate(), "yyyy-MM-dd"))
                        .collect(Collectors.toList());
                info.setExceptDateList(exceptDateList);
            }

            AttendanceConfig config = attendanceConfigBO.getConfig(employee.getShopNo());
            if (config != null){
                info.setStartWorkTime(DateUtils.formatDate(config.getStartWork(), "HH:mm"));
                info.setEndWorkTime(DateUtils.formatDate(config.getEndWork(), "HH:mm"));
            }
            return BaseResponseWrapper.success(info);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return BaseResponseWrapper.fail(null, e.getMessage());
        }
    }

    /**
     * 查询当日出勤记录，最小时间和最大时间
     * @param empNo
     * @param today
     * @return
     */
    @ApiOperation("指定日期的签到时间")
    @RequestMapping(value = "/todayVacation", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseWrapper<Map<String,String>> todayVacation(@RequestParam @ApiParam Long empNo,
                                                                 @RequestParam @ApiParam Date today){

        try {
            //考勤异常日期列表
            ShopEmployee employee = shopEmployeeBO.getEmployee(empNo);
            if (employee == null){
                throw new RuntimeException("员工不存在");
            }
            List<ClockInLog> logs = clockInLogBO.getMyLog(empNo, today);
            if (CollectionUtils.isEmpty(logs)){
                return BaseResponseWrapper.success(new HashMap<>());
            }
            Date start = logs.get(0).getClockInTime();
            Date end = logs.get(0).getClockInTime();
            for (ClockInLog log : logs){
                if (start.after(log.getClockInTime())){
                    start = log.getClockInTime();
                }
                if (end.before(log.getClockInTime())){
                    end = log.getClockInTime();
                }
            }
            if (start.compareTo(end) == 0){
                end = null;
            }
            Map<String, String> map = new HashMap<>();
            map.put("start", DateUtils.formatDate(start, "HH:mm"));
            if (end != null) {
                map.put("end", DateUtils.formatDate(end, "HH:mm"));
            }
            return BaseResponseWrapper.success(map);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return BaseResponseWrapper.fail(null, e.getMessage());
        }
    }

    @ApiOperation("上下班打卡")
    @RequestMapping(value = "/clockIn", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseWrapper clockIn(@RequestParam @ApiParam Long empNo,
                                       @RequestParam @ApiParam Double longitude,
                                       @RequestParam @ApiParam Double latitude,
                                       @RequestParam @ApiParam Boolean isOutSide){
        try {
            ClockInLog clockInLog = buildClockInLog(empNo, longitude, latitude, isOutSide);
            clockInLogBO.createClockInfoLog(clockInLog);
            return BaseResponseWrapper.success(null);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponseWrapper.fail(null, e.getMessage());
        }
    }

    @ApiOperation("设置考勤")
    @RequestMapping(value = "/setAttendance", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseWrapper setAttendance(@RequestBody SetAttendanceDTO dto){
        try {
            attendanceConfigBO.createConfig(buildAttendanceConfig(dto));
            ShopConfig shopConfig = shopConfigBO.getShopConfig(dto.getShopNo(), ShopConfigTypeEnum.REWARD_CONFIG.getCode());
            if (shopConfig == null){
                shopConfig = new ShopConfig();
                shopConfig.setConfigName(ShopConfigTypeEnum.REWARD_CONFIG.getDesc());
                shopConfig.setConfigType(ShopConfigTypeEnum.REWARD_CONFIG.getCode());
                shopConfig.setCreator(0L);
                shopConfig.setShopNo(dto.getShopNo());
                JSONObject object = new JSONObject();
                object.put(AutoRewardConfigEnum.ATTENDANCE.getCode(), dto.getRewardPoint());
                JSONArray array = new JSONArray();
                array.add(object);
                shopConfig.setContent(array.toJSONString());
                shopConfigBO.createModifyConfig(shopConfig);
            }else{
                JSONArray array = JSONArray.parseArray(shopConfig.getContent());
                Iterator iterator = array.iterator();
                while (iterator.hasNext()){
                    JSONObject json = (JSONObject)iterator.next();
                    if (null != json.get(AutoRewardConfigEnum.ATTENDANCE.getCode())){
                        iterator.remove();
                    }
                }
                JSONObject object = new JSONObject();
                object.put(AutoRewardConfigEnum.ATTENDANCE.getCode(), dto.getRewardPoint());
                array.add(object);
                shopConfig.setContent(array.toJSONString());
                shopConfigBO.createModifyConfig(shopConfig);
            }
            return BaseResponseWrapper.success(null);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponseWrapper.fail(null, e.getMessage());
        }
    }

    private AttendanceConfig buildAttendanceConfig(SetAttendanceDTO dto) {
        AttendanceConfig config = new AttendanceConfig();
        config.setCreator(dto.getOperator());
        config.setEndWork(com.sparky.lirenadmin.utils.DateUtils.getDateTime("1990-01-01 " + dto.getEndWorkTime()));
        config.setShopNo(dto.getShopNo());
        config.setLatitude(dto.getLatitude());
        config.setLongitude(dto.getLongitude());
        config.setRadius(dto.getScopeRadio());
        config.setStartWork(com.sparky.lirenadmin.utils.DateUtils.getDateTime("1990-01-01 " + dto.getStartWorkTime()));
        config.setWorkDay(dto.getWorkDay());
        return config;
    }

    private ClockInLog buildClockInLog(Long empNo, Double longitude, Double latitude, Boolean isOutSide) {
        ClockInLog clockInLog = new ClockInLog();
        clockInLog.setEmpNo(empNo);
        ShopEmployee employee = shopEmployeeBO.getEmployee(empNo);
        if (employee == null){
            throw new RuntimeException("员工不存在");
        }
        clockInLog.setCreator(empNo);
        clockInLog.setShopNo(employee.getShopNo());
        clockInLog.setClockInDate(new Date());
        clockInLog.setClockInTime(new Date());
        clockInLog.setLongitude(new BigDecimal(longitude));
        clockInLog.setLatitude(new BigDecimal(latitude));
        clockInLog.setIsOutside(isOutSide);
        if (isOutSide){
            clockInLog.setIsEnable(false);
        }else{
            clockInLog.setIsEnable(true);
        }
        return clockInLog;
    }
}

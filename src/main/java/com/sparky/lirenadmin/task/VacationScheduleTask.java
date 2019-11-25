package com.sparky.lirenadmin.task;

import com.sparky.lirenadmin.bo.AttendanceConfigBO;
import com.sparky.lirenadmin.bo.BeautyShopBO;
import com.sparky.lirenadmin.bo.ClockInLogBO;
import com.sparky.lirenadmin.bo.EmployeeBO;
import com.sparky.lirenadmin.entity.AttendanceConfig;
import com.sparky.lirenadmin.entity.BeautyShop;
import com.sparky.lirenadmin.entity.ClockInLog;
import com.sparky.lirenadmin.entity.ShopEmployee;
import com.sparky.lirenadmin.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Configuration
@EnableScheduling
public class VacationScheduleTask {

    @Autowired
    private AttendanceConfigBO attendanceConfigBO;
    @Autowired
    private ClockInLogBO clockInLogBO;
    @Autowired
    private EmployeeBO employeeBO;
    @Autowired
    private BeautyShopBO beautyShopBO;

    //正常考勤申请奖励
//    @Scheduled(cron = "0/5 * * * * ?")
    @Scheduled(cron = "0 1 0 * * ?")
    private void configureTask(){
        System.out.println("执行定任务=========");
        List<BeautyShop> shops = beautyShopBO.getAllShop();
        for (BeautyShop shop : shops){
            executeShop(shop.getId());
        }
    }

    private void executeShop(Long shopNo){
        //前一日是否周末
        Date yestoday = DateUtils.yestoday(new Date());
        AttendanceConfig config = attendanceConfigBO.getConfig(shopNo);
        if (config == null){
            return;
        }
        if (isWeekend(yestoday, config)){
            return;
        }

        //获取前一日有效记录，以后单店员工增加后需要改为分页查
        List<ClockInLog> clockInLogs = clockInLogBO.getAllClockInfoLogs(shopNo, yestoday);
        Map<Long, List<ClockInLog>> empAttendanceMap = new HashMap<>();
        if (CollectionUtils.isEmpty(clockInLogs)){
            //如果工作日都没有考勤，就全部记旷工
            empAttendanceMap = clockInLogs.stream().collect(Collectors.groupingBy(ClockInLog::getEmpNo));
        }
        List<ShopEmployee> employees = employeeBO.getEmployeeByShopNo(shopNo);
        if (CollectionUtils.isEmpty(employees)){
            return;
        }
        for (ShopEmployee emp : employees){
            try{
                List<ClockInLog> list = empAttendanceMap.get(emp.getId());
                execute(emp, list, config, yestoday);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private boolean isWeekend(Date yestoday, AttendanceConfig config) {
        String weekDay = DateUtils.getWeekOfDate(yestoday);
        String[] weekDays = config.getWorkDay().split(",");
        if (weekDays.length < 1){
            System.out.println("未配置工作日");
            return false;
        }
        if (!Stream.of(weekDays).collect(Collectors.toList()).contains(weekDay)){
            //非工作日
            return true;
        }
        return false;
    }

    private void execute(ShopEmployee emp, List<ClockInLog> att, AttendanceConfig config, Date yestoday){
        clockInLogBO.executeAttendance(emp, att, config, yestoday);
    }

}

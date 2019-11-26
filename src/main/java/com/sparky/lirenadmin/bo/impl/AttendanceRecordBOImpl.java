package com.sparky.lirenadmin.bo.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sparky.lirenadmin.bo.AttendanceCompleteBO;
import com.sparky.lirenadmin.bo.AttendanceRecordBO;
import com.sparky.lirenadmin.bo.ShopConfigBO;
import com.sparky.lirenadmin.constant.AttendanceStatus;
import com.sparky.lirenadmin.constant.AutoRewardConfigEnum;
import com.sparky.lirenadmin.constant.ShopConfigTypeEnum;
import com.sparky.lirenadmin.entity.AttendanceComplete;
import com.sparky.lirenadmin.entity.AttendanceRecord;
import com.sparky.lirenadmin.entity.AttendanceRecordExample;
import com.sparky.lirenadmin.entity.ShopConfig;
import com.sparky.lirenadmin.entity.po.AttendanceStatisticsPO;
import com.sparky.lirenadmin.mapper.ext.AttendanceRecordMapperExt;
import com.sparky.lirenadmin.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class AttendanceRecordBOImpl implements AttendanceRecordBO {

    @Autowired
    private AttendanceRecordMapperExt attendanceRecordMapper;
    @Autowired
    private AttendanceCompleteBO attendanceCompleteBO;
    @Autowired
    private ShopConfigBO shopConfigBO;

    @Override
    public void createAttendanceRecord(AttendanceRecord record) {
        doCreateRecord(record);
        //考勤正常提起奖励申请
        if (AttendanceStatus.NORMAL.getCode().equals(record.getStatus())){
            attendanceCompleteBO.createAttendanceComplete(buildComplete(record));
        }
    }

    @Override
    public AttendanceStatisticsPO getStatistics(Long empNo, Date month) {
        Date begin = DateUtils.getMonthBegining(month);
        Date end = DateUtils.getMonthEnding(month);
        return attendanceRecordMapper.getStatistics(empNo, begin, end);
    }

    @Override
    public List<AttendanceRecord> getExceptRecord(Long empNo, Date month) {
        AttendanceRecordExample example = new AttendanceRecordExample();
        Date begin  = DateUtils.getMonthBegining(month);
        Date end = DateUtils.getMonthEnding(month);
        example.createCriteria().andIsValidEqualTo(true).andEmpNoEqualTo(empNo).andAttendanceDateBetween(begin, end);
        return attendanceRecordMapper.selectByExample(example);
    }

    private AttendanceComplete buildComplete(AttendanceRecord record) {
        AttendanceComplete complete = new AttendanceComplete();
        complete.setIsRewarded(false);
        complete.setRewardTime(null);
        complete.setCompleteDate(record.getGmtCreate());
        complete.setCreator(record.getCreator());
        complete.setEmpNo(record.getEmpNo());
        //从配置读取，考勤正常奖励积分。未配置则奖励 0
        ShopConfig rewardConfig = shopConfigBO.getShopConfig(record.getShopNo(), ShopConfigTypeEnum.REWARD_CONFIG.getCode());
        complete.setRewardPoint(0);
        if (null != rewardConfig && rewardConfig.getContent() != null){
            JSONArray array = JSONArray.parseArray(rewardConfig.getContent());
            Iterator it = array.iterator();
            while(it.hasNext()){
                JSONObject obj = (JSONObject) it.next();
                Integer point = obj.getInteger(AutoRewardConfigEnum.ATTENDANCE.getCode());
                if (null != point){
                    complete.setRewardPoint(point);
                }
            }
        }
        complete.setShopNo(record.getShopNo());
        return complete;
    }

    private void doCreateRecord(AttendanceRecord record){
        record.setGmtCreate(new Date());
        record.setGmtModify(new Date());
        record.setIsValid(true);
        attendanceRecordMapper.insertSelective(record);
    }
}

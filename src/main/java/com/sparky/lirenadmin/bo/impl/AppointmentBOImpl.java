package com.sparky.lirenadmin.bo.impl;

import com.sparky.lirenadmin.bo.AppointmentBO;
import com.sparky.lirenadmin.bo.PointBO;
import com.sparky.lirenadmin.bo.cond.IncreasePointDO;
import com.sparky.lirenadmin.bo.cond.QueryAppointmentCond;
import com.sparky.lirenadmin.constant.RewardTypeEnum;
import com.sparky.lirenadmin.entity.Appointment;
import com.sparky.lirenadmin.entity.AppointmentExample;
import com.sparky.lirenadmin.entity.ShopEmployee;
import com.sparky.lirenadmin.mapper.ext.AppointmentMapperExt;
import com.sparky.lirenadmin.utils.PagingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AppointmentBOImpl implements AppointmentBO {

    @Autowired
    private PointBO pointBO;

    @Autowired
    private AppointmentMapperExt appointmentMapper;

    @Override
    public void createAppointment(Appointment appointment) {
        doCreateAppointment(appointment);
        reward(appointment);
    }

    @Override
    public Integer countAppointCustomer(ShopEmployee employee, Date today) {
        int total;
        if (employee.getIsAdmin()){
            total = appointmentMapper.countAppointCustomerByShop(employee.getShopNo(), today);
        }else{
            total = appointmentMapper.countAppointCustomerByEmp(employee.getShopNo(), today);
        }
        return total;
    }

    @Override
    public List<Appointment> queryAppointment(QueryAppointmentCond cond) {
        AppointmentExample example = new AppointmentExample();
        AppointmentExample.Criteria criteria = example.createCriteria();
        criteria.andIsValidEqualTo(true).andShopNoEqualTo(cond.getShopNo());
        if (cond.getEmpNo() != null){
            criteria.andAppointEmpNoEqualTo(cond.getEmpNo());
        }
        if (cond.getBegin() != null && cond.getEnd() != null){
            criteria.andAppointTimeBetween(cond.getBegin(), cond.getEnd());
        }
        if (cond.getBegin() != null && cond.getEnd() == null){
            criteria.andAppointTimeGreaterThanOrEqualTo(cond.getBegin());
        }
        if (cond.getBegin() == null && cond.getEnd() != null){
            criteria.andAppointTimeLessThanOrEqualTo(cond.getEnd());
        }
        return appointmentMapper.selectByExample(example);
    }

    @Override
    public Integer countAppointCustomer(QueryAppointmentCond cond){
        return appointmentMapper.countAppointCustomerByCond(cond);
    }

    @Override
    public List<Appointment> pagingQueryAppointment(QueryAppointmentCond cond, Integer start, Integer pageSize) {
        List<Appointment> aps = appointmentMapper.pagingQueryAppointment(cond, start, pageSize);
        return aps;
    }

    private IncreasePointDO buildIncreasePointDO(Appointment appointment) {
        return pointBO.buildIncreasePointDO(RewardTypeEnum.APPOINTMENT.getCode(), appointment.getId(),
                appointment.getAppointEmpNo(), appointment.getCreator(), appointment.getRewardPoint(), appointment.getShopNo());

    }

    private Boolean doReward(Long salesId) {
        Appointment appointment = new Appointment();
        appointment.setRewardTime(new Date());
        appointment.setGmtModify(new Date());
        appointment.setId(salesId);
        appointment.setIsRewarded(true);
        appointmentMapper.updateByPrimaryKeySelective(appointment);
        return true;
    }

    private void reward(Appointment appointment) {
        pointBO.increasePoint(buildIncreasePointDO(appointment), this::doReward);
    }

    private void doCreateAppointment(Appointment appointment) {
        appointment.setIsValid(true);
        appointment.setGmtCreate(new Date());
        appointment.setGmtModify(new Date());
        appointmentMapper.insertSelective(appointment);
    }
}

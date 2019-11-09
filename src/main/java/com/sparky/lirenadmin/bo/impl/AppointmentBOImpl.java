package com.sparky.lirenadmin.bo.impl;

import com.sparky.lirenadmin.bo.AppointmentBO;
import com.sparky.lirenadmin.bo.PointBO;
import com.sparky.lirenadmin.bo.cond.IncreasePointDO;
import com.sparky.lirenadmin.entity.Appointment;
import com.sparky.lirenadmin.mapper.AppointmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AppointmentBOImpl implements AppointmentBO {

    @Autowired
    private PointBO pointBO;

    @Autowired
    private AppointmentMapper appointmentMapper;

    @Override
    public void createAppointment(Appointment appointment) {
        doCreateAppointment(appointment);
        reward(appointment);
    }

    private IncreasePointDO buildIncreasePointDO(Appointment appointment) {
        return pointBO.buildIncreasePointDO("APPOINTMENT", appointment.getId(),
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

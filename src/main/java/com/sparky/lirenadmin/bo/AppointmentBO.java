package com.sparky.lirenadmin.bo;

import com.sparky.lirenadmin.bo.cond.QueryAppointmentCond;
import com.sparky.lirenadmin.entity.Appointment;
import com.sparky.lirenadmin.entity.ShopEmployee;

import java.util.Date;
import java.util.List;

public interface AppointmentBO {

    void createAppointment(Appointment appointment);

    Integer countAppointCustomer(ShopEmployee employee, Date today);

    List<Appointment> queryAppointment(QueryAppointmentCond queryAppointmentCond);

    Integer countAppointCustomer(QueryAppointmentCond cond);

    List<Appointment> pagingQueryAppointment(QueryAppointmentCond queryAppointmentCond, Integer currentPage, Integer pageSize);
}

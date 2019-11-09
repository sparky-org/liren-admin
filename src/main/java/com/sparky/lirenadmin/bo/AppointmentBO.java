package com.sparky.lirenadmin.bo;

import com.sparky.lirenadmin.entity.Appointment;
import com.sparky.lirenadmin.entity.ShopEmployee;

import java.util.Date;

public interface AppointmentBO {

    void createAppointment(Appointment appointment);

    Integer countAppointCustomer(ShopEmployee employee, Date today);
}

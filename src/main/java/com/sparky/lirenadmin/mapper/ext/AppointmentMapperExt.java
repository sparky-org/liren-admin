package com.sparky.lirenadmin.mapper.ext;

import com.sparky.lirenadmin.mapper.AppointmentMapper;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface AppointmentMapperExt extends AppointmentMapper {

    int countAppointCustomerByShop(Long shopNo, Date today);

    int countAppointCustomerByEmp(Long empNo, Date today);
}
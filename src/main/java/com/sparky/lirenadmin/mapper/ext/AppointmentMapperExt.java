package com.sparky.lirenadmin.mapper.ext;

import com.sparky.lirenadmin.bo.cond.QueryAppointmentCond;
import com.sparky.lirenadmin.entity.Appointment;
import com.sparky.lirenadmin.mapper.AppointmentMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AppointmentMapperExt extends AppointmentMapper {

    int countAppointCustomerByShop(Long shopNo, Date today);

    int countAppointCustomerByEmp(Long empNo, Date today);

    Integer countAppointCustomerByCond(@Param("cond") QueryAppointmentCond cond);

    List<Appointment> pagingQueryAppointment(@Param("cond") QueryAppointmentCond cond, @Param("start") Integer start, @Param("pageSize") Integer pageSize);
}
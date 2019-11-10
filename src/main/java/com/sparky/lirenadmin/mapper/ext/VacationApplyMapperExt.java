package com.sparky.lirenadmin.mapper.ext;

import com.sparky.lirenadmin.mapper.VacationApplyMapper;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface VacationApplyMapperExt extends VacationApplyMapper {

    int sumRestEmployeeNumByShop(Long shopNo, Date today);

    int sumRestEmployeeNumByEmp(Long empNo, Date today);
}
package com.sparky.lirenadmin.mapper.ext;

import com.sparky.lirenadmin.mapper.ServiceItemRecordMapper;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface ServiceItemRecordMapperExt extends ServiceItemRecordMapper {

    int sumServiceItemRecordNumByShop(Long shopNo, Date today);

    int sumServiceItemRecordNumByEmp(Long shopNo, Date today);
}
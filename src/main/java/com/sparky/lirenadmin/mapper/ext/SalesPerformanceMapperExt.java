package com.sparky.lirenadmin.mapper.ext;

import com.sparky.lirenadmin.mapper.SalesPerformanceMapper;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface SalesPerformanceMapperExt extends SalesPerformanceMapper {

    int sumSalePerformanceNumByShop(Long shopNo, Date today);

    /**
     * 查询当前员工和该员工下级的业绩数据
     * @param empNo
     * @param today
     * @return
     */
    int sumSalePerformanceNumByEmp(Long empNo, Date today);
}

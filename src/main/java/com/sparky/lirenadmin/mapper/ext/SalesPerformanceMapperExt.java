package com.sparky.lirenadmin.mapper.ext;

import com.sparky.lirenadmin.mapper.SalesPerformanceMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;

@Repository
public interface SalesPerformanceMapperExt extends SalesPerformanceMapper {

    BigDecimal sumSalePerformanceNumByShop(Long shopNo, Date today);

    /**
     * 查询当前员工和该员工下级的业绩数据
     * @param empNo
     * @param today
     * @return
     */
    BigDecimal sumSalePerformanceNumByEmp(Long empNo, Date today);
}

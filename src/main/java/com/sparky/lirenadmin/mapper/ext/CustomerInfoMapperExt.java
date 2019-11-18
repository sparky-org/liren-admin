package com.sparky.lirenadmin.mapper.ext;

import com.sparky.lirenadmin.bo.cond.QueryCustomerCond;
import com.sparky.lirenadmin.entity.CustomerInfo;
import com.sparky.lirenadmin.mapper.CustomerInfoMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerInfoMapperExt extends CustomerInfoMapper {
    int countGrowthOfSpecialInterval(@Param("shopNo") Long shopNo, @Param("empNo") Long empNo, @Param("interval") String interval);

    Integer countCustomerByCond(@Param("cond") QueryCustomerCond cond);

    List<CustomerInfo> pagingQueryCustomerByCond(@Param("cond") QueryCustomerCond cond);
}
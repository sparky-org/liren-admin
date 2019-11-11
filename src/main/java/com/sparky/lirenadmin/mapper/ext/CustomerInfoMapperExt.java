package com.sparky.lirenadmin.mapper.ext;

import com.sparky.lirenadmin.mapper.CustomerInfoMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerInfoMapperExt extends CustomerInfoMapper {
    int countGrowthOfSpecialInterval(@Param("shopNo") Long shopNo, @Param("empNo") Long empNo, @Param("interval") String interval);
}
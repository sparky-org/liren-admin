package com.sparky.lirenadmin.mapper.ext;

import com.sparky.lirenadmin.entity.ServiceItem;
import com.sparky.lirenadmin.mapper.ServiceItemMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceItemMapperExt extends ServiceItemMapper {

    Integer countServiceItem(@Param("shopNo") Long shopNo);

    List<ServiceItem> pagingQueryServiceItem(@Param("shopNo") Long shopNo,@Param("start") Integer start,@Param("pageSize") Integer pageSize);
}
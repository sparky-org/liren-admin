package com.sparky.lirenadmin.mapper.ext;

import com.sparky.lirenadmin.entity.CustomerTrace;
import com.sparky.lirenadmin.mapper.CustomerTraceMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerTraceMapperExt extends CustomerTraceMapper {

    List<CustomerTrace> pagingQueryTrace(Long customerNo, Integer start, Integer length);
}
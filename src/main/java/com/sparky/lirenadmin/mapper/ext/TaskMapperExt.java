package com.sparky.lirenadmin.mapper.ext;

import com.sparky.lirenadmin.bo.cond.QueryTaskCond;
import com.sparky.lirenadmin.entity.Task;
import com.sparky.lirenadmin.entity.TaskExample;
import com.sparky.lirenadmin.entity.po.MyTaskPO;
import com.sparky.lirenadmin.mapper.TaskMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskMapperExt extends TaskMapper {
    int countByQueryCond(@Param("cond") QueryTaskCond cond);

    List<MyTaskPO> pagingByQueryCond(@Param("cond") QueryTaskCond cond);

}
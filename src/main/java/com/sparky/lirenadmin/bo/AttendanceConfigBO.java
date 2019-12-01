package com.sparky.lirenadmin.bo;

import com.sparky.lirenadmin.entity.AttendanceConfig;

public interface AttendanceConfigBO {
    AttendanceConfig getConfig(Long shopNo);

    void createConfig(AttendanceConfig buildAttendanceConfig);

}

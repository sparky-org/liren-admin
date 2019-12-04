package com.sparky;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.sparky.lirenadmin.mapper")
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
//    public static void main(String[] args) {
//        List<String> monthList = new ArrayList<>();
//        Date today = new Date();
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(today);
//        for(int i=0; i<11; i++){
//            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
//            Date day = calendar.getTime();
//            monthList.add(DateUtils.formatDate(day, "yyyy-MM"));
//        }
//        System.out.println(JSONArray.toJSONString(monthList));
//    }
}

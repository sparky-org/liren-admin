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
//        Set<Long> set = new HashSet<>(200000);
//        for (int i=0;i<100001;i++){
//            UUID uuid = UUID.randomUUID();
//            set.add(Math.abs(uuid.getMostSignificantBits()));
//        }
//        System.out.println(set.size());
//    }
}

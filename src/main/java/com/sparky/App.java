package com.sparky;

import com.sparky.lirenadmin.utils.TokenManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
//    public static void main(String[] args) {
//        String s = "hello";
//        String token = TokenManager.sign(s, 100000);
//        System.out.println(token);
//        String sr = TokenManager.unsign(token, String.class);
//        if (null !=sr ){
//            token = TokenManager.sign(s, 100000);
//        }
//    }
}

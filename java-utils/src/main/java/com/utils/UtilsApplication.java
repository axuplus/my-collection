package com.utils;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = {"com.utils.research.mapper"})
public class UtilsApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(UtilsApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

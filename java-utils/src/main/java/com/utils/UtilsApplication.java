package com.utils;

import com.utils.util.support.GlobalExceptionHandler;
import com.utils.util.swagger.SwaggerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableSwagger2
public class UtilsApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(UtilsApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

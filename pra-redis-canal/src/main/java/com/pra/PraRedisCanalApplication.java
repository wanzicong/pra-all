package com.pra;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.pra.mysql.dao")
public class PraRedisCanalApplication {

    public static void main(String[] args) {
        SpringApplication.run(PraRedisCanalApplication.class, args);
    }

}

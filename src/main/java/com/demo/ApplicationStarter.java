package com.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @className: ApplicationStarter
 * @description: TODO
 * @version: 1.0
 * @author: minsky
 * @date: 2022/10/18
 */
@MapperScan("com.demo.dal")
@SpringBootApplication
public class ApplicationStarter {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationStarter.class);
    }
}

package com.xiaoman.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @description: 启动类
 * @author: shuxiaoman
 * @time: 2020/5/6 2:49 下午
 */
@MapperScan("com.xiaoman.springboot.mapper")
@SpringBootApplication
@EnableTransactionManagement
public class SpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
    }

}

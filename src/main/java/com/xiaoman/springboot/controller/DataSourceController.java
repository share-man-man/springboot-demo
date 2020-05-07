package com.xiaoman.springboot.controller;

import com.xiaoman.springboot.bean.HelloBean;
import com.xiaoman.springboot.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @description: 数据库连接
 * @author: shuxiaoman
 * @time: 2020/5/6 5:04 下午
 */
@RestController
@RequestMapping("/datasource")
public class DataSourceController {
    @Autowired
    HelloService helloService;

    /**
     * @description: mybatis连接mysql
     * @author: shuxiaoman
     * @time: 2020/5/6 5:01 下午
     */
    @GetMapping("/mybatisGetList")
    public List<HelloBean> mybatisGetList(){
        List<HelloBean> list = helloService.getUserList();
        System.out.println(list.toString());
        return list;
    }
}

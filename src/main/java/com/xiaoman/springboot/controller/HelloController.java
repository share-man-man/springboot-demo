package com.xiaoman.springboot.controller;

import com.xiaoman.springboot.bean.HelloBean;
import org.springframework.web.bind.annotation.*;

/**
 * @description: 测试类
 * @author: shuxiaoman
 * @time: 2020/4/30 11:35 上午
 */
@RestController
@RequestMapping("/hello")
public class HelloController {

    /**
     * @description: 测试
     * @author: shuxiaoman
     * @time: 2020/4/30 12:18 下午
     */
    @GetMapping("/say")
    public String say(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello: %s!", name);
    }

    /**
     * @description: 测试2
     * @author: shuxiaoman
     * @time: 2020/5/7 2:26 下午
     */
    @GetMapping("/greeting")
    public HelloBean greet(@RequestParam(value = "name", defaultValue = "xiaomanman") String name) {
        return new HelloBean(name, Integer.toString((int) (Math.random() * 10 + 1)), (int) (Math.random() * 20 + 1));
    }
}

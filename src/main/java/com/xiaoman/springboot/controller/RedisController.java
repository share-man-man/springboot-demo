package com.xiaoman.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: redis连接模版
 * @author: shuxiaoman
 * @time: 2020/5/6 5:03 下午
 */
@RestController
@RequestMapping("/redis")
public class RedisController {
    @Autowired
    RedisTemplate redisTemplate;

    /**
     * @description: 设置redis键值
     * @author: shuxiaoman
     * @time: 2020/5/6 5:00 下午
     */
    @GetMapping("/setRedisKey")
    public String redis(@RequestParam(value = "key") String key, @RequestParam String value) {
        redisTemplate.opsForValue().set(key, value);
        return "";
    }

    /**
     * @description: 获取redis键值
     * @author: shuxiaoman
     * @time: 2020/5/6 5:01 下午
     */
    @GetMapping("/getRedisValue")
    public String getValue(@RequestParam(value = "key") String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }
}
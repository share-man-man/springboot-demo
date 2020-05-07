package com.xiaoman.springboot.service.impl;

import com.xiaoman.springboot.bean.HelloBean;
import com.xiaoman.springboot.mapper.UserMapper;
import com.xiaoman.springboot.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description: 实现层
 * @author: shuxiaoman
 * @time: 2020/5/6 2:49 下午
 */
@Service
public class HelloServiceImpl implements HelloService {

    @Autowired
    UserMapper userMapper;

    /**
     * @description: 获取列表
     * @author: shuxiaoman
     * @time: 2020/5/6 2:49 下午
     */
    @Override
    public List<HelloBean> getUserList() {
        return userMapper.getList2();
    }
}

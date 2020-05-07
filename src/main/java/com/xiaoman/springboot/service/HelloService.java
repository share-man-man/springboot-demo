package com.xiaoman.springboot.service;

import com.xiaoman.springboot.bean.HelloBean;

import java.util.List;

/**
 * @description: 业务接口
 * @author: shuxiaoman
 * @time: 2020/5/6 10:58 上午
 */
public interface HelloService {
     /**
      * @description: 获取列表
      * @author: shuxiaoman
      * @time: 2020/5/6 2:48 下午
      */
     List<HelloBean> getUserList();
}

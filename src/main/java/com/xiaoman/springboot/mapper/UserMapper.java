package com.xiaoman.springboot.mapper;

import com.xiaoman.springboot.bean.HelloBean;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @description: mapper层
 * @author: shuxiaoman
 * @time: 2020/4/30 4:49 下午
 */
public interface UserMapper {
    /**
     * @description: 获取列表
     * @author: shuxiaoman
     * @time: 2020/5/6 2:48 下午
     */
    List<HelloBean> getList2();
}

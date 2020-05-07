package com.xiaoman.springboot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @description: //TODO 配置类
 * @author: shuxiaoman
 * @time: 2020/5/7 4:32 下午
 */
@Component
public class GrainFullProperties {
    /**
     * cookie有效期
     * */
    @Value("${com.grainfull.cookie.expires}")
    private float expires;

    public float getExpires() {
        return expires;
    }

    public void setExpires(float expires) {
        this.expires = expires;
    }
}

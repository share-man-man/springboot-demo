package com.xiaoman.springboot.bean;

public class LoginBean {
    /**
     * token
     * */
    private String token;

    /**
     * 有效期
     * */
    private float expires;

    public float getExpires() {
        return expires;
    }

    public void setExpires(float expires) {
        this.expires = expires;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

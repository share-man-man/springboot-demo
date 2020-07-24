package com.xiaoman.springboot.bean;

public class WebSocketMessage {
    private String name;
    private String token;

//    public WebSocketMessage() {
//    }
//
//    public WebSocketMessage(String name) {
//        this.name = name;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

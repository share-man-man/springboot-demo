package com.xiaoman.springboot.bean;

import java.util.ArrayList;

public class WebSocketMessage {
    private String name;
    private String token;
    private ArrayList<String> userTokens;

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

    public ArrayList<String> getUserTokens() {
        return userTokens;
    }

    public void setUserTokens(ArrayList<String> userTokens) {
        this.userTokens = userTokens;
    }
}

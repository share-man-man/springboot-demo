package com.xiaoman.springboot.bean;

/**
 * @description:实体类
 * @author: shuxiaoman
 * @time: 2020/4/30 12:20 下午
 */
public class HelloBean {
    private String id;
    private String name;
    private int age;

    public HelloBean(String id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

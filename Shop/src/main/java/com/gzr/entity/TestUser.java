package com.gzr.entity;

import java.util.Date;

/**
 * Created by GZR on 2017/3/5.
 */
public class TestUser {

    private String id;
    private String name;
    private Date time;

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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}

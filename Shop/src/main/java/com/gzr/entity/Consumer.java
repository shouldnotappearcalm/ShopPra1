package com.gzr.entity;

/**
 * Created by GZR on 2017/3/9.
 */
public class Consumer {

    /**
     * CREATE TABLE consumer (
     `uid` int(11) NOT NULL AUTO_INCREMENT,
     `username` varchar(255) DEFAULT NULL,
     `password` varchar(255) DEFAULT NULL,
     `name` varchar(255) DEFAULT NULL,
     `email` varchar(255) DEFAULT NULL,
     `phone` varchar(255) DEFAULT NULL,
     `addr` varchar(255) DEFAULT NULL,
     `stage` int(11) DEFAULT NULL COMMENT '用户状态： 0未激活 1已经激活',
     `code` varchar(64) DEFAULT NULL COMMENT '激活码',
     `role` varchar(64) default 'consumer' COMMENT '账户身份',
     PRIMARY KEY (`uid`)
     ) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
     */
    private Integer uid;
    private String username;
    private String password;
    private String name;
    private String email;
    private String phone;
    private String addr;
    private Integer stage;
    private String code;
    private String role;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public Integer getStage() {
        return stage;
    }

    public void setStage(Integer stage) {
        this.stage = stage;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

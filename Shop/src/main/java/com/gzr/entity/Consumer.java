package com.gzr.entity;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

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

    /**
     * Bean Validation 中内置的 constraint
     * @Null   被注释的元素必须为 null
     * @NotNull    被注释的元素必须不为 null
     * @AssertTrue     被注释的元素必须为 true
     * @AssertFalse    被注释的元素必须为 false
     * @Min(value)     被注释的元素必须是一个数字，其值必须大于等于指定的最小值
     * @Max(value)     被注释的元素必须是一个数字，其值必须小于等于指定的最大值
     * @DecimalMin(value)  被注释的元素必须是一个数字，其值必须大于等于指定的最小值
     * @DecimalMax(value)  被注释的元素必须是一个数字，其值必须小于等于指定的最大值
     * @Size(max=, min=)   被注释的元素的大小必须在指定的范围内
     * @Digits (integer, fraction)     被注释的元素必须是一个数字，其值必须在可接受的范围内
     * @Past   被注释的元素必须是一个过去的日期
     * @Future     被注释的元素必须是一个将来的日期
     * @Pattern(regex=,flag=)  被注释的元素必须符合指定的正则表达式
     * Hibernate Validator 附加的 constraint
     * @NotBlank(message =)   验证字符串非null，且长度必须大于0
     * @Email  被注释的元素必须是电子邮箱地址
     * @Length(min=,max=)  被注释的字符串的大小必须在指定的范围内
     * @NotEmpty   被注释的字符串的必须非空
     * @Range(min=,max=,message=)  被注释的元素必须在合适的范围内
     */
    private Integer uid;
    @NotBlank(message ="{registValid.usernameBlank}")
    @Length(min = 2,max = 16,message = "{registValid.usernameLength}")
    private String username;
    @NotBlank(message ="{registValid.passwordBlank}")
    @Pattern(regexp="(?!^\\d+$)(?!^[a-zA-Z]+$)(?!^[_#@+-/.?]+$).{6,18}"  , message="密码必须是6~18位数字字母的组合")
    private String password;
    @NotBlank(message ="{registValid.nameBlank}")
    private String name;
    @NotBlank(message ="{registValid.emailBlank}")
    @Email(message = "{registValid.emailFormat}")
    private String email;
    @NotBlank(message ="{registValid.phoneBlank}")
    @Pattern(regexp = "^((13[0-9])|(15[^4])|(14[5,7])|(18[0,2,3,5-9])|(17[0-8]))\\d{8}$",message = "{registValid.phoneFormat}")
    private String phone;
    @NotBlank(message ="{registValid.addrBlank}")
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

    @Override
    public String toString() {
        return "Consumer{" +
                "uid=" + uid +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", addr='" + addr + '\'' +
                ", stage=" + stage +
                ", code='" + code + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}

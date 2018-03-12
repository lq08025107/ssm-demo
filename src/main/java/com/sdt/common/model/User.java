package com.sdt.common.model;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable{
    private static final long serialVersionUID = 1L;
    //0禁止登陆
    public static final Long _0 = new Long(0);
    //1有效
    public static final Long _1 = new Long(1);

    private Long id;

    private String nickname;

    private String email;

    private String pswd;

    private Date createTime;

    private Date lastLoginTime;

    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPswd() {
        return pswd;
    }

    public void setPswd(String pswd) {
        this.pswd = pswd;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User() {
    }

    public User(User user){
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.pswd = user.getPswd();
        this.createTime = user.getCreateTime();
        this.lastLoginTime = user.getLastLoginTime();
    }
    @Override
    public String toString() {
        return JSONObject.toJSONString(this).toString();
    }
}

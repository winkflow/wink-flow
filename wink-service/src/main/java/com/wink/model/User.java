package com.wink.model;

public class User extends BaseModel {
    private static final long serialVersionUID = -3373924367373984785L;


    private String userName;

    private String password;

    private String nickname;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
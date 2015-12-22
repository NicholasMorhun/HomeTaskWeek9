package com.geekhub.hw8.beans;

public class CloudPocketUser {
    private int id;
    private String login;
    private String passwordMd5;

    public CloudPocketUser(int id, String username, String passwordMd5) {
        this.id = id;
        this.login = username;
        this.passwordMd5 = passwordMd5;
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPasswordMd5() {
        return passwordMd5;
    }

}

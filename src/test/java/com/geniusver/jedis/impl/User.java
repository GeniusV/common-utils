package com.geniusver.jedis.impl;

import java.io.Serializable;

/**
 * Copyright 2017 GeniusV
 * All rights reserved.
 * Created by GeniusV on 8/9/17.
 */
class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private String userName;
    private String password;

    public User() {
    }


    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

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
}

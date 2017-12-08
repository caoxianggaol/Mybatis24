package com.kaishengit.entity;

/**
 * Created by xiaogao on 2017/12/8.
 */
public class User {

    private Integer id;

    private String userName;

    private String address;


    public User() {

    }


    public User(Integer id, String userName, String address) {
        this.id = id;
        this.userName = userName;
        this.address = address;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

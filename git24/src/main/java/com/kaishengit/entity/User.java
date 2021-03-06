package com.kaishengit.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xiaogao on 2017/10/25.
 */
         /*开启二级缓存，实现序列化接口*/
public class User implements Serializable{

    private Integer id;
    private String userName;
    private String address;
    private String password;
    private Integer deptId;
    private  Dept dept;
    private List<Tag> tagList;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public Dept getDept() {
        return dept;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
    }

    public List<Tag> getTagList() {
        return tagList;
    }

    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", userName='" + userName + '\'' + ", address='" + address + '\'' + ", password='" + password + '\'' + ", deptId=" + deptId + ", dept=" + dept + '}';
    }
}

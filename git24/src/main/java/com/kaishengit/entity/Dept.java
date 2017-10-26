package com.kaishengit.entity;

import java.io.Serializable;

/**
 * Created by xiaogao on 2017/10/25.
 */
public class Dept implements Serializable{

    private Integer id;
    private String deptName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    @Override
    public String toString() {
        return "Dept{" + "id=" + id + ", deptName='" + deptName + '\'' + '}';
    }
}

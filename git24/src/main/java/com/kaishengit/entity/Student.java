package com.kaishengit.entity;

/**
 * Created by xiaogao on 2017/10/23.
 */
public class Student {

    private Integer id;
    private String stuName;
    private Integer stuAge;

    public Student() {}

    public Student(String stuName, Integer stuAge) {
        this.stuName = stuName;
        this.stuAge = stuAge;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public Integer getStuAge() {
        return stuAge;
    }

    public void setStuAge(Integer stuAge) {
        this.stuAge = stuAge;
    }

    @Override
    public String toString() {
        return "Student{" + "id=" + id + ", stuName='" + stuName + '\'' + ", stuAge=" + stuAge + '}';
    }
}

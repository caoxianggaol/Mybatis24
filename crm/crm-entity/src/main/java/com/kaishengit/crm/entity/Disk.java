package com.kaishengit.crm.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class Disk implements Serializable {

    public static final String DISK_TYPE_FOLDER = "dir";
    public static final String DISK_TYPE_FILE = "file";

    private Integer id;

    private String name;

    private String fileSize;

    private Integer pId;

    private String type;

    private Date updateTime;

    private Integer accountId;

    private Integer downlooadCount;

    private String password;

    private String saveName;

    private String md5;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getDownlooadCount() {
        return downlooadCount;
    }

    public void setDownlooadCount(Integer downlooadCount) {
        this.downlooadCount = downlooadCount;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSaveName() {
        return saveName;
    }

    public void setSaveName(String saveName) {
        this.saveName = saveName;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }
}
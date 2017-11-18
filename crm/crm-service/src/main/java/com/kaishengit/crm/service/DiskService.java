package com.kaishengit.crm.service;

import com.kaishengit.crm.entity.Disk;
import com.kaishengit.crm.exception.ServiceException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 网盘业务层
 * Created by xiaogao on 2017/11/17.
 */
public interface DiskService {
    /**
     * 保存文件夹
     * @param disk
     */
    void saveNewFolder(Disk disk);

    /**
     * 根据pId获得子文件夹及文件
     * @param pid
     * @return
     */
    List<Disk> findDiskBypId(Integer pid);

    /**
     * 根据id获取disk对象
     * @param id
     * @return
     */
    Disk findById(Integer id);

    /**
     * 上传文件
     * @param inputStream 文件输入流
     * @param fileSize 文件大小 单位字节
     * @param fileName 文件名称
     * @param pId 所属文件夹的id
     * @param accountId 上传文件的用户ID
     */
    void saveNewFile(InputStream inputStream, long fileSize, String fileName, Integer pId, Integer accountId);

    /**
     * 根据id获取文件的输入流
     * @param id
     * @return
     */
    InputStream downloadFile(Integer id) throws ServiceException,IOException;
}

package com.kaishengit.crm.service.impl;

import com.kaishengit.crm.entity.Disk;
import com.kaishengit.crm.example.DiskExample;
import com.kaishengit.crm.exception.ServiceException;
import com.kaishengit.crm.mapper.DiskMapper;
import com.kaishengit.crm.service.DiskService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.IIOException;
import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 网盘业务层
 * Created by xiaogao on 2017/11/17.
 */
@Service
public class DiskServiceImpl implements DiskService {

    @Autowired
    private DiskMapper diskMapper;

    @Value("${uploadfile.path}")
    private String saveFilePath;

    /**
     * 保存文件夹
     *
     * @param disk
     */
    @Override
    public void saveNewFolder(Disk disk) {
        disk.setType(Disk.DISK_TYPE_FOLDER);
        disk.setUpdateTime(new Date());
        diskMapper.insertSelective(disk);
    }

    @Override
    public List<Disk> findDiskBypId(Integer pid) {
        DiskExample diskExample = new DiskExample();
        diskExample.createCriteria().andPIdEqualTo(pid);
        diskExample.setOrderByClause("type asc");

        return diskMapper.selectByExample(diskExample);
    }

    /**
     * 根据id获取disk对象
     *
     * @param id
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Disk findById(Integer id) {
        return diskMapper.selectByPrimaryKey(id);
    }

    /**
     * 上传文件
     *
     * @param inputStream 文件输入流
     * @param fileSize    文件大小 单位字节
     * @param fileName    文件名称
     * @param pId         所属文件夹的id
     * @param accountId   上传文件的用户ID
     */
    @Override
    public void saveNewFile(InputStream inputStream, long fileSize, String fileName, Integer pId, Integer accountId) {
        Disk disk = new Disk();
        disk.setType(Disk.DISK_TYPE_FILE);//类型
        disk.setDownlooadCount(0);//下载次数
        disk.setAccountId(accountId);
        disk.setpId(pId);
        disk.setUpdateTime(new Date());
        disk.setName(fileName);
        //字节转换为可读大小
        disk.setFileSize(FileUtils.byteCountToDisplaySize(fileSize));


        //重命名
        String newFileName = UUID.randomUUID() + fileName.substring(fileName.lastIndexOf("."));
    try {
        //本地磁盘 需要选择 所以在实现类的config.properties中配置
        //获取输出流 new File(1,2)参数1为文件夹路径 2为文件名（重命名）
        FileOutputStream outputStream = new FileOutputStream(new File(saveFilePath, newFileName));
        IOUtils.copy(inputStream, outputStream);

        outputStream.flush();
        outputStream.close();
        inputStream.close();
    } catch(IOException ex) {
        throw new ServiceException(ex,"上传文件到本地磁盘失败");
    }
        disk.setSaveName(newFileName);
        diskMapper.insertSelective(disk);
        //FastDFS
        //cloud
    }

    /**
     * 根据id获取文件的输入流
     *
     * @param id
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public InputStream downloadFile(Integer id) throws ServiceException,IOException {
        Disk disk = diskMapper.selectByPrimaryKey(id);
        if(disk == null || disk.getType().equals(Disk.DISK_TYPE_FOLDER)) {
            throw new ServiceException(id+"对应的文件不存在或已被删除");
        }

        //更新下载数量
        disk.setDownlooadCount(disk.getDownlooadCount() + 1);
        diskMapper.updateByPrimaryKeySelective(disk);

        FileInputStream inputStream = new FileInputStream(new File(saveFilePath,disk.getSaveName()));
        return inputStream;
    }


}

package com.kaishengit.service.Impl;

import com.kaishengit.dao.StudentDao;
import com.kaishengit.entity.Student;
import com.kaishengit.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by xiaogao on 2017/10/30.
 */
/*交给bean管理*/
@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    /*注入方法一  其他注入1.JSR330注解-》@Inject来自—》javax.inject.Inject
                       2.JSR250注解-》@Resource来自—》javax.annotation.Resource */
    @Autowired
    private StudentDao studentDao;


      /*注入方法二*/
   /* @Autowired
    public void setStudentDao(StudentDao studentDao) {
        this.studentDao = studentDao;
    }*/
  /*方法三 构造方法*/
 /* @Autowired
   public StudentServiceImpl(StudentDao studentDao) {
       this.studentDao = studentDao;
   }*/


   /* @Override
    public void save() {
        studentDao.save();
    }*/


    @Override
    public void save(Student student) {

        studentDao.save(student);
        if(1==1) {
            throw new RuntimeException();
        }
        studentDao.save(student);


    }
}

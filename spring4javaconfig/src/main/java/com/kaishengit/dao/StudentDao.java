package com.kaishengit.dao;


import com.kaishengit.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Bean管理
 * Created by xiaogao on 2017/10/30.
 */
/*@Lazy 懒加载*/
/*@Scope("prototype") 不为单例*/
/* JSR330--》java中的 @Named  Spring内置注解 @Component  @Service  @Repository */
@Repository
public class StudentDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void save(Student student) {
       String sql = "insert into student(stu_name, stu_age) values(?,?)";
       jdbcTemplate.update(sql,student.getStuName(),student.getStuAge());
    }

}

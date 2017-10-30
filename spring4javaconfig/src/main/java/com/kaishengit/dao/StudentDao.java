package com.kaishengit.dao;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import javax.inject.Named;

/**
 * Bean管理
 * Created by xiaogao on 2017/10/30.
 */
/*@Lazy 懒加载*/
/*@Scope("prototype") 不为单例*/
/* JSR330--》java中的 @Named  Spring内置注解 @Component  @Service  @Repository */
@Repository
public class StudentDao {

    public void save() {
        System.out.println("--------------");
    }
}

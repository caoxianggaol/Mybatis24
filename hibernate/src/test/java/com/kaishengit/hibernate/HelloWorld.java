package com.kaishengit.hibernate;

import com.kaishengit.pojo.User;
import com.kaishengit.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.Test;

import java.util.List;

/**
 * Created by xiaogao on 2017/11/27.
 */
public class HelloWorld {

    @Test
    public void hello() {
        //读取classpath中名称为hibernate.cfg.xml的配置文件
        Configuration configuration = new Configuration().configure();
        //创建SessionFactory 4.x
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();
        SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        /*5.x以上版本*/
        /*ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .configure()
                .build();

        SessionFactory sessionFactory = new MetadataSources(serviceRegistry)
                .buildMetadata()
                .buildSessionFactory(); */
       //创建Session
        Session session = sessionFactory.getCurrentSession();
        //创建事物
        Transaction transaction = session.beginTransaction();
        //执行操作
        User user = new User();
        user.setName("小明");
        user.setAge(15);
        user.setAddress("甘肃");

        session.save(user);

        //提交或回滚事物
        transaction.commit();
        //释放资源
        //session.close();
    }

    @Test
    public void findById() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        //.,...
        User user = (User) session.get(User.class,3);
        System.out.println(user);

        session.getTransaction().commit();
    }

    @Test
    public void update() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();

        User user = (User) session.get(User.class,2);
        user.setAge(20);

        session.getTransaction().commit();
    }

    @Test
    public void delete() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();

       User user = (User) session.get(User.class,2);
        session.delete(user);

        session.getTransaction().commit();
    }

    @Test
    public void findAll() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();

        //HQL
        String hql = "from User order by id desc ";
        Query query = session.createQuery(hql);
        List<User>userList = query.list();

        for(User user : userList) {
            System.out.println(user);
        }

        session.getTransaction().commit();
    }

}

package com.kaishengit.hibernate;

import com.kaishengit.pojo.User;
import com.kaishengit.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.annotations.SourceType;
import org.hibernate.criterion.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by xiaogao on 2017/11/28.
 */
public class CriteriaTest {

    private Session session;

    @Before
    public void before() {
        session = HibernateUtil.getSession();
        session.getTransaction().begin();
    }

    public void after() {
        session.getTransaction().commit();
    }


    @Test
    public void findAll() {
        Criteria criteria = session.createCriteria(User.class);
        List<User> userList = criteria.list();
        showlist(userList);
    }

    @Test
    public void findById() {
        Criteria criteria = session.createCriteria(User.class);
        criteria.add(Restrictions.eq("id",3));
       /*两种都可以*/
       // List<User> user = criteria.list();
        User user = (User) criteria.uniqueResult();
        System.out.println(user);
    }

    @Test  //加where条件
    public void findBy() {
        Criteria criteria = session.createCriteria(User.class);
       /*需要传入criterion 但它是接口 所以使用实现类Restriction
       id属性  相当于写了 where id = 3 */
        /*criteria.add(Restrictions.eq("id",3));
        User user = (User) criteria.uniqueResult();
        System.out.println(user);*/

        /*where user like '%小明%' add()方法组合的多个where条件之间是and关系 。。*/
        /*criteria.add(Restrictions.like("name","小明", MatchMode.ANYWHERE));
        criteria.add(Restrictions.eq("age",23));
        List<User> userList = criteria.list();
        showlist(userList);*/

        /*or 的关系(相同的单列)*/
        /*criteria.add(Restrictions.in("age", Arrays.asList(20,15)));
        List<User> userList = criteria.list();
        showlist(userList);*/

        /*or的多列关系 方式一 Restrictions.or(多个 Criterion 对象) */
       /* Criterion userNameCriterion = Restrictions.eq("name","小明");
        Criterion userAgeCriterion = Restrictions.eq("age",20);
        criteria.add(Restrictions.or(userNameCriterion,userAgeCriterion));*/

       /*or的多列关系  方式二  Disjunction对象的add()方法填进去的也是or的关系*/
        Criterion userNameCriterion = Restrictions.eq("name","小明");
        Criterion userAgeCriterion = Restrictions.eq("age",20);

        /*Disjunction-》继承了junction-》实现了Criterion*/
        Disjunction disjunction = Restrictions.disjunction();
        disjunction.add(userNameCriterion);
        disjunction.add(userAgeCriterion);

        criteria.add(disjunction);

        List<User> userList = criteria.list();
        showlist(userList);
    }

    @Test  //排序
    public void findOrder() {
        Criteria criteria = session.createCriteria(User.class);
        /*单个排序*/
        //criteria.addOrder(Order.asc("id"));

        /*多个排序 order by 后可以跟多个条件 谁在前谁的优先级高*/
        criteria.addOrder(Order.desc("age"));
        criteria.addOrder(Order.asc("name"));

        List<User> userList = criteria.list();
        showlist(userList);
    }


    @Test  //分页
    public void page() {
        Criteria criteria = session.createCriteria(User.class);

        /*排序*/
        criteria.addOrder(Order.desc("age"));
        criteria.setFirstResult(0);//从0开始
        criteria.setMaxResults(5);

        List<User> userList = criteria.list();
        showlist(userList);
    }


    @Test  //聚合函数
    public void count() {
        Criteria criteria = session.createCriteria(User.class);

        /*Projection接口->实现类Projections  相当于count(*)
        * 通过set不能添加多个条件 因为前面的会覆盖后面的*/
       /* criteria.setProjection(Projections.rowCount());
        criteria.setProjection(Projections.max("age"));
        Long count = (Long) criteria.uniqueResult();
        System.out.println(count);*/

       /*正确做法*/
       //Projections.distinct(); 去重
       ProjectionList projectionList = Projections.projectionList();
       projectionList.add(Projections.rowCount());
       projectionList.add(Projections.avg("age"));

       criteria.setProjection(projectionList);

       Object[] data = (Object[]) criteria.uniqueResult();
        System.out.println("RowCount -> “" + data[0]);
        System.out.println("Avg -> “" + data[1]);


    }


    private void showlist(List<User> userList) {
        for(User user : userList) {
            System.out.println(user);
        }
    }
}

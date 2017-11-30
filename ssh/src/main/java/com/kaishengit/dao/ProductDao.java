package com.kaishengit.dao;

import com.kaishengit.pojo.Product;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.util.List;


/**
 * 单个搜索
 * Created by xiaogao on 2017/11/29.
 */
@Repository
public class ProductDao extends BaseDao<Product,Integer>{
   /* public List<Product> findByProducName(String productName) {
        Criteria criteria = getSession().createCriteria(Product.class);
        *//*MatchMode.ANYWHERE 在任何查询的 字段前后加% 即like %xxx% 查询*//*
        criteria.add(Restrictions.like("productName",productName, MatchMode.ANYWHERE));
        return criteria.list();

    }*/
/*=====================================================================================*/
   /* @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }
*/

    /*public void save(Product product) {
        getSession().saveOrUpdate(product);
    }*/

   /* public Product findById(Integer id) {
        return (Product) getSession().get(Product.class,id);
    }*/

   /* public void delete(Integer id) {
        getSession().delete(findById(id));
    }
*/

   /*public void delete(Product product) {
       getSession().delete(product);
   }*/

   /* public List<Product> findAll() {
        String hql = "from Product order by id desc";
        Query query = getSession().createQuery(hql);
        query.setFirstResult(0);
        query.setMaxResults(100);

        return query.list();
    }*/
}

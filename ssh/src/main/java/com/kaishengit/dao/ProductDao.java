package com.kaishengit.dao;

import com.kaishengit.pojo.Product;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.util.List;


/**
 * Created by xiaogao on 2017/11/29.
 */
@Repository
public class ProductDao {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }


    public void save(Product product) {
        getSession().saveOrUpdate(product);
    }

    public Product findById(Integer id) {
        return (Product) getSession().get(Product.class,id);
    }

    public void delete(Integer id) {
        getSession().delete(findById(id));
    }

    public List<Product> findAll() {
        String hql = "from Product order by id desc";
        Query query = getSession().createQuery(hql);
        query.setFirstResult(0);
        query.setMaxResults(100);

        return query.list();
    }
}

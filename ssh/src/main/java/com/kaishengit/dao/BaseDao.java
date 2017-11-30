package com.kaishengit.dao;

import com.kaishengit.pojo.Product;
import com.kaishengit.util.RequestQuery;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Created by xiaogao on 2017/11/30.
 */
/*主键是可序列化的，范型中extends即是继承又是实现*/
public abstract class BaseDao<T,PK extends Serializable> {

    @Autowired
    private SessionFactory sessionFactory;

    private Class<T> entityClazz;

    public BaseDao() {
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        entityClazz = (Class<T>) parameterizedType.getActualTypeArguments()[0];
    }

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public void save(T entity) {
        getSession().saveOrUpdate(entity);
    }

    public T findById(PK id) {
        return (T) getSession().get(entityClazz,id);
    }

    public void deleteById(PK id) {
        getSession().delete(findById(id));
    }

    public void deleteProduct(T entity) {
        getSession().delete(entity);
    }

    public List<T> findAll() {
        Criteria criteria = getSession().createCriteria(entityClazz);
        return criteria.list();
    }

    public List<T> findByPage(Integer start,Integer size) {
        Criteria criteria = getSession().createCriteria(entityClazz);
        criteria.setFirstResult(start);
        criteria.setMaxResults(size);
        return criteria.list();
    }

    public List<T> findByRequestQueryList(List<RequestQuery> requestQueryList) {
        Criteria criteria = getSession().createCriteria(entityClazz);
        for(RequestQuery requestQuery : requestQueryList) {
            String paramName = requestQuery.getParameterName();
            String type = requestQuery.getEqualType();
            Object value = requestQuery.getValue();
            criteria.add(createCriterion(paramName,type,value));
        }
        return criteria.list();
    }

    public Criterion createCriterion(String paramName, String type, Object value) {
        if("eq".equalsIgnoreCase(type)) {
            return Restrictions.eq(paramName,value);
        } else if("like".equalsIgnoreCase(type)) {
            return Restrictions.like(paramName,value.toString(), MatchMode.ANYWHERE);
        } else if("gt".equalsIgnoreCase(type)) {
            return Restrictions.gt(paramName,value);
        } else if("ge".equalsIgnoreCase(type)) {
            return Restrictions.ge(paramName,value);
        } else if("lt".equalsIgnoreCase(type)) {
            return Restrictions.lt(paramName,value);
        } else if("le".equalsIgnoreCase(type)) {
            return Restrictions.le(paramName,value);
        }
        return null;
    }
}

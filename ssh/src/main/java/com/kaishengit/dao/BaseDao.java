package com.kaishengit.dao;

import com.kaishengit.pojo.Product;
import com.kaishengit.util.Page;
import com.kaishengit.util.RequestQuery;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.*;
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
        //创建条件
        countWhereConditions(requestQueryList, criteria);
        return criteria.list();
    }

    public Criterion createCriterion(String paramName, String type, Object value) {
        if(paramName.contains("_or_")) {
            //price_or_marketPrice --> price,marketPrice
            String[] paramNames = paramName.split("_or_");
            Disjunction disjunction = Restrictions.disjunction();
            for(String name : paramNames) {
                disjunction.add(builderWhere(name,type,value));
            }
            return disjunction;
        } else {
            return builderWhere(paramName,type,value);
        }
    }

    private Criterion builderWhere(String paramName, String type, Object value) {
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
/*====================================分页=========================================*/

    /**
     * count全部没有条件 获得总记录数
     * @return
     */
    public Long count() {
        Criteria criteria = getSession().createCriteria(entityClazz);
        criteria.setProjection(Projections.rowCount());
        return (Long) criteria.uniqueResult();
    }

    /**
     * 根据查询条件获得总记录数
     * @return
     */
    public Long count(List<RequestQuery> requestQueryList) {
        Criteria criteria = getSession().createCriteria(entityClazz);
        countWhereConditions(requestQueryList, criteria);
        criteria.setProjection(Projections.rowCount());
        return (Long) criteria.uniqueResult();
    }

    /**
     *      //创建要count的条件
     * @param requestQueryList
     * @param criteria
     */
    private void countWhereConditions(List<RequestQuery> requestQueryList, Criteria criteria) {
        for(RequestQuery requestQuery : requestQueryList) {
            String paramName = requestQuery.getParameterName();
            String type = requestQuery.getEqualType();
            Object value = requestQuery.getValue();
            criteria.add(createCriterion(paramName,type,value));
        }
    }

    public Page<T> findByRequestQueryAndPage(List<RequestQuery> requestQueryList, Integer pageNo) {
        //计算总记录数（根据查询条件）
        Long count = count(requestQueryList);
        //根据总记录数计算总页数
        Page<T> page = new Page<>(count.intValue(),20,pageNo);
        //给定页号获取起始行数 util的构造方法中已算出
        //page.getStart();
        //查询
        Criteria criteria = getSession().createCriteria(entityClazz);

        countWhereConditions(requestQueryList,criteria);
        criteria.setFirstResult(page.getStart());
        criteria.setMaxResults(20);

        List<T> resultList = criteria.list();

        page.setItems(resultList);
        return page;
    }

}

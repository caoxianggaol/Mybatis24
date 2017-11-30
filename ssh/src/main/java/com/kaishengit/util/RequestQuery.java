package com.kaishengit.util;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by xiaogao on 2017/11/30.
 */
public class RequestQuery {

    private String parameterName;
    private String equalType;
    private Object value;

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public String getEqualType() {
        return equalType;
    }

    public void setEqualType(String equalType) {
        this.equalType = equalType;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

public static List<RequestQuery> builderRequestQuery(HttpServletRequest request) {

        List<RequestQuery> requestQueryList = new ArrayList<>();

        //获取所有查询参数的键值  Enumeration<String>类似迭代器
    // q_proudectName_like解析 -> RequestQuery ->Restrictions
    Enumeration<String> enumeration = request.getParameterNames();
    while(enumeration.hasMoreElements()) {
        String queryKey = enumeration.nextElement();//请求的jian
        String value = request.getParameter(queryKey);//根据建获取值
        if(queryKey.startsWith("q_") && !"".equals(value) && value != null) {
            //q_xxx_eq_s
            String[] array = queryKey.split("_");
            if(array == null || array.length != 4) {
                throw new IllegalArgumentException("查询条件异常:" + queryKey);
            }
            RequestQuery requestQuery = new RequestQuery();
            requestQuery.setParameterName(array[1]);
            requestQuery.setEqualType(array[2]);
            requestQuery.setValue(tranValueType(array[3],value));

            requestQueryList.add(requestQuery);
        }
    }
    return requestQueryList;
}

    private static Object tranValueType(String valueType,String value) {
        if("s".equalsIgnoreCase(valueType)) {
            return value;
        } else if("d".equalsIgnoreCase(valueType)) {
            return Double.valueOf(value);
        } else if("f".equalsIgnoreCase(valueType)) {
            return Float.valueOf(value);
        } else if("i".equalsIgnoreCase(valueType)) {
            return Integer.valueOf(value);
        } else if("bd".equalsIgnoreCase(valueType)) {
            return new BigDecimal(value);
        }
        return null;
    }
}
package com.kaishengit.proxy;

/**
 * Created by xiaogao on 2017/10/29.
 */
/*只能代理联想，依赖具体的实现类*/
public class LenovoProxy implements Sales {

    /*创建实现类对象*/
    private Lenovo lenovo = new Lenovo();
    @Override
    public void salePc() {
        lenovo.salePc();
    }
}

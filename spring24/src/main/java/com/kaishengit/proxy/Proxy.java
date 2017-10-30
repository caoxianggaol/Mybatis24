package com.kaishengit.proxy;



/**
 * Created by xiaogao on 2017/10/29.
 */
public class Proxy implements Sales {

    /*依赖接口*/
    private Sales sales;

    public Proxy(Sales sales){
        this.sales = sales;
    }


    /* 代理模式
    即可以销售电脑还可以加一些自己的东西*/
        @Override
        public void salePc() {
            System.out.println("加价100");
            sales.salePc();
        }
}

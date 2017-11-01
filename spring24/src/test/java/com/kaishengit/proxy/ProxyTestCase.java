package com.kaishengit.proxy;

import com.kaishengit.proxy.Dell;
import com.kaishengit.proxy.Lenovo;
import com.kaishengit.proxy.LenovoProxy;
import com.kaishengit.proxy.Proxy;
import org.junit.Test;

/**
 * Created by xiaogao on 2017/10/29.
 */
public class ProxyTestCase {

    @Test
    public void proxy(){

        /*依赖接口并重写了构造方法后可直接创建被代理对象
        并创建代理对象将被代理对象传入即可*/
      /*  Lenovo lenovo = new Lenovo();
        Proxy proxy = new Proxy(lenovo);
        proxy.salePc();*/

      Dell dell = new Dell();
      Proxy proxy = new Proxy(dell);
      proxy.salePc();

       /* LenovoProxy proxy = new LenovoProxy();
        proxy.salePc();*/
    }
}

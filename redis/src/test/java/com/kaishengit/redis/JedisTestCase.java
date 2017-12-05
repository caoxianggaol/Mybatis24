package com.kaishengit.redis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Jedis  是 Redis 的客户端之一
 * Created by xiaogao on 2017/12/4.
 */
public class JedisTestCase {

    /*redis普通连接*/
    @Test
    public void setString() {
        Jedis jedis = new Jedis("192.168.27.111",6379);
        //jedis.set("name","tom");
        jedis.set("name2","丽丽");
        jedis.close();
    }

    @Test
    public void getString() {
        Jedis jedis = new Jedis("192.168.27.111",6379);
        String name = jedis.get("name2");
        System.out.println(name);
    }

    @Test
    public void setList() {
        Jedis jedis = new Jedis("192.168.27.111",6379);
        jedis.lpush("user:1:address","北京","焦作");
        jedis.close();
    }

    /*@Test
    public void getList() {
        Jedis jedis = new Jedis("192.168.27.111",6379);
        List<String> result = jedis.lrange("user:1:address",0,-1);
        for(String str : result) {
            System.out.println(str);
        }
        jedis.close();
    }*/
    @Test
    public void getList() {
        try(Jedis jedis = new Jedis("192.168.27.111",6379)) {
            List<String> result = jedis.lrange("user:1:address", 0, -1);
            for (String str : result) {
                System.out.println(str);
            }
        } catch(Exception ex) {
            ex.fillInStackTrace();
        }
    }
/*==============================================================*/

    /*redis连接池连接*/
    @Test
    public void pooled() {
    GenericObjectPoolConfig config = new GenericObjectPoolConfig();
    config.setMaxTotal(10);
    config.setMaxIdle(3);

    JedisPool jedisPool = new JedisPool(config,"192.168.27.111",6379);
    Jedis jedis = jedisPool.getResource();//从连接池中取连接

    Map<String,String> data = new HashMap<String, String>();
    data.put("id","1");
    data.put("age","23");
    data.put("address","北京");

    jedis.hmset("user:102",data);

    jedis.close();//关闭链接
    jedisPool.destroy();//销毁链接池

    }


}

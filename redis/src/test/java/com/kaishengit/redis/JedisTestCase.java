package com.kaishengit.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * Jedis  是 Redis 的客户端之一
 * Created by xiaogao on 2017/12/4.
 */
public class JedisTestCase {

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

    @Test
    public void getList() {
        Jedis jedis = new Jedis("192.168.27.111",6379);
        List<String> result = jedis.lrange("user:1:address",0,-1);
        for(String str : result) {
            System.out.println(str);
        }
        jedis.close();
    }
/*==============================================================*/
    



}

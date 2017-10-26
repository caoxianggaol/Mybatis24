package com.kaishengit;

import com.kaishengit.entity.User;
import com.kaishengit.mapper.UserMapper;
import com.kaishengit.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;

/**
 * Created by xiaogao on 2017/10/26.
 */
public class CacheTestCase {

    @Test
    public void levelOneCache() {

        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
/*两次查询只有一，第二次是在一级缓存中获取的，一级缓本省就是开启的
   范围：在同一个SqlSession中查询同一个对象多次，第二次以后都在一级缓存中获取的**/
        User user = userMapper.findById(1);
       // User user2 = userMapper.findById(1);
        System.out.println(user);

        sqlSession.close();

      //----------------------------------------------------------

        SqlSession sqlSession2 = MyBatisUtil.getSqlSession();
        UserMapper userMapper2 = sqlSession2.getMapper(UserMapper.class);

        User user2 = userMapper2.findById(1);

        System.out.println(user2);
        sqlSession.close();

        /*以上两段代码合起来查了两次，则证明了一级缓存的范围*/

        /* 开启二级缓存后，以上两段代码合起来查，第二次查询会看到--Cache Hit Ratio（命中率），
        则说明二级缓存在起作用  范围：在同一个SqlSessionFactory中产生的SQLSession之间共用
        更改默认配置 1.存储2048个对象 2.不是只读缓存 3.缓存策略为FIFO 4.每隔60秒刷新一次缓存
       <cache size="2048" readOnly="false" eviction="FIFO" flushInterval="60000"/>*/

    }

}

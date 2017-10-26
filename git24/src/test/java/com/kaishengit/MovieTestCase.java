package com.kaishengit;

import com.kaishengit.entity.Movie;
import com.kaishengit.mapper.MovieMapper;
import com.kaishengit.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiaogao on 2017/10/25.
 */
public class MovieTestCase {

    private SqlSession sqlSession;
    private MovieMapper movieMapper;

    @Before
    public void init() {

        sqlSession = MyBatisUtil.getSqlSession();
        movieMapper =sqlSession.getMapper(MovieMapper.class);
    }

    @After
    public void close() {
        sqlSession.commit();
    }

    @Test
    public void find() {

        String title = "%平凡%";
        List<Movie> movieList = movieMapper.find(title);
        for (Movie movie : movieList) {
            System.out.println(movie);
        }

            System.out.println("---------" + movieList.size());
    }

    @Test
    public void findByParam(){

        Map<String,Object> seachParam = new HashMap<>();

       seachParam.put("title","%平凡%");
       //seachParam.put("director","李四");

        List<Movie> movieList = movieMapper.findByParam(seachParam);
        for(Movie movie : movieList) {
            System.out.println(movie);
        }
    }

    @Test
    public void findByIdList() {
        List<Integer> idList = Arrays.asList(1,2,3);

        List<Movie> movieList = movieMapper.findByIdList(idList);
        for(Movie movie : movieList) {
            System.out.println(movie);

        }
    }
}

package com.kaishengit.mapper;

import com.kaishengit.entity.Movie;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by xiaogao on 2017/10/25.
 */
public interface MovieMapper {

    List<Movie> find(@Param("title") String title);
    /*title和其他，多个搜索*/
    List<Movie> findByParam(Map<String, Object> searchParam);
    List<Movie> findByIdList(@Param("idList") List<Integer> idList);
}

package com.kaishengit.mapper;

import com.kaishengit.entity.Kaola;
import com.kaishengit.entity.KaolaExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

public interface KaolaMapper {
    long countByExample(KaolaExample example);

    int deleteByExample(KaolaExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Kaola record);

    int insertSelective(Kaola record);

    List<Kaola> selectByExample(KaolaExample example);

    Kaola selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Kaola record, @Param("example") KaolaExample example);

    int updateByExample(@Param("record") Kaola record, @Param("example") KaolaExample example);

    int updateByPrimaryKeySelective(Kaola record);

    int updateByPrimaryKey(Kaola record);

    /*查分类*/
     List<Kaola> findWithType();
     /*搜索*/
     List<Kaola> findByQueryParamWithType(Map<String, Object> queryParam);
     List<String> findAllPlace();
}
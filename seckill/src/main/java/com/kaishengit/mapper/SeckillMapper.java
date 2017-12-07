package com.kaishengit.mapper;

import com.kaishengit.entity.Seckill;
import com.kaishengit.entity.SeckillExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SeckillMapper {
    long countByExample(SeckillExample example);

    int deleteByExample(SeckillExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Seckill record);

    int insertSelective(Seckill record);

    List<Seckill> selectByExampleWithBLOBs(SeckillExample example);

    List<Seckill> selectByExample(SeckillExample example);

    Seckill selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Seckill record, @Param("example") SeckillExample example);

    int updateByExampleWithBLOBs(@Param("record") Seckill record, @Param("example") SeckillExample example);

    int updateByExample(@Param("record") Seckill record, @Param("example") SeckillExample example);

    int updateByPrimaryKeySelective(Seckill record);

    int updateByPrimaryKeyWithBLOBs(Seckill record);

    int updateByPrimaryKey(Seckill record);
}
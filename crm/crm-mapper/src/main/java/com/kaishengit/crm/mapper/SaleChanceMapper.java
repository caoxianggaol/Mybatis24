package com.kaishengit.crm.mapper;

import com.kaishengit.crm.entity.SaleChance;

import java.util.List;

import com.kaishengit.crm.example.SaleChanceExample;
import org.apache.ibatis.annotations.Param;

public interface SaleChanceMapper {
    long countByExample(SaleChanceExample example);

    int deleteByExample(SaleChanceExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SaleChance record);

    int insertSelective(SaleChance record);

    List<SaleChance> selectByExample(SaleChanceExample example);

    SaleChance selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SaleChance record, @Param("example") SaleChanceExample example);

    int updateByExample(@Param("record") SaleChance record, @Param("example") SaleChanceExample example);

    int updateByPrimaryKeySelective(SaleChance record);

    int updateByPrimaryKey(SaleChance record);

    /*1*/
    List<SaleChance> findByAccountId(Integer id);

    SaleChance findChanceWithCustomerById(Integer id);

}
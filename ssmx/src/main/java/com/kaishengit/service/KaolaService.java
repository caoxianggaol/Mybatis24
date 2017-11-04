package com.kaishengit.service;

import com.github.pagehelper.PageInfo;
import com.kaishengit.entity.Kaola;
import com.kaishengit.entity.KaolaType;

import java.util.List;

/**
 * Created by xiaogao on 2017/11/4.
 */
public interface KaolaService {

    Kaola findById(Integer id);

    PageInfo<Kaola> findByPageNo(Integer pageNo);


    /**
     * 获取所有商品类型
     * @return
     */
    List<KaolaType> findAllType();

    void save(Kaola kaola);

    void editKaola(Kaola kaola);

    void deleteKaolaById(Integer id);
}

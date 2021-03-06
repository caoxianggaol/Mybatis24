package com.kaishengit.service;

import com.github.pagehelper.PageInfo;
import com.kaishengit.entity.Kaola;
import com.kaishengit.entity.KaolaType;

import java.util.List;
import java.util.Map;

/**
 * Created by xiaogao on 2017/11/4.
 */
public interface KaolaService {

    Kaola findById(Integer id);

    PageInfo<Kaola> findByPageNo(Integer pageNo);
/*各种搜索*/
    PageInfo<Kaola> findByPageNo(Integer pageNo,Map<String,Object> queryParam);
    List<String> findProductPlaceList();

    /**
     * 获取所有商品类型
     * @return
     */
    List<KaolaType> findAllType();

    void save(Kaola kaola);

    void editKaola(Kaola kaola);

    void deleteKaolaById(Integer id);




}

package com.kaishengit.mapper;

import com.kaishengit.entity.Tag;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by xiaogao on 2017/10/27.
 */
public interface TagMapper {

    @Select("SELECT * FROM t_tag WHERE id IN (SELECT tag_id FROM user_tag WHERE user_id = #{userId})")
    List<Tag> findByUserId(Integer userId);
}

package org.weather.dao;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.weather.bean.Image;

@Repository
public interface ImageDao {

    /**
     *  获取图片
     */
    @Select("select * from image where weather = #{weather}")
    Image getImage(String weather);
}

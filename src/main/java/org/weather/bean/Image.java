package org.weather.bean;

import lombok.Data;

/**
 * 天气信息图片
 * @author Mr.Liu
 */
@Data
public class Image {
   private String weather;
   private String imgUrl;
   private int num;
}

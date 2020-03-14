package org.weather.bean;

import lombok.Data;
/**
* @Author Mr.Liu
* @Date 2020/3/13 21:54
* @description
*/
@Data
public class WeatherInfo {
    private int id;
    private String city;
    private String weather;
    private String wind;
    private String max;
    private String min;
    private String dates;
    private int type;

}

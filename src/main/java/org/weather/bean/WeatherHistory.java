package org.weather.bean;

import lombok.Data;

/**
* @Author Mr.Liu
* @Date 2020/3/13 21:53
* @description
*/
@Data
public class WeatherHistory {
    private int id;
    private String city;
    private String dates;
    private String weather;
    private String wind;
    private String max;
    private String min;

}

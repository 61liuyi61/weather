package org.weather.dao;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.weather.bean.WeatherHistory;
import org.weather.bean.WeatherInfo;

import java.util.List;

@Repository
public interface WeatherDao {

    @Select("select * from weatherinfo where city = #{city} and type = 1")
    WeatherInfo getTodayWeather(String city);

    @Select("select * from weatherinfo where city = #{city} order by type")
    List<WeatherInfo> getSevenDaysWeather(String city);

    @Select("select * from historyweather where city like #{city}")
    List<WeatherHistory> getHistoryWeather(String city);

    @Select("select * from historyweather where city like #{city} and dates like #{historyDate} order by id")
    List<WeatherHistory> getOneMonthWeather(String city, String historyDate);
}

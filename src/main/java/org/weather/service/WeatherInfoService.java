package org.weather.service;

import org.weather.bean.WeatherHistory;
import org.weather.bean.WeatherInfo;

import java.util.List;

public interface WeatherInfoService {

    WeatherInfo getTodayWeather(String city);

    List<WeatherInfo> getSevenDaysWeather(String city);

    List<WeatherHistory> getHistoryWeather(String city);

    List<WeatherHistory> getOneMonthWeather(String city, String historyDate);
}

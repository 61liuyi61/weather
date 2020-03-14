package org.weather.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.weather.bean.WeatherHistory;
import org.weather.bean.WeatherInfo;
import org.weather.dao.WeatherDao;
import org.weather.service.WeatherInfoService;

import java.util.List;

@Service
public class WeathreInforServiceImpl implements WeatherInfoService {

    @Autowired
    private WeatherDao weatherDao;

    @Override
    public WeatherInfo getTodayWeather(String city) {
        return weatherDao.getTodayWeather(city);
    }

    @Override
    public List<WeatherInfo> getSevenDaysWeather(String city) {
        return weatherDao.getSevenDaysWeather(city);
    }

    @Override
    public List<WeatherHistory> getHistoryWeather(String city) {
        return weatherDao.getHistoryWeather("%"+city+"%");
    }

    @Override
    public List<WeatherHistory> getOneMonthWeather(String city, String historyDate) {
        return weatherDao.getOneMonthWeather("%"+city+"%", "%"+historyDate+"%");
    }
}

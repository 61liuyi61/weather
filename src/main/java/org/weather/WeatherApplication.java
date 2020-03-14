package org.weather;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.weather.util.DoCMD;

@MapperScan("org.weather.dao")
@SpringBootApplication
public class WeatherApplication {

    public static void main(String[] args) {
        //DoCMD.docmd();
        SpringApplication.run(WeatherApplication.class, args);
    }

}

package org.weather.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.weather.bean.Image;
import org.weather.dao.ImageDao;
import org.weather.service.ImageService;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    ImageDao imageDao;

    @Override
    public Image getImage(String weather) {
        return imageDao.getImage(weather);
    }
}

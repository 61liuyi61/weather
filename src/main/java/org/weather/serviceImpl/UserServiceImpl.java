package org.weather.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.weather.bean.User;
import org.weather.dao.UserDao;
import org.weather.service.UserService;

/**
 * @author Mr.Liu
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	@Override
	public User getUserInfo(String name) {
		return userDao.getUserInfo(name);
	}

	@Override
	public void updateUserInfo(String name, String password) {
		userDao.updateUserInfo(name, password);
	}

}

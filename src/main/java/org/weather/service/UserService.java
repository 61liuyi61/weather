package org.weather.service;

import org.weather.bean.User;

public interface UserService {

	User getUserInfo(String name);

	void updateUserInfo(String name, String password);

}

package org.weather.controller;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.weather.bean.Image;
import org.weather.bean.User;
import org.weather.bean.WeatherHistory;
import org.weather.bean.WeatherInfo;
import org.weather.service.ImageService;
import org.weather.service.UserService;
import org.weather.service.WeatherInfoService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private WeatherInfoService weatherInfoService;

	@Autowired
	private ImageService imageService;

	/**
	*登录跳转
	 */
	@RequestMapping("/login")
	public String login(){
		return "login";
	}

	/**
	 * 跳转页面
	 * <a href="toPage?url=login">&nbsp;登录</a>
	 */
	@RequestMapping(value = "/toPage", method = RequestMethod.GET)
	public String toPage(HttpServletRequest request) {
		String url = request.getParameter("url");
		return url;
	}

	/**
	 * 登录实现
	 */
	@RequestMapping(value = "login_do")
	public String login_do(@RequestParam("name")String name, @RequestParam("password")String password, HttpServletRequest request, Map<String, Object> map) {
		try {
			if ("".equals(userService.getUserInfo(name)) || null == userService.getUserInfo(name)){
				System.out.println("用户名或密码错误！");
				map.put("msg", 	"用户名或密码错误！");
				return "login";
			}else if (name.equals(userService.getUserInfo(name).getName()) && password.equals(userService.getUserInfo(name).getPassword())) {
				System.out.println("登录成功！用户名和密码分别为：" + userService.getUserInfo(name).getName() + userService.getUserInfo(name).getPassword());
				HttpSession session = request.getSession();
				User user = new User();
				user.setName(name);
				session.setAttribute("user", user);
				System.out.println(name);
				return "body";
			}else {
				System.out.println("用户名或密码错误！");
				map.put("msg", 	"用户名或密码错误！");
				return "login";
			}
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			System.out.println(name);
			System.out.println(userService.getUserInfo(name));
		}
		return "login";

	}

	/**
	 * 注销登录
	 * @param
	 * @return
	 */
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession();
		session.removeAttribute("name");
		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length > 0) {
			for (Cookie c : cookies) {
				if ("autoLogin".equals(c.getName())) {
					//设置cookie存活时间为0
					c.setMaxAge(0);
					//将cookie响应到前台
					response.addCookie(c);
					break;
				}
			}
		}
		return "login";
	}

	/**
	 * 注册用户
	 */
	@RequestMapping(value = "register_do")
	public String register_do(@RequestParam("name")String name, @RequestParam("password")String password,  Map<String, Object> map) {
		try {
			if(null == userService.getUserInfo(name)){
				userService.updateUserInfo(name, password);
				map.put("message", "注册成功！请登录！");
				return "login";
			}else{
				System.out.println(userService.getUserInfo(name));
				map.put("message", "该用户名已注册，请重新注册！");
				return "register";
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return "login";
	}

	/**
	 * 去掉当日天气前面的前两个字“今天”,满足格式要求
	 */
	public static String removeCharAt(String s, int pos) {
		return s.substring(0, pos) + s.substring(pos + 1);
	}

	/**
	 * 去掉字符串最后一个字符 ℃
	 */
	public static String removelast(String s){
		s = s.substring(0, s.length() - 1);
		return s;
	}


	/**
	 * 查询天气信息
	 */
	@RequestMapping(value = "select_do")
	public String select_do(@RequestParam("city")String city, Map<String, Object> map, HttpServletRequest request, Model model){
		try{
			if("".equals(weatherInfoService.getTodayWeather(city)) || null == weatherInfoService.getTodayWeather(city)){
				map.put("errorCity", "没有该城市信息");
				return "body";
			}else if("-".equals(weatherInfoService.getTodayWeather(city).getWeather()) || "-".equals(weatherInfoService.getTodayWeather(city).getWeather())){
				map.put("errorInfo","中国天气网没有公布该城市的天气信息");
				return "body";
			}else {
				WeatherInfo weatherInfo = weatherInfoService.getTodayWeather(city);
				List<WeatherInfo> sevenDaysWeather = weatherInfoService.getSevenDaysWeather(city);
				List<WeatherHistory> historyWeather = weatherInfoService.getHistoryWeather(city);
				//修改日期格式
				String dates = sevenDaysWeather.get(0).getDates();
				String removeCharAtDate1 = removeCharAt(dates, 0);
				String removeCharAtDate2 = removeCharAt(removeCharAtDate1, 0);
				sevenDaysWeather.get(0).setDates(removeCharAtDate2);
				weatherInfo.setDates(removeCharAtDate2);

				//修改温度格式,并添加到一个新的列表中，用于展示波动图
				List<String> historyListMax = new ArrayList<>();
				for(int i = 0; i < historyWeather.size(); i++){
					historyListMax.add(removelast(historyWeather.get(i).getMax()));
				}
				List<String> historyListMin = new ArrayList<>();
				for(int i = 0; i < historyWeather.size(); i++){
					historyListMin.add(removelast(historyWeather.get(i).getMin()));
				}
				//当前城市一年的温度
				model.addAttribute("historyListMax",historyListMax);
				model.addAttribute("historyListMin",historyListMin);
				model.addAttribute("city", city);

				//7天天气列表
				List<String> weatherList = new ArrayList<>();
				//7天天气图标列表
				List<String> imageList = new ArrayList<>();
				for(int i = 0; i < 7; i++){
					weatherList.add(sevenDaysWeather.get(i).getWeather());
					imageList.add(imageService.getImage(weatherList.get(i)).getImgUrl());
				}
				String weather = weatherInfo.getWeather();
				Image image = imageService.getImage(weather);
				HttpSession session = request.getSession();
				session.setAttribute("imageList", imageList);
				session.setAttribute("image", image);
				session.setAttribute("weatherInfo", weatherInfo);
				session.setAttribute("sevenDaysWeather", sevenDaysWeather);
				return "cityInfo";
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return "cityInfo";

	}

	/**
	 * 点击查看历史天气信息
	 */
	@RequestMapping(value = "find_history")
	public String find_history(@RequestParam("historyDate")String historyDate, @RequestParam("city")String city, HttpServletRequest request){
		List<WeatherHistory> oneMonthWeather = weatherInfoService.getOneMonthWeather(city, historyDate);
		HttpSession session = request.getSession();
		session.setAttribute("oneMonthWeather",oneMonthWeather);
		return "historyWeather";
	}

}

package org.weather.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.weather.bean.User;
import org.weather.dto.AcceseTokenDTO;
import org.weather.dto.GithubUser;
import org.weather.provider.GithubProvider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * @Author Mr.Liu
 * @Date 2020-03-14 14:59
 * @description 获取GitHub的一系列信息
 **/
@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.url}")
    private String redirectUrl;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request) {
        AcceseTokenDTO accessToDTO = new AcceseTokenDTO();
        accessToDTO.setClient_id(clientId);
        accessToDTO.setClient_secret(clientSecret);
        accessToDTO.setCode(code);
        accessToDTO.setRedirect_uri(redirectUrl);
        accessToDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessToDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if (githubUser != null && githubUser.getId() != null) {
            User user = new User();
            String token = UUID.randomUUID().toString();
            System.out.println(token);
            user.setName(githubUser.getName());
            HttpSession session = request.getSession();
            session.setAttribute("user",user);
            System.out.println(user.getName());
            return "body";
        } else {
            //登录失败，重新登录
            return "redirect:login";
        }

    }
}

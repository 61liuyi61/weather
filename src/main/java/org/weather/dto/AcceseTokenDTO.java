package org.weather.dto;

import lombok.Data;

/**
 * @Author Mr.Liu
 * @Date 2020-03-14 15:04
 * @description
 **/
@Data
public class AcceseTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;
}

package com.example.sell_master.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @zbh
 * @2020/3/5 3:17
 */
@Data
@ConfigurationProperties(prefix = "project-url")
@Component
public class ProjectUrlConfig {
    public String wechatMpAuthorize;  //微信公众平台授权url
    public String wechatOpenAuthorize; //微信开放平台授权url
    public String sell; //点餐系统
}


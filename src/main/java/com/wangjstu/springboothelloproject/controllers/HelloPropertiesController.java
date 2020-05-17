package com.wangjstu.springboothelloproject.controllers;

import com.wangjstu.springboothelloproject.beans.ConfigBean;
import com.wangjstu.springboothelloproject.beans.ConfigTestBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableConfigurationProperties({ConfigBean.class, ConfigTestBean.class})
@RestController
public class HelloPropertiesController {

    @Value("${com.wangjstu.name}")
    private String myName;

    @Value("${com.wangjstu.age}")
    private int myAge;

    @Autowired
    private ConfigBean configBean;

    @Autowired
    private ConfigTestBean configTestBean;

    @RequestMapping(value = "/getMyProperties")
    public String getMyProperties() {
        return this.myName + ":" + this.myAge;
    }

    @RequestMapping(value = "/getMyPropertiesByBean")
    public String getMyPropertiesByBean() {
        return this.configBean.getName() + ":" + this.configBean.getAge() + "-->" + this.configBean.getHello();
    }

    @RequestMapping(value = {"/", ""})
    public String getMyTestPropertiesByBean() {
        return "less than ten: " + this.configTestBean.getLessthanten()
                + "<br/>scret: " + this.configTestBean.getScret()
                + "<br/>uuid: " + this.configTestBean.getUuid()
                + "<br/>long: " + this.configTestBean.getBignumber()
                + "<br/>numberinrange: " + this.configTestBean.getNumberinrange()
                + "<br/>number: " + this.configTestBean.getNumber();
    }
}

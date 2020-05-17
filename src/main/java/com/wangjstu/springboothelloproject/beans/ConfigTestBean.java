package com.wangjstu.springboothelloproject.beans;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix = "com.wangjstutest")
@PropertySource("classpath:config/test.properties")
public class ConfigTestBean {

    private String scret;
    private int number;
    private long bignumber;
    private String uuid;
    private int lessthanten;
    private int numberinrange;

    public String getScret() {
        return scret;
    }

    public void setScret(String scret) {
        this.scret = scret;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public long getBignumber() {
        return bignumber;
    }

    public void setBignumber(long bignumber) {
        this.bignumber = bignumber;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getLessthanten() {
        return lessthanten;
    }

    public void setLessthanten(int lessthanten) {
        this.lessthanten = lessthanten;
    }

    public int getNumberinrange() {
        return numberinrange;
    }

    public void setNumberinrange(int numberinrange) {
        this.numberinrange = numberinrange;
    }

}

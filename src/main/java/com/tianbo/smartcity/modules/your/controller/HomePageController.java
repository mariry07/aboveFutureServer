package com.tianbo.smartcity.modules.your.controller;

import com.tianbo.smartcity.modules.your.serviceimpl.HomePageServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/smartcity")
public class HomePageController  {

    @Autowired
    private  HomePageServiceImpl homePageService;

    @RequestMapping(value = "/homePage",method = RequestMethod.GET)
    public Map getHomePageData(){
        Map map =   homePageService.getHomePageData();
       return map;
    }
}

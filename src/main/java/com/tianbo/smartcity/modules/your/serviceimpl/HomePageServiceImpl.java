package com.tianbo.smartcity.modules.your.serviceimpl;

import com.tianbo.smartcity.modules.your.service.HomePageService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class HomePageServiceImpl implements HomePageService {
    @Override
    public Map getHomePageData() {
        Map map = new HashMap();
        map.put("success",true);
        map.put("message","success");
        map.put("code",200);
        map.put("timestamp", System.currentTimeMillis());

        Map result = new HashMap();
        String str1[] = {"凤城市","青岛市","济宁市","呼和浩特市","烟台市","金华市"};
        int str2[] = {22100,22100,22100,22100,22100,22100};
        result.put("cityName",str1);
        result.put("cityCount",str2);
        result.put("visits",83);
        result.put("allUser",50);
        result.put("todayUser",10);
        result.put("alarms",103);
        result.put("fault",55);

        map.put("result",result);


        return map;
    }
}

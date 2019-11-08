package com.tianbo.smartcity.modules.base.controller.manage;

import com.tianbo.smartcity.common.utils.ResultUtil;
import com.tianbo.smartcity.common.vo.EsCount;
import com.tianbo.smartcity.common.vo.EsInfo;
import com.tianbo.smartcity.common.vo.Result;
import com.tianbo.smartcity.common.exception.SmartCityException;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@Api(description = "Elasticsearch信息接口")
@RequestMapping("/smartcity/es")
@Transactional
public class EsController {

    @Value("${smartcity.elasticsearch.nodeClient}")
    private String ES_NODE_CLIENT;

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ApiOperation(value = "获取es状态")
    public Result<EsInfo> getAllByPage() {

        String healthUrl = "http://" + ES_NODE_CLIENT + "/_cluster/health";
        String countUrl = "http://" + ES_NODE_CLIENT + "/_count";
        String healthResult = HttpUtil.get(healthUrl);
        String countResult = HttpUtil.get(countUrl);
        if (StrUtil.isBlank(healthResult) || StrUtil.isBlank(countResult)) {
            throw new SmartCityException("连接ES失败，请检查ES运行状态");
        }
        EsInfo esInfo = new EsInfo();
        EsCount esCount = new EsCount();
        try {
            esInfo = new Gson().fromJson(healthResult, EsInfo.class);
            esCount = new Gson().fromJson(countResult, EsCount.class);
            esInfo.setCount(esCount.getCount());
        } catch (Exception e) {
            e.printStackTrace();
            throw new SmartCityException("获取ES信息出错");
        }
        return new ResultUtil<EsInfo>().setData(esInfo);
    }
}

package com.tianbo.smartcity.modules.third.controller;

import cn.hutool.core.util.StrUtil;
import com.tianbo.smartcity.common.constant.CommonConstant;
import com.tianbo.smartcity.common.enums.ThirdConstant;
import com.tianbo.smartcity.common.utils.PageUtil;
import com.tianbo.smartcity.common.utils.ResultUtil;
import com.tianbo.smartcity.common.vo.PageVo;
import com.tianbo.smartcity.common.vo.Result;
import com.tianbo.smartcity.common.vo.SearchVo;
import com.tianbo.smartcity.modules.third.entity.ThirdPlatform;
import com.tianbo.smartcity.modules.third.service.ThirdPlatformService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * 第三方平台标识和秘钥管理
 *
 * @author yxk
 */
@Slf4j
@RestController
@Api(description = "第三方平台标识和秘钥接口")
@RequestMapping("/smartcity/third")
@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
public class ThirdPlatformController {

    /**
     * 第三方平台标识服务
     */
    @Autowired
    private ThirdPlatformService thirdPlatformService;

    /**
     * redis
     */
    @Autowired
    private StringRedisTemplate redisTemplate;

    @RequestMapping(value = "/getAllList", method = RequestMethod.GET)
    @ApiOperation(value = "获取第三方平台标识")
    public Result<List<ThirdPlatform>> thirdGetAll() {
        List<ThirdPlatform> list = this.thirdPlatformService.getAll();
        return new ResultUtil<List<ThirdPlatform>>().setData(list);
    }

    @RequestMapping(value = "/getByCondition", method = RequestMethod.GET)
    @ApiOperation(value = "多条件分页获取第三方平台标识列表")
    public Result<Page<ThirdPlatform>> getByCondition(@ModelAttribute ThirdPlatform thirdPlatform,
                                                      @ModelAttribute SearchVo searchVo,
                                                      @ModelAttribute PageVo pageVo) {
        Page<ThirdPlatform> page = this.thirdPlatformService.findByCondition(thirdPlatform, searchVo, PageUtil.initPage(pageVo));
        return new ResultUtil<Page<ThirdPlatform>>().setData(page);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ApiOperation(value = "用户绑定第三方平台标识")
    public Result<ThirdPlatform> save(@ModelAttribute ThirdPlatform thirdPlatform) {
        // 参数校验
        if (StrUtil.isBlank(thirdPlatform.getThirdName())
                || StrUtil.isBlank(thirdPlatform.getAccessKey())
                || StrUtil.isBlank(thirdPlatform.getUserId())
                || StrUtil.isBlank(thirdPlatform.getUserName())) {
            return new ResultUtil<ThirdPlatform>().setErrorMsg("缺少必需表单字段");
        }
        // 校验是否该accessKey已经绑定过用户
        // 先从缓存里查找
        String accessKey = this.redisTemplate.opsForValue().get(ThirdConstant.THIRD_ACCESS_KEY.getName() + thirdPlatform.getUserName());

        if (StrUtil.isNotBlank(accessKey)) {
            return new ResultUtil<ThirdPlatform>().setErrorMsg("该第三方平台标识已绑定");
        }
        // 从物理库查找
        if (null != this.thirdPlatformService.getThird(thirdPlatform)) {
            return new ResultUtil<ThirdPlatform>().setErrorMsg("该第三方平台标识已绑定");
        }
        // 保存到物理库
        ThirdPlatform t = this.thirdPlatformService.save(thirdPlatform);
        // 保存到redis中
        redisTemplate.opsForValue().set(ThirdConstant.THIRD_ACCESS_KEY + t.getUserName(), t.getAccessKey());
        return new ResultUtil<ThirdPlatform>().setData(t);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ApiOperation(value = "修改第三方平台标识信息")
    public Result<ThirdPlatform> edit(@ModelAttribute ThirdPlatform thirdPlatform) {
        // 不可修改第三方标识
        String accessKey = redisTemplate.opsForValue().get(ThirdConstant.THIRD_ACCESS_KEY.getName() + thirdPlatform.getUserName());
        if (StrUtil.isBlank(accessKey)) {
            return new ResultUtil<ThirdPlatform>().setErrorMsg("不可修改第三方标识");
        }
        thirdPlatform.setAccessKey(accessKey);
        ThirdPlatform t = this.thirdPlatformService.update(thirdPlatform);
        return new ResultUtil<ThirdPlatform>().setData(t);
    }

    @RequestMapping(value = "/delByIds/{ids}", method = RequestMethod.DELETE)
    @ApiOperation(value = "通过id解绑第三方平台标识")
    public Result<ThirdPlatform> delete(@PathVariable String[] ids) {

        for (String id : ids) {
            ThirdPlatform thirdPlatform = this.thirdPlatformService.get(id);
            if (null != thirdPlatform) {
                // 删除物理库
                this.thirdPlatformService.delete(id);
                // 删除缓存
                this.redisTemplate.delete(ThirdConstant.THIRD_ACCESS_KEY.getName() + thirdPlatform.getUserName());
            }
        }
        return new ResultUtil<ThirdPlatform>().setSuccessMsg("删除成功");
    }

    @RequestMapping(value = "/generateAccessKey", method = RequestMethod.GET)
    @ApiOperation(value = "生成第三方平台标识")
    public Result<ThirdPlatform> generateAccessKey() {
        // 当前使用uuid作为秘钥对
        ThirdPlatform thirdPlatform = new ThirdPlatform();
        thirdPlatform.setAccessKey(UUID.randomUUID().toString().replace("-", ""));
        return new ResultUtil<ThirdPlatform>().setData(thirdPlatform);
    }

    @RequestMapping(value = "/setFreezeStatus", method = RequestMethod.POST)
    @ApiOperation(value = "修改第三方平台冻结标识")
    public Result<ThirdPlatform> setFreezeStatus(@RequestParam String id,
                                                 @RequestParam Integer freezeStatus) {

        if (!CommonConstant.THIRD_STATUS_NORMAL.equals(freezeStatus) && !CommonConstant.THIRD_STATUS_FREEZE.equals(freezeStatus)) {
            return new ResultUtil<ThirdPlatform>().setErrorMsg("状态错误");
        }

        ThirdPlatform thirdPlatform = this.thirdPlatformService.get(id);
        if (null == thirdPlatform) {
            return new ResultUtil<ThirdPlatform>().setErrorMsg("第三方平台不存在");
        }

        if (CommonConstant.THIRD_STATUS_NORMAL.equals(freezeStatus)) {
            // 保存到缓存中
            this.redisTemplate.opsForValue().set(ThirdConstant.THIRD_ACCESS_KEY.getName() + thirdPlatform.getUserName(), thirdPlatform.getAccessKey());
        }
        if (CommonConstant.THIRD_STATUS_FREEZE.equals(freezeStatus)) {
            // 从缓存中删除
            this.redisTemplate.delete(ThirdConstant.THIRD_ACCESS_KEY.getName() + thirdPlatform.getUserName());
        }
        thirdPlatform.setFreezeStatus(freezeStatus);
        this.thirdPlatformService.update(thirdPlatform);
        return new ResultUtil<ThirdPlatform>().setSuccessMsg("修改状态成功");
    }

}

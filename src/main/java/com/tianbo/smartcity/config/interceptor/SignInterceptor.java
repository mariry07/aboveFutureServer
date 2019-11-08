package com.tianbo.smartcity.config.interceptor;

import cn.hutool.core.util.StrUtil;
import com.google.gson.Gson;
import com.tianbo.smartcity.common.constant.SecurityConstant;
import com.tianbo.smartcity.common.enums.ThirdConstant;
import com.tianbo.smartcity.common.utils.JedisUtil;
import com.tianbo.smartcity.common.utils.Md5Util;
import com.tianbo.smartcity.common.utils.ResponseUtil;
import com.tianbo.smartcity.common.vo.TokenUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * 参数验签拦截器
 *
 * @author yxk
 */
@Slf4j
@Component
public class SignInterceptor extends HandlerInterceptorAdapter {

    /**
     * 请求时间范围
     */
    @Value("${smartcity.timestamp.range}")
    private Integer timeRange;

    /**
     * 随机数过期时间
     */
    @Value("${smartcity.nonce.expire}")
    private Integer nonceExpireTime;

    /**
     * Redis
     */
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private JedisUtil jedisUtil;

    /**
     * 预处理方法里进行参数验签
     *
     * @param request  请求对象
     * @param response 响应对象
     * @param handler  响应处理器
     * @return 是否通关
     * @throws Exception 异常
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 时间戳
        String requestTime = request.getParameter(ThirdConstant.TIMESTAMP.getName());
        // 随机数
        String nonce = request.getParameter(ThirdConstant.NONCE.getName());
        // 签名
        String sign = request.getParameter(ThirdConstant.SIGN.getName());
        // 获取accessToken
        String accessToken = StrUtil.isNotBlank(request.getHeader(SecurityConstant.HEADER)) ?
                request.getHeader(SecurityConstant.HEADER) : request.getParameter(SecurityConstant.HEADER);

        // 如果验签参数不全则拦截请求
        if (StrUtil.isBlank(requestTime) || StrUtil.isBlank(nonce) || StrUtil.isBlank(sign)) {
            ResponseUtil.out(response, ResponseUtil.resultMap(false, 401, "参数不正确"));
            return false;
        } else {
            // 获取当前系统时间戳，与请求时间戳对比，如果超过配置时间，则返回
            if (Math.abs(System.currentTimeMillis() - Long.parseLong(requestTime)) < timeRange) {
                // 从redis里查询是否已经存在请求随机数key，如果存在则返回，不存在则保存，保存格式：key：REQUEST_NONCE:accessToken:nonce，value：nonce
                // 保证在同一个accessToken下请求随机码唯一，随机数保存5分钟
                boolean present = this.jedisUtil.setNX(ThirdConstant.REDIS_NONCE + accessToken + ":" + nonce, nonce, nonceExpireTime);
                if (present) {
                    ResponseUtil.out(response, ResponseUtil.resultMap(false, 401, "请求过于频繁"));
                    return false;
                }

                // 根据token获取用户名称
                String jsonUser = redisTemplate.opsForValue().get(SecurityConstant.TOKEN_PRE + accessToken);
                if (StrUtil.isBlank(jsonUser)) {
                    ResponseUtil.out(response, ResponseUtil.resultMap(false, 401, "登录已失效，请重新登录"));
                    return false;
                }

                TokenUser user = new Gson().fromJson(jsonUser, TokenUser.class);
                String username = user.getUsername();
                // 从redis里根据开发者标识获取秘钥
                String accessKey = this.redisTemplate.opsForValue().get(ThirdConstant.THIRD_ACCESS_KEY + username);

                if (StrUtil.isNotBlank(accessKey)) {
                    // 对请求参数进行验签
                    Map<String, String> params = this.bodyMap(request);
                    boolean signFlag = this.verifySign(params, sign, accessKey);
                    if (signFlag) {
                        return true;
                    } else {
                        ResponseUtil.out(response, ResponseUtil.resultMap(false, 401, "验签失败"));
                        return false;
                    }
                } else {
                    ResponseUtil.out(response, ResponseUtil.resultMap(false, 401, "认证失败"));
                    return false;
                }
            } else {
                ResponseUtil.out(response, ResponseUtil.resultMap(false, 401, "请求超时"));
                return false;
            }
        }
    }

    /**
     * 验签
     *
     * @param params    请求参数
     * @param sign      签名
     * @param secretKey 秘钥
     * @return 验签结果
     */
    private boolean verifySign(Map<String, String> params, String sign, String secretKey) throws NoSuchAlgorithmException {
        params.put(ThirdConstant.SECRET_KEY.getName(), secretKey);
        String paramsStr = this.createLinkString(params);
        // 将参数字符串进行MD5加密
        String encryptStr = Md5Util.md5(paramsStr).toUpperCase();
        return StrUtil.equals(sign, encryptStr);
    }

    /**
     * 将请求头参数转化为Map对象
     *
     * @param request 请求对象
     * @return Map
     */
    private Map<String, String> headerMap(HttpServletRequest request) {
        Map<String, String> params = new HashMap<>(16);
        Enumeration<String> enumeration = request.getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String headerName = enumeration.nextElement().trim();
            params.put(headerName, request.getHeader(headerName));
        }
        return params;
    }

    /**
     * 将请求体参数转化为Map对象
     *
     * @param request 请求对象
     * @return Map
     */
    private Map<String, String> bodyMap(HttpServletRequest request) {
        Map<String, String> params = new HashMap<>(16);
        Enumeration<String> enumeration = request.getParameterNames();
        while (enumeration.hasMoreElements()) {
            String paramName = enumeration.nextElement().trim();
            params.put(paramName, request.getParameter(paramName));
        }
        // 删除accessToken
        params.remove(SecurityConstant.HEADER);
        return params;
    }

    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用拼接成字符串
     *
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    private String createLinkString(Map<String, String> params) {
        List<String> keyList = new ArrayList<>(params.keySet());
        Collections.sort(keyList);
        StringBuilder paramsStr = new StringBuilder();
        for (String key : keyList) {
            Object value = params.get(key);
            if (null == value || "".equals(value.toString()) || ThirdConstant.SIGN.getName().equals(key)) {
                continue;
            }
            paramsStr.append(key).append("=").append(value);
        }
        return paramsStr.toString();
    }
}

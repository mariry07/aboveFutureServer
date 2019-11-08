package com.tianbo.smartcity.modules.third.serviceimpl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.tianbo.smartcity.base.SmartCityBaseDao;
import com.tianbo.smartcity.common.vo.SearchVo;
import com.tianbo.smartcity.modules.base.entity.Permission;
import com.tianbo.smartcity.modules.third.dao.ThirdPlatformDao;
import com.tianbo.smartcity.modules.third.dao.mapper.ThirdPlatformMapper;
import com.tianbo.smartcity.modules.third.entity.ThirdPlatform;
import com.tianbo.smartcity.modules.third.service.ThirdPlatformService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 第三方平台标识实现
 *
 * @author yxk
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class ThirdPlatformServiceImpl implements ThirdPlatformService {

    /**
     * 第三方平台标识处理层
     */
    @Autowired
    private ThirdPlatformDao thirdPlatformDao;

    /**
     * 第三方平台标识映射
     */
    @Autowired
    private ThirdPlatformMapper thirdPlatformMapper;

    @Override
    public SmartCityBaseDao<ThirdPlatform, String> getRepository() {
        return thirdPlatformDao;
    }

    /**
     * 获取所有第三方平台接口
     *
     * @return 第三方平台列表
     */
    @Override
    public List<ThirdPlatform> getAll() {
        return thirdPlatformMapper.getAll();
    }

    /**
     * 多条件分页获取第三方平台列表
     *
     * @param thirdPlatform 第三方平台对象
     * @param searchVo      查询条件
     * @param pageable      分页对象
     * @return 分页列表
     */
    @Override
    public Page<ThirdPlatform> findByCondition(ThirdPlatform thirdPlatform, SearchVo searchVo, Pageable pageable) {

        return this.thirdPlatformDao.findAll(new Specification<ThirdPlatform>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<ThirdPlatform> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

                Path<String> userIdField = root.get("userId");
                Path<String> userNameField = root.get("userName");
                Path<String> thirdNameField = root.get("thirdName");
                Path<String> accessKeyField = root.get("accessKey");
                Path<Integer> freezeStatusField = root.get("freezeStatus");
                Path<Date> createTimeField = root.get("createTime");

                List<Predicate> list = new ArrayList<>();

                // 用户id
                if (StrUtil.isNotBlank(thirdPlatform.getUserId())) {
                    list.add(cb.equal(userIdField, thirdPlatform.getUserId()));
                }

                // 模糊搜索
                if (StrUtil.isNotBlank(thirdPlatform.getThirdName())) {
                    list.add(cb.like(thirdNameField, '%' + thirdPlatform.getThirdName() + '%'));
                }

                if (StrUtil.isNotBlank(thirdPlatform.getUserName())) {
                    list.add(cb.like(userNameField, '%' + thirdPlatform.getUserName() + '%'));
                }

                // 平台标识
                if (StrUtil.isNotBlank(thirdPlatform.getAccessKey())) {
                    list.add(cb.equal(accessKeyField, thirdPlatform.getAccessKey()));
                }

                // 冻结标识
                if (thirdPlatform.getFreezeStatus() != null) {
                    list.add(cb.equal(freezeStatusField, thirdPlatform.getFreezeStatus()));
                }

                // 创建时间
                if (StrUtil.isNotBlank(searchVo.getStartDate()) && StrUtil.isNotBlank(searchVo.getEndDate())) {
                    Date start = DateUtil.parse(searchVo.getStartDate());
                    Date end = DateUtil.parse(searchVo.getEndDate());
                    list.add(cb.between(createTimeField, start, DateUtil.endOfDay(end)));
                }

                Predicate[] arr = new Predicate[list.size()];
                cq.where(list.toArray(arr));
                return null;
            }
        }, pageable);
    }

    /**
     * 根据第三方平台标识条件获取对象
     *
     * @param thirdPlatform 第三方标识
     * @return 第三方标识对象
     */
    @Override
    public ThirdPlatform getThird(ThirdPlatform thirdPlatform) {
        return this.thirdPlatformMapper.getThird(thirdPlatform);
    }

    /**
     * 根据第三方平台标识获取权限
     *
     * @param accessKey 第三方标识
     * @return 权限列表
     */
    @Override
    public List<Permission> findPermissionByAccessKey(String accessKey) {
        return this.thirdPlatformMapper.findPermissionByAccessKey(accessKey);
    }

    /**
     * 根据用户id删除第三方平台
     * @param id 用户id
     */
    @Override
    public void deleteByUserId(String id) {
        this.thirdPlatformMapper.deleteByUserId(id);
    }

}

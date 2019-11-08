package com.tianbo.smartcity.modules.patrol.serviceimpl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.tianbo.smartcity.common.utils.QrCodeUtil;
import com.tianbo.smartcity.common.utils.SecurityUtil;
import com.tianbo.smartcity.common.vo.SearchVo;
import com.tianbo.smartcity.modules.base.entity.User;
import com.tianbo.smartcity.modules.patrol.dao.PatrolMarkDao;
import com.tianbo.smartcity.modules.patrol.entity.PatrolMark;
import com.tianbo.smartcity.modules.patrol.service.PatrolMarkService;
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
 * 巡更点接口实现
 * @author WangCH
 */
@Slf4j
@Service
@Transactional
public class PatrolMarkServiceImpl implements PatrolMarkService {

    @Autowired
    private PatrolMarkDao patrolMarkDao;

    @Autowired
    private SecurityUtil securityUtil;

    @Override
    public PatrolMarkDao getRepository() {
        return patrolMarkDao;
    }

    /**
     *  多条件分页获取
     * @param patrolMark
     * @param searchVo
     * @param pageable
     * @return
     */
    @Override
    public Page<PatrolMark> findByCondition(PatrolMark patrolMark, SearchVo searchVo, Pageable pageable) {

        return patrolMarkDao.findAll(new Specification<PatrolMark>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<PatrolMark> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<Predicate>();

                // TODO 可添加你的其他搜索过滤条件 默认已有创建时间过滤
                Path<Date> createTimeField = root.get("createTime");
                Path<String> nameField = root.get("name");

                // 名称模糊搜素
                if(StrUtil.isNotBlank(patrolMark.getName())){
                    list.add(cb.like(nameField,'%' + patrolMark.getName() + '%'));
                }
                //创建时间
                if(StrUtil.isNotBlank(searchVo.getStartDate())&&StrUtil.isNotBlank(searchVo.getEndDate())){
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
     * 新增巡更点
     * @param patrolMark
     * @return
     */
    @Override
    public PatrolMark add(PatrolMark patrolMark){

        User user = securityUtil.getCurrUser();
        patrolMark.setDepartmentId(user.getDepartmentId());
        //二维码生成base64
        String base64 = QrCodeUtil.createQrCode(patrolMark.getId());
        //更新二维码数据
        patrolMark.setQrCode("data:image/jpg;base64," + base64);
        //todo CompanyId获取
        //新增数据
        PatrolMark result = save(patrolMark);
        return result;
    }

    /**
     * 根据名字获取巡更点数目
     * @param name
     * @return
     */
    @Override
    public Integer countByNameAndIdNot(String name,String id) {

        return patrolMarkDao.countByNameAndIdNot(name,id);
    }
}
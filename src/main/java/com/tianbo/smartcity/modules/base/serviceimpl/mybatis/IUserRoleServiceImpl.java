package com.tianbo.smartcity.modules.base.serviceimpl.mybatis;

import com.tianbo.smartcity.modules.base.dao.mapper.UserRoleMapper;
import com.tianbo.smartcity.modules.base.entity.Role;
import com.tianbo.smartcity.modules.base.entity.UserRole;
import com.tianbo.smartcity.modules.base.service.mybatis.IUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class IUserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public List<Role> findByUserId(String userId) {

        return userRoleMapper.findByUserId(userId);
    }

    @Override
    public List<String> findDepIdsByUserId(String userId) {

        return userRoleMapper.findDepIdsByUserId(userId);
    }
}

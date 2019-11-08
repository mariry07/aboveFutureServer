package com.tianbo.smartcity.modules.base.serviceimpl.mybatis;

import com.tianbo.smartcity.modules.base.dao.mapper.PermissionMapper;
import com.tianbo.smartcity.modules.base.entity.Permission;
import com.tianbo.smartcity.modules.base.service.mybatis.IPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class IPermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<Permission> findByUserId(String userId) {

        return permissionMapper.findByUserId(userId);
    }
}

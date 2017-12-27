package com.paopaoxiong.ppx.service.system.impl;

import com.paopaoxiong.ppx.mapper.system.UserRoleMapper;
import com.paopaoxiong.ppx.model.system.UserRole;
import com.paopaoxiong.ppx.service.system.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public List<UserRole> queryAllUserRole(UserRole userRole) {
        return userRoleMapper.queryAllUserRole(userRole);
    }
}

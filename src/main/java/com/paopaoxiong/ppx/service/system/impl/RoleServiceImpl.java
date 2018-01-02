package com.paopaoxiong.ppx.service.system.impl;

import com.paopaoxiong.ppx.mapper.system.RoleMapper;
import com.paopaoxiong.ppx.model.system.Role;
import com.paopaoxiong.ppx.service.system.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> queryAllRole(Role role) {
        return roleMapper.queryAllRole(role);
    }

    @Override
    public void add(Role role) {
        roleMapper.add(role);
    }

    @Override
    public void delete(Integer id) {
        roleMapper.delete(id);
    }
}

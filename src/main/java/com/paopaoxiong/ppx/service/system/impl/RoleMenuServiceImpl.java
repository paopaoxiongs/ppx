package com.paopaoxiong.ppx.service.system.impl;

import com.paopaoxiong.ppx.mapper.system.RoleMenuMapper;
import com.paopaoxiong.ppx.model.system.RoleMenu;
import com.paopaoxiong.ppx.service.system.RoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleMenuServiceImpl implements RoleMenuService {

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Override
    public List<RoleMenu> queryAllRoleMenu(RoleMenu roleMenu) {
        return roleMenuMapper.queryAllRoleMenu(roleMenu);
    }
}

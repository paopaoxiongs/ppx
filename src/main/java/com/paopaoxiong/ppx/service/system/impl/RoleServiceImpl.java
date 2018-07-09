package com.paopaoxiong.ppx.service.system.impl;

import com.paopaoxiong.ppx.mapper.system.RoleMapper;
import com.paopaoxiong.ppx.mapper.system.RoleMenuMapper;
import com.paopaoxiong.ppx.mapper.system.UserRoleMapper;
import com.paopaoxiong.ppx.model.system.Role;
import com.paopaoxiong.ppx.model.system.RoleMenu;
import com.paopaoxiong.ppx.service.system.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public List<Role> queryAllRole(Role role) {
        return roleMapper.queryAllRole(role);
    }

    @Override
    public void add(Role role) {
        roleMapper.add(role);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(Integer id) {
        roleMapper.delete(id);
        roleMenuMapper.removeByRoleId(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(Role role) {
        int count = roleMapper.save(role);
        List<Integer> menuIds = role.getMenuIds();
        Integer roleId = role.getId();
        List<RoleMenu> rms = new ArrayList<>();
        for (Integer menuId : menuIds) {
            RoleMenu rm = new RoleMenu();
            rm.setRoleId(roleId);
            rm.setMenuId(menuId);
            rms.add(rm);
        }
        roleMenuMapper.removeByRoleId(roleId);
        if (rms.size() > 0) {
            roleMenuMapper.batchSave(rms);
        }
    }

    @Override
    public Role get(Integer id) {
        return roleMapper.get(id);
    }

    @Override
    public void update(Role role) {
        roleMapper.update(role);
        Integer roleId = role.getId();
        List<Integer> menuIds = role.getMenuIds();
        roleMenuMapper.removeByRoleId(roleId);
        List<RoleMenu> rms = new ArrayList<>();
        for (Integer menuId : menuIds) {
            RoleMenu rm = new RoleMenu();
            rm.setRoleId(roleId);
            rm.setMenuId(menuId);
            rms.add(rm);
        }
        roleMenuMapper.removeByRoleId(roleId);
        if (rms.size() > 0) {
            roleMenuMapper.batchSave(rms);
        }
    }

    @Override
    public List<Role> getRoleListByUserId(Integer id) {
        List<Integer> rolesIds = userRoleMapper.getRoleIdListByUserId(id);
        List<Role> roles = roleMapper.queryAllRole(null);
        for (Role role : roles) {
            role.setRoleSign("false");
            for (Integer roleId : rolesIds) {
                if (role.getId().intValue() == roleId.intValue()) {
                    role.setRoleSign("true");
                    break;
                }
            }
        }
        return roles;
    }
}

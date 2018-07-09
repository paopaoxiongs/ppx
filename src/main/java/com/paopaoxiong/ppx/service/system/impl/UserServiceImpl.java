package com.paopaoxiong.ppx.service.system.impl;

import com.paopaoxiong.ppx.mapper.system.UserMapper;
import com.paopaoxiong.ppx.mapper.system.UserRoleMapper;
import com.paopaoxiong.ppx.model.system.User;
import com.paopaoxiong.ppx.model.system.UserRole;
import com.paopaoxiong.ppx.service.system.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public List<User> queryAllUser(User user) {
        return userMapper.queryAllUser(user);
    }

    @Override
    public User queryUserByAccount(String account) {
        return userMapper.queryUserByAccount(account);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(User user) {
        userMapper.add(user);
        Integer userId = user.getId();
        List<Integer> roleIds = user.getRoleIds();
        userRoleMapper.removeByUserId(userId);
        List<UserRole> list = new ArrayList<>();
        for (Integer roleId : roleIds) {
            UserRole ur = new UserRole();
            ur.setUserId(userId);
            ur.setRoleId(roleId);
            list.add(ur);
        }
        if (list.size() > 0) {
            userRoleMapper.batchSave(list);
        }
    }

    @Override
    public void retry(User user) {
        if(user == null) {
            throw new RuntimeException();
        }
    }

    @Override
    public boolean exit(Map<String, Object> params) {
        User user = new User();
        user.setAccount(params.get("account").toString());
        boolean exit = userMapper.queryAllUser(user).size() > 0;
        return exit;
    }

    @Override
    public void removeByUserId(Integer userId) {
        userRoleMapper.removeByUserId(userId);
    }

    @Override
    public User get(Integer userId) {
        return userMapper.get(userId);
    }

    @Override
    public void update(User user) {
        userMapper.update(user);
        Integer userId = user.getId();
        List<Integer> roles = user.getRoleIds();
        userRoleMapper.removeByUserId(userId);
        List<UserRole> list = new ArrayList<>();
        for (Integer roleId : roles) {
            UserRole ur = new UserRole();
            ur.setUserId(userId);
            ur.setRoleId(roleId);
            list.add(ur);
        }
        if (list.size() > 0) {
            userRoleMapper.batchSave(list);
        }
    }

    @Override
    public void remove(Integer userId) {
        userRoleMapper.removeByUserId(userId);
        userMapper.delete(userId);
    }

    @Override
    public void delete(Integer userId) {
        userRoleMapper.removeByUserId(userId);
        userMapper.delete(userId);
    }
}

package com.paopaoxiong.ppx.service.system.impl;

import com.paopaoxiong.ppx.mapper.system.UserMapper;
import com.paopaoxiong.ppx.model.system.User;
import com.paopaoxiong.ppx.service.system.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> queryAllUser(User user) {
        return userMapper.queryAllUser(user);
    }

    @Override
    public User queryUserByAccount(String account) {
        return userMapper.queryUserByAccount(account);
    }

    @Override
    public void add(User user) {
        userMapper.add(user);
    }
}

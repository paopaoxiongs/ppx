package com.paopaoxiong.ppx.service.system;

import com.paopaoxiong.ppx.model.system.User;

import java.util.List;

public interface UserService {

    public List<User> queryAllUser(User user);

    public User queryUserByAccount(String account);

    public void add(User user);
}

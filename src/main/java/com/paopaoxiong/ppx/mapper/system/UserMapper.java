package com.paopaoxiong.ppx.mapper.system;

import com.paopaoxiong.ppx.model.system.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    public List<User> queryAllUser(User user);

    public User queryUserByAccount(String account);

    public void add(User user);
}

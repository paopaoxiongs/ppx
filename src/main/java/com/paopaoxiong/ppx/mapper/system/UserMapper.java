package com.paopaoxiong.ppx.mapper.system;

import com.paopaoxiong.ppx.model.system.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    public List<User> queryAllUser(User user);

    public User queryUserByAccount(String account);

    public void add(User user);

    public User get(Integer userId);

    public void update(User user);

    public void delete(Integer userId);
}

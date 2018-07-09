package com.paopaoxiong.ppx.service.system;

import com.paopaoxiong.ppx.model.system.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    public List<User> queryAllUser(User user);

    public User queryUserByAccount(String account);

    public void add(User user);

    public void retry(User user);

	public boolean exit(Map<String, Object> params);

	public void removeByUserId(Integer userId);

	public User get(Integer userId);

	public void update(User user);

	public void remove(Integer id);

	public void delete(Integer userId);
}

package com.paopaoxiong.ppx.service.system;

import com.paopaoxiong.ppx.model.system.Role;

import java.util.List;

public interface RoleService {

    public List<Role> queryAllRole(Role role);

    public void add(Role role);

    public void delete(Integer id);
}

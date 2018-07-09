package com.paopaoxiong.ppx.mapper.system;

import com.paopaoxiong.ppx.model.system.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper {

    public List<Role> queryAllRole(Role role);

    public void add(Role role);

    public void delete(Integer id);

	public int save(Role role);

	public Role get(Integer id);

	public void update(Role role);
}

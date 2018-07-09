package com.paopaoxiong.ppx.mapper.system;

import com.paopaoxiong.ppx.model.system.UserRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserRoleMapper {

    public List<UserRole> queryAllUserRole(UserRole userRole);

    public void removeByUserId(Integer userId);

	public void batchSave(List<UserRole> list);

	public List<Integer> getRoleIdListByUserId(Integer userId);
}

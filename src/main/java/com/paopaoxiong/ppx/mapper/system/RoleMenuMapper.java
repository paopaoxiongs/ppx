package com.paopaoxiong.ppx.mapper.system;

import com.paopaoxiong.ppx.model.system.RoleMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMenuMapper {

    public List<RoleMenu> queryAllRoleMenu(RoleMenu roleMenu);

    public void removeByRoleId(Integer roleId);

    public void batchSave(List<RoleMenu> rms);

    public List<Integer> getMenuIdByRoleId(Integer roleId);
}

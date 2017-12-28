package com.paopaoxiong.ppx.mapper.system;

import com.paopaoxiong.ppx.model.system.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenuMapper {
    public List<Menu> queryAllMenu(Menu menu);

    public List<Menu> queryAllMenuByUserId(Integer userId);

    public void add(Menu menu);

    public Menu getMenuById(Integer id);

    public void delete(Integer id);
}

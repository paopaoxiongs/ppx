package com.paopaoxiong.ppx.service.system;

import com.paopaoxiong.ppx.model.system.Menu;

import java.util.List;

public interface MenuService {

    public List<Menu> queryAllMenu(Menu menu);

    public List<Menu> queryAllMenuByUserId(Integer userId);

    public void add(Menu menu);

    public Menu getMenuById(Integer id);

    public void delete(Integer id);
}

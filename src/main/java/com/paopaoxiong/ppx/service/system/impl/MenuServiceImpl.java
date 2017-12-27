package com.paopaoxiong.ppx.service.system.impl;

import com.paopaoxiong.ppx.mapper.system.MenuMapper;
import com.paopaoxiong.ppx.model.system.Menu;
import com.paopaoxiong.ppx.service.system.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> queryAllMenu(Menu menu) {
        return menuMapper.queryAllMenu(menu);
    }

    @Override
    public List<Menu> queryAllMenuByUserId(Integer userId) {
        return menuMapper.queryAllMenuByUserId(userId);
    }

    @Override
    public void add(Menu menu) {
        menuMapper.add(menu);
    }
}

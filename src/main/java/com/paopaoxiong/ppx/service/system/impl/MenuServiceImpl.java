package com.paopaoxiong.ppx.service.system.impl;

import com.paopaoxiong.ppx.mapper.system.MenuMapper;
import com.paopaoxiong.ppx.mapper.system.RoleMenuMapper;
import com.paopaoxiong.ppx.model.domain.Tree;
import com.paopaoxiong.ppx.model.system.Menu;
import com.paopaoxiong.ppx.service.system.MenuService;
import com.paopaoxiong.ppx.utils.BuildTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RoleMenuMapper roleMenuMapper;

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

    @Override
    public Menu getMenuById(Integer id) {
        return menuMapper.getMenuById(id);
    }

    @Override
    public void delete(Integer id) {
        menuMapper.delete(id);
    }

    @Override
    public void update(Menu menu) {
        menuMapper.update(menu);
    }

    @Override
    public Tree<Menu> getMenuTree() {
        List<Tree<Menu>> trees = new ArrayList<Tree<Menu>>();
        List<Menu> menus  = menuMapper.queryAllMenu(null);
        for (Menu sysMenu : menus) {
            Tree<Menu> tree = new Tree<Menu>();
            tree.setId(sysMenu.getMenuId().toString());
            tree.setParentId(sysMenu.getParentId().toString());
            tree.setText(sysMenu.getName());
            trees.add(tree);
        }
        // 默认顶级菜单为０，根据数据库实际情况调整
        Tree<Menu> tree = BuildTree.build(trees);
        return tree;
    }

    @Override
    public Tree<Menu> getTree(Integer roleId) {
        List<Integer> menuIds = roleMenuMapper.getMenuIdByRoleId(roleId);
        List<Tree<Menu>> trees = new ArrayList<Tree<Menu>>();
        List<Menu> menus  = menuMapper.queryAllMenu(null);
        for (Menu sysMenu : menus) {
            Tree<Menu> tree = new Tree<Menu>();
            tree.setId(sysMenu.getMenuId().toString());
            tree.setParentId(sysMenu.getParentId().toString());
            tree.setText(sysMenu.getName());
            Map<String, Object> state = new HashMap<>();
            Integer menuId = sysMenu.getMenuId();
            if (menuIds.contains(menuId)) {
                state.put("selected", true);
            } else {
                state.put("selected", false);
            }
            tree.setState(state);
            trees.add(tree);
        }
        // 默认顶级菜单为０，根据数据库实际情况调整
        Tree<Menu> tree = BuildTree.build(trees);
        return tree;
    }

    @Override
    public List<Tree<Menu>> getMenuTreeByUserId(Integer userId) {
        List<Tree<Menu>> trees = new ArrayList<Tree<Menu>>();
        List<Menu> menus = menuMapper.queryAllMenuByUserId(userId);

        for (Menu menu : menus) {
            Tree<Menu> tree = new Tree<Menu>();
            tree.setId(menu.getMenuId().toString());
            tree.setParentId(menu.getParentId().toString());
            tree.setText(menu.getName());
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("url", menu.getUrl());
            attributes.put("icon", menu.getIcon());
            tree.setAttributes(attributes);
            trees.add(tree);
        }
        // 默认顶级菜单为０，根据数据库实际情况调整
        List<Tree<Menu>> list = BuildTree.buildList(trees,"0");
        return list;
    }
}

package com.paopaoxiong.ppx.controller.system;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.paopaoxiong.ppx.authorization.TreeBuildFactory;
import com.paopaoxiong.ppx.common.ResultInfo;
import com.paopaoxiong.ppx.common.SystemLogOperation;
import com.paopaoxiong.ppx.common.TreeNode;
import com.paopaoxiong.ppx.model.system.Menu;
import com.paopaoxiong.ppx.service.system.MenuService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/sys/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @RequiresPermissions("sys:menu:tree")
    @RequestMapping("/tree")
    public ResultInfo getMenuTree(){
        ResultInfo resultInfo = new ResultInfo();
        try {
            List<Menu> list = menuService.queryAllMenu(null);
            TreeNode tree = new TreeBuildFactory(list).buildTreeWithRoot();
            resultInfo.setData(tree);
            resultInfo.setSuccess(true);
            resultInfo.setMessage("成功");
        }catch (Exception e){
            resultInfo.setData(Collections.EMPTY_LIST);
            resultInfo.setSuccess(false);
            resultInfo.setMessage("失败,"+e.getMessage());
        }
        return resultInfo;
    }

    @RequiresPermissions("sys:menu:list")
    @RequestMapping("/list")
    @SystemLogOperation(module = "系统管理-菜单管理" ,description = "查看菜单列表")
    public ResultInfo getMmenuList(Menu menu,
                                   @RequestParam(required = false, defaultValue = "1") int pageIndex,
                                   @RequestParam(required = false, defaultValue = "10") int pageSize){
        ResultInfo resultInfo = new ResultInfo();
        try {
            PageHelper.startPage(pageIndex, pageSize);
            List<Menu> list = menuService.queryAllMenu(menu);
            PageInfo<Menu> pageInfo = new PageInfo<>(list);
            resultInfo.setMessage("成功");
            resultInfo.setSuccess(true);
            resultInfo.setData(pageInfo);
        }catch (Exception e){
            resultInfo.setMessage("获取菜单数据失败，");
            resultInfo.setSuccess(false);
            resultInfo.setData(Collections.EMPTY_LIST);
        }
        return resultInfo;
    }

    /**
     * 新建
     * @param menu
     * @return
     */
    @RequiresPermissions("sys:menu:add")
    @RequestMapping("/addMenu")
    @SystemLogOperation(module = "系统管理-菜单管理" ,description = "新增菜单")
    public ResultInfo addMenu(Menu menu){
        ResultInfo resultInfo = new ResultInfo();
        try {
            menuService.add(menu);
        }catch (Exception e){
            resultInfo.setData(Collections.EMPTY_LIST);
            resultInfo.setSuccess(false);
            resultInfo.setMessage("失败");
        }
        return resultInfo;
    }

    @RequiresPermissions("sys:menu:get")
    @RequestMapping("/getMenuById/{id}")
    @SystemLogOperation(module = "系统管理-菜单管理" ,description = "获取菜单详情")
    public ResultInfo getMenuById(@PathVariable Integer id){
        ResultInfo resultInfo = new ResultInfo();
        try {
            Menu menu = menuService.getMenuById(id);
            resultInfo.setData(menu);
            resultInfo.setSuccess(true);
            resultInfo.setMessage("成功");
        }catch (Exception e){
            resultInfo.setMessage("失败");
            resultInfo.setData(Collections.EMPTY_LIST);
            resultInfo.setSuccess(false);
        }
        return resultInfo;
    }

    @RequiresPermissions("sys:menu:delete")
    @RequestMapping("/delete/{id}")
    @SystemLogOperation(module = "系统管理-菜单管理" ,description = "删除菜单")
    public void delete(@PathVariable Integer id){
        ResultInfo resultInfo = new ResultInfo();
        try {
            menuService.delete(id);
            resultInfo.setMessage("成功");
            resultInfo.setSuccess(true);
            resultInfo.setData(Collections.EMPTY_LIST);
        }catch (Exception e){
            resultInfo.setMessage("失败，"+e.getMessage());
            resultInfo.setSuccess(false);
            resultInfo.setData(Collections.EMPTY_LIST);
        }
    }
}

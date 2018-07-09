package com.paopaoxiong.ppx.controller.system;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.paopaoxiong.ppx.authorization.TreeBuildFactory;
import com.paopaoxiong.ppx.common.ResultInfo;
import com.paopaoxiong.ppx.common.SystemLogOperation;
import com.paopaoxiong.ppx.common.TreeNode;
import com.paopaoxiong.ppx.model.domain.Tree;
import com.paopaoxiong.ppx.model.system.Menu;
import com.paopaoxiong.ppx.service.system.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/sys/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/tree")
    @ResponseBody
    public TreeNode getMenuTree(){
        TreeNode tree = new TreeNode();
        try {
            List<Menu> list = menuService.queryAllMenu(null);
            tree = new TreeBuildFactory(list).buildTreeWithRoot();
        }catch (Exception e){

        }
        return tree;
    }

    @GetMapping("/menuTree")
    @ResponseBody
    public Tree<Menu> getTree(){
        Tree<Menu> tree = new Tree<Menu>();
        try {
            tree = menuService.getMenuTree();
        }catch (Exception e){

        }
        return tree;
    }

    @GetMapping("/menuTree/{roleId}")
    @ResponseBody
    public Tree<Menu> getTreeByRoleId(@PathVariable("roleId") Integer roleId) {
        Tree<Menu> tree =  menuService.getTree(roleId);
        return tree;
    }

    @GetMapping("/list")
    @ResponseBody
    public List<Menu> getMenuList(){
        List<Menu> list = menuService.queryAllMenu(null);
        return list;
    }

    @GetMapping("/listPage")
    @SystemLogOperation(module = "系统管理-菜单管理" ,description = "查看菜单列表")
    public ResultInfo getMmenuPageList(Menu menu,
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

    @GetMapping("/menuList")
    @SystemLogOperation(module = "系统管理-菜单管理" ,description = "查看菜单列表")
    public String list(){
        return "system/menu/menuList";
    }

    /**
     *
     * @param model
     * @param pId
     * @return
     */
    @GetMapping("/add/{pId}")
    String add(Model model, @PathVariable("pId") Integer pId) {
        model.addAttribute("pId", pId);
        if (pId == 0) {
            model.addAttribute("pName", "根目录");
        } else {
            model.addAttribute("pName", menuService.getMenuById(pId).getName());
        }
        return "system/menu/add";
    }

    /**
     * 新增保存菜单
     * @param menu
     * @return
     */
    @PostMapping("/save")
    @ResponseBody
    @SystemLogOperation(module = "系统管理-菜单管理" ,description = "新增菜单")
    public ResultInfo save(Menu menu) {
        ResultInfo resultInfo = new ResultInfo();
        try {
            menuService.add(menu);
            resultInfo.setSuccess(true);
            resultInfo.setMessage("操作成功");
            resultInfo.setCode(200);
        }catch (Exception e){
            resultInfo.setMessage("保存失败");
            resultInfo.setSuccess(false);
            System.out.println(e.getMessage());
        }
        return resultInfo;
    }

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

    /**
     *
     * @param model
     * @param id
     * @return
     */
    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("menu", menuService.getMenuById(id));
        return "system/menu/edit";
    }

    @PostMapping("/update")
    @ResponseBody
    public ResultInfo update(Menu menu) {
        ResultInfo resultInfo = new ResultInfo();
        try {
            menuService.update(menu);
            resultInfo.setSuccess(true);
            resultInfo.setCode(200);
            resultInfo.setMessage("操作成功");
        }catch (Exception e){
            resultInfo.setMessage("操作失败");
            resultInfo.setData(Collections.EMPTY_LIST);
            resultInfo.setSuccess(false);
        }
        return resultInfo;
    }

    @PostMapping("/remove")
    @ResponseBody
    @SystemLogOperation(module = "系统管理-菜单管理" ,description = "删除菜单")
    public ResultInfo delete(Integer id){
        ResultInfo resultInfo = new ResultInfo();
        try {
            menuService.delete(id);
            resultInfo.setMessage("操作成功");
            resultInfo.setSuccess(true);
            resultInfo.setCode(200);
            resultInfo.setData(Collections.EMPTY_LIST);
        }catch (Exception e){
            resultInfo.setMessage("删除失败，"+e.getMessage());
            resultInfo.setSuccess(false);
            resultInfo.setData(Collections.EMPTY_LIST);
        }
        return resultInfo;
    }

}

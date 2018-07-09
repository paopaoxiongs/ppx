package com.paopaoxiong.ppx.controller.system;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.paopaoxiong.ppx.common.ResultInfo;
import com.paopaoxiong.ppx.model.system.Role;
import com.paopaoxiong.ppx.service.system.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/sys/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/page")
    public String role() {
        return "system/role/role";
    }

    @GetMapping("/list")
    @ResponseBody()
    public List<Role> getRoleList(){
        List<Role> list = new ArrayList<>();
        try {
            list = roleService.queryAllRole(null);
        }catch (Exception e){

        }
        return list;
    }

    @GetMapping("/add")
    public String add(){
        return "system/role/addRole";
    }

    /**
     * 保存角色
     * @param role
     * @return
     */
    @PostMapping("/save")
    @ResponseBody()
    public ResultInfo save(Role role) {
        ResultInfo resultInfo = new ResultInfo();
        try {
            roleService.save(role);
            resultInfo.setMessage("操作成功");
            resultInfo.setCode(200);
        }catch (Exception e){
            resultInfo.setMessage("保存失败,"+e.getMessage());
            resultInfo.setSuccess(false);
        }
        return resultInfo;
    }

    @PostMapping("/remove")
    @ResponseBody()
    public ResultInfo remove(Integer id){
        ResultInfo resultInfo = new ResultInfo();
        try {
            roleService.delete(id);
            resultInfo.setMessage("操作成功");
            resultInfo.setCode(200);
        }catch (Exception e){
            resultInfo.setMessage("删除失败,"+e.getMessage());
            resultInfo.setSuccess(false);
        }
        return resultInfo;
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        Role role = roleService.get(id);
        model.addAttribute("role", role);
        return "system/role/editRole";
    }

    @PostMapping("/update")
    @ResponseBody()
    public ResultInfo update(Role role) {
        ResultInfo resultInfo = new ResultInfo();
        try {
            roleService.update(role);
            resultInfo.setMessage("操作成功");
            resultInfo.setCode(200);
        }catch (Exception e){
            resultInfo.setMessage("更新失败,"+e.getMessage());
            resultInfo.setSuccess(false);
        }
        return resultInfo;
    }

}

package com.paopaoxiong.ppx.controller.system;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.paopaoxiong.ppx.common.ResultInfo;
import com.paopaoxiong.ppx.model.system.Role;
import com.paopaoxiong.ppx.service.system.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/sys/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping("/list")
    public ResultInfo getRoleList(Role role,
                                  @RequestParam(required = false, defaultValue = "1") int pageIndex,
                                  @RequestParam(required = false, defaultValue = "10") int pageSize){
        ResultInfo resultInfo = new ResultInfo();
        try {
            PageHelper.startPage(pageIndex,pageSize);
            List<Role> list = roleService.queryAllRole(role);
            PageInfo<Role> pageInfo = new PageInfo<>(list);
            resultInfo.setData(pageInfo);
            resultInfo.setMessage("成功");
            resultInfo.setSuccess(true);
        }catch (Exception e){
            resultInfo.setSuccess(false);
            resultInfo.setData(Collections.EMPTY_LIST);
            resultInfo.setMessage("获取失败，"+e.getMessage());
        }
        return resultInfo;
    }

    @RequestMapping("/add")
    public ResultInfo add(Role role){
        ResultInfo resultInfo = new ResultInfo();
        try {
            roleService.add(role);
            resultInfo.setData(Collections.EMPTY_LIST);
            resultInfo.setMessage("成功");
            resultInfo.setSuccess(true);
        }catch (Exception e){
            resultInfo.setData(Collections.EMPTY_LIST);
            resultInfo.setMessage("失败,"+e.getMessage());
            resultInfo.setSuccess(false);
        }
        return resultInfo;
    }

}

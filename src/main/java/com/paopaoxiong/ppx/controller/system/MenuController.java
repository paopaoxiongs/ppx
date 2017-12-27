package com.paopaoxiong.ppx.controller.system;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.paopaoxiong.ppx.common.ResultInfo;
import com.paopaoxiong.ppx.model.system.Menu;
import com.paopaoxiong.ppx.service.system.MenuService;
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

    @RequestMapping("/list")
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
    @RequestMapping("/addMenu")
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

    @RequestMapping("/getMenuById/{id}")
    public ResultInfo getMenuById(@PathVariable Integer id){
        ResultInfo resultInfo = new ResultInfo();

        try {

        }catch (Exception e){
            resultInfo.setMessage("失败");
            resultInfo.setData(Collections.EMPTY_LIST);
            resultInfo.setSuccess(false);
        }
        return resultInfo;
    }

    public void delete(){

    }
}

package com.paopaoxiong.ppx.controller.system;

import com.paopaoxiong.ppx.authorization.TreeBuildFactory;
import com.paopaoxiong.ppx.authorization.UserInfoUtil;
import com.paopaoxiong.ppx.common.TreeNode;
import com.paopaoxiong.ppx.model.domain.Tree;
import com.paopaoxiong.ppx.model.system.Menu;
import com.paopaoxiong.ppx.model.system.User;
import com.paopaoxiong.ppx.service.system.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping
public class IndexController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/index")
    public ModelAndView index() throws Exception{
        ModelAndView mav = new ModelAndView();
        List<Menu> list = menuService.queryAllMenu(null);
        TreeNode menus = new TreeBuildFactory(list).buildTreeWithRoot();
        //List<Tree<Menu>> menus = menuService.getMenuTreeByUserId(UserInfoUtil.getUserId());
        mav.addObject("menus",menus);
        mav.setViewName("index");
        return mav;
    }

    @GetMapping("/main")
    public String main() {
        return "main";
    }
}

package com.paopaoxiong.ppx.controller.system;

import com.paopaoxiong.ppx.authorization.UserInfoUtil;
import com.paopaoxiong.ppx.common.ResultInfo;
import com.paopaoxiong.ppx.model.system.Menu;
import com.paopaoxiong.ppx.model.system.Role;
import com.paopaoxiong.ppx.model.system.User;
import com.paopaoxiong.ppx.service.system.MenuService;
import com.paopaoxiong.ppx.service.system.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sys")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private MenuService menuService;

    /**
     * 登录接口
     * @param account
     * @param password
     * @param rememberMe
     * @return
     */
    @RequestMapping("/login")
    public ResultInfo login(@RequestParam(value = "account") String account,
                            @RequestParam(value = "password") String password,
                            @RequestParam(value = "rememberMe", defaultValue = "false") boolean rememberMe){
        ResultInfo resultInfo = new ResultInfo();
        Map<String, Object> data = new HashMap<String, Object>();
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        UsernamePasswordToken token = new UsernamePasswordToken(account,password);
        token.setRememberMe(rememberMe);

        try{
            subject.login(token);
            //用户信息
            User user = userService.queryUserByAccount(account);
            data.put("user",user);
            //角色信息
            List<Role> roles = user.getRoles();
            data.put("roles",roles);
            List<Menu> menuList = menuService.queryAllMenuByUserId(user.getId());
            session.setAttribute(UserInfoUtil.USER_PERMISSION, menuList);
            data.put("permissions", menuList);
            resultInfo.setSuccess(true);
            resultInfo.setData(data);
            resultInfo.setMessage("成功");
        }catch (UnknownAccountException uae) {
            resultInfo.setSuccess(false);
            resultInfo.setData(Collections.EMPTY_LIST);
            resultInfo.setMessage("未知账户");
            resultInfo.setCode(1);
        } catch (IncorrectCredentialsException ice) {
            resultInfo.setSuccess(false);
            resultInfo.setData(Collections.EMPTY_LIST);
            resultInfo.setMessage("密码错误");
            resultInfo.setCode(2);
        } catch (AuthenticationException ae) {
            resultInfo.setSuccess(false);
            resultInfo.setData(Collections.EMPTY_LIST);
            resultInfo.setMessage("用户或密码错误");
            resultInfo.setCode(3);
            token.clear();
        } catch (Exception e) {
            resultInfo.setSuccess(false);
            resultInfo.setData(Collections.EMPTY_LIST);
            resultInfo.setMessage("用户或密码错误");
            resultInfo.setCode(4);
        }

        return resultInfo;
    }

    /**
     * 退出接口
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ResultInfo logout() {
        ResultInfo resultInfo = new ResultInfo();
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.logout();
            resultInfo.setSuccess(true);
            resultInfo.setData(Collections.EMPTY_LIST);
            resultInfo.setMessage("成功");
        } catch (Exception e) {
            resultInfo.setSuccess(false);
            resultInfo.setData(Collections.EMPTY_LIST);
            resultInfo.setMessage("失败");
        }
        return resultInfo;
    }

}

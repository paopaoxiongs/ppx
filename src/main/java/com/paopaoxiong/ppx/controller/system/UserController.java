package com.paopaoxiong.ppx.controller.system;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.paopaoxiong.ppx.authorization.UserInfoUtil;
import com.paopaoxiong.ppx.common.ResultInfo;
import com.paopaoxiong.ppx.common.SystemLogOperation;
import com.paopaoxiong.ppx.model.system.Role;
import com.paopaoxiong.ppx.model.system.User;
import com.paopaoxiong.ppx.service.system.RoleService;
import com.paopaoxiong.ppx.service.system.UserService;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sys/user")
public class UserController {

    //默认密码
    private final static String DEFAULT_PASSWORD = "123456";

    @Autowired
    private UserService userService;
    //
    @Autowired
    private RoleService roleService;

    @GetMapping("/page")
    public String user(Model model) {
        return "system/user/user";
    }

    @RequestMapping("/pageList")
    public ModelAndView list(){
        ModelAndView mav = new ModelAndView();
        Map<String,Object> map = new HashMap<String,Object>();
        List<User> list = userService.queryAllUser(null);
        map.put("list",list);

        mav.setViewName("/system/userList");
        return mav;
    }

    /**
     *
     * @param user
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @GetMapping("/list")
    @ResponseBody
    public Map<String,Object> getUserDataList(User user,
                                      @RequestParam(required = false, defaultValue = "1") int pageIndex,
                                      @RequestParam(required = false, defaultValue = "10") int pageSize){
        Map<String,Object> resultInfo = new HashMap<>();
        try {
            List<User> users  = userService.queryAllUser(user);
            int total = users.size();
            PageHelper.startPage(pageIndex,pageSize);
            List<User> pageInfo = userService.queryAllUser(user);
            resultInfo.put("rows",pageInfo);
            resultInfo.put("total",total);
        }catch (Exception e){

        }
        return resultInfo;
    }

    /**
     * 添加用户
     * @param model
     * @return
     */
    @GetMapping("/add")
    String add(Model model) {
        List<Role> roles = roleService.queryAllRole(null);
        model.addAttribute("roles", roles);
        return "system/user/add";
    }

    /**
     * 新增时，验证用户名是否存在
     * @param params
     * @return
     */
    @PostMapping("/exit")
    @ResponseBody
    public boolean exit(@RequestParam Map<String, Object> params) {
        return !userService.exit(params);// 存在，不通过，false
    }

    @SystemLogOperation(module = "系统管理-用户管理",description = "新增用户")
    @ResponseBody
    @PostMapping(value = "/save")
    public ResultInfo addUser(User user) {
        ResultInfo resultInfo = new ResultInfo();
        user.setPassword(DEFAULT_PASSWORD);
        UserEncry userEncry = new UserEncry();
        userEncry.encrypt(user);
        try{
            userService.add(user);
            resultInfo.setSuccess( true );
            resultInfo.setCode(200);
            resultInfo.setMessage( "操作成功" );
        } catch (Exception e) {
            resultInfo.setSuccess(false);
            resultInfo.setMessage("操作失败");
        }
        return resultInfo;
    }

    /**
     * 编辑用户
     * @param model
     * @param id
     * @return
     */
    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") Integer id) {
        User user = userService.get(id);
        model.addAttribute("user", user);
        List<Role> roles = roleService.getRoleListByUserId(id);
        model.addAttribute("roles", roles);
        return "system/user/editRole";
    }

    @PostMapping("/update")
    @ResponseBody
    public ResultInfo update(User user) {
        ResultInfo resultInfo = new ResultInfo();
        try{
            userService.update(user);
            resultInfo.setCode(200);
            resultInfo.setMessage( "操作成功" );
        } catch (Exception e) {
            resultInfo.setSuccess(false);
            resultInfo.setMessage("操作失败");
        }

        return resultInfo;
    }

    @PostMapping("/remove")
    @ResponseBody
    public ResultInfo remove(Integer id) {
        ResultInfo resultInfo = new ResultInfo();
        try{
            userService.delete(id);
            resultInfo.setCode(200);
            resultInfo.setMessage( "操作成功" );
        } catch (Exception e) {
            resultInfo.setSuccess(false);
            resultInfo.setMessage("操作失败");
        }
        return resultInfo;
    }

    class UserEncry {
        /**
         * 随机数生成器
         */
        private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
        /**
         * 指定散列算法
         */
        private String algorithmName = "MD5";
        /**
         * 散列迭代次数
         */
        private final int hashIterations = 2;

        /**
         *
         * */
        public User encrypt(User user) {
            String salt = randomNumberGenerator.nextBytes().toHex();
            user.setSalt( salt );
            String newPassword = new SimpleHash( algorithmName, user.getPassword(),
                    ByteSource.Util.bytes(user.getSalt()), hashIterations ).toHex();
            user.setPassword( newPassword );
            return user;
        }

    }
}

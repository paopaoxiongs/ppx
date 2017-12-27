package com.paopaoxiong.ppx.controller.system;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.paopaoxiong.ppx.authorization.UserInfoUtil;
import com.paopaoxiong.ppx.common.ResultInfo;
import com.paopaoxiong.ppx.common.SystemLogOperation;
import com.paopaoxiong.ppx.model.system.User;
import com.paopaoxiong.ppx.service.system.UserService;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sys/user")
public class UserController {

    //默认密码
    private final static String DEFAULT_PASSWORD = "123456";

    @Autowired
    private UserService userService;

    @RequestMapping("/page")
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
    @PostMapping("/userList")
    public ResultInfo getUserDataList(User user,
                                      @RequestParam(required = false, defaultValue = "1") int pageIndex,
                                      @RequestParam(required = false, defaultValue = "10") int pageSize){
        ResultInfo resultInfo = new ResultInfo();
        try {
            PageHelper.startPage(pageIndex,pageSize);
            List<User> list = userService.queryAllUser(user);
            PageInfo<User> pageInfo = new PageInfo<>(list);
            resultInfo.setData(pageInfo);
            resultInfo.setSuccess(true);
            resultInfo.setMessage("成功");
        }catch (Exception e){
            resultInfo.setSuccess(false);
            resultInfo.setData(Collections.EMPTY_LIST);
            resultInfo.setMessage("失败");
        }
        return resultInfo;
    }

    @SystemLogOperation(module = "系统管理-用户管理",description = "新增用户")
    @PostMapping(value = "/addUser", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultInfo addUser(User user) {
        ResultInfo resultInfo = new ResultInfo();
        User currentUser = UserInfoUtil.CurrentUserInfo();

        user.setPassword(DEFAULT_PASSWORD);
        UserEncry userEncry = new UserEncry();
        userEncry.encrypt(user);
        user.setStatus(1);

        //user.setCreateUser(currentUser.getId().toString());
        //user.setUpdateUser(currentUser.getId().toString());
        try{
            userService.add(user);
            resultInfo.setSuccess( true );
            resultInfo.setData( Collections.EMPTY_LIST );
            resultInfo.setMessage( "成功" );
        } catch (Exception e) {
            System.out.println(e.getMessage());
            resultInfo.setSuccess(false);
            resultInfo.setData(Collections.EMPTY_LIST);
            resultInfo.setMessage("失败");
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

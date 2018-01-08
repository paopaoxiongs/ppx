package com.paopaoxiong.ppx.shiro;

import com.paopaoxiong.ppx.authorization.UserInfoUtil;
import com.paopaoxiong.ppx.model.system.Menu;
import com.paopaoxiong.ppx.model.system.Role;
import com.paopaoxiong.ppx.model.system.User;
import com.paopaoxiong.ppx.service.system.MenuService;
import com.paopaoxiong.ppx.service.system.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.List;

public class UserAuthRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private MenuService menuService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        User user = userService.queryUserByAccount((String) principal.getPrimaryPrincipal());
        SecurityUtils.getSubject().getSession().setAttribute( String.valueOf( user.getId() ), SecurityUtils.getSubject().getPrincipal() );

        if("admin".equals(user.getAccount())){//超级管理员

        }
        //赋予角色
        List<Role> roles = user.getRoles();
        for(Role role: roles){
            info.addRole(role.getRoleName());
        }
        //赋予权限
        List<Menu> menuList = menuService.queryAllMenuByUserId(user.getId());
        for(Menu menu: menuList ){
            if(!StringUtils.isEmpty(menu.getPermission())){
                info.addStringPermission(menu.getPermission());
            }
        }
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        User user = userService.queryUserByAccount(username);
        if(user != null) {
            Session session = SecurityUtils.getSubject().getSession();
            session.setAttribute( UserInfoUtil.CURRENT_USER, user );
            ByteSource credentialsSalt = ByteSource.Util.bytes( user.getSalt() );
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(),credentialsSalt, getName());
            return info;
        }
        return null;
    }

}

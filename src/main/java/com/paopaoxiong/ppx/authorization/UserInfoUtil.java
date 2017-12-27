package com.paopaoxiong.ppx.authorization;

import com.paopaoxiong.ppx.model.system.User;
import org.apache.shiro.SecurityUtils;

public class UserInfoUtil{

        public static final String CURRENT_USER = "currentUser";
        public static final String USER_ROLE = "userRole";
        public static final String USER_PERMISSION = "userPermission";
        public static User CurrentUserInfo(){
            User user = (User) SecurityUtils.getSubject().getSession().getAttribute( CURRENT_USER );
            return user;
        }
}

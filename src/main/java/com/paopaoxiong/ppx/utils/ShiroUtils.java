package com.paopaoxiong.ppx.utils;

import com.paopaoxiong.ppx.model.system.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * shiro工具类
 */
public class ShiroUtils {
	public static Subject getSubjct() {
		return SecurityUtils.getSubject();
	}
	public static User getUser() {
		return (User)getSubjct().getPrincipal();
	}
	public static Integer getUserId() {
		return getUser().getId();
	}
	public static void logout() {
		getSubjct().logout();
	}
}

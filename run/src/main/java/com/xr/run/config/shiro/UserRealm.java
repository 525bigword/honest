package com.xr.run.config.shiro;

import com.alibaba.fastjson.JSONObject;
import com.xr.run.entity.SysStaff;
import com.xr.run.service.SysStaffService;
import com.xr.run.util.constants.Constants;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

/**
 * @author: hxy
 * @description: 自定义Realm
 * @date: 2017/10/24 10:06
 */
@Slf4j
public class UserRealm extends AuthorizingRealm {
	private Logger logger = LoggerFactory.getLogger(UserRealm.class);

	@Autowired
	private SysStaffService sysStaffService;

	@Override
	@SuppressWarnings("unchecked")
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println(principals);
		Session session = SecurityUtils.getSubject().getSession();
		//查询用户的权限
		JSONObject permission = (JSONObject) session.getAttribute(Constants.SESSION_USER_PERMISSION);
		logger.info("permission的值为:" + permission);
		logger.info("本用户权限为:" + permission.get("permissionList"));
		//为当前用户设置角色和权限
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		authorizationInfo.addStringPermissions((Collection<String>) permission.get("permissionList"));
		return authorizationInfo;
	}

	/**
	 * 验证当前登录的Subject
	 * LoginController.login()方法中执行Subject.login()时 执行此方法
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		String loginName = (String) authcToken.getPrincipal();
		// 获取用户密码
		log.info("获取用户密码::"+loginName);
		String password = new String((char[]) authcToken.getCredentials());
			SysStaff user = sysStaffService.getUser(loginName, password);
		if (user == null) {
			//没找到帐号
			throw new UnknownAccountException();
		}
		System.out.println(user);
		//交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
				user.getUsername(),
				user.getPassword(),
				//ByteSource.Util.bytes("salt"), salt=username+salt,采用明文访问时，不需要此句
				getName()
		);

		//session中不需要保存密码
//		user.setPassword("");
		//将用户信息放入session中
		SecurityUtils.getSubject().getSession().setAttribute(Constants.SESSION_USER_INFO, user);
		return authenticationInfo;
	}

	public static void main(String[] args) {
		SimpleHash md5 = new SimpleHash("md5", "123456", null, 2);
		System.out.println(md5);
	}
}

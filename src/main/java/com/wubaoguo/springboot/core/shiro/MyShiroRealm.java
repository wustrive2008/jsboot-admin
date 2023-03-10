package com.wubaoguo.springboot.core.shiro;

import com.wubaoguo.springboot.constant.ShiroConstants;
import com.wubaoguo.springboot.core.bean.CurrentRole;
import com.wubaoguo.springboot.core.bean.CurrentUser;
import com.wubaoguo.springboot.core.bean.LoginParam;
import com.wubaoguo.springboot.core.exception.LoginSecurityException;
import com.wubaoguo.springboot.manage.service.AuthenticationService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

/**
 * Description:
 *
 * @author: wubaoguo
 * @email: wustrive2008@gmail.com
 * @date: 2018/3/19 16:01
 */
public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private AuthenticationService authenticationService;

    /**
     * 认证信息.(身份验证) : Authentication 是用来验证用户身份
     *
     * @param authcToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
            throws AuthenticationException {
        // 获取基于用户名和密码的令牌
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        String username = token.getUsername();

        if (StringUtils.isNotBlank(username)) {
            try {
                LoginParam loginParam = new LoginParam(token.getUsername(), String.valueOf(token.getPassword()));
                // 登录信息数据底层校验
                CurrentUser currentUser = authenticationService.login(loginParam);
                this.setSession(ShiroConstants.SESSION_CURRENT_USER, currentUser);
                // 查询底层角色信息
                List<CurrentRole> roles =
                        authenticationService.findUserRoles(loginParam.getUsername());
                if (roles != null && !roles.isEmpty()) {
                    // 获取 多角色 第一个 角色 作为默认使用角色
                    this.setSession(ShiroConstants.SESSION_CURRENT_ROLE, roles.get(0).getCode());
                    this.setSession(ShiroConstants.SESSION_CURRENT_ROLES, roles);
                } else {
                    throw new ConcurrentAccessException("当前用户未配置角色");
                }
                return new SimpleAuthenticationInfo(loginParam.getUsername(), loginParam.getPassword(),
                        this.getName());
            } catch (LoginSecurityException | AccountException le) {
                if (le instanceof LoginSecurityException) {
                    throw new AccountException(le.getMessage());
                } else {
                    throw (AccountException) le;
                }
            }
        } else {
            throw new UnknownAccountException("No account found for user [" + username + "]");
        }
    }

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 获取当前登录的用户名
        String currentUsername = (String) super.getAvailablePrincipal(principals);
        // 为当前用户设置角色和权限
        SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();
        if (null != currentUsername) {
            // 查询底层角色信息
            Set<String> roles = authenticationService.findShiroRoles(currentUsername);
            // 赋予shrio 权限验证
            simpleAuthorInfo.addRoles(roles);
            return simpleAuthorInfo;
        }
        return null;
    }

    private void setSession(Object key, Object value) {
        Subject currentUser = SecurityUtils.getSubject();
        if (null != currentUser) {
            Session session = currentUser.getSession();
            if (null != session) {
                session.setAttribute(key, value);
            }
        }
    }

}

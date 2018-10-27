package org.yingzuidou.cms.cmsweb.core.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.yingzuidou.cms.cmsweb.biz.UserBiz;
import org.yingzuidou.cms.cmsweb.entity.CmsUserEntity;
import org.yingzuidou.cms.cmsweb.entity.UserRoleEntity;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Shiro相关的授权、校验
 *
 * @author 鹰嘴豆
 * @date 2018/10/14     
 */
public class CmsRealm extends AuthorizingRealm {

    @Autowired
    private UserBiz userBiz;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String userName = token.getUsername();
        String password = new String((char[]) token.getCredentials());
        // 从数据库获取对应用户名密码的用户
        CmsUserEntity user = userBiz.findByUserAccount(userName);
        // 保存当前用户
        SecurityUtils.getSubject().getSession().setAttribute("curUser", user);
        if (Objects.isNull(user) || !user.getUserPassword().equals(password)) {
            throw new AuthenticationException("用户名或密码不存在");
        }
        return new SimpleAuthenticationInfo(user, user.getUserPassword(), getName());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("需要调用subject.hasRoles()才能调用该方法");
        return null;
    }

}

package org.yingzuidou.cms.cmsweb.core.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.yingzuidou.cms.cmsweb.biz.UserBiz;
import org.yingzuidou.cms.cmsweb.entity.CmsUserEntity;

import java.util.HashSet;
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
        CmsUserEntity user = userBiz.findByUserAccount(token.getUsername());
        if (Objects.isNull(user) || !user.getUserPassword().equals(password)) {
            throw new AuthenticationException("用户名或密码不存在");
        }
        return new SimpleAuthenticationInfo(token.getPrincipal(), user.getUserPassword(), getName());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //获得该用户角色
        String role = null;
        Set<String> set = new HashSet<>();
        //需要将 role 封装到 Set 作为 info.setRoles() 的参数
        set.add(role);
        //设置该用户拥有的角色
        info.setRoles(set);
        return info;
    }
}

package org.yingzuidou.cms.cmsweb.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yingzuidou.cms.cmsweb.core.CmsMap;
import org.yingzuidou.cms.cmsweb.core.exception.BusinessException;
import org.yingzuidou.cms.cmsweb.dto.UserDTO;
import org.yingzuidou.cms.cmsweb.entity.CmsUserEntity;
import org.yingzuidou.cms.cmsweb.service.LoginService;

/**
 * 登录控制类
 *
 * @author 鹰嘴豆
 * @date 2018/10/14     
 */
@RestController
@RequestMapping(value="/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login.do")
    public CmsMap login(@RequestBody UserDTO userDTO) {
        Subject subject = SecurityUtils.getSubject();
        if ( !subject.isAuthenticated() ) {
            UsernamePasswordToken token = new UsernamePasswordToken(userDTO.getUserAccount(), userDTO.getUserPassword());
            try {
                subject.login( token );
            } catch (IncorrectCredentialsException iException) {
                throw new BusinessException("账号或者密码不正确");
            } catch ( AuthenticationException authException ) {
                throw new BusinessException(authException.getMessage());
            }
        }
        CmsUserEntity user = (CmsUserEntity) subject.getPrincipals().getPrimaryPrincipal();
        return CmsMap.<CmsUserEntity>ok()
                .appendData("token", subject.getSession().getId())
                .setResult(user);
    }

    @PostMapping("/logout.do")
    public CmsMap logout() {
        SecurityUtils.getSubject().logout();
        return CmsMap.ok();
    }

    /**
     * 未授权接口返回
     *
     * @return 403未授权状态码
     */
    @GetMapping("/unAuthor.do")
    public CmsMap unAuthor() {
        return CmsMap.error("403", "当前请求资源未授权");
    }
}

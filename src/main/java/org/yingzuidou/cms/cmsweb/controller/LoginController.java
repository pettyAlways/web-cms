package org.yingzuidou.cms.cmsweb.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yingzuidou.cms.cmsweb.core.CmsMap;
import org.yingzuidou.cms.cmsweb.core.vo.Node;
import org.yingzuidou.cms.cmsweb.dto.UserDTO;
import org.yingzuidou.cms.cmsweb.entity.CmsUserEntity;
import org.yingzuidou.cms.cmsweb.entity.ResourceEntity;
import org.yingzuidou.cms.cmsweb.service.LoginService;

import java.util.List;

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
        CmsMap<List<Integer>> cMap = new CmsMap<>();
        Subject subject = SecurityUtils.getSubject();
        if ( !subject.isAuthenticated() ) {
            UsernamePasswordToken token = new UsernamePasswordToken(userDTO.getUserAccount(), userDTO.getUserPassword());
            try {
                subject.login( token );
            } catch ( AuthenticationException authException ) {
                cMap.error("10086", authException.getMessage());
                return cMap;
            }
        }
        CmsUserEntity user = (CmsUserEntity) subject.getSession().getAttribute("curUser");
        Node permissions = loginService.acquireUserPermission(user.getId());
        subject.getSession().setAttribute("resources", permissions);
        return cMap.success().appendData("token", subject.getSession().getId());
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

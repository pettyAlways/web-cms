package org.yingzuidou.cms.cmsweb.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yingzuidou.cms.cmsweb.core.CmsMap;
import org.yingzuidou.cms.cmsweb.dto.UserDTO;

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
            }
        }

        return cMap;
    }
}

package org.yingzuidou.cms.cmsweb.core.shiro;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;

import java.util.Map;

/**
 * ShiroService
 *
 * @author 鹰嘴豆
 * @date 2018/10/22
 */
public interface ShiroService {

    Map<String, String> loadFilterChainDefinitions();

    void updatePermission(ShiroFilterFactoryBean shiroFilterFactoryBean);
}

package org.yingzuidou.cms.cmsweb.core.configuration;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.yingzuidou.cms.cmsweb.biz.ResourceBiz;
import org.yingzuidou.cms.cmsweb.core.shiro.CmsRealm;
import org.yingzuidou.cms.cmsweb.core.shiro.CustomFormAuthenticationFilter;
import org.yingzuidou.cms.cmsweb.core.shiro.CustomRolesAuthorizationFilter;
import org.yingzuidou.cms.cmsweb.core.shiro.ShiroService;

import javax.servlet.Filter;
import java.util.Map;

/**
 * 配置属性类
 *
 * @author 鹰嘴豆
 * @date 2018/10/14     
 */

@Configuration
public class CmsConfiguration{

    @Autowired
    private ResourceBiz resourceBiz;

    @Value("${skip.login.url}")
    private String skipPath;

    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager, ShiroService shiroService) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
         // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 设置无权限时跳转的 url;
        shiroFilterFactoryBean.setUnauthorizedUrl("/login/unAuthor.do");

        // 重写roles拦截规则 path = roles['role,role2']
        Map<String, Filter> extraFilter = shiroFilterFactoryBean.getFilters();
        extraFilter.put("roles", new CustomRolesAuthorizationFilter());
        extraFilter.put("authc", new CustomFormAuthenticationFilter());
        shiroFilterFactoryBean.setFilters(extraFilter);
        // 加载拦截资源
        shiroFilterFactoryBean.setFilterChainDefinitionMap(shiroService.loadFilterChainDefinitions());
        return shiroFilterFactoryBean;
    }

    /**
     * 注入 securityManager
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setSessionManager(sessionManager());
        securityManager.setRealm(cmsRealm());
        return securityManager;
    }

    @Bean
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
        defaultWebSessionManager.setGlobalSessionTimeout(6000000);
        defaultWebSessionManager.setDeleteInvalidSessions(true);
        return defaultWebSessionManager;
    }
    /**
     * 自定义身份认证 realm,指定密码加密的credentialsMetcher
     * <p>
     * 必须写这个类，并加上 @Bean 注解，目的是注入 cmsRealm，
     * 否则会影响 cmsRealm类 中其他类的依赖注入
     */
    @Bean
    public CmsRealm cmsRealm() {
        CmsRealm cmsRealm = new CmsRealm();
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        // 加密算法的名称
        matcher.setHashAlgorithmName("MD5");
        // 配置加密的次数
        matcher.setHashIterations(1024);
        cmsRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return cmsRealm;
    }

    /**
     * 默认使用的是SimpleCredentialsMatcher，这里要使用HashedCredentialsMatcher
     * 用户密码不可逆，所以查看用户信息看到的是密文
     * @return HashedCredentialsMatcher对象
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        // 加密算法的名称
        matcher.setHashAlgorithmName("MD5");
        // 配置加密的次数
        matcher.setHashIterations(1024);
        return matcher;
    }

    @Bean("defaultAdvisorAutoProxyCreator")
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        //指定强制使用cglib为action创建代理对象
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

}

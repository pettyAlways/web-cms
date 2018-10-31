package org.yingzuidou.cms.cmsweb.core.shiro;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * CustomFormAuthenticationFilter
 *
 * @author 鹰嘴豆
 * @date 2018/10/22
 */
public class CustomFormAuthenticationFilter extends FormAuthenticationFilter {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (isLoginRequest(request, response)) {
            if (isLoginSubmission(request, response)) {
                if (logger.isTraceEnabled()) {
                    logger.trace("发现登录请求，准备登录");
                }
                return executeLogin(request, response);
            } else {
                if (logger.isTraceEnabled()) {
                    logger.trace("回到登录页面");
                }
                return true;
            }
        } else {
            if (logger.isTraceEnabled()) {
                logger.trace("试图访问一个需要认证的地址，重定向到登录页面");
            }
            // 需要登录才能访问
            WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
    }
}

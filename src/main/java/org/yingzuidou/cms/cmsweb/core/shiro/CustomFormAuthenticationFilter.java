package org.yingzuidou.cms.cmsweb.core.shiro;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

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

            saveRequestAndRedirectToLogin(request, response);
            return false;
        }
    }
    @Override
    protected void saveRequestAndRedirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = response.getWriter();
        Map<String, Object> map = new HashMap<>();
        map.put("flag", false);
        map.put("code", "401");
        map.put("message", "请求未经授权，请登录");
        writer.write(map.toString());
    }
}

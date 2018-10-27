package org.yingzuidou.cms.cmsweb.core.interceptor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * SessionInterceptor
 *
 * @author 鹰嘴豆
 * @date 2018/10/17
 */
@Component
public class SessionInterceptor implements HandlerInterceptor {

    @Value("${skip.login.url}")
    private String skipPath;

    private PathMatcher matcher = new AntPathMatcher();
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String[] paths = skipPath.split(",");
        for (String uriPath : paths) {
            if (matcher.match(uriPath, request.getRequestURI())) {
                return true;
            }
        }
        HttpSession session = request.getSession();
        if (Objects.isNull(session)) {
            response.setStatus(403);
            response.getWriter().write("Session超时了");
            return false;
        }

        return true;
    }
}

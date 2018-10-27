package org.yingzuidou.cms.cmsweb.core.shiro;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.yingzuidou.cms.cmsweb.biz.UserBiz;
import org.yingzuidou.cms.cmsweb.core.SpringUtil;
import org.yingzuidou.cms.cmsweb.entity.CmsUserEntity;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * CustomRolesAuthorizationFilter
 *
 * @author 鹰嘴豆
 * @date 2018/10/21
 */
public class CustomRolesAuthorizationFilter extends AuthorizationFilter {

    private HttpServletRequest request;

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        Subject subject = getSubject(request, response);
        CmsUserEntity user = (CmsUserEntity) subject.getPrincipals().getPrimaryPrincipal();

        if (null == user) {
            return true;
        }
        String[] rolesArray = (String[]) mappedValue;
        if (rolesArray == null || rolesArray.length == 0) {
            return true;
        }
        List<String> rolesList = Arrays.asList(rolesArray);
        UserBiz userBiz = SpringUtil.getBean(UserBiz.class);
        List<String> roleNames = userBiz.findRoleNameByUserId(user.getId());
        Set<String> roleSet = new HashSet<>(roleNames);

        boolean disjoint = Collections.disjoint(roleSet, rolesList);
        return !disjoint;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {

        Subject subject = getSubject(request, response);
        // If the subject isn't identified, redirect to login URL
        if (subject.getPrincipal() == null) {
            saveRequestAndRedirectToLogin(request, response);
        } else {
            // If subject is known but not authorized, redirect to the unauthorized URL if there is one
            // If no unauthorized URL is specified, just return an unauthorized HTTP status code
            String unauthorizedUrl = getUnauthorizedUrl();
            //SHIRO-142 - ensure that redirect _or_ error code occurs - both cannot happen due to response commit:
            if (StringUtils.hasText(unauthorizedUrl)) {
                WebUtils.issueRedirect(request, response, unauthorizedUrl);
            } else {
                WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
        return false;
    }
}

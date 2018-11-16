package org.yingzuidou.cms.cmsweb.core.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.yingzuidou.cms.cmsweb.entity.CmsUserEntity;

/**
 * 类功能描述
 * 提供系统常用到的工具接口
 *
 * @author 鹰嘴豆
 * @date 2018/11/13
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 * 2018/11/13     鹰嘴豆        v.10        常用工具接口
 */
public class CmsCommonUtil {

    /**
     * 从shiro中得到当前的登录用户
     *
     * @return 当前登录用户对象
     */
    public static CmsUserEntity getCurrentLoginUser() {
        Subject subject = SecurityUtils.getSubject();
        CmsUserEntity user = (CmsUserEntity) subject.getPrincipals().getPrimaryPrincipal();
        return user;
    }

    /**
     * 得到当前登录用户的ID,在新增和修改数据库记录中
     * 需要获取当前操作用户的ID
     *
     * @return 当前登录用户ID
     */
    public static Integer getCurrentLoginUserId() {
        CmsUserEntity currentUser = getCurrentLoginUser();
        return currentUser.getId();
    }
}

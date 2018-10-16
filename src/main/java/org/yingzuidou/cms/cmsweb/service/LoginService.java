package org.yingzuidou.cms.cmsweb.service;

import org.yingzuidou.cms.cmsweb.core.vo.Node;

/**
 * LoginService
 *
 * @author shangguanls
 * @date 2018/10/16
 */
public interface LoginService {

    Node acquireUserPermission(int userId);
}

package org.yingzuidou.cms.cmsweb.service;

import org.yingzuidou.cms.cmsweb.core.paging.PageInfo;
import org.yingzuidou.cms.cmsweb.dto.PermissionDTO;

/**
 * PermissionService
 *
 * @author shangguanls
 * @date 2018/9/26
 */
public interface PermissionService {

    PermissionDTO listPower();

    PermissionDTO subPower(PermissionDTO permissionDTO, PageInfo pageInfo);

    PermissionDTO deletePower(String ids);
}

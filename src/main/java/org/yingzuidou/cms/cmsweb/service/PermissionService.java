package org.yingzuidou.cms.cmsweb.service;

import org.yingzuidou.cms.cmsweb.core.paging.PageInfo;
import org.yingzuidou.cms.cmsweb.core.vo.Node;
import org.yingzuidou.cms.cmsweb.dto.PermissionDTO;
import org.yingzuidou.cms.cmsweb.entity.CmsConstEntity;
import org.yingzuidou.cms.cmsweb.entity.ResourceEntity;

import java.util.List;

/**
 * PermissionService
 *
 * @author shangguanls
 * @date 2018/9/26
 */
public interface PermissionService {

    PermissionDTO listPower();

    PermissionDTO subPower(PermissionDTO permissionDTO, PageInfo pageInfo);

    void deletePower(String ids);

    void updateResouce(ResourceEntity entity);

    void saveResource(ResourceEntity entity);

    Node acquireUserPermission(int userId);

}

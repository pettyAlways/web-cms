package org.yingzuidou.cms.cmsweb.service.impl;

import org.springframework.stereotype.Service;
import org.yingzuidou.cms.cmsweb.core.paging.PageInfo;
import org.yingzuidou.cms.cmsweb.dto.PermissionDTO;
import org.yingzuidou.cms.cmsweb.service.PermissionService;

/**
 * PermissionService
 *
 * @author shangguanls
 * @date 2018/9/26
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Override
    public PermissionDTO listPower() {
        return null;
    }

    @Override
    public PermissionDTO subPower(PermissionDTO permissionDTO, PageInfo pageInfo) {
        return null;
    }

    @Override
    public PermissionDTO deletePower(String ids) {
        return null;
    }
}

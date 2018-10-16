package org.yingzuidou.cms.cmsweb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yingzuidou.cms.cmsweb.biz.ResourceBiz;
import org.yingzuidou.cms.cmsweb.core.vo.Node;
import org.yingzuidou.cms.cmsweb.dao.PermissionRepository;
import org.yingzuidou.cms.cmsweb.dao.RoleResourceRepository;
import org.yingzuidou.cms.cmsweb.dao.UserRoleRepository;
import org.yingzuidou.cms.cmsweb.entity.ResourceEntity;
import org.yingzuidou.cms.cmsweb.entity.RoleResourceEntity;
import org.yingzuidou.cms.cmsweb.entity.UserRoleEntity;
import org.yingzuidou.cms.cmsweb.service.LoginService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * LoginServiceImpl
 *
 * @author shangguanls
 * @date 2018/10/16
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private RoleResourceRepository roleResourceRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private ResourceBiz resouceBiz;

    @Override
    public Node acquireUserPermission(int userId) {
        List<UserRoleEntity> userRoleEntities = userRoleRepository.findAllByUserId(userId);
        List<ResourceEntity> resourceEntities = null;
        if (!Objects.isNull(userRoleEntities)) {
           List<Integer> roleIds =  userRoleEntities.stream().map(UserRoleEntity::getRoleId).collect(Collectors.toList());
           List<RoleResourceEntity> roleResourceEntities = roleResourceRepository.findAllByRoleIdIn(roleIds);
           if (!Objects.isNull(roleResourceEntities)) {
               List<Integer> resourceIds = roleResourceEntities.stream().map(RoleResourceEntity::getResourceId).collect(Collectors.toList());
               if (!Objects.isNull(resourceIds)) {
                   resourceEntities = permissionRepository.findAllByIdInAndIsDeleteIs(resourceIds, "N");
               }
           }
        }
        return resouceBiz.acquirePermissions(resourceEntities);
    }
}

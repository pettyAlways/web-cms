package org.yingzuidou.cms.cmsweb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.yingzuidou.cms.cmsweb.biz.ResourceBiz;
import org.yingzuidou.cms.cmsweb.core.paging.PageInfo;
import org.yingzuidou.cms.cmsweb.core.vo.Node;
import org.yingzuidou.cms.cmsweb.dao.PermissionRepository;
import org.yingzuidou.cms.cmsweb.dao.RoleResourceRepository;
import org.yingzuidou.cms.cmsweb.dao.UserRoleRepository;
import org.yingzuidou.cms.cmsweb.dto.PermissionDTO;
import org.yingzuidou.cms.cmsweb.entity.ResourceEntity;
import org.yingzuidou.cms.cmsweb.entity.RoleResourceEntity;
import org.yingzuidou.cms.cmsweb.entity.UserRoleEntity;
import org.yingzuidou.cms.cmsweb.service.PermissionService;
import org.yingzuidou.cms.cmsweb.core.utils.CmsBeanUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * PermissionService
 *
 * @author shangguanls
 * @date 2018/9/26
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private RoleResourceRepository roleResourceRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private ResourceBiz resouceBiz;

    @Override
    public PermissionDTO listPower() {
        List<ResourceEntity> flatNode = permissionRepository.findAllByIsDeleteIs("N");
        Node root = resouceBiz.acquirePermissions(flatNode);
        PermissionDTO permissionDTO = new PermissionDTO();
        permissionDTO.setTree(root);
        return permissionDTO;
    }


    @Override
    public PermissionDTO subPower(PermissionDTO permissionDTO, PageInfo pageInfo) {
        Page<ResourceEntity> ResourcePage = resouceBiz.findAllResourceWithCondition(permissionDTO, pageInfo.toPageable());
        permissionDTO.setResources(ResourcePage.getContent());
        pageInfo.setCounts(ResourcePage.getTotalElements());
        return permissionDTO;
    }

    @Override
    public void deletePower(String ids) {
        List<Integer> idsList = Arrays.stream(ids.split(",")).map(Integer::valueOf)
                .collect(Collectors.toList());
        List<ResourceEntity> resourceList = permissionRepository.findAllByIdInAndIsDeleteIs(idsList, "N");
        Optional.ofNullable(resourceList).orElse(Collections.emptyList()).forEach(item -> {
            item.setIsDelete("Y");
            item.setUpdator(1);
            item.setUpdateTime(new Date());
        });
        permissionRepository.saveAll(resourceList);
    }

    @Override
    public void updateResouce(ResourceEntity entity) {
        // 这里对于对象是否已经被删除等就不做判断
        ResourceEntity resourceEntity = permissionRepository.findById(entity.getId()).get();
         CmsBeanUtils.copyMorNULLProperties(entity, resourceEntity);
        resourceEntity.setUpdator(1);
        resourceEntity.setUpdateTime(new Date());
        permissionRepository.save(resourceEntity);
    }

    @Override
    public void saveResource(ResourceEntity entity) {
        entity.setCreator(1);
        entity.setCreateTime(new Date());
        // 设置根资源
        if (entity.getParentId() == null) {
            entity.setParentId(-1);
        }
        // 如果不设置jpa会用null覆盖mysql的默认值
        entity.setIsDelete("N");
        permissionRepository.save(entity);
    }

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

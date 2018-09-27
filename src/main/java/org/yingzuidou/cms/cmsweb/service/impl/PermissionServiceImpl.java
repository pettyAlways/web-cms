package org.yingzuidou.cms.cmsweb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.yingzuidou.cms.cmsweb.biz.ResourceBiz;
import org.yingzuidou.cms.cmsweb.core.paging.PageInfo;
import org.yingzuidou.cms.cmsweb.core.vo.Node;
import org.yingzuidou.cms.cmsweb.dao.PermissionRepository;
import org.yingzuidou.cms.cmsweb.dto.OrganizationDTO;
import org.yingzuidou.cms.cmsweb.dto.PermissionDTO;
import org.yingzuidou.cms.cmsweb.entity.OrganizationEntity;
import org.yingzuidou.cms.cmsweb.entity.ResourceEntity;
import org.yingzuidou.cms.cmsweb.service.PermissionService;
import org.yingzuidou.cms.cmsweb.util.CmsBeanUtils;

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
    private PermissionRepository permissionRepository;

    @Autowired
    private ResourceBiz resouceBiz;

    @Override
    public PermissionDTO listPower() {
        Node root = new Node();
        // 从字典中查找
        root.setName("CMS平台资源");
        root.setId(-1);
        List<ResourceEntity> flatNode = permissionRepository.findAllByIsDeleteIs("N");
        getChildrenList(root, flatNode);
        PermissionDTO permissionDTO = new PermissionDTO();
        permissionDTO.setTree(root);
        return permissionDTO;
    }

    private void getChildrenList(Node parentNode, List<ResourceEntity> allNode) {
        List<ResourceEntity> allNodeList = Optional.ofNullable(allNode).orElse(Collections.EMPTY_LIST);
        List<Node> childrenNodeList = allNodeList.stream()
                .filter(node -> parentNode.getId().equals(node.getParentId()))
                .map(node -> {
                    // Entity -> DTO 转化页面所需的信息
                    Node child = new Node();
                    child.setId(node.getId());
                    child.setName(node.getResourceName());
                    child.setIcon(node.getResourceIcon());
                    child.setType(node.getResourceType());
                    child.setSort(node.getResourceSort());
                    return child;
                })
                .collect(Collectors.toList());
        parentNode.setChildren(childrenNodeList);

        // 孩子列表获取出来可能是空，因此需要用Optional处理
        Optional.ofNullable(childrenNodeList).orElse(Collections.EMPTY_LIST)
                .forEach(node -> getChildrenList((Node) node, allNode));

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
        List<Integer> idsList = Arrays.stream(ids.split(",")).map(item -> Integer.valueOf(item))
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
}

package org.yingzuidou.cms.cmsweb.biz;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.yingzuidou.cms.cmsweb.core.vo.Node;
import org.yingzuidou.cms.cmsweb.dao.PermissionRepository;
import org.yingzuidou.cms.cmsweb.dto.PermissionDTO;
import org.yingzuidou.cms.cmsweb.entity.QResourceEntity;
import org.yingzuidou.cms.cmsweb.entity.ResourceEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * 资源业务类
 *
 * @author 鹰嘴豆
 * @date 2018/9/27     
 */
@Service
public class ResourceBiz {

    @Autowired
    private PermissionRepository permissionRepository;
    private QResourceEntity qResourceEntity = QResourceEntity.resourceEntity;

    public Page<ResourceEntity> findAllResourceWithCondition(PermissionDTO permissionDTO, Pageable pageable) {
        BooleanExpression expression = qResourceEntity.isDelete.eq("N").and(qResourceEntity.parentId
                .eq(permissionDTO.getParentId()));
        if (!StringUtils.isEmpty(permissionDTO.getResourceName())) {
            expression = expression.and(qResourceEntity.resourceName.like("%" + permissionDTO.getResourceName() + "%"));
        }
        return permissionRepository.findAll(expression, pageable);
    }

    public Node acquirePermissions(List<ResourceEntity> flatNode) {
        Node root = new Node();
        // 从字典中查找
        root.setName("CMS平台资源");
        root.setId(-1);
        getChildrenList(root, flatNode);
        return root;
    }


    public void getChildrenList(Node parentNode, List<ResourceEntity> allNode) {
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
                    child.setPath(node.getResourcePath());
                    child.setAlias(node.getAlias());
                    child.setDefaultPage(node.getDefaultPage());
                    return child;
                })
                .collect(Collectors.toList());
        parentNode.setChildren(childrenNodeList);

        // 孩子列表获取出来可能是空，因此需要用Optional处理
        Optional.ofNullable(childrenNodeList).orElse(Collections.EMPTY_LIST)
                .forEach(node -> getChildrenList((Node) node, allNode));

    }
}

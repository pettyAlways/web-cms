package org.yingzuidou.cms.cmsweb.biz;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.yingzuidou.cms.cmsweb.dao.PermissionRepository;
import org.yingzuidou.cms.cmsweb.dto.PermissionDTO;
import org.yingzuidou.cms.cmsweb.entity.QResourceEntity;
import org.yingzuidou.cms.cmsweb.entity.ResourceEntity;


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
}

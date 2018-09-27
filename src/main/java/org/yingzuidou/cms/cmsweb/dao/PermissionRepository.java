package org.yingzuidou.cms.cmsweb.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.yingzuidou.cms.cmsweb.entity.OrganizationEntity;
import org.yingzuidou.cms.cmsweb.entity.ResourceEntity;
import sun.security.provider.PolicyParser;

import javax.annotation.Resource;
import java.util.List;

/**
 * 组织机构持久化类
 *
 * @author yingzuidou
 * @date 2018/9/13     
 */
public interface PermissionRepository extends PagingAndSortingRepository<ResourceEntity, Integer>, QuerydslPredicateExecutor<ResourceEntity> {

    List<ResourceEntity> findAllByIsDeleteIs(String isDelete);

    List<ResourceEntity> findAllByIdInAndIsDeleteIs(List<Integer> ids, String isDelete);
}

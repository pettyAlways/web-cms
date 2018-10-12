package org.yingzuidou.cms.cmsweb.dao;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.yingzuidou.cms.cmsweb.entity.RoleEntity;
import org.yingzuidou.cms.cmsweb.entity.RoleResourceEntity;

import java.util.List;

/**
 * RoleResourceRepository
 *
 * @author 鹰嘴豆
 * @date 2018/9/24
 */
public interface RoleResourceRepository extends PagingAndSortingRepository<RoleResourceEntity, Integer>, QuerydslPredicateExecutor<RoleResourceEntity> {

    void deleteAllByRoleIdIs(Integer roleId);
    List<RoleResourceEntity> findAllByRoleIdIs(Integer roleId);

}

package org.yingzuidou.cms.cmsweb.dao;

import org.springframework.data.jpa.repository.Query;
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

    List<RoleResourceEntity> findAllByRoleIdIn(List<Integer> roleIds);

    @Query(nativeQuery = true, value = "SELECT r.resource_path, GROUP_CONCAT( role.role_name ) " +
            "FROM resource r LEFT JOIN role_resource roleReource ON r.id = roleReource.resource_id " +
            "LEFT JOIN role role ON role.id = roleReource.role_id WHERE r.is_delete = 'N' AND r.resource_type= 'button' GROUP BY r.id")
    List<Object> acquireRoleResources();

}

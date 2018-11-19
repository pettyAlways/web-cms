package org.yingzuidou.cms.cmsweb.dao;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.yingzuidou.cms.cmsweb.entity.RoleEntity;
import org.yingzuidou.cms.cmsweb.entity.UserRoleEntity;

import java.util.List;

/**
 * RoleRepository
 *
 * @author 鹰嘴豆
 * @date 2018/9/24
 */
public interface UserRoleRepository extends PagingAndSortingRepository<UserRoleEntity, Integer>, QuerydslPredicateExecutor<UserRoleEntity> {

    void deleteAllByUserId(Integer userId);

    List<UserRoleEntity> findAllByUserId(Integer userId);

    List<UserRoleEntity> findAllByRoleId(Integer roleId);

}

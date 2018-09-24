package org.yingzuidou.cms.cmsweb.dao;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.yingzuidou.cms.cmsweb.entity.CmsUserEntity;

/**
 * UserRepository
 *
 * @author shangguanls
 * @date 2018/9/24
 */
public interface UserRepository extends PagingAndSortingRepository<CmsUserEntity, Integer>, QuerydslPredicateExecutor<CmsUserEntity> {

}

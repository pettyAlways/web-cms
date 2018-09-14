package org.yingzuidou.cms.cmsweb.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.yingzuidou.cms.cmsweb.entity.OrganizationEntity;

import java.util.List;
import java.util.Map;

/**
 * 组织机构持久化类
 *
 * @author yingzuidou
 * @date 2018/9/13     
 */
public interface OrganizationRepository extends PagingAndSortingRepository<OrganizationEntity, Integer> {

    List<OrganizationEntity> findByParentId(Integer parentId);

    List<OrganizationEntity> findAllByIsDeleteIs(String isDelete);
}

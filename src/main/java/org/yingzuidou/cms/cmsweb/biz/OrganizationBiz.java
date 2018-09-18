package org.yingzuidou.cms.cmsweb.biz;


import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.yingzuidou.cms.cmsweb.dao.OrganizationRepository;
import org.yingzuidou.cms.cmsweb.dto.OrganizationDTO;
import org.yingzuidou.cms.cmsweb.entity.OrganizationEntity;
import org.yingzuidou.cms.cmsweb.entity.QOrganizationEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 组织业务实现类
 *
 * @author yingzuidou
 * @date 2018/9/18     
 */
@Service
public class OrganizationBiz {

    @Autowired
    public OrganizationRepository organizationRepository;

    private QOrganizationEntity qOrganizationEntity = QOrganizationEntity.organizationEntity;

    /**
     * 根据传入orgId查询未删除的实体类
     *
     * @param orgId  当前组织ID
     * @return
     */
    public Optional<OrganizationEntity> findOrgById(Integer orgId) {
        Predicate predicate = qOrganizationEntity.isDelete.eq("N").and(qOrganizationEntity.id.eq(orgId));
        Optional<OrganizationEntity> orgEntity = organizationRepository.findOne(predicate);
        return orgEntity;
    }

    public Page<OrganizationEntity> findAllSubOrganizationWithCondition(OrganizationDTO organizationDTO, Pageable pageable) {
        List<Predicate> predicateList = new ArrayList<>();
        if (!StringUtils.isEmpty(organizationDTO.getOrgName())) {
            predicateList.add(qOrganizationEntity.orgName.like("%" + organizationDTO.getOrgName() + "%"));
        }
        Predicate[] predicates = new Predicate[predicateList.size()];
        Predicate predicate = qOrganizationEntity.parentId.eq(organizationDTO.getParentId())
                .and(qOrganizationEntity.isDelete.eq("N")).andAnyOf(predicateList.toArray(predicates));
        return organizationRepository.findAll(predicate, pageable);
    }
}

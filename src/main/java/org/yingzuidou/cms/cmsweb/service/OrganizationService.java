package org.yingzuidou.cms.cmsweb.service;

import org.yingzuidou.cms.cmsweb.dto.OrganizationDTO;
import org.yingzuidou.cms.cmsweb.entity.OrganizationEntity;

import java.util.List;
import java.util.Map;

/**
 * 组织机构接口
 *
 * @author yingzuidou
 * @date 2018/9/13
 */
public interface OrganizationService {

    public List<OrganizationEntity> list(Integer nodeId);

    public void save(OrganizationEntity organizationEntity);

    public void update(OrganizationEntity organizationEntity);

    public OrganizationDTO listTree();

}

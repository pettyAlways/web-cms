package org.yingzuidou.cms.cmsweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yingzuidou.cms.cmsweb.core.CmsMap;
import org.yingzuidou.cms.cmsweb.dto.OrganizationDTO;
import org.yingzuidou.cms.cmsweb.entity.OrganizationEntity;
import org.yingzuidou.cms.cmsweb.service.OrganizationService;

import java.util.List;
import java.util.Map;

/**
 * OrganizationController 组织机构
 *
 * @author yingzuidou
 * @date 2018/9/13
 */

@RestController
@RequestMapping(value="/organization")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @RequestMapping(value="/list.do")
    public Map getUser(Integer nodeId) {
        CmsMap<OrganizationEntity> cMap = new CmsMap<>();
        List<OrganizationEntity> result = organizationService.list(nodeId);
        cMap.success().setData(result);
        return cMap;
    }

    @RequestMapping(value="/listTree.do")
    public Map listTree(Integer nodeId) {
        CmsMap<OrganizationEntity> cMap = new CmsMap<>();
        OrganizationDTO result = organizationService.listTree();
        cMap.success().setData(result);
        return cMap;
    }
}

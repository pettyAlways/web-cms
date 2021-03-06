package org.yingzuidou.cms.cmsweb.controller;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yingzuidou.cms.cmsweb.core.CmsMap;
import org.yingzuidou.cms.cmsweb.core.paging.PageInfo;
import org.yingzuidou.cms.cmsweb.dto.OrganizationDTO;
import org.yingzuidou.cms.cmsweb.entity.OrganizationEntity;
import org.yingzuidou.cms.cmsweb.service.OrganizationService;

/**
 * OrganizationController 组织机构
 *
 * @author yingzuidou
 * @date 2018/9/13
 */

@RestController
@RequestMapping(value="/organization")
public class OrganizationController {

    private final OrganizationService organizationService;

    @Autowired
    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @GetMapping(value="/list.do")
    public CmsMap<OrganizationDTO> list(OrganizationDTO organizationDTO, PageInfo pageInfo) {
        CmsMap<OrganizationDTO> cMap = new CmsMap<>();
        OrganizationDTO result = organizationService.list(organizationDTO, pageInfo);
        cMap.success().appendData("counts", pageInfo.getCounts()).setResult(result);
        return cMap;
    }

    @GetMapping(value="/listTree.do")
    public CmsMap listTree() {
        CmsMap<OrganizationDTO> cMap = new CmsMap<>();
        OrganizationDTO result = organizationService.listTree();
        cMap.success().setResult(result);
        return cMap;
    }

    @PostMapping(value="/save.do")
    public CmsMap save(@RequestBody OrganizationDTO organizationDTO) {
        CmsMap<OrganizationEntity> cMap = new CmsMap<>();
        organizationService.save(organizationDTO);
        cMap.success();
        return cMap;
    }

    @PutMapping(value="/edit.do")
    public CmsMap edit(@RequestBody OrganizationDTO organizationDTO) {
        CmsMap<OrganizationEntity> cMap = new CmsMap<>();
        organizationService.update(organizationDTO);
        cMap.success();
        return cMap;
    }

    @DeleteMapping(value="/delete.do")
    public CmsMap delete(@RequestBody Integer[] delIds) {
        CmsMap<OrganizationEntity> cMap = new CmsMap<>();
        organizationService.delete(delIds);
        return cMap.success();
    }

    @DeleteMapping(value="/delete11.do")
    public CmsMap delete11(@RequestBody Integer[] delIds) {
        CmsMap<OrganizationEntity> cMap = new CmsMap<>();
        organizationService.delete(delIds);
        return cMap.success();
    }
}

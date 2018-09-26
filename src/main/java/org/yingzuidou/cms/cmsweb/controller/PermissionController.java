package org.yingzuidou.cms.cmsweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yingzuidou.cms.cmsweb.core.CmsMap;
import org.yingzuidou.cms.cmsweb.core.paging.PageInfo;
import org.yingzuidou.cms.cmsweb.dto.OrganizationDTO;
import org.yingzuidou.cms.cmsweb.dto.PermissionDTO;
import org.yingzuidou.cms.cmsweb.service.PermissionService;

/**
 * PermissionController
 *
 * @author shangguanls
 * @date 2018/9/26
 */
@RestController
@RequestMapping(value="/permission")
public class PermissionController {

    private final PermissionService permissionService;

    @Autowired
    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping(value="/listPower.do")
    public CmsMap listPower() {
        CmsMap<PermissionDTO> cMap = new CmsMap<>();
        PermissionDTO result = permissionService.listPower();
        cMap.success().setResult(result);
        return cMap;
    }

    /**
     * 根据父资源ID获取其下所有的子资源
     *
     * @param permissionDTO 查询条件
     * @param pageInfo 分页条件
     * @return 子资源
     */
    @GetMapping(value="/subPower.do")
    public CmsMap subPower(PermissionDTO permissionDTO, PageInfo pageInfo) {
        CmsMap<PermissionDTO> cMap = new CmsMap<>();
        PermissionDTO result = permissionService.subPower(permissionDTO, pageInfo);
        cMap.success().setResult(result);
        return cMap;
    }

    @DeleteMapping(value="/deletePower.do")
    public CmsMap deletePower(String ids) {
        CmsMap<PermissionDTO> cMap = new CmsMap<>();
        PermissionDTO result = permissionService.deletePower(ids);
        cMap.success().setResult(result);
        return cMap;
    }

}

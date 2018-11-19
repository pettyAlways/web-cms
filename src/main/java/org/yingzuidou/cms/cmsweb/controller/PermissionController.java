package org.yingzuidou.cms.cmsweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yingzuidou.cms.cmsweb.core.CmsMap;
import org.yingzuidou.cms.cmsweb.core.paging.PageInfo;
import org.yingzuidou.cms.cmsweb.core.vo.Node;
import org.yingzuidou.cms.cmsweb.dto.PermissionDTO;
import org.yingzuidou.cms.cmsweb.entity.ResourceEntity;
import org.yingzuidou.cms.cmsweb.service.PermissionService;

import java.util.List;

/**
 * PermissionController
 * 资源管理
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
        CmsMap<Node> cMap = new CmsMap<>();
        PermissionDTO result = permissionService.listPower();
        cMap.success().setResult(result.getTree());
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
        CmsMap<List<ResourceEntity>> cMap = new CmsMap<>();
        PermissionDTO result = permissionService.subPower(permissionDTO, pageInfo);
        cMap.success().appendData("total", pageInfo.getCounts()).setResult(result.getResources());
        return cMap;
    }

    /**
     * 删除指定的资源
     *
     * @param ids 待删除的资源ID
     * @return 删除结果
     */
    @DeleteMapping(value="/deletePower.do")
    public CmsMap deletePower(String ids) {
        CmsMap<PermissionDTO> cMap = new CmsMap<>();
         permissionService.deletePower(ids);
        cMap.success();
        return cMap;
    }

    @PutMapping(value="/updatePower.do")
    public CmsMap updatePower(@RequestBody ResourceEntity entity) {
        permissionService.updateResouce(entity);
        return CmsMap.ok();
    }

    @PostMapping(value="/savePower.do")
    public CmsMap savePower(@RequestBody ResourceEntity entity) {
        CmsMap<PermissionDTO> cMap = new CmsMap<>();
        permissionService.saveResource(entity);
        cMap.success();
        return cMap;
    }
}

package org.yingzuidou.cms.cmsweb.controller;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yingzuidou.cms.cmsweb.core.CmsMap;
import org.yingzuidou.cms.cmsweb.core.paging.PageInfo;
import org.yingzuidou.cms.cmsweb.core.shiro.ShiroService;
import org.yingzuidou.cms.cmsweb.core.utils.CmsCommonUtil;
import org.yingzuidou.cms.cmsweb.dto.RoleDTO;
import org.yingzuidou.cms.cmsweb.entity.RoleEntity;
import org.yingzuidou.cms.cmsweb.service.RoleService;

import java.util.List;

/**
 * 角色管理相关接口
 *
 * @author 鹰嘴豆
 * @date 2018/10/1
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private ShiroService shiroService;

    @Autowired
    private ShiroFilterFactoryBean shiroFilterFactoryBean;

    @GetMapping("/list.do")
    public CmsMap list(RoleDTO roleDTO, PageInfo pageInfo) {
        CmsMap<List<RoleEntity>> result = new CmsMap<>();
        roleService.pagingRole(roleDTO, pageInfo);
        result.success().appendData("counts", pageInfo.getCounts()).setResult(roleDTO.getRoles());
        return result;
    }

    @GetMapping("/listAll.do")
    public CmsMap listAll() {
        CmsMap<List<RoleEntity>> result = new CmsMap<>();
        RoleDTO roleDTO = roleService.listAll();
        result.success().setResult(roleDTO.getRoles());
        return result;
    }

    @PostMapping("/save.do")
    public CmsMap save(@RequestBody RoleEntity roleEntity) {
        roleService.save(roleEntity);
        return CmsMap.ok();
    }

    @PutMapping("/edit.do")
    public CmsMap edit(@RequestBody RoleEntity roleEntity) {
        roleService.edit(roleEntity);
        return CmsMap.ok();
    }

    @DeleteMapping("/delete.do")
    public CmsMap delete(String ids) {
        roleService.delete(ids);
        return CmsMap.ok();
    }

    /**
     * 更新当前用户的被授权的资源，动态更新shiro中的已缓存的
     * 旧授权资源，清空缓存在resourceCache中以resourceTree_用户id
     * 为key的资源
     *
     * @param roleDTO 新授权的资源
     * @return 返回状态
     */
    @PostMapping("/resourceAuth.do")
    public CmsMap resourceAuth(@RequestBody RoleDTO roleDTO) {
        roleService.resourceAuth(roleDTO, CmsCommonUtil.getCurrentLoginUserId());
        shiroService.updatePermission(shiroFilterFactoryBean);
        return CmsMap.ok();
    }

    @GetMapping("/acquireResource.do")
    public CmsMap acquireResource(Integer id) {
        RoleDTO roleDTO = roleService.acquireResource(id);
        CmsMap<List<Integer>> result = new CmsMap<>();
        return result.success().setResult(roleDTO.getResources());
    }
}

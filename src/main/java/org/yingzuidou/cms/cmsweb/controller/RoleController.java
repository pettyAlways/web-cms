package org.yingzuidou.cms.cmsweb.controller;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yingzuidou.cms.cmsweb.core.CmsMap;
import org.yingzuidou.cms.cmsweb.core.paging.PageInfo;
import org.yingzuidou.cms.cmsweb.core.shiro.ShiroService;
import org.yingzuidou.cms.cmsweb.dto.RoleDTO;
import org.yingzuidou.cms.cmsweb.dto.UserDTO;
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

    @PostMapping("/resourceAuth.do")
    public CmsMap resourceAuth(@RequestBody RoleDTO roleDTO) {
        roleService.resourceAuth(roleDTO);
        // 动态授权
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

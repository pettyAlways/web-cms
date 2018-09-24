package org.yingzuidou.cms.cmsweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yingzuidou.cms.cmsweb.core.CmsMap;
import org.yingzuidou.cms.cmsweb.core.paging.PageInfo;
import org.yingzuidou.cms.cmsweb.dto.OrganizationDTO;
import org.yingzuidou.cms.cmsweb.dto.UserDTO;
import org.yingzuidou.cms.cmsweb.entity.CmsUserEntity;
import org.yingzuidou.cms.cmsweb.entity.OrganizationEntity;
import org.yingzuidou.cms.cmsweb.service.OrganizationService;
import org.yingzuidou.cms.cmsweb.service.UserService;

/**
 * OrganizationController 组织机构
 *
 * @author yingzuidou
 * @date 2018/9/13
 */

@RestController
@RequestMapping(value="/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value="/list.do")
    public CmsMap<UserDTO> list(UserDTO userDTO, PageInfo pageInfo) {
        CmsMap<UserDTO> cMap = new CmsMap<>();
        UserDTO result = userService.list(userDTO, pageInfo);
        cMap.success().appendData("counts", pageInfo.getCounts()).setResult(result);
        return cMap;
    }


    @PostMapping(value="/save.do")
    public CmsMap save(@RequestBody CmsUserEntity cmsUserEntity) {
        CmsMap<OrganizationEntity> cMap = new CmsMap<>();
        userService.save(cmsUserEntity);
        cMap.success();
        return cMap;
    }

    @PutMapping(value="/edit.do")
    public CmsMap edit(@RequestBody CmsUserEntity cmsUserEntity) {
        CmsMap cMap = new CmsMap<>();
        userService.update(cmsUserEntity);
        cMap.success();
        return cMap;
    }

    @DeleteMapping(value="/delete.do")
    public CmsMap delete(@RequestBody Integer[] delIds) {
        CmsMap<UserDTO> cMap = new CmsMap<>();
        userService.delete(delIds);
        return cMap.success();
    }


}

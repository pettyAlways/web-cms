package org.yingzuidou.cms.cmsweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yingzuidou.cms.cmsweb.core.CmsMap;
import org.yingzuidou.cms.cmsweb.core.paging.PageInfo;
import org.yingzuidou.cms.cmsweb.dto.UserDTO;
import org.yingzuidou.cms.cmsweb.entity.CmsUserEntity;
import org.yingzuidou.cms.cmsweb.service.UserService;

import java.util.List;

/**
 * OrganizationController 组织机构
 *
 * @author yingzuidou
 * @date 2018/9/13
 */

@RestController
@RequestMapping(value="/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value="/list.do")
    public CmsMap<UserDTO> list(UserDTO userDTO, PageInfo pageInfo) {
        CmsMap<UserDTO> cMap = new CmsMap<>();
        UserDTO result = userService.list(userDTO, pageInfo);
        cMap.success().appendData("counts", pageInfo.getCounts()).setResult(result);
        return cMap;
    }


    @PostMapping(value="/save.do")
    public CmsMap save(@RequestBody CmsUserEntity cmsUserEntity) {
        userService.save(cmsUserEntity);
        return CmsMap.ok();
    }

    @PutMapping(value="/edit.do")
    public CmsMap edit(@RequestBody CmsUserEntity cmsUserEntity) {
        userService.update(cmsUserEntity);
        return CmsMap.ok();
    }

    @DeleteMapping(value="/delete.do")
    public CmsMap delete(@RequestBody Integer[] delIds) {
        userService.delete(delIds);
        return CmsMap.ok();
    }

    @GetMapping("/userInfo.do")
    public CmsMap<UserDTO> userInfo() {
        CmsMap<UserDTO> cmsMap = new CmsMap<>();
        UserDTO userDTO = userService.userInfo();
        return cmsMap.success().setResult(userDTO);
    }

    @PostMapping("/login.do")
    public CmsMap login () {
        return CmsMap.ok().appendData("token",  "12333");
    }

    @PostMapping("/authUser.do")
    public CmsMap authUser(@RequestBody UserDTO userDTO) {
        userService.authUser(userDTO);
        return CmsMap.ok();
    }

    @GetMapping("/acquireRoles.do")
    public CmsMap acquireRoles(Integer id) {
        CmsMap<List<Integer>> cMap = new CmsMap<>();
        UserDTO userDTO = userService.acquireRoles(id);
        return cMap.success().setResult(userDTO.getRoles());
    }
}

package org.yingzuidou.cms.cmsweb.service;

import org.yingzuidou.cms.cmsweb.core.paging.PageInfo;
import org.yingzuidou.cms.cmsweb.dto.RoleDTO;
import org.yingzuidou.cms.cmsweb.entity.RoleEntity;

/**
 * RoleService 角色服务接口
 *
 * @author 鹰嘴豆
 * @date 2018/10/1
 */

public interface RoleService {

    void pagingRole(RoleDTO roleDTO, PageInfo pageInfo);

    void save(RoleEntity roleEntity);

    void edit(RoleEntity roleEntity);

    void delete(String ids);

    RoleDTO listAll();

    void resourceAuth(RoleDTO roleDTO);

    RoleDTO acquireResource(Integer roleId);
}

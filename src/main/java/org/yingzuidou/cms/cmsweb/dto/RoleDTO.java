package org.yingzuidou.cms.cmsweb.dto;

import org.yingzuidou.cms.cmsweb.entity.RoleEntity;

import java.util.List;

/**
 * RoleDTO 角色DTO
 *
 * @author 鹰嘴豆
 * @date 2018/10/1
 */

public class RoleDTO {

    /**
     * 角色名字
     */
    private String roleName;

    /**
     * 角色表格呈现的数据
     */
    private List<RoleEntity> roles;

    /**
     * 是否启用
     */
    private String inUse;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleEntity> roles) {
        this.roles = roles;
    }

    public String getInUse() {
        return inUse;
    }

    public void setInUse(String inUse) {
        this.inUse = inUse;
    }
}

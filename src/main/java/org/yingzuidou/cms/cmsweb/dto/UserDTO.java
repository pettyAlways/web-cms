package org.yingzuidou.cms.cmsweb.dto;

import org.yingzuidou.cms.cmsweb.entity.CmsUserEntity;

import java.util.List;

/**
 * UserDTO
 *
 * @author shangguanls
 * @date 2018/9/24
 */
public class UserDTO {

    /**
     * 用户ID
     */
    private Integer id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 部门ID
     */
    private Integer userDepart;

    /**
     * 用户列表
     */
    List<CmsUserEntity> users;

    public List<CmsUserEntity> getUsers() {
        return users;
    }

    public void setUsers(List<CmsUserEntity> users) {
        this.users = users;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public Integer getUserDepart() {
        return userDepart;
    }

    public void setUserDepart(Integer userDepart) {
        this.userDepart = userDepart;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}

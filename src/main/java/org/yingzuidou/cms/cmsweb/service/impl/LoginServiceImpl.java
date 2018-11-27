package org.yingzuidou.cms.cmsweb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yingzuidou.cms.cmsweb.biz.ResourceBiz;
import org.yingzuidou.cms.cmsweb.biz.UserBiz;
import org.yingzuidou.cms.cmsweb.core.utils.CmsCommonUtil;
import org.yingzuidou.cms.cmsweb.core.vo.Node;
import org.yingzuidou.cms.cmsweb.dao.PermissionRepository;
import org.yingzuidou.cms.cmsweb.dao.RoleResourceRepository;
import org.yingzuidou.cms.cmsweb.dao.UserRepository;
import org.yingzuidou.cms.cmsweb.dao.UserRoleRepository;
import org.yingzuidou.cms.cmsweb.entity.CmsUserEntity;
import org.yingzuidou.cms.cmsweb.entity.ResourceEntity;
import org.yingzuidou.cms.cmsweb.entity.RoleResourceEntity;
import org.yingzuidou.cms.cmsweb.entity.UserRoleEntity;
import org.yingzuidou.cms.cmsweb.service.LoginService;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * LoginServiceImpl
 *
 * @author 鹰嘴豆
 * @date 2018/10/16
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserBiz userBiz;

    @Autowired
    private UserRepository userRepository;

    /**
     * 登录错误次数操作5次则锁定用户
     */
    @Override
    public void userLock(String userAccount) {
        CmsUserEntity currentUser = userBiz.findByUserAccount(userAccount);
        currentUser.setUserStatus("2");
        currentUser.setLockTime(new Date());
        userRepository.save(currentUser);
    }

    /**
     * 更新用户如登录时间等信息
     *
     * @param user 用户对象
     */
    @Override
    public void saveUser(CmsUserEntity user) {
        userRepository.save(user);
    }
}

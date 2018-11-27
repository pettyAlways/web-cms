package org.yingzuidou.cms.cmsweb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yingzuidou.cms.cmsweb.biz.UserBiz;
import org.yingzuidou.cms.cmsweb.constant.LockStatusEnum;
import org.yingzuidou.cms.cmsweb.core.exception.BusinessException;
import org.yingzuidou.cms.cmsweb.core.paging.PageInfo;
import org.yingzuidou.cms.cmsweb.core.shiro.ShiroService;
import org.yingzuidou.cms.cmsweb.core.utils.CmsBeanUtils;
import org.yingzuidou.cms.cmsweb.dao.OrganizationRepository;
import org.yingzuidou.cms.cmsweb.dao.RoleRepository;
import org.yingzuidou.cms.cmsweb.dao.UserRepository;
import org.yingzuidou.cms.cmsweb.dao.UserRoleRepository;
import org.yingzuidou.cms.cmsweb.dto.OnlineDTO;
import org.yingzuidou.cms.cmsweb.entity.CmsUserEntity;
import org.yingzuidou.cms.cmsweb.entity.OrganizationEntity;
import org.yingzuidou.cms.cmsweb.entity.RoleEntity;
import org.yingzuidou.cms.cmsweb.service.OnlineService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2018/11/26
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@Service
@Transactional
public class OnlineServerImpl implements OnlineService {

    @Autowired
    private ShiroService shiroService;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private UserBiz userBiz;

    @Autowired
    private UserRepository userRepository;


    @Override
    public List<OnlineDTO> list(OnlineDTO onlineDTO, PageInfo pageInfo) {
        List<Integer> userIds = shiroService.currentOnlineUser();
        List<CmsUserEntity> users = userBiz.findUsersByIds(userIds);
        List<OnlineDTO> onlineDTOS = new ArrayList<>();
        Optional.ofNullable(users).orElse(new ArrayList<>()).forEach(item -> {
            OnlineDTO online = new OnlineDTO();
            online.setUserId(item.getId());
            CmsBeanUtils.copyMorNULLProperties(item, online);
            List<Object> roleIds = userRoleRepository.findAllByUserIdAndRoleInUse(item.getId());
            if (Objects.nonNull(roleIds)) {
                List<Integer> againTransform = roleIds.stream()
                        .map(ritem -> Integer.parseInt(String.valueOf(ritem))).collect(Collectors.toList());
                List<RoleEntity> roles = roleRepository.findAllByIdInAndIsDeleteIs(againTransform, "N");
                StringBuilder sBuild = new StringBuilder();
                if (Objects.nonNull(roles)) {
                    roles.forEach(rnItem -> sBuild.append(rnItem.getRoleName()).append(","));
                    String roleNames = sBuild.toString().substring(0, sBuild.length() - 1);
                    online.setRoleName(roleNames);
                }
                Optional<OrganizationEntity> organizationEntity = organizationRepository.findById(item.getUserDepart());
                online.setDepartName(organizationEntity.get().getOrgName());
                onlineDTOS.add(online);
            }
        });
        return onlineDTOS;
    }

    @Override
    public void inValidUser(Integer userId) {
        Optional<CmsUserEntity> userOp = userBiz.findById(userId);
        if (!userOp.isPresent()) {
            throw new BusinessException("用户不存在");
        }
        CmsUserEntity user = userOp.get();
        user.setUserStatus(LockStatusEnum.INVAILD.getValue());
        userRepository.save(user);
    }
}

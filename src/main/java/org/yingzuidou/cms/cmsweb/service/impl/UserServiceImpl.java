package org.yingzuidou.cms.cmsweb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.yingzuidou.cms.cmsweb.biz.UserBiz;
import org.yingzuidou.cms.cmsweb.core.paging.PageInfo;
import org.yingzuidou.cms.cmsweb.dao.UserRepository;
import org.yingzuidou.cms.cmsweb.dto.UserDTO;
import org.yingzuidou.cms.cmsweb.entity.CmsUserEntity;
import org.yingzuidou.cms.cmsweb.service.UserService;
import org.yingzuidou.cms.cmsweb.util.CmsBeanUtils;

import java.util.Date;
import java.util.Optional;

/**
 * UserServiceImpl
 *
 * @author shangguanls
 * @date 2018/9/24
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserBiz userBiz;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDTO list(UserDTO userDTO, PageInfo pageInfo) {
        Page<CmsUserEntity> orgPage = userBiz.findAllUserWithCondition(userDTO, pageInfo.toPageable());
        pageInfo.setCounts(orgPage.getTotalElements());
        userDTO.setUsers(orgPage.getContent());
        return userDTO;
    }

    @Override
    public void save(CmsUserEntity cmsUserEntity) {
        cmsUserEntity.setCreator(1);
        cmsUserEntity.setCreateTime(new Date());
        cmsUserEntity.setIsDelete("N");
        userRepository.save(cmsUserEntity);
    }

    @Override
    public void update(CmsUserEntity userEntity) {
        Optional<CmsUserEntity> optionEntity = userBiz.findById(userEntity.getId());
        CmsUserEntity entity = optionEntity.get();
        CmsBeanUtils.copyMorNULLProperties(userEntity, entity);
        entity.setUpdator(1);
        entity.setUpdateTime(new Date());
        userRepository.save(entity);
    }

    @Override
    public void delete(Integer[] delIds) {
        userBiz.deleteUserByIds(delIds);
    }
}

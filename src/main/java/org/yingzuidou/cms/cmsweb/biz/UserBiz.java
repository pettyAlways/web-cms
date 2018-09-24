package org.yingzuidou.cms.cmsweb.biz;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.yingzuidou.cms.cmsweb.dao.UserRepository;
import org.yingzuidou.cms.cmsweb.dto.UserDTO;
import org.yingzuidou.cms.cmsweb.entity.CmsUserEntity;
import org.yingzuidou.cms.cmsweb.entity.OrganizationEntity;
import org.yingzuidou.cms.cmsweb.entity.QCmsUserEntity;
import org.yingzuidou.cms.cmsweb.entity.QOrganizationEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * UserBiz
 *
 * @author shangguanls
 * @date 2018/9/24
 */
@Service
public class UserBiz {

    private QCmsUserEntity qCmsUserEntity = QCmsUserEntity.cmsUserEntity;

    @Autowired
    private UserRepository userRepository;

    public Page<CmsUserEntity> findAllUserWithCondition(UserDTO userDTO, Pageable pageable) {
        BooleanExpression expression = qCmsUserEntity.isDelete.eq("N").and(qCmsUserEntity.userDepart.eq(userDTO.getUserDepart()));
        if (!StringUtils.isEmpty(userDTO.getUserName())) {
            expression = expression.and(qCmsUserEntity.userName.like("%" + userDTO.getUserName() + "%"));
        }
        if (!StringUtils.isEmpty(userDTO.getUserAccount())) {
            expression = expression.and(qCmsUserEntity.userAccount.like("%" + userDTO.getUserAccount() + "%"));
        }
        return userRepository.findAll(expression, pageable);
    }

    public void deleteUserByIds(Integer[] delIds) {
        Predicate predicate = qCmsUserEntity.id.in(delIds).and(qCmsUserEntity.isDelete.eq("N"));
        Iterable<CmsUserEntity> entities = userRepository.findAll(predicate);
        entities.forEach(item -> item.setIsDelete("Y"));
        userRepository.saveAll(entities);
    }

    public Optional<CmsUserEntity> findById(Integer id) {
        Predicate predicate = qCmsUserEntity.isDelete.eq("N").and(qCmsUserEntity.id.eq(id));
        return userRepository.findOne(predicate);
    }
}

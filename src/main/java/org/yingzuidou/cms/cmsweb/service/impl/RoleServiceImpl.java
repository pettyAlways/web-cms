package org.yingzuidou.cms.cmsweb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.yingzuidou.cms.cmsweb.biz.RoleBiz;
import org.yingzuidou.cms.cmsweb.core.paging.PageInfo;
import org.yingzuidou.cms.cmsweb.dao.RoleRepository;
import org.yingzuidou.cms.cmsweb.dto.RoleDTO;
import org.yingzuidou.cms.cmsweb.entity.RoleEntity;
import org.yingzuidou.cms.cmsweb.service.RoleService;
import org.yingzuidou.cms.cmsweb.util.CmsBeanUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * RoleServiceImpl 角色服务实现类
 *
 * @author 鹰嘴豆
 * @date 2018/10/1
 */
@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleBiz roleBiz;

    @Override
    public void pagingRole(RoleDTO roleDTO, PageInfo pageInfo) {
        Page<RoleEntity> entityPage = roleBiz.findAllRoleWithCondition(roleDTO, pageInfo.toPageable());
        pageInfo.setCounts(entityPage.getTotalElements());
        roleDTO.setRoles(entityPage.getContent());
    }

    @Override
    public void save(RoleEntity roleEntity) {
        roleEntity.setCreator(1);
        roleEntity.setCreateTime(new Date());
        roleEntity.setIsDelete("N");
        roleRepository.save(roleEntity);
    }

    @Override
    public void edit(RoleEntity roleEntity) {
       RoleEntity entity = roleRepository.findById(roleEntity.getId()).get();
       CmsBeanUtils.copyMorNULLProperties(roleEntity, entity);
       entity.setUpdator(1);
       entity.setUpdateTime(new Date());
       roleRepository.save(entity);

    }

    @Override
    public void delete(String ids) {
        List<Integer> idsList = Arrays.stream(ids.split(",")).map(item -> Integer.valueOf(item))
                .collect(Collectors.toList());
        List<RoleEntity> resourceList = roleRepository.findAllByIdInAndIsDeleteIs(idsList, "N");
        Optional.ofNullable(resourceList).orElse(Collections.emptyList()).forEach(item -> {
            item.setIsDelete("Y");
            item.setUpdator(1);
            item.setUpdateTime(new Date());
        });
        roleRepository.saveAll(resourceList);
    }
}

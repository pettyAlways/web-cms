package org.yingzuidou.cms.cmsweb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yingzuidou.cms.cmsweb.dao.OrganizationRepository;
import org.yingzuidou.cms.cmsweb.dto.OrganizationDTO;
import org.yingzuidou.cms.cmsweb.entity.OrganizationEntity;
import org.yingzuidou.cms.cmsweb.service.OrganizationService;
import org.yingzuidou.cms.cmsweb.util.CmsBeanUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 组织机构实现类
 *
 * @author yingzuidou
 * @date 2018/9/13     
 */
@Service
public class OrganizationServiceImpl implements OrganizationService{

    @Autowired
    private OrganizationRepository organizationRepository;

    @Override
    public List<OrganizationEntity> list(Integer nodeId) {
        nodeId = nodeId == null ? 0 : nodeId;
        return organizationRepository.findByParentId(nodeId);
    }

    @Override
    public void save(OrganizationEntity organizationEntity) {
        organizationRepository.save(organizationEntity);
    }

    @Override
    public void update(OrganizationEntity organizationEntity) {
        organizationEntity.setUpdateTime(new Date());
        Optional<OrganizationEntity> entity = organizationRepository.findById(organizationEntity.getId());
        OrganizationEntity updateEntity = entity.orElse(organizationEntity);
        CmsBeanUtils.copyMorNULLProperties(organizationEntity, updateEntity);
        organizationRepository.save(updateEntity);
    }

    @Override
    public OrganizationDTO listTree() {
        // 先找出所有的扁平组织数据
        List<OrganizationEntity> flatNode = organizationRepository.findAllByIsDeleteIs("N");
        OrganizationEntity parent = flatNode.stream().filter(node -> node.getParentId().intValue() == 0).findFirst().get();
        OrganizationDTO parentDTO = new OrganizationDTO();
        parentDTO.setId(parent.getId());
        parentDTO.setLabel(parent.getOrgName());
        getChildrenList(parentDTO, Optional.ofNullable(flatNode));
        return parentDTO;
    }

    private void getChildrenList(OrganizationDTO parentNode, Optional<List<OrganizationEntity>> allNode) {
        List<OrganizationEntity> allNodeList = allNode.orElse(Collections.EMPTY_LIST);
        List<OrganizationDTO> childrenNodeList = allNodeList.stream()
                .filter(node -> parentNode.getId().equals(node.getParentId()))
                .map(node -> {
                    // Entity -> DTO 转化页面所需的信息
                    OrganizationDTO curOrganizationDTO = new OrganizationDTO();
                    curOrganizationDTO.setId(node.getId());
                    curOrganizationDTO.setLabel(node.getOrgName());
                    return curOrganizationDTO;
                })
                .collect(Collectors.toList());
        parentNode.setChildren(childrenNodeList);

        // 孩子列表获取出来可能是空，因此需要用Optional处理
        Optional.ofNullable(childrenNodeList).orElse(Collections.EMPTY_LIST)
                .forEach(node -> getChildrenList((OrganizationDTO) node, allNode));

    }
}

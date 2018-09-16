package org.yingzuidou.cms.cmsweb.controller;

import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.web.bind.annotation.*;
import org.yingzuidou.cms.cmsweb.core.CmsMap;
import org.yingzuidou.cms.cmsweb.core.paging.PageInfo;
import org.yingzuidou.cms.cmsweb.dto.OrganizationDTO;
import org.yingzuidou.cms.cmsweb.entity.OrganizationEntity;
import org.yingzuidou.cms.cmsweb.service.OrganizationService;

import java.util.Map;

/**
 * OrganizationController 组织机构
 *
 * @author yingzuidou
 * @date 2018/9/13
 */

@RestController
@RequestMapping(value="/organization")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @GetMapping(value="/list.do")
    public Map list(@QuerydslPredicate(root = OrganizationEntity.class) Predicate predicate, Pageable pageable) {
        CmsMap<OrganizationEntity> cMap = new CmsMap<>();
        // OrganizationDTO result = organizationService.list(params, pageInfo);
        cMap.success().setData(null);
        return cMap;
    }

    @RequestMapping(value="/listTree.do")
    public Map listTree(Integer nodeId) {
        CmsMap<OrganizationEntity> cMap = new CmsMap<>();
        OrganizationDTO result = organizationService.listTree();
        cMap.success().setData(result);
        return cMap;
    }

    @RequestMapping(value="/save.do")
    public Map save(@RequestBody OrganizationDTO organizationDTO) {
        CmsMap<OrganizationEntity> cMap = new CmsMap<>();
        organizationService.save(organizationDTO);
        cMap.success();
        return cMap;
    }
}

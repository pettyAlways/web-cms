package org.yingzuidou.cms.cmsweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yingzuidou.cms.cmsweb.core.CmsMap;
import org.yingzuidou.cms.cmsweb.core.paging.PageInfo;
import org.yingzuidou.cms.cmsweb.dto.ConstDTO;
import org.yingzuidou.cms.cmsweb.entity.CmsConstEntity;
import org.yingzuidou.cms.cmsweb.service.ConstService;

import java.util.List;

/**
 * 类功能描述
 * key/value、字典、枚举、系统配置等内容可在页面中配置
 *
 * @author 鹰嘴豆
 * @date 2018/11/13
 *
 * 时间           作者          版本        描述
 * ====================================================
 * 2018/11/13     鹰嘴豆        v1          提供常量可配置
 */

@RestController
@RequestMapping(value="/const")
public class ConstController {

    @Autowired
    private ConstService constService;

    @GetMapping(value="/list.do")
    public CmsMap<List<CmsConstEntity>> list(ConstDTO constDTO, PageInfo pageInfo) {
        CmsMap<List<CmsConstEntity>> cMap = new CmsMap<>();
        ConstDTO result = constService.list(constDTO, pageInfo);
        cMap.success().appendData("counts", pageInfo.getCounts()).setResult(result.getConstList());
        return cMap;
    }

    @PostMapping(value="/save.do")
    public CmsMap save(@RequestBody CmsConstEntity cmsConstEntity) {
        constService.save(cmsConstEntity);
        return CmsMap.ok();
    }

    @PutMapping(value="/edit.do")
    public CmsMap edit(@RequestBody CmsConstEntity cmsConstEntity) {
        constService.update(cmsConstEntity);
        return CmsMap.ok();
    }

    @DeleteMapping(value="/delete.do")
    public CmsMap delete(String ids) {
        constService.delete(ids);
        return CmsMap.ok();
    }
}

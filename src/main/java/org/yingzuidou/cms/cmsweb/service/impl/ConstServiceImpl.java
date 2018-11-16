package org.yingzuidou.cms.cmsweb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yingzuidou.cms.cmsweb.biz.ConstBiz;
import org.yingzuidou.cms.cmsweb.core.exception.BusinessException;
import org.yingzuidou.cms.cmsweb.core.paging.PageInfo;
import org.yingzuidou.cms.cmsweb.dao.CmsConstRepository;
import org.yingzuidou.cms.cmsweb.dto.ConstDTO;
import org.yingzuidou.cms.cmsweb.entity.CmsConstEntity;
import org.yingzuidou.cms.cmsweb.service.ConstService;
import org.yingzuidou.cms.cmsweb.core.utils.CmsBeanUtils;
import org.yingzuidou.cms.cmsweb.core.utils.CmsCommonUtil;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 类功能描述
 * 系统常量配置实现类
 * cms系统中网站的标题、绕过登录的页面，动态可变枚举等都是可配置的，这些都可以在
 * 常量配置页面配置
 *
 * @author 鹰嘴豆
 * @date 2018/11/13
 *
 * 时间           作者          版本        描述
 * ====================================================
 * 2018/11/13     鹰嘴豆        v1.0        常量配置服务实现类
 */

@Service
@Transactional
public class ConstServiceImpl implements ConstService {

    private final ConstBiz constBiz;

    private final CmsConstRepository cmsConstRepository;

    @Autowired
    public ConstServiceImpl(CmsConstRepository cmsConstRepository, ConstBiz constBiz) {
        this.cmsConstRepository = cmsConstRepository;
        this.constBiz = constBiz;
    }

    @Override
    public ConstDTO list(ConstDTO constDTO, PageInfo pageInfo) {
        Page<CmsConstEntity> cmsConstEntities = constBiz.findConstWithCondition(constDTO, pageInfo.toPageable());
        pageInfo.setCounts(cmsConstEntities.getTotalElements());
        constDTO.setConstList(cmsConstEntities.getContent());
        return constDTO;
    }

    /**
     * 保存一个常量
     *
     * @param cmsConstEntity 常量内容
     */
    @Override
    public void save(CmsConstEntity cmsConstEntity) {
        cmsConstEntity.setCreator(CmsCommonUtil.getCurrentLoginUserId());
        cmsConstEntity.setCreateTime(new Date());
        cmsConstRepository.save(cmsConstEntity);
    }

    /**
     * 更新一个常量，需要根据主键获取实例在保存
     *
     * @param cmsConstEntity 常量内容
     */
    @Override
    public void update(CmsConstEntity cmsConstEntity) {
        Optional<CmsConstEntity> constEntityOp = cmsConstRepository.findById(cmsConstEntity.getId());
        if (!constEntityOp.isPresent()) {
            throw new BusinessException("该常量已经被删除");
        }
        CmsConstEntity dbCmsConstEntity = constEntityOp.get();
        CmsBeanUtils.copyMorNULLProperties(cmsConstEntity, dbCmsConstEntity);
        dbCmsConstEntity.setUpdator(CmsCommonUtil.getCurrentLoginUserId());
        dbCmsConstEntity.setUpdateTime(new Date());
        cmsConstRepository.save(dbCmsConstEntity);
    }

    /**
     * 批量删除常量，硬删除不可恢复
     *
     * @param ids 常量ID以逗号隔开
     */
    @Override
    public void delete(String ids) {
        List<Integer> idsList = Arrays.stream(ids.split(",")).map(Integer::valueOf)
                .collect(Collectors.toList());
        cmsConstRepository.deleteAllByIdIn(idsList);
    }

    /**
     * 刷新缓存
     */
    @Override
    public void refresh(String type) {
        constBiz.evictAndRefresh(type);
    }
}

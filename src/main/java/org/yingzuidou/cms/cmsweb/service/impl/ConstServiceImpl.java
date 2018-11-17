package org.yingzuidou.cms.cmsweb.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.yingzuidou.cms.cmsweb.biz.ConstBiz;
import org.yingzuidou.cms.cmsweb.core.exception.BusinessException;
import org.yingzuidou.cms.cmsweb.core.paging.PageInfo;
import org.yingzuidou.cms.cmsweb.core.utils.CmsBeanUtils;
import org.yingzuidou.cms.cmsweb.core.utils.CmsCommonUtil;
import org.yingzuidou.cms.cmsweb.dao.CmsConstRepository;
import org.yingzuidou.cms.cmsweb.dto.ConstDTO;
import org.yingzuidou.cms.cmsweb.entity.CmsConstEntity;
import org.yingzuidou.cms.cmsweb.service.ConstService;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2018/11/17
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@Service
public class ConstServiceImpl implements ConstService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ConstBiz constBiz;

    @Autowired
    private CmsConstRepository cmsConstRepository;

    @Override
    public ConstDTO list(ConstDTO constDTO, PageInfo pageInfo) {
        Page<CmsConstEntity> cmsConstEntities = constBiz.findConstWithCondition(constDTO, pageInfo.toPageable());
        pageInfo.setCounts(cmsConstEntities.getTotalElements());
        constDTO.setConstList(cmsConstEntities.getContent());
        return constDTO;
    }

    /**
     * 保存一个常量
     * 每次保存时需要更新缓存中的系统常量，因此这里移除constCache的
     * key为const_key_1表示系统常量的缓存
     *
     * @param cmsConstEntity 常量内容
     */
    @Override
    @CacheEvict(value="constCache", key="'const_key_1'")
    public void save(CmsConstEntity cmsConstEntity) {
        cmsConstEntity.setCreator(CmsCommonUtil.getCurrentLoginUserId());
        cmsConstEntity.setCreateTime(new Date());
        cmsConstRepository.save(cmsConstEntity);
    }

    /**
     * 更新一个常量，需要根据主键获取实例在保存
     * 每次更新时需要更新缓存中的系统常量，因此这里移除constCache的
     * key为const_key_1表示系统常量的缓存
     *
     * @param cmsConstEntity 常量内容
     */
    @Override
    @CacheEvict(value="constCache", key="'const_key_1'")
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
     * 每次删除时需要更新缓存中的系统常量，因此这里移除constCache的
     * key为const_key_1表示系统常量的缓存
     *
     * @param ids 常量ID以逗号隔开
     */
    @Override
    @CacheEvict(value="constCache", key="'const_key_1'")
    public void delete(String ids) {
        List<Integer> idsList = Arrays.stream(ids.split(",")).map(Integer::valueOf)
                .collect(Collectors.toList());
        cmsConstRepository.deleteAllByIdIn(idsList);
    }

    /**
     * 获取指定类型的常量并以key为cache_key_1缓存在constCache中
     *
     * @param type 1、2等表示系统常量、枚举
     * @return 从缓存或者数据库获取到的常量列表
     */
    @Override
    @Cacheable(value="constCache", key="'const_key_'+#type")
    public List<CmsConstEntity> findAllConstByType(String type) {
        logger.info("缓存未命中");
        return cmsConstRepository.findAllByType(type);
    }
}

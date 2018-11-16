package org.yingzuidou.cms.cmsweb.biz;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.yingzuidou.cms.cmsweb.dao.CmsConstRepository;
import org.yingzuidou.cms.cmsweb.dto.ConstDTO;
import org.yingzuidou.cms.cmsweb.entity.CmsConstEntity;
import org.yingzuidou.cms.cmsweb.entity.QCmsConstEntity;

import java.util.List;

/**
 * 类功能描述
 * 常量配置更加细化的接口，方便其他地方复用
 *
 * @author 鹰嘴豆
 * @date 2018/11/13
 * 
 * 时间           作者          版本        描述
 * ====================================================
 * 2018/11/13     鹰嘴豆        v1.0        常量细化接口方便其他地方复用
 * 2018/11/16     鹰嘴豆        v1.0        增加ehcache作为常量的缓存框架
 */

@Service
public class ConstBiz {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 常量配置表JPA接口
     */
    @Autowired
    private CmsConstRepository cmsConstRepository;

    /**
     * JPA QueryDsl：常量复杂查询
     */
    private QCmsConstEntity qCmsConstEntity = QCmsConstEntity.cmsConstEntity;


    public Page<CmsConstEntity> findConstWithCondition(ConstDTO constDTO, Pageable pageable) {
        BooleanExpression expression = qCmsConstEntity.type.eq(constDTO.getType());
        if (!StringUtils.isEmpty(constDTO.getConstName())) {
            expression = expression.and(qCmsConstEntity.constName.like("%" + constDTO.getConstName() + "%"));
        }
        if (!StringUtils.isEmpty(constDTO.getConstKey())) {
            expression = expression.and(qCmsConstEntity.constKey.like("%" + constDTO.getConstKey() + "%"));
        }
        return cmsConstRepository.findAll(expression, pageable);
    }


    /**
     * 获取指定类型的常量并以key为cache_key_1缓存在constCache中
     *
     * @param type 1、2等表示系统常量、枚举
     * @return 从缓存或者数据库获取到的常量列表
     */
    @Cacheable(value="constCache", key="'cache_key_' + #type")
    public List<CmsConstEntity> findAllConstByType(String type) {
        logger.info("我走了缓存");
        return cmsConstRepository.findAllByType(type);
    }

    /**
     * 刷新缓存时实际上是清空constCache中指定key的常量，下一次获取该
     * key则重新从数据库获取
     *
     * @param type 1、2等表示系统常量、枚举
     */
    @CacheEvict(value="constCache", key="'cache_key_' + #type")
    public void evictAndRefresh(String type) {
        logger.info("清空了类型为" + type + "缓存");
    }
}

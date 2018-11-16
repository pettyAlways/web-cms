package org.yingzuidou.cms.cmsweb.service;

import org.yingzuidou.cms.cmsweb.core.paging.PageInfo;
import org.yingzuidou.cms.cmsweb.dto.ConstDTO;
import org.yingzuidou.cms.cmsweb.entity.CmsConstEntity;

/**
 * 类功能描述
 * 常量配置服务接口
 *
 * @author 鹰嘴豆
 * @date 2018/11/13
 *
 * 时间           作者          版本        描述
 * ====================================================
 * 2018/11/13     鹰嘴豆        v1.0        常量服务接口
 */

public interface ConstService {

    /**
     * 分页得到常量配置
     * type变量代表不同类型的常量
     *
     * @param constDTO 查询条件
     * @param pageInfo 分页变量
     * @return 常量列表
     */
    ConstDTO list(ConstDTO constDTO, PageInfo pageInfo);

    /**
     * 保存指定类型的常量
     *
     * @param cmsConstEntity 常量内容
     */
    void save(CmsConstEntity cmsConstEntity);

    /**
     * 更新指定类型常量
     *
     * @param cmsConstEntity 常量内容
     */
    void update(CmsConstEntity cmsConstEntity);

    /**
     * 删除指定类型常量
     *
     * @param ids 常量ID以逗号隔开
     */
    void delete(String ids);

    /**
     * 刷新Ehcache缓存
     */
    void refresh(String type);
}

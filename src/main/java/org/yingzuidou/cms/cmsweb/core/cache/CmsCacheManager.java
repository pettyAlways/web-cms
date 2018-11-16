package org.yingzuidou.cms.cmsweb.core.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yingzuidou.cms.cmsweb.biz.ConstBiz;
import org.yingzuidou.cms.cmsweb.entity.CmsConstEntity;

import java.util.*;

/**
 * 类功能描述
 * 该类专门用来管理缓存，通过它来调用constBiz去获取缓存中的常量，如果不存在就
 * 从数据获取
 *
 * @author 鹰嘴豆
 * @date 2018/11/16
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 * 2018/11/16     鹰嘴哦度      v1.0        获取系统常量列表
 */
@Service
public class CmsCacheManager{

    @Autowired
    private ConstBiz constBiz;

    /**
     * 从缓存(数据库)中获取系统常量
     *
     * @return 系统常量
     */
    public  Map<String, String> systemConst() {
        Map<String, String> systemParams = new HashMap<>(100);
        List<CmsConstEntity> constEntities = constBiz.findAllConstByType("1");
        Optional.ofNullable(constEntities).orElse(new ArrayList <>())
                .forEach(item -> systemParams.put(item.getConstKey(), item.getConstValue()));
        return systemParams;
    }

}

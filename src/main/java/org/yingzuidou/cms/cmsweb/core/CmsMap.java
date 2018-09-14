package org.yingzuidou.cms.cmsweb.core;

import java.util.HashMap;

/**
 * 返回结果的Map数据结构
 *
 * @author yingzuidou
 * @date 2018/9/13     
 */
public class CmsMap<T> extends HashMap{

    public CmsMap success() {
        this.put("code", 200);
        this.put("flag", true);
        return this;
    }

    public void setData(T data) {
        this.put("datas", data);
    }

    public void error(String code, String message) {
        this.put("code", code);
        this.put("message", message);
        this.put("flag", false);
    }
}

package org.yingzuidou.cms.cmsweb.core;

import java.util.HashMap;

/**
 * 返回结果的Map数据结构
 *
 * @author yingzuidou
 * @date 2018/9/13     
 */
public class CmsMap<T> extends HashMap<String, Object>{

    public CmsMap success() {
        this.put("code", 200);
        this.put("flag", true);
        return this;
    }

    public void setResult(T data) {
        this.put("data", data);
    }

    public <D> CmsMap appendData(String code, D data) {
        this.put(code, data);
        return this;
    }

    public void error(String code, String message) {
        this.put("code", code);
        this.put("message", message);
        this.put("flag", false);
    }
}

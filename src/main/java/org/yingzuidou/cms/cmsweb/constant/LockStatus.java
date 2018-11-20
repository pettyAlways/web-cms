package org.yingzuidou.cms.cmsweb.constant;

/**
 * 类功能描述
 * 锁定状态枚举
 *
 * @author 鹰嘴豆
 * @date 2018/11/21
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
public enum  LockStatus {
    // 账户正常状态
    NORMAL("正常", "1"),

    // 账户被锁定
    LOCK("锁定", "2"),

    // 账户状态禁用
    INVAILD("无效", "3");

    /**
     * 用户状态
     */
    private String name;

    /**
     * 状态值
     */
    private String status;

    private LockStatus(String name, String status) {
        this.name = name;
        this.status = status;
    }

    public String getValue() {
        return this.status;
    }
}

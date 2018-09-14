package org.yingzuidou.cms.cmsweb.core.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.yingzuidou.cms.cmsweb.core.CmsMap;

import javax.servlet.http.HttpServletRequest;

/**
 * @author dell
 * @date 2018/9/13     
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public void handleGlobalException(HttpServletRequest req, Exception e) {
        CmsMap cMap = new CmsMap<>();
        if (e instanceof BusinessException) {
            cMap.error(((BusinessException) e).getCode(), e.getMessage());
        } else if (e instanceof RuntimeException) {
            // 增加日志
            cMap.error("500", "系统异常");
        }
    }
}

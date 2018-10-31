package org.yingzuidou.cms.cmsweb.core;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * ResponseUtil
 *
 * @author shangguanls
 * @date 2018/10/31
 */
public class ResponseUtil {


    public static void sendCode(ServletResponse response, int status) {
        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.write(status + "");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

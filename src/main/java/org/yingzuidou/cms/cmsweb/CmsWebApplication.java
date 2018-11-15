package org.yingzuidou.cms.cmsweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * cms web 入口函数
 * @author yingzuidou
 */
@SpringBootApplication
@EnableCaching
public class CmsWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(CmsWebApplication.class, args);
	}
}

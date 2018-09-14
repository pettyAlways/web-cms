package org.yingzuidou.cms.cmsweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

/**
 * cms web 入口函数
 * @author yingzuidou
 */
@SpringBootApplication
public class CmsWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(CmsWebApplication.class, args);
	}
}

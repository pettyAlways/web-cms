package org.yingzuidou.cms.cmsweb.core.shiro;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.yingzuidou.cms.cmsweb.biz.ResourceBiz;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * ShiroServiceImpl
 *
 * @author shangguanls
 * @date 2018/10/22
 */
@Service
public class ShiroServiceImpl implements ShiroService {

    @Value("${skip.login.url}")
    private String skipPath;

    @Autowired
    private ResourceBiz resourceBiz;

    private static final String PREMISSION_STRING = "roles[\"%s\"]";

    @Override
    public Map<String, String> loadFilterChainDefinitions() {
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // 需要跳过的路径
        String[] paths = skipPath.split(",");
        Arrays.stream(paths).forEach(item -> filterChainDefinitionMap.put(item, "anon"));

        // 需要校验的路径
        List<Object> resources = resourceBiz.acquireRoleResources();
        resources.forEach(item -> {
            Object[] objs = (Object[])item;
            filterChainDefinitionMap.put(objs[0].toString(), String.format(PREMISSION_STRING, objs[1]));
        });

        // 其他没有匹配的路径都需要登录了才能访问
        filterChainDefinitionMap.put("/**", "authc");
        return filterChainDefinitionMap;
    }

    @Override
    public void updatePermission(ShiroFilterFactoryBean shiroFilterFactoryBean) {
        synchronized (this) {
            AbstractShiroFilter shiroFilter;
            try {
                shiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean.getObject();
            } catch (Exception e) {
                throw new RuntimeException("get ShiroFilter from shiroFilterFactoryBean error!");
            }

            PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) shiroFilter.getFilterChainResolver();
            DefaultFilterChainManager manager = (DefaultFilterChainManager) filterChainResolver.getFilterChainManager();

            // 清空老的权限控制
            manager.getFilterChains().clear();

            shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();
            shiroFilterFactoryBean.setFilterChainDefinitionMap(loadFilterChainDefinitions());
            // 重新构建生成
            Map<String, String> chains = shiroFilterFactoryBean.getFilterChainDefinitionMap();
            for (Map.Entry<String, String> entry : chains.entrySet()) {
                String url = entry.getKey();
                String chainDefinition = entry.getValue().trim()
                        .replace(" ", "");
                manager.createChain(url, chainDefinition);
            }
        }
    }
}

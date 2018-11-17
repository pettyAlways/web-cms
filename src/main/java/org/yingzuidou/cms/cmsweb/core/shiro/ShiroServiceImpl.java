package org.yingzuidou.cms.cmsweb.core.shiro;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.yingzuidou.cms.cmsweb.dao.RoleResourceRepository;

import java.util.*;

/**
 * ShiroServiceImpl
 *
 * @author 鹰嘴豆
 * @date 2018/10/22
 */
@Service
public class ShiroServiceImpl implements ShiroService {

    @Value("${skip.login.url}")
    private String skipPath;

    /**
     * 这里本来使用ResourceBiz resourceBiz;依赖注入来加载资源，但是因为esourceBiz
     * 比shiro实例化早因此会出现AOP功能失效，实例化不会创建代理对象.增加@Lazy无效，
     * 因为ShiroConfiguration中shiroFilter创建实例前需要调用loadFilterChainDefinitions，
     * 在执行这个方法时需要调用resourceBiz中的资源加载方法，这时候resourceBiz即使
     * 懒加载也会实例化，因此退而求其次使用与AOP无关的DAO接口加载
     */
    @Autowired
    private RoleResourceRepository roleResourceRepository;

    private static final String PREMISSION_STRING = "roles[\"%s\"]";

    @Override
    public Map<String, String> loadFilterChainDefinitions() {
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // 需要跳过的路径
        String[] paths = skipPath.split(",");
        Arrays.stream(paths).forEach(item -> filterChainDefinitionMap.put(item, "anon"));

        // 需要校验按钮级别的权限路径（菜单模块等权限校验交给前端vue路由）
        List<Object> resources = roleResourceRepository.acquireRoleResources();
        Optional.ofNullable(resources).orElse(new ArrayList<>()).forEach(item -> {
            Object[] objs = (Object[])item;
            filterChainDefinitionMap.put(objs[0].toString(), String.format(PREMISSION_STRING, Objects.isNull(objs[1]) ? "" : objs[1]));
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

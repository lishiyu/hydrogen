package cn.net.hylink.hydrogen.core.config;

import cn.net.hylink.hydrogen.core.page.PageRequestFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;

public class PageConfig {

    @Resource
    private Environment env;

    /**
     * 配置分页请求过滤器
     */
    @Bean
    public FilterRegistrationBean<PageRequestFilter> pageRequestFilter() {
        FilterRegistrationBean<PageRequestFilter> fr = new FilterRegistrationBean<>();
        fr.setFilter(new PageRequestFilter(env));
        fr.addUrlPatterns("/*");
        fr.setName("pageRequestFilter");
        fr.setOrder(0);
        return fr;
    }
}

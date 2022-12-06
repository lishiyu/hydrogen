package cn.net.hylink.hydrogen.core.config;

import org.springframework.boot.autoconfigure.AutoConfigurationImportFilter;
import org.springframework.boot.autoconfigure.AutoConfigurationMetadata;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;

import java.util.HashSet;
import java.util.Set;

public class AutoConfigFilter implements AutoConfigurationImportFilter {

    /**
     * 要过滤掉的自动装载配置类
     */
    private Set<String> excludes = new HashSet<>();

    public AutoConfigFilter() {
        excludes.add(DataSourceAutoConfiguration.class.getName());
        excludes.add(LiquibaseAutoConfiguration.class.getName());
        excludes.add("com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure");
    }

    @Override
    public boolean[] match(String[] autoConfigurationClasses, AutoConfigurationMetadata autoConfigurationMetadata) {
        boolean[] match = new boolean[autoConfigurationClasses.length];
        for (int i = 0; i < autoConfigurationClasses.length; i++) {
            String className = autoConfigurationClasses[i];
            match[i] = !this.excludes.contains(className);
        }
        return match;
    }


}

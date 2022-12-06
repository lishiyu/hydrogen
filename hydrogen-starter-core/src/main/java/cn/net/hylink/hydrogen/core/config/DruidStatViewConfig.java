package cn.net.hylink.hydrogen.core.config;

import com.alibaba.druid.support.http.StatViewServlet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass({ StatViewServlet.class })
public class DruidStatViewConfig {

    @Value("${common.druid.username:druid}")
    private String username;

    @Value("${common.druid.password:druid}")
    private String password;

    @Bean
    public StatViewServlet statViewServlet() {
        return new StatViewServlet();
    }

    @Bean
    public ServletRegistrationBean<StatViewServlet> druidServlet(StatViewServlet servlet) {
        ServletRegistrationBean<StatViewServlet> registration = new ServletRegistrationBean<>(servlet);
        registration.addInitParameter("loginUsername", username);
        registration.addInitParameter("loginPassword", password);
        registration.setEnabled(true);
        registration.addUrlMappings("/druid/*");
        return registration;
    }
}

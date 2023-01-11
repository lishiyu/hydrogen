package cn.net.hylink.cloud.config;

import com.alibaba.cloud.sentinel.SentinelProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
public class CurrentUserIdConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        CurrentUserIdInterceptor currentUserInterceptor = new CurrentUserIdInterceptor();

        registry.addInterceptor(currentUserInterceptor);
    }

    @Bean
    public WebMvcConfigurer currentUserIdConfigurer() {
        return new CurrentUserIdConfiguration();
    }

}

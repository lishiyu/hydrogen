package cn.net.hylink.hydrogen.core.config;

import cn.net.hylink.hydrogen.core.config.health.CpuHealthIndicator;
import cn.net.hylink.hydrogen.core.config.health.DiscHealthIndicator;
import cn.net.hylink.hydrogen.core.config.health.MacHealthIndicator;
import cn.net.hylink.hydrogen.core.config.health.MemoryHealthIndicator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnWebApplication
public class HealthIndicatorConfig {

    @Bean
    public CpuHealthIndicator cpuHealthIndicator() {
        return new CpuHealthIndicator();
    }

    @Bean
    public MemoryHealthIndicator memoryHealthIndicator() {
        return new MemoryHealthIndicator();
    }

    @Bean
    public DiscHealthIndicator discHealthIndicator() {
        return new DiscHealthIndicator();
    }

    @Bean
    public MacHealthIndicator macHealthIndicator() {
        return new MacHealthIndicator();
    }
}

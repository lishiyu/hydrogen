package cn.net.hylink.hydrogen.core.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnWebApplication
public class GracefulShutdownConfig {


}

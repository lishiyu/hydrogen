package cn.net.hylink.cloud.property;

import cn.net.hylink.cloud.enums.HeaderType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "request.header")
public class RequestHeaderProperty {

    private List<Property> properties;

    @Data
    @NoArgsConstructor
    public static class Property {
        private String headerName;
        private HeaderType headerType;
    }
}

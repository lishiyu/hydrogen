package cn.net.hylink.cloud.config;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.EnumUtil;
import cn.net.hylink.cloud.enums.HeaderType;
import cn.net.hylink.cloud.property.RequestHeaderProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Request;
import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Slf4j
@Configuration
@ConditionalOnClass({ RequestInterceptor.class })
public class UserInfoRequestInterceptor {

    @Value("${request.header:}")
    private List<RequestHeaderProperty> requestHeaderProperty;

    @Bean
    public Request.Options options() {
        return new Request.Options();
    }

    @Bean
    public RequestInterceptor getRequestInterceptor() {
        return requestTemplate -> {
            ServletRequestAttributes attributes = null;
            try {
                attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            } catch (Exception e) {
                attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            }
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                Map<String, Collection<String>> headers = new HashMap<>();

                for (RequestHeaderProperty property: requestHeaderProperty) {
                    // 获取需要传递的头信息
                    if (HeaderType.SINGLETON == property.getHeaderType()) {
                        headers.put(property.getHeaderName(), Collections.singletonList(request.getHeader(property.getHeaderName())));
                    } else {
                        try {
                            ObjectMapper objectMapper = new ObjectMapper();
                            String[] header = objectMapper.readValue(request.getHeader(property.getHeaderName()), String[].class);
                            headers.put(property.getHeaderName(), CollUtil.toList(header));
                        } catch (JsonProcessingException e) {
                            log.error("错误信息:{}", e.getMessage(), e);
                        }

                    }
                }
                // feign请求时，携带上该头信息
                requestTemplate.headers(headers);
            }
        };
    }

}


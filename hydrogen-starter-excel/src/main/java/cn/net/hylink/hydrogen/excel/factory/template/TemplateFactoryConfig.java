package cn.net.hylink.hydrogen.excel.factory.template;

import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
public abstract class TemplateFactoryConfig {
    private static final TemplateFactory templateFactory = new TemplateFactory();

    static {
        Reflections reflections = new Reflections("cn.net.hylink");
        Set<Class<? extends TemplateService>> classSet = reflections.getSubTypesOf(TemplateService.class);

        Map<String, TemplateService> serviceMap = new HashMap<>();
        for (Class<? extends TemplateService> clazz : classSet){
            // 实例化获取到的类
            try {
                TemplateService template = clazz.newInstance();
                serviceMap.put(template.getTemplateType(), template);
            } catch (Exception e) {
                log.error("错误信息：{}", e.getMessage(), e);
                throw new RuntimeException(e);
            }
        }
        templateFactory.setServiceMap(serviceMap);
    }

    public static TemplateFactory getTemplateFactory() {
        return templateFactory;
    }
}

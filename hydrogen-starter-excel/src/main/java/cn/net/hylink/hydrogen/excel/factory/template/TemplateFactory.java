package cn.net.hylink.hydrogen.excel.factory.template;

import java.io.OutputStream;
import java.util.Map;

public class TemplateFactory {
    private static Map<String, TemplateService> serviceMap;

    public void createWorkbook(String type, OutputStream outputStream) {
        TemplateService templateService = serviceMap.get(type);
        if (templateService == null) {
            throw new RuntimeException("模板类型不存在");
        }
        templateService.createTemplate(outputStream);
    }

    public String getTemplateName(String type) {
        TemplateService templateService = serviceMap.get(type);
        return templateService.getTemplateName();
    }

    public void setServiceMap(Map<String, TemplateService> serviceMap) {
        TemplateFactory.serviceMap = serviceMap;
    }
}

package cn.net.hylink.hydrogen.excel.factory.template;

import java.io.OutputStream;

public interface TemplateService {

    void createTemplate(OutputStream outputStream);

    String getTemplateType();

    String getTemplateName();

}

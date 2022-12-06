package cn.net.hylink.hydrogen.excel.util;

import cn.hutool.core.util.StrUtil;
import cn.net.hylink.hydrogen.excel.factory.template.TemplateFactory;
import cn.net.hylink.hydrogen.excel.factory.template.TemplateFactoryConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 *
 */
@Slf4j
public class TemplateUtil {

    public static void createWorkbook(String type) throws IOException {

        HttpServletResponse response =
                ((ServletRequestAttributes) Objects.requireNonNull(
                        RequestContextHolder.getRequestAttributes()))
                        .getResponse();
        assert response != null;
        createWorkbook(type, response);
    }

    public static void createWorkbook(String type, HttpServletResponse response) throws IOException {
        TemplateFactory templateFactory = TemplateFactoryConfig.getTemplateFactory();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        String contentDisposition = StrUtil.format("attachment;filename={}.xls", templateFactory.getTemplateName(type));
        response.setHeader("Content-disposition", new String(contentDisposition.getBytes(), StandardCharsets.ISO_8859_1));
        OutputStream outputStream = response.getOutputStream();
        templateFactory.createWorkbook(type, outputStream);
    }
}

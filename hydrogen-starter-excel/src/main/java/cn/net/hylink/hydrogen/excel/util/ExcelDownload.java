package cn.net.hylink.hydrogen.excel.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class ExcelDownload {

    public static <T> void download(HttpServletResponse response , List<T> data, WriteSheet writeSheet, String fileName, Class<T> entityClass) {

        try {
            OutputStream outputStream = response.getOutputStream();
            //通知浏览器以附件的形式下载处理，设置返回头要注意文件名有中文
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;fileName=" + new String(fileName.getBytes(), StandardCharsets.ISO_8859_1) + ".xls");
            response.setCharacterEncoding("utf-8");
            //设置自适应宽度
            writeSheet.setAutoTrim(Boolean.TRUE);
            // 第一个 sheet 名称
            writeSheet.setSheetName("sheet");
            String json = new ObjectMapper().writeValueAsString(data);
            List<Map> maps = JSONUtil.toList(json, Map.class);
            data = maps.stream().map(map -> {
                T t = BeanUtil.toBean(map, entityClass);
                Set set = map.keySet();

                for (Object key: set) {
                    if (key.toString().contains("_dictText")) {
                        try {
                            String methodName = key.toString().replace("_dictText", "");
                            Field f = t.getClass().getDeclaredField(methodName);
                            f.setAccessible(true);
                            f.set(t, map.get(key));
                        } catch (NoSuchFieldException | IllegalAccessException e) {
                            log.error("错误信息：{}", e.getMessage(), e);
                            throw new RuntimeException(e);
                        }
                    }
                }
                return t;
            }).collect(Collectors.toList());

            ExcelWriter writer = EasyExcelFactory
                    .write(outputStream, entityClass)
                    .excelType(ExcelTypeEnum.XLS)
                    .build().write(data, writeSheet);
            outputStream.flush();
            writer.finish();
        } catch (Exception e) {
            log.error("错误信息：{}", e.getMessage(), e);
        }

    }
}

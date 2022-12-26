package cn.net.hylink.hydrogen.generator;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.map.MapBuilder;
import cn.hutool.core.util.StrUtil;
import cn.net.hylink.hydrogen.mybatis.base.BasicPo;
import cn.net.hylink.hydrogen.mybatis.injector.RootMapper;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import lombok.NonNull;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 代码生成器
 */
public class HydrogenGenerator {

    // 代码作者
    private String author;
    // 数据库连接URL
    private String url;
    // 数据库用户名
    private String username;
    // 数据库密码
    private String password;
    // 表空间
    private String schemaName;

    private final String JOINER_POINT = ".";


    public void generateMVC() {
        String projectPath = System.getProperty("user.dir");
        String parentPackageName = scanner("父包名");
        String outputDirPath = projectPath + "/src/main/java";
        String moduleName = scanner("模块名");

        FastAutoGenerator.create(new DataSourceConfig.Builder(url,username,password).schema(schemaName))
                .globalConfig(builder -> builder
                        .author(author)
                        .dateType(DateType.TIME_PACK)
                        .disableOpenDir()
                        .outputDir(outputDirPath)
                        .commentDate(() -> LocalDateTimeUtil.format(LocalDateTime.now(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                        .build())
                .packageConfig(builder -> builder
                        .parent(parentPackageName)
                        .moduleName(moduleName)
                        .entity("pojo.po")
//                        .other("pojo.dto")
                        // OutputFile.mapperXml, projectPath + "/hydrogen-example/hydrogen-starter-generator-example/src/main/resources/mapper"
                        .pathInfo(MapBuilder.create(new HashMap<OutputFile, String>(10))
                                .put(OutputFile.controller, StrUtil.join("/", outputDirPath, StrUtil.replace(parentPackageName, ".", "/"), moduleName, "app", "web"))
                                .put(OutputFile.mapperXml, projectPath + "/src/main/resources/mapper")
                                .put(OutputFile.other, projectPath + "/hydrogen-example/hydrogen-starter-generator-example/src/main/java/pojo/dto")
                                .build()))
                .strategyConfig(builder -> builder
                        .addInclude(scanner("表名，多个英文逗号分割").split(","))
                        .controllerBuilder()
                        .enableRestStyle()
                        .enableHyphenStyle()
                        .serviceBuilder()
                        .formatServiceFileName("%sService")
                        .formatServiceImplFileName("%sServiceImpl")
                        .entityBuilder()
                        .formatFileName("%sPo")
                        .enableLombok()
                        .superClass(BasicPo.class)
                        .addSuperEntityColumns("id", "created_by", "updated_by", "created_at", "updated_at")
                        .versionColumnName("version")
                        .versionPropertyName("version")
                        .mapperBuilder()
                        .superClass(RootMapper.class)
                        .enableMapperAnnotation()
                        .build())
                .templateEngine(new EnhanceFreemarkerTemplateEngine()).templateConfig(builder -> builder
                        .entity("/template/entity.java")
                        .service("/template/service.java")
                        .serviceImpl("/template/serviceImpl.java")
                        .mapper("/template/mapper.java")
                        .mapperXml("/template/mapper.xml")
                        .controller("/template/controller.java")
                        .build())
                .injectionConfig(builder -> builder
                        .customMap(MapBuilder.create(new HashMap<String, Object>(10))
                                .put("module_name", moduleName)
                                .put("package_controller", StrUtil.join(JOINER_POINT, parentPackageName, moduleName, "app.web"))
                                .put("package_service", StrUtil.join(JOINER_POINT,parentPackageName, moduleName, "service"))
                                .put("package_service_impl", StrUtil.join(JOINER_POINT,parentPackageName, moduleName, "service.impl"))
                                .put("package_entity", StrUtil.join(JOINER_POINT,parentPackageName, moduleName, "pojo.po"))
                                .put("package_mapper", StrUtil.join(JOINER_POINT,parentPackageName, moduleName, "mapper"))
//                                .put("package_dto", StrUtil.join(JOINER_POINT,parentPackageName, moduleName + "pojo.dto"))
//                                .put("package_converter", StrUtil.join(JOINER_POINT,parentPackageName, moduleName, "converter"))
                                .build())
                        .build())
                .execute();




    }

    public static class EnhanceFreemarkerTemplateEngine extends FreemarkerTemplateEngine {
        @Override
        protected void outputCustomFile(@NonNull Map<String, String> customFile, @NonNull TableInfo tableInfo, @NonNull Map<String, Object> objectMap) {
            String entityName = tableInfo.getEntityName().replace("Po", "");
            String otherPath = this.getPathInfo(OutputFile.other);
            customFile.forEach((key, value) -> {
                String fileName = null;
                if (Objects.equals(key, "Repository")) {
                    fileName = String.format(otherPath + File.separator + entityName + "%s", key + ".java");
                }
                if (Objects.equals(key, "RepositoryImpl")) {
                    fileName = String.format(otherPath + "impl" + File.separator + entityName + "%s", key + ".java");
                }
                if (Objects.equals(key, "DTO")) {
                    fileName = String.format(otherPath + "entity" + File.separator + "dto" + File.separator + entityName + "%s", key + ".java");
                }
                if (Objects.equals(key, "Converter")) {
                    fileName = String.format(otherPath + "converter" + File.separator + entityName + "%s", key + ".java");
                }
                objectMap.put("repository_name", entityName + "Repository");
                objectMap.put("repository_impl_name", entityName + "RepositoryImpl");
                objectMap.put("dto_name", entityName + "DTO");
                objectMap.put("converter_name", entityName + "Converter");
                Optional.ofNullable(fileName).ifPresent(name -> this.outputFile(new File(name), objectMap, value));
            });
        }
    }

    /**
     * 设置代码作者
     */
    public HydrogenGenerator setAuthor(String author) {
        if (StrUtil.isBlank(author)) {
            throw new MybatisPlusException("Author is null");
        }
        this.author = author;
        return this;
    }

    /**
     * 设置数据库连接URL
     */
    public HydrogenGenerator setUrl(String url) {
        if (StrUtil.isBlank(url)) {
            throw new MybatisPlusException("Database url is null");
        }
        this.url = url;
        return this;
    }

    /**
     * 设置数据库用户名
     */
    public HydrogenGenerator setUsername(String username) {
        if (StrUtil.isBlank(username)) {
            throw new MybatisPlusException("Database username is null");
        }
        this.username = username;
        return this;
    }

    /**
     * 设置数据库密码
     */
    public HydrogenGenerator setPassword(String password) {
        if (StrUtil.isBlank(password)) {
            throw new MybatisPlusException("Database password is null");
        }
        this.password = password;
        return this;
    }

    /**
     * 设置数据库密码
     */
    public HydrogenGenerator setSchemaName(String schemaName) {
        if (StrUtil.isBlank(schemaName)) {
            throw new MybatisPlusException("Database password is null");
        }
        this.schemaName = schemaName;
        return this;
    }

    /**
     * 读取控制台内容
     */
    private String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        String help = "请输入" + tip + "：";
        System.out.println(help);
        if (scanner.hasNext()) {
            String input = scanner.next();
            if (!StrUtil.isBlank(input)) {
                return input;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static HydrogenGenerator get() {
        return new HydrogenGenerator();
    }

    private HydrogenGenerator() {}
}

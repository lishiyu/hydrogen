package ${package_controller};

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import ${package_service}.${table.serviceName};
import org.springframework.web.bind.annotation.RestController;

/**
 * ${table.comment!}模块
 *
 * @author ${author}
 */
@Validated
@RestController
@RequestMapping("${module_name}")
public class ${table.controllerName} {

    @Resource
    private ${table.serviceName} service;

}

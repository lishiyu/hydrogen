package ${package_service_impl};

import ${package_entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package_service}.${table.serviceName};
import ${superServiceImplClassPackage};
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 * @author ${author}
 */
@Service
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements ${table.serviceName} {

    @Resource
    private ${table.mapperName} mapper;


}

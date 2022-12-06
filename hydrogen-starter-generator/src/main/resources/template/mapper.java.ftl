package ${package_mapper};

import ${package_entity}.${entity};
import ${superMapperClassPackage};
import org.apache.ibatis.annotations.Mapper;

/**
 * @author ${author}
 */
@Mapper
public interface ${table.mapperName} extends ${superMapperClass}<${entity}> {

}

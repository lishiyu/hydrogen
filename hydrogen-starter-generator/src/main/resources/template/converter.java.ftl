package ${package_converter};

import ${package_entity}.${entity};
import ${package_dto}.${dto_name};
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author ${author}
 */
@Mapper(builder = @Builder(disableBuilder = true), unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ${converter_name} {

    ${converter_name} INSTANCE = Mappers.getMapper(${converter_name}.class);

    /**
     * 参数转换
     *
     * @param dto dto
     * @return dataObject
     */
    ${entity} to(${dto_name} dto);

    /**
     * 参数转换
     *
     * @param dataObject dataObject
     * @return dto
     */
    ${dto_name} to(${entity} dataObject);

}


package ${package_dto};

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author ${author}
 */
@Data
@Accessors(chain = true)
public class ${dto_name} implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;
<#list table.commonFields as field>
    <#if field.comment!?length gt 0>

    /**
    * ${field.comment}
    */
    </#if>
    private ${field.propertyType} ${field.propertyName};
</#list>
<#list table.fields as field>
    <#if field.comment!?length gt 0>
        
    /**
     * ${field.comment}
     */
    </#if>
    private ${field.propertyType} ${field.propertyName};
</#list>

}

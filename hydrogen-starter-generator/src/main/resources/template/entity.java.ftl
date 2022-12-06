package ${package_entity};

import cn.net.hylink.hydrogen.mybatis.base.BasicPo;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

<#if table.importPackages?seq_contains('java.time.LocalDateTime')>
import java.time.LocalDateTime;
</#if>

/**
 * ${table.comment!}
 *
 * @author ${author}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@TableName("${table.name}")
public class ${entity} extends ${superEntityClass} {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;
<#list table.fields as field>
    <#if field.keyFlag>
        <#assign keyPropertyName="${field.propertyName}"/>
    </#if>
    <#if field.comment!?length gt 0>

    /**
     * ${field.comment}
     */
    </#if>
    <#if field.keyFlag>
        <#if field.keyIdentityFlag>
    @TableId(value = "${field.annotationColumnName}", type = IdType.AUTO)
        <#elseif idType??>
    @TableId(value = "${field.annotationColumnName}", type = IdType.${idType})
        <#elseif field.convert>
    @TableId("${field.annotationColumnName}")
        </#if>
    <#elseif field.fill??>
        <#if field.convert>
    @TableField(value = "${field.annotationColumnName}", fill = FieldFill.${field.fill})
        <#else>
    @TableField(fill = FieldFill.${field.fill})
        </#if>
    <#elseif field.convert>
    @TableField("${field.annotationColumnName}")
    </#if>
    <#if (versionFieldName!"") == field.name>
    @Version
    </#if>
    <#if (logicDeleteFieldName!"") == field.name>
    @TableLogic
    </#if>
    private ${field.propertyType} ${field.propertyName};
</#list>

}

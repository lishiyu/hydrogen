package cn.net.hylink.department.pojo.po;

import cn.net.hylink.hydrogen.mybatis.base.BasicPo;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * 
 *
 * @author 李同学
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@TableName("t_department")
public class TDepartmentPo extends BasicPo {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 父级code
     */
    private String parentCode;

    /**
     * 机构编码
     */
    private String deptCode;

    /**
     * 机构全称
     */
    private String deptName;

    /**
     * 机构简称
     */
    private String deptAlias;

    /**
     * 全路径
     */
    private String path;

    /**
     * 节点深度
     */
    private Integer depth;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 是否是叶子节点
     */
    private Integer isLeaf;

    /**
     * 机构类型
     */
    private String deptType;

    /**
     * 启动状态
     */
    private Integer state;

    /**
     * 逻辑删除
     */
    private Integer deleted;

    /**
     * 应用id
     */
    private Long appId;

}

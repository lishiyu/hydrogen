package cn.net.hylink.hydrogen.log.annotation;

import java.lang.annotation.*;

/**
 * Description: 操作日志数据注解，用以标识ID和NAME字段<br>
 * Create Date: 2020年5月22日<br>
 * Modified By：<br>
 * Modified Date：<br>
 * Why & What is modified：<br> 
 * @author L
 * @version 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface OperateLog {

	/**
	 * Description: 数据字段类型<br> 
	 * Created date: 2020年5月22日
	 * @return
	 * @author L
	 */
	LogData value();
}

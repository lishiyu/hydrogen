package cn.net.hylink.hydrogen.core.validator;

import com.baidu.unbiz.fluentvalidator.ValidationError;
import org.springframework.http.HttpStatus;

/**
 * Description: FluentValidator工具类<br>
 * Create Date: 2020年5月4日<br>
 * Modified By：<br>
 * Modified Date：<br>
 * Why & What is modified：<br> 
 * @version 1.0
 */
public class FluentValidatorTools {
	
	/**
	 * 构建验证失败对象
	 * @param field 校验属性
	 * @param fieldValue 校验属性值
	 * @param errorMsg 提示消息
	 * @param errorCode 错误编号
	 * @return
	 */
	public static ValidationError createError(String field, Object fieldValue, String errorMsg, int errorCode) {
		ValidationError validationError = new ValidationError();
		validationError.setField(field);
		validationError.setInvalidValue(fieldValue);
		validationError.setErrorMsg(errorMsg);
		validationError.setErrorCode(errorCode);
		return validationError;
	}
	
	/**
	 * 构建验证失败对象，使用默认的http 500做为错误编码
	 * @param field 校验属性
	 * @param fieldValue 校验属性值
	 * @param errorMsg 提示消息
	 * @return
	 */
	public static ValidationError createError(String field, Object fieldValue, String errorMsg) {
		ValidationError validationError = new ValidationError();
		validationError.setField(field);
		validationError.setInvalidValue(fieldValue);
		validationError.setErrorMsg(errorMsg);
		validationError.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		return validationError;
	}
}

package cn.net.hylink.hydrogen.core.validator;

import cn.net.hylink.hydrogen.core.exception.CommonException;
import cn.net.hylink.hydrogen.core.exception.FluentValidException;
import cn.net.hylink.hydrogen.core.result.ResultCode;
import com.baidu.unbiz.fluentvalidator.ValidateCallback;
import com.baidu.unbiz.fluentvalidator.ValidationError;
import com.baidu.unbiz.fluentvalidator.Validator;
import com.baidu.unbiz.fluentvalidator.validator.element.ValidatorElementList;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Description: Fluent验证回调<br>
 * Create Date: 2020年5月4日<br>
 * Modified By：<br>
 * Modified Date：<br>
 * Why & What is modified：<br>
 * @version 1.0
 */
@Component
public class FluentValidatorCallback implements ValidateCallback {

	/**
	 * 所有验证完成并且成功后
	 *
	 * @param validatorElementList
	 *            验证器list
	 */
	@Override
	public void onSuccess(ValidatorElementList validatorElementList) {
	}

	/**
	 * 所有验证步骤结束，发现验证存在失败后 失败信息发送给自定义异常，通过自定义异常发送给调用端信息
	 * 
	 * @param validatorElementList
	 *            验证器list
	 * @param errors
	 *            验证过程中发生的错误
	 */
	@Override
	public void onFail(ValidatorElementList validatorElementList, List<ValidationError> errors) {
		List<String> errorsList = errors.stream().map(ValidationError::getErrorMsg).collect(Collectors.toList());
		throw new FluentValidException(ResultCode.PARAM_IS_INVALID.code(), errorsList);
	}

	/**
	 * 执行验证过程中发生了异常后
	 */
	@Override
	public void onUncaughtException(Validator validator, Exception e, Object target) throws Exception {
		throw new CommonException(e);
	}

}

package cn.net.hylink.hydrogen.core.result;

import cn.net.hylink.hydrogen.core.exception.CommonException;
import cn.net.hylink.hydrogen.core.util.MessageUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;
import java.util.List;

/**
 * 全局错误返回结果
 */
@Getter
@Setter
@ToString
public class ErrorResult<T> extends Result<T> {

	private static final long serialVersionUID = 5465771636997300686L;

	/**
	 * HTTP响应状态码
	 * {@link org.springframework.http.HttpStatus}
	 */
	private Integer status;
	/**
	 * HTTP响应状态码的英文提示
	 */
	private String error;
	/**
	 * 调用接口路径
	 */
	private String path;
	/**
	 * 异常的名字
	 */
	private String exception;
	/**
	 * 异常的错误传递的数据
	 */
	private List<?> errors;
	/**
	 * 时间戳
	 */
	private Date timestamp;

	/**
	 * 异常错误定义，加入错误信息
	 */
	public static <T> ErrorResult<T> error(ResultCode resultCode, Throwable e, HttpStatus httpStatus, List<?> errors) {
		ErrorResult<T> result = error(resultCode, e, httpStatus);
		result.setErrors(errors);
		return result;
	}

	/**
	 * 异常错误定义
	 */
	public static <T> ErrorResult<T> error(ResultCode resultCode, Throwable e, HttpStatus httpStatus) {
		ErrorResult<T> result = new ErrorResult<>();
		result.setSuccess(false);
		result.setCode(resultCode.code());
		result.setMessage(MessageUtil.get(resultCode.message()));
		result.setStatus(httpStatus.value());
		result.setError(httpStatus.getReasonPhrase());
		// 渗透测试错误信息暴露后端使用框架，设置为null
		result.setException(null);
		String path = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
				.getRequestURI();
		result.setPath(path);
		result.setTimestamp(new Date());
		return result;
	}

	/**
	 * 通用业务异常错误定义
	 */
	public static <T> ErrorResult<T> error(CommonException e) {
		ErrorResult<T> result = new ErrorResult<>();
		result.setSuccess(false);
		result.setCode(e.getCode());
		result.setMessage(e.getMessage());
		result.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		result.setError(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
		// 渗透测试错误信息暴露后端使用框架，设置为null
		result.setException(null);
		String path = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
				.getRequestURI();
		result.setPath(path);
		result.setTimestamp(new Date());
		return result;
	}
	
}

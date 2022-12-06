package cn.net.hylink.hydrogen.core.exception;

import java.util.List;

/**
 * Fluent校验异常
 */
public class FluentValidException extends RuntimeException {

	private static final long serialVersionUID = -4175098165705424808L;
	
	/**
	 * 错误码
	 */
	private Integer code;
	
	/**
	 * 错误信息
	 */
	private List<String> errors;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	
	public FluentValidException() {
		super();
	}

	public FluentValidException(int code, List<String> errors) {
		super(String.join(";", errors));
		this.code = code;
		this.errors = errors;
	}
	
}

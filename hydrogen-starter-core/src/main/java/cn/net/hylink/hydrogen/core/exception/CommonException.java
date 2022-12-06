package cn.net.hylink.hydrogen.core.exception;

/**
 * 通用业务自定义异常
 */
public class CommonException extends RuntimeException {

	private static final long serialVersionUID = -9127122568791186301L;
	
	/**
	 * 错误码
	 */
	private Integer code;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public CommonException() {
		super();
	}

	public CommonException(String message) {
		super(message);
	}
	
	public CommonException(int code, String message) {
		super(message);
		this.code = code;
	}
	
	public CommonException(Throwable cause) {
		super(cause);
	}
	public CommonException(String message, Throwable cause) {
		super(message,cause);
	}
}

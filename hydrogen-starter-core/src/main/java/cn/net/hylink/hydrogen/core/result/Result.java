package cn.net.hylink.hydrogen.core.result;

import cn.net.hylink.hydrogen.core.util.MessageUtil;
import lombok.Data;

/**
 * 通用返回结果
 */
@Data
public class Result<T> implements java.io.Serializable {

	private static final long serialVersionUID = 1332083524709890293L;

	/**
	 * 系统内部自定义的返回值编码，{@link ResultCode}
	 */
	private Integer code;

	/**
	 * 提示消息
	 */
	private String message;

	/**
	 * 数据对象
	 */
	private T data;

	/**
	 * 操作是否成功
	 */
	private Boolean success;
	
	public Result() {
	}

	/**
	 * 操作成功结果
	 */
	public static <T> Result<T> success() {
		return success(MessageUtil.get(ResultCode.SUCCESS.message()), null);
	}

	/**
	 * 操作成功结果，自定义信息
	 */
	public static <T> Result<T> success(String msg) {
		return success(msg, null);
	}

	/**
	 * 操作成功结果，自定义信息和返回数据对象
	 */
	public static <T> Result<T> success(String msg, T object) {
		Result<T> result = new Result<>();
		result.setCode(ResultCode.SUCCESS.code());
		result.setMessage(msg);
		result.setData(object);
		result.setSuccess(true);
		return result;
	}

	/**
	 * 操作成功结果，自定义编码和信息
	 */
	public static <T> Result<T> success(Integer code, String message) {
		Result<T> result = new Result<>();
		result.setCode(code);
		result.setMessage(message);
		result.setSuccess(true);
		return result;
	}

	/**
	 * 操作成功结果，自定义编码和信息
	 */
	public static <T> Result<T> success(T o) {
		return success(MessageUtil.get(ResultCode.SUCCESS.message()), o);
	}

	/**
	 * 操作失败结果
	 */
	public static <T> Result<T> failure() {
		return failure(MessageUtil.get(ResultCode.FAILURE.message()), null);
	}

	/**
	 * 操作失败结果，自定义信息
	 */
	public static <T> Result<T> failure(String msg) {
		return failure(msg, null);
	}

	/**
	 * 操作失败结果，自定义信息和返回数据对象
	 */
	public static <T> Result<T> failure(String msg, T object) {
		Result<T> result = new Result<>();
		result.setCode(ResultCode.FAILURE.code());
		result.setMessage(msg);
		result.setSuccess(false);
		result.setData(object);
		return result;
	}

	/**
	 * 操作失败结果，自定义编码和信息
	 */
	public static <T> Result<T> failure(Integer code, String message) {
		Result<T> result = new Result<>();
		result.setCode(code);
		result.setMessage(message);
		result.setSuccess(false);
		return result;
	}

	/**
	 * 操作结果，根据结果数据判断操作成功或失败
	 * 如果结果数据为Boolean则根据此值返回结果，否则不为空则返回成功
	 */
	public static <T> Result<T> result(T operateResult) {
		Result<T> result = new Result<>();
		if (operateResult == null) {
			result.setSuccess(false);
			result.setCode(ResultCode.FAILURE.code());
			result.setMessage(MessageUtil.get(ResultCode.FAILURE.message()));
		} else {
			result.setCode(ResultCode.SUCCESS.code());
			result.setMessage(MessageUtil.get(ResultCode.SUCCESS.message()));
			result.setSuccess(true);
			result.setData(operateResult);
			if (operateResult instanceof Boolean) {
				result.setData(null);
				Boolean b = (Boolean) operateResult;
				if (!b) {
					result.setSuccess(false);
					result.setCode(ResultCode.FAILURE.code());
					result.setMessage(MessageUtil.get(ResultCode.FAILURE.message()));
				}
			}
			if (operateResult instanceof Integer) {
				result.setData(null);
				Integer i = (Integer) operateResult;
				if (i <= 0) {
					result.setSuccess(false);
					result.setCode(ResultCode.FAILURE.code());
					result.setMessage(MessageUtil.get(ResultCode.FAILURE.message()));
				}
			}
		}
		return result;
	}

}

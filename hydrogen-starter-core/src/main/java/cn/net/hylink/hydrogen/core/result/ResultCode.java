package cn.net.hylink.hydrogen.core.result;

/**
 * Description: 操作码<br>
 * Create Date: 2020年4月30日<br>
 * Modified By：<br>
 * Modified Date：<br>
 * Why & What is modified：<br>
 * 
 * @author L
 * @version 1.0
 */
public enum ResultCode {

	/**
	 * 操作成功状态码
	 */
	SUCCESS(200, "operate.result.success"),

	/**
	 * 操作失败状态码
	 */
	FAILURE(500, "operate.result.failure"),

	/**
	 * 无访问权限
	 */
	PERMISSION_NO_ACCESS(401, "no.access"),

	/**
	 * 数据库操作异常
	 */
	DATABASE_ERROR(1001, "database.operate.exception"),

	/**
	 * 参数校验失败
	 */
	PARAM_IS_INVALID(1002, "invalid.parameter"),

	/**
	 * 系统错误，请稍后重试
	 */
	SYSTEM_INNER_ERROR(1003, "system.error.try.later");

	private Integer code;

	private String message;

	ResultCode(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public Integer code() {
		return this.code;
	}

	public String message() {
		return this.message;
	}

	public static String getMessage(String name) {
		for (ResultCode item : ResultCode.values()) {
			if (item.name().equals(name)) {
				return item.message;
			}
		}
		return name;
	}

	public static Integer getCode(String name) {
		for (ResultCode item : ResultCode.values()) {
			if (item.name().equals(name)) {
				return item.code;
			}
		}
		return null;
	}

}

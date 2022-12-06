package cn.net.hylink.hydrogen.log.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 操作对象信息
 */
@Data
public class OperateObject implements Serializable {

	private static final long serialVersionUID = -567093527891809154L;

	/**
	 * 操作对象ID
	 */
	private String id;
	
	/**
	 * 操作对象名
	 */
	private String name;
}

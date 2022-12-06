package cn.net.hylink.hydrogen.log.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 原始日志实体类
 */
@Data
public class LogEntity implements Serializable {

	private static final long serialVersionUID = -1485666942993792196L;
	
	/**
	 * 当前操作人
	 */
	private String username;

	/**
     * session-id
     */
    private String sessionId;

    /**
     * 操作时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date operateTime;

    /**
     * 操作人IP
     */
    private String userSourceIp;

    /**
     * 操作编号
     */
    private String operateCode;

    /**
     * 操作结果：0失败；1成功。
     */
    private String operateResult;
    
    /**
	 * 成功/失败消息 该提示消息需支持国际化
	 */
	private String msg;
  
    /**
	 * 操作对象
	 */
	private List<OperateObject> operateObjs;

	/**
	 * 自定义参数Json字符串
	 */
	private String customData;
}

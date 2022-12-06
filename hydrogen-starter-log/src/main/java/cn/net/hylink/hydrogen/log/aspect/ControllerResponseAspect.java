package cn.net.hylink.hydrogen.log.aspect;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONUtil;
import cn.net.hylink.hydrogen.core.result.ErrorResult;
import cn.net.hylink.hydrogen.core.result.Result;
import cn.net.hylink.hydrogen.log.annotation.LogData;
import cn.net.hylink.hydrogen.log.annotation.OperateLog;
import cn.net.hylink.hydrogen.log.entity.LogEntity;
import cn.net.hylink.hydrogen.log.entity.OperateObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * 操作日志记录拦截器
 */
@Slf4j
@ControllerAdvice
public class ControllerResponseAspect implements ResponseBodyAdvice<Object> {
	
	private static final String SUCCESS = "1";
	
	private static final String FAILURE = "0";
	
	/**
	 * 是否启用查询操作日志记录
	 */
	@Value("${common.operlogs.record-query.enable:false}")
	private boolean recordQuery;

	@Override
	public Object beforeBodyWrite(Object result, MethodParameter methodParam, MediaType mediaType,
			Class<? extends HttpMessageConverter<?>> converter, ServerHttpRequest request, ServerHttpResponse response) {
//		HttpHeaders headers = request.getHeaders();
//		String uid = headers.getFirst(CurrentUser.UID);
//		String operateCode = headers.getFirst(SporeCoreConstants.OPERATE_CODE);
//		// trace-id为空表示该操作请求为调用链头
//		boolean need = StrUtil.isEmpty(headers.getFirst("uber-trace-id"));
//		uid = StrUtil.isBlank(uid) ? this.analysisUid(result) : uid;
//		if (need && StrUtil.isNotBlank(operateCode) && StrUtil.isNotBlank(uid)) {
//			log.debug("record operation log, uid: {}, operate code: {}", uid, operateCode);
//			LogEntity logEntity = this.buildLogEntity(result);
//			if (logEntity != null) {
//				logEntity.setUsername(uid);
//				logEntity.setSessionId(SecureUtil.md5(request.toString()));
//				logEntity.setOperateTime(new Date());
//				logEntity.setUserSourceIp(headers.getFirst(CurrentUser.UIP));
//				logEntity.setOperateCode(operateCode);
//				try {
//					// 插入
//				} catch (Exception e) {
//					log.error("record operation log error, log content: {}", JSONUtil.toJsonStr(log));
//				}
//			}
//		}
		return result;
	}

	@Override
	public boolean supports(MethodParameter methodParam, Class<? extends HttpMessageConverter<?>> converter) {
		return true;
	}

	/**
	 * 构造log对象
	 */
	private LogEntity buildLogEntity(Object result) {
		if (result == null) {
			return null;
		}
		// 增、删、改操作出现异常
		if (result instanceof ErrorResult) {
			ErrorResult r = (ErrorResult) result;
			LogEntity log = new LogEntity();
			log.setOperateResult(FAILURE);
			log.setMsg(r.getMessage());
			log.setOperateObjs(this.buildOperateObjs(r.getData()));
			log.setCustomData(JSONUtil.toJsonStr(r.getData()));
			return log;
		}
		// 增、删、改操作无异常
		if (result instanceof Result) {
			Result<?> r = (Result<?>) result;
			LogEntity log = new LogEntity();
			log.setOperateResult(r.getSuccess() ? SUCCESS : FAILURE);
			log.setMsg(r.getMessage());
			log.setOperateObjs(this.buildOperateObjs(r.getData()));
			log.setCustomData(JSONUtil.toJsonStr(r.getData()));
			return log;
		}
		// 查询操作
		if (recordQuery) {
			LogEntity log = new LogEntity();
			log.setOperateResult(SUCCESS);
			log.setMsg("Operation Success");
			if (result instanceof Page){
				// 查询返回分页对象PageInfo
				Page<?> p = (Page<?>) result;
				log.setOperateObjs(this.buildOperateObjs(p.getRecords()));
			} else if (result instanceof Collection<?>){
				// 查询返回集合List Set ArrayList
				log.setOperateObjs(this.buildOperateObjs(result));
			} else {
				// 返回单个对象
				log.setOperateObjs(this.buildOperateObjs(result));
			}
			return log;
		}
		return null;
	}

	/**
	 * 构造操作对象集合
	 */
	private List<OperateObject> buildOperateObjs(Object data) {
		if (data == null || data instanceof String || ObjectUtil.isBasicType(data)) {
			return null;
		}
		List<OperateObject> operates = new ArrayList<>();
		if (data instanceof Collection<?>) {
			Collection<?> cs = (Collection<?>) data;
			if (CollectionUtil.isNotEmpty(cs)) {
				cs.forEach(c -> {
					OperateObject oo = this.buildOperateObj(c);
					if (oo != null) {
						operates.add(oo);
					}
				});
			}
		} else {
			OperateObject oo = this.buildOperateObj(data);
			if (oo != null) {
				operates.add(oo);
			}
		}
		return operates;
	}
	
	/**
	 * 构造操作对象
	 */
	private OperateObject buildOperateObj(Object obj) {
		Field[] fields = ReflectUtil.getFields(obj.getClass());
		Object id = null;
		Object name = null;
		for (Field field : fields) {
			OperateLog ol = field.getAnnotation(OperateLog.class);
			if (ol == null) {
				continue;
			}
			if (ol.value() == LogData.ID) {
				id = ReflectUtil.getFieldValue(obj, field);
			}
			if (ol.value() == LogData.NAME) {
				name = ReflectUtil.getFieldValue(obj, field);
			}
		}
		if (id != null || name != null) {
			OperateObject operateObject = new OperateObject();
			operateObject.setId(id == null ? "" : String.valueOf(id));
			operateObject.setName(name == null ? "" : String.valueOf(name));
			return operateObject;
		}
		return null;
	}

	/**
	 * 尝试从Result --> data对象内解析出uid
	 */
	private String analysisUid(Object result) {
		if (result == null) {
			return null;
		}
		if (result instanceof Result) {
			Object data = ((Result) result).getData();
			if (data == null || data instanceof String || ObjectUtil.isBasicType(data)) {
				return null;
			}
			if (data instanceof Collection<?>) {
				Collection<?> cs = (Collection<?>) data;
				if (CollectionUtil.isNotEmpty(cs)) {
					for (Object c : cs) {
						Field userNameField = ReflectUtil.getField(c.getClass(), "_username");
						if (userNameField != null) {
							return (String) ReflectUtil.getFieldValue(c, userNameField);
						}
					}
				}
			} else {
				Field userNameField = ReflectUtil.getField(data.getClass(), "_username");
				if (userNameField != null) {
					return (String) ReflectUtil.getFieldValue(data, userNameField);
				}
			}
		}
		return null;
	}
	
}

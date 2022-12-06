package cn.net.hylink.hydrogen.core.util;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

/**
 * 国际化工具类
 */
@Component
public class MessageUtil {

	private static MessageSource messageSource;

	public MessageUtil(MessageSource messageSource) {
		MessageUtil.messageSource = messageSource;
	}

	/**
	 * Description: 获取国际化翻译值<br> 
	 * Created date: 2020年5月4日
	 * @param code 配置code
	 * @return
	 * @author L
	 */
	public static String get(String code) {
		try {
			return messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
		} catch (Exception e) {
			return code;
		}
	}
	
	/**
	 * Description: 获取国际化翻译值<br> 
	 * Created date: 2020年5月4日
	 * @param code 配置code
	 * @param args 占位符参数
	 * @return
	 * @author L
	 */
	public static String get(String code, Object... args) {
		try {
			return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
		} catch (Exception e) {
			return code;
		}
	}
}

package cn.net.hylink.hydrogen.core.validator;

import com.baidu.unbiz.fluentvalidator.interceptor.FluentValidateInterceptor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Description: Fluent校验配置 拦截器<br>
 * Create Date: 2020年5月4日<br>
 * Modified By：<br>
 * Modified Date：<br>
 * Why & What is modified：<br>
 * 
 * @version 1.0
 */
@Configuration
public class FluentValidatorConfig {
	
	private static final String EXPRESSION = "@within(org.springframework.web.bind.annotation.RestController)";

	@Autowired
	private FluentValidatorCallback callback;

	@Bean
	public FluentValidateInterceptor fluentValidateInterceptor() {
		FluentValidateInterceptor validateInterceptor = new FluentValidateInterceptor();
		validateInterceptor.setCallback(callback);
		validateInterceptor.setLocale("zh_CN");
		validateInterceptor.setHibernateDefaultErrorCode(10000);
		return validateInterceptor;
	}
	
	@Bean
    public DefaultPointcutAdvisor fluentPointcut() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(EXPRESSION);
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
        advisor.setPointcut(pointcut);
        advisor.setAdvice(fluentValidateInterceptor());
        advisor.setOrder(100);
        return advisor;
    }
}

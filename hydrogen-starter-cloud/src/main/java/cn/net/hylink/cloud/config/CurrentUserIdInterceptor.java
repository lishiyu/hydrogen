package cn.net.hylink.cloud.config;

import cn.net.hylink.cloud.common.Constant;
import cn.net.hylink.hydrogen.mybatis.config.Common;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CurrentUserIdInterceptor implements HandlerInterceptor {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) {
        Common.CURRENT_USER_ID.set(request.getHeader(Constant.ID));
        return true;
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) {
        Common.CURRENT_USER_ID.remove();
    }



}

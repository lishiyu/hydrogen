package cn.net.hylink.hydrogen.mybatis.config;

import cn.net.hylink.hydrogen.core.result.ErrorResult;
import cn.net.hylink.hydrogen.core.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class DatabaseExceptionHandlerAdvice {

    /**
     * 处理DataAccessException异常
     */
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorResult> handleDataAccessException(DataAccessException e, HttpServletRequest request) {
        log.error("handleDataAccessException, uri:{}, exception:{}, caused by: {}", request.getRequestURI(),
                e.getClass(), e.getMessage(), e);
        ErrorResult result = ErrorResult.error(ResultCode.DATABASE_ERROR, e, HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(result, HttpStatus.valueOf(result.getStatus()));
    }
}

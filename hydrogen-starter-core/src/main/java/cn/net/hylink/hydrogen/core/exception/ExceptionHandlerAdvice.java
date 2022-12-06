package cn.net.hylink.hydrogen.core.exception;

import cn.net.hylink.hydrogen.core.result.ErrorResult;
import cn.net.hylink.hydrogen.core.result.ResultCode;
import com.alibaba.druid.support.http.StatViewServlet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerAdvice {

    /**
     * 处理Exception异常
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResult<?>> handleException(Exception e, HttpServletRequest request) {
        log.error("handleException, uri:{}, exception:{}, caused by: {}", request.getRequestURI(), e.getClass(),
                e.getMessage(), e);
        ErrorResult<?> result = ErrorResult.error(ResultCode.SYSTEM_INNER_ERROR, e, HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(result, HttpStatus.valueOf(result.getStatus()));
    }

    /**
     * 处理CommonException异常
     */
    @ExceptionHandler(CommonException.class)
    public ResponseEntity<ErrorResult<?>> handleCommonException(CommonException e, HttpServletRequest request) {
        log.error("handleCommonException, uri:{}, exception:{}, caused by: {}", request.getRequestURI(),
                e.getClass(), e.getMessage(), e);
        ErrorResult<?> result = ErrorResult.error(e);
        return new ResponseEntity<>(result, HttpStatus.valueOf(result.getStatus()));
    }

    /**
     * 处理MethodArgumentNotValidException异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResult<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        log.error("handleMethodArgumentNotValidException, uri:{}, exception:{}, caused by: {}", request.getRequestURI(),
                e.getClass(), e.getMessage(), e);
        List<ObjectError> objectErrors = e.getBindingResult().getAllErrors();
        List<String> errors = objectErrors.stream().map(ObjectError::getDefaultMessage).collect(Collectors.toList());
        ErrorResult<?> result = ErrorResult.error(ResultCode.PARAM_IS_INVALID, e, HttpStatus.INTERNAL_SERVER_ERROR, errors);
        return new ResponseEntity<>(result, HttpStatus.valueOf(result.getStatus()));
    }

    /**
     * 处理ConstraintViolationException异常
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResult<?>> handleConstraintViolationException(ConstraintViolationException e, HttpServletRequest request) {
        log.error("handleConstraintViolationException, uri:{}, exception:{}, caused by: {}", request.getRequestURI(),
                e.getClass(), e.getMessage(), e);
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        List<String> errors = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        ErrorResult<?> result = ErrorResult.error(ResultCode.PARAM_IS_INVALID, e, HttpStatus.INTERNAL_SERVER_ERROR, errors);
        return new ResponseEntity<>(result, HttpStatus.valueOf(result.getStatus()));
    }

    /**
     * 处理FluentValidException异常
     */
    @ExceptionHandler(FluentValidException.class)
    public ResponseEntity<ErrorResult<?>> handleFluentValidException(FluentValidException e, HttpServletRequest request) {
        log.error("handleFluentValidException, uri:{}, exception:{}, caused by: {}", request.getRequestURI(),
                e.getClass(), e.getMessage(), e);
        ErrorResult<?> result = ErrorResult.error(ResultCode.PARAM_IS_INVALID, e, HttpStatus.INTERNAL_SERVER_ERROR, e.getErrors());
        return new ResponseEntity<>(result, HttpStatus.valueOf(result.getStatus()));
    }

}

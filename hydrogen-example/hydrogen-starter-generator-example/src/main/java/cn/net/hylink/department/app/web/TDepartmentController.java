package cn.net.hylink.department.app.web;

import cn.net.hylink.department.pojo.po.TDepartmentPo;
import cn.net.hylink.hydrogen.core.result.Result;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import cn.net.hylink.department.service.TDepartmentService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 模块
 *
 * @author 李同学
 */
@Validated
@RestController
@RequestMapping("department")
public class TDepartmentController {

    @Resource
    private TDepartmentService service;

    @GetMapping("")
    public Result<List<TDepartmentPo>> test() {

        throw new DataAccessException("aaa") {
            @Override
            public String getMessage() {
                return super.getMessage();
            }
        };

    }

}

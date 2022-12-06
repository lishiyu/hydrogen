package cn.net.hylink.department.service.impl;

import cn.net.hylink.department.pojo.po.TDepartmentPo;
import cn.net.hylink.department.mapper.TDepartmentMapper;
import cn.net.hylink.department.service.TDepartmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 * @author 李同学
 */
@Service
public class TDepartmentServiceImpl extends ServiceImpl<TDepartmentMapper, TDepartmentPo> implements TDepartmentService {

    @Resource
    private TDepartmentMapper mapper;


}

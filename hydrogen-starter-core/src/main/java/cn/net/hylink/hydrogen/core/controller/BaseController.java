package cn.net.hylink.hydrogen.core.controller;

import cn.net.hylink.hydrogen.core.page.PageRequest;
import cn.net.hylink.hydrogen.core.page.PageUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 接口层基类
 */
public class BaseController {

    /**
     * Description: 构造分页参数<br>
     * Created date: 2020年5月3日
     * @param pageRequest 分页请求
     * @return 分页参数对象
     * @author L
     */
    public <P> IPage<P> getPageInfo(PageRequest pageRequest) {
        return PageUtil.getPageInfo(pageRequest);
    }

    /**
     * Description: 构造分页参数<br>
     * Created date: 2020年5月3日
     * @param current 当前页数
     * @param size 页大小
     * @return 分页参数对象
     * @author L
     */
    public <P> IPage<P> getPageInfo(long current, long size) {
        return PageUtil.getPageInfo(current, size);
    }
}

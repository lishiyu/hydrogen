package cn.net.hylink.hydrogen.mybatis.util;

import cn.hutool.core.util.PageUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

public class PageUtils {

    /**
     * 分页数据转换
     *
     * @param page     源分页对象
     * @param dataList 目标对象数据集
     * @return 转换数据类型后的分页对象
     */
    public static <E, T> IPage<T> convert(IPage<E> page, List<T> dataList) {
        Page<T> pageResult = new Page<>();
        pageResult.setCurrent(page.getCurrent());
        pageResult.setSize(page.getSize());
        pageResult.setTotal(page.getTotal());
        pageResult.setPages(PageUtil.totalPage((int) page.getTotal(), (int) page.getSize()));
        pageResult.setRecords(dataList);
        return pageResult;
    }
}

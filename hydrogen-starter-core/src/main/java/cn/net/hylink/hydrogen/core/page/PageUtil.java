package cn.net.hylink.hydrogen.core.page;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Collections;
import java.util.List;

public class PageUtil {

    /**
     * Description: 构造分页参数
     * @param pageRequest 分页请求
     * @return 分页参数对象
     */
    public static <P> IPage<P> getPageInfo(PageRequest pageRequest) {
        if (pageRequest == null) {
            return new Page<>(1, 10);
        }

        return new Page<>(pageRequest.getPageIndex(), pageRequest.getPageSize());
    }

    /**
     * Description: 构造分页参数
     * @param current 当前页数
     * @param size 页大小
     * @return 分页参数对象
     */
    public static <P> IPage<P> getPageInfo(long current, long size) {
        return new Page<>(current, size);
    }

    /**
     * Description: 逻辑分页
     * @param pageInfo 分页参数对象
     * @param list 参与分页的数据列表
     * @return 分页参数对象
     */
    public static <P> IPage<P> getLogicalPageInfo(IPage<P> pageInfo, List<P> list) {
        //清空分页结果数据
        pageInfo.setRecords(Collections.emptyList());
        //判断参加逻辑分页的数据参数是否合法
        if (list != null && list.size() != 0){
            //总数据条数
            int totalCount = list.size();
            //分页页数
            int pageCount;
            //每页数据条数
            int size = (int) pageInfo.getSize();
            //临时计算页数
            int m = totalCount % size;
            //当前页数
            int currentPage = (int) pageInfo.getCurrent();
            //计算分页页数
            if (m > 0) {
                pageCount = totalCount / size + 1;
            } else {
                pageCount = totalCount / size;
            }
            //分页起始位置
            int fromIndex = 0;
            //需要截取的位置
            int toIndex = 0;
            //如果当前页数小于0则置为1
            currentPage = (currentPage <= 0) ? 1 : currentPage;
            //如果当前页数大于最大页数则置为最大页数
            currentPage = (currentPage > pageCount) ? pageCount : currentPage;
            //计算分页起始位置
            fromIndex = (currentPage - 1) * size;
            if (currentPage == pageCount) {
                toIndex = list.size();
                //计算需要截取的位置
            } else {
                toIndex = currentPage * size;
            }
            pageInfo.setTotal(totalCount);
            //放入分页结果
            pageInfo.setRecords(list.subList(fromIndex, toIndex));
        }
        return pageInfo;
    }

    public static <T> PageVo<T> success(IPage<?> page, List<T> records) {
        PageVo<T> result = new PageVo<>();
        result.setRecords(records);
        result.setPage(page);
        return result;
    }

    public static <T> PageVo<T> empty(PageRequest pageRequest) {
        PageVo<T> result = new PageVo<>();
        result.setPage(new Page<>(pageRequest.getPageIndex(), pageRequest.getPageSize()));
        return result;
    }

}

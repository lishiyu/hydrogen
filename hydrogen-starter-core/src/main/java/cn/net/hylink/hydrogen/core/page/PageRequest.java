package cn.net.hylink.hydrogen.core.page;

import lombok.Data;

@Data
public class PageRequest {

    /**
     * 页大小
     */
    private long pageSize = 10;
    /**
     * 当前页数
     */
    private long pageIndex = 1;
}

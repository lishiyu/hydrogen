package cn.net.hylink.hydrogen.core.page;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.springframework.beans.factory.annotation.Value;

/**
 * 分页对象属性命名策略
 */
public class PagePropertyNamingStrategy extends PropertyNamingStrategy.PropertyNamingStrategyBase {

    private static final long serialVersionUID = -5989641972134253152L;

    /**
     * PageInfo.current 当前页数
     */
    protected static final String PAGE_CURRENT_ORI = "current";

    /**
     * PageInfo.size 页大小
     */
    protected static final String PAGE_SIZE_ORI = "size";

    /**
     * PageInfo.total 总条数
     */
    protected static final String PAGE_TOTAL_ORI = "total";

    /**
     * PageInfo.records 当前页数据
     */
    protected static final String PAGE_RECORDS_ORI = "records";

    /**
     * PageInfo.pages 总页数
     */
    protected static final String PAGE_PAGES_ORI = "pages";

    @Value("${common.page.current:" + PAGE_CURRENT_ORI + "}")
    private String current;

    @Value("${common.page.size:" + PAGE_SIZE_ORI + "}")
    private String size;

    @Value("${common.page.total:" + PAGE_TOTAL_ORI + "}")
    private String total;

    @Value("${common.page.records:" + PAGE_RECORDS_ORI + "}")
    private String records;

    @Value("${common.page.pages:" + PAGE_PAGES_ORI + "}")
    private String pages;

    @Override
    public String translate(String ori) {
        switch (ori) {
            case PAGE_CURRENT_ORI:
                return current;
            case PAGE_SIZE_ORI:
                return size;
            case PAGE_TOTAL_ORI:
                return total;
            case PAGE_RECORDS_ORI:
                return records;
            case PAGE_PAGES_ORI:
                return pages;
            default:
                return ori;
        }
    }
}

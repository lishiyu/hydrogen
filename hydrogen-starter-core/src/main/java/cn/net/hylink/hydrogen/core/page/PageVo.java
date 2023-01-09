package cn.net.hylink.hydrogen.core.page;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Collections;
import java.util.List;


@Getter
@ToString
@EqualsAndHashCode
public class PageVo<T> {

    private Info page;

    private List<T> records = Collections.emptyList();;

    public void setPage(IPage<?> page) {
        Info info = new Info();
        info.setPages(Integer.parseInt(String.valueOf(page.getPages())));
        info.setCurrent(Integer.parseInt(String.valueOf(page.getCurrent())));
        info.setSize(Integer.parseInt(String.valueOf(page.getSize())));
        info.setTotal(Integer.parseInt(String.valueOf(page.getTotal())));
        this.page = info;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }

    @Data
    private static class Info {
        /**
         * 当前页
         */
        private Integer current;
        /**
         * 每页显示数量
         */
        private Integer size;
        /**
         * 总数
         */
        private Integer total;
        /**
         * 总分页数
         */
        private Integer Pages;
    }

}

package cn.net.hylink.hydrogen.excel.handler;

import cn.hutool.core.collection.CollUtil;
import cn.net.hylink.hydrogen.excel.util.ExcelUtil;
import com.alibaba.excel.write.handler.RowWriteHandler;
import com.alibaba.excel.write.metadata.holder.*;
import org.apache.poi.ss.usermodel.*;

import java.util.Map;

/**
 * 自定义批注处理器
 */
public class CommentWriteHandler implements RowWriteHandler {

    private final Map<String, String> commentMap;

    /**
     * 自定义批注适配器构造方法
     */
    public CommentWriteHandler(Map<String, String> commentMap) {

        this.commentMap = commentMap;
    }

    @Override
    public void afterRowDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row,
                                Integer relativeRowIndex, Boolean isHead) {
        if (CollUtil.isEmpty(commentMap)) {
            return;
        }
        Sheet sheet = writeSheetHolder.getSheet();
        for (Map.Entry<String, String> entry : commentMap.entrySet()) {
            ExcelUtil.annotation(sheet, 0, entry.getKey(), entry.getValue());
        }
    }
}

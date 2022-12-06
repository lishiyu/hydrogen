package cn.net.hylink.hydrogen.excel.handler;

import cn.net.hylink.hydrogen.excel.util.ExcelUtil;
import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;

import java.util.Map;

/**
 * 下拉框拦截器
 */
public class SelectSheetWriteHandler implements SheetWriteHandler {
    private final Map<String, String[]> selectMap;
    public SelectSheetWriteHandler(Map<String, String[]> selectMap) {
        this.selectMap = selectMap;
    }

    @Override
    public void beforeSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
    }

    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
        if (selectMap == null || selectMap.size() == 0) {
            return;
        }
        // 需要设置下拉框的sheet页
        Sheet sheet = writeSheetHolder.getSheet();
        for (Map.Entry<String, String[]> entry : selectMap.entrySet()) {
            CellRangeAddressList regions = new CellRangeAddressList(1, 65533, ExcelUtil.colNameToIndex(entry.getKey()), ExcelUtil.colNameToIndex(entry.getKey()));
            DataValidationHelper dataValidationHelper = sheet.getDataValidationHelper();
            // 构造下拉框和数据
            DataValidationConstraint constraint = dataValidationHelper.createExplicitListConstraint(entry.getValue());
            // 绑定下拉框和区域
            DataValidation validation = dataValidationHelper.createValidation(constraint, regions);
            // 为sheet添加验证
            sheet.addValidationData(validation);
        }
    }
}
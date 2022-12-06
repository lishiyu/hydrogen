package cn.net.hylink.hydrogen.excel.util;

import cn.hutool.core.util.ObjectUtil;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public class ExcelUtil extends cn.hutool.poi.excel.ExcelUtil{



    public static void dropDown(Sheet sheet, String colName, String[] listOfValues) {
        CellRangeAddressList regions = new CellRangeAddressList(1, 65535, colNameToIndex(colName), colNameToIndex(colName));
        DataValidationHelper dataValidationHelper = sheet.getDataValidationHelper();
        // 构造下拉框和数据
        DataValidationConstraint constraint = dataValidationHelper.createExplicitListConstraint(listOfValues);
        // 绑定下拉框和区域
        DataValidation validation = dataValidationHelper.createValidation(constraint, regions);
        // 为sheet添加验证
        sheet.addValidationData(validation);
    }



    public static void annotation(Sheet sheet, int rowNum, int cellNum, String annotation) {
        Drawing draw = sheet.createDrawingPatriarch();
        Comment comment = null;
        if (sheet instanceof HSSFSheet){
            comment = draw.createCellComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 1, 3, (short) 3, 7));
            //设置批注内容
            comment.setString(new HSSFRichTextString(annotation));
        } else {
            comment = draw.createCellComment(new XSSFClientAnchor(0, 0, 0, 0, (short) 1, 3, (short) 3, 7));
            //设置批注内容
            comment.setString(new XSSFRichTextString(annotation));
        }
        comment.setAuthor("system");
        sheet.getRow(rowNum).getCell(cellNum).setCellComment(comment);
    }

    public static void annotation(Sheet sheet, int rowNum, String colName, String annotation) {
        annotation(sheet, rowNum, colNameToIndex(colName), annotation);
    }

    public static void primary(Workbook workbook, int firstRow, String colName, String[] strings){
        primary(workbook, firstRow, colNameToIndex(colName), strings);
    }

    public static void primary(Workbook workbook, int firstRow, int col, String[] strings){
        Cell cell = null;
        Sheet sheet = workbook.getSheetAt(0);
        Sheet hidden = workbook.getSheetAt(1);
        Row row = null;
        for (int i = 0, length = strings.length; i < length; i++){
            //取出数组中的每个元素
            String name = strings[i];
            //根据i创建相应的行对象（说明我们将会把每个元素单独放一行）
            row = hidden.getRow(i);
            if (ObjectUtil.isNull(row)) {
                row = hidden.createRow(i);
            }
            cell = row.createCell(col);
            //然后将数组中的元素赋值给这个单元格
            cell.setCellValue(name);
        }
        String colName = indexToColName(col);
        String name = hidden.getSheetName() + col;
        Name namedCell = workbook.createName();
        namedCell.setNameName(name);
        namedCell.setRefersToFormula(hidden.getSheetName() + "!$" + colName + "$1:$" + colName + "$" + strings.length);
        CellRangeAddressList addressList = new CellRangeAddressList(firstRow, 65535, col, col);
        DataValidation validation = null;
        DataValidationConstraint constraint = null;
        if (sheet instanceof XSSFSheet || sheet instanceof SXSSFSheet) {
            DataValidationHelper dvHelper = sheet.getDataValidationHelper();
            constraint = dvHelper.createFormulaListConstraint(name);
            validation = dvHelper.createValidation(constraint, addressList);
        } else {
            constraint = DVConstraint.createFormulaListConstraint(name);
            validation = new HSSFDataValidation(addressList, constraint);
        }
        workbook.setSheetHidden(1, true);
        workbook.getSheetAt(0).addValidationData(validation);
    }
}

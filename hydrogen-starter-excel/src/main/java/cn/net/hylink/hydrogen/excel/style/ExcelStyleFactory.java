package cn.net.hylink.hydrogen.excel.style;


public class ExcelStyleFactory {


    public static ExcelStyleBuilder styleBuilder() {

        return new ExcelStyleBuilder();
    }


    public static WriteFontBuilder writeFontBuilder() {

        return new WriteFontBuilder();
    }

}

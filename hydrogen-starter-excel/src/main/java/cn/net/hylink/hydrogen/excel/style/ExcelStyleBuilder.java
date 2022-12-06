package cn.net.hylink.hydrogen.excel.style;

import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import org.apache.poi.ss.usermodel.VerticalAlignment;

public class ExcelStyleBuilder {

    private final WriteCellStyle writeCellStyle;

    public ExcelStyleBuilder() {
        writeCellStyle = new WriteCellStyle();
    }

    public ExcelStyleBuilder foregroundColor(Short foregroundColor) {
        writeCellStyle.setFillForegroundColor(foregroundColor);
        return this;
    }

    public ExcelStyleBuilder writeFont(WriteFont writeFont) {
        writeCellStyle.setWriteFont(writeFont);
        return this;
    }

    public ExcelStyleBuilder wrapped(boolean wrapped) {
        writeCellStyle.setWrapped(wrapped);
        return this;
    }

    public ExcelStyleBuilder verticalAlignment(VerticalAlignment verticalAlignment) {
        writeCellStyle.setVerticalAlignment(verticalAlignment);
        return this;
    }


    public WriteCellStyle build() {
        return writeCellStyle;
    }
}

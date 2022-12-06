package cn.net.hylink.hydrogen.excel.style;

import com.alibaba.excel.write.metadata.style.WriteFont;

public class WriteFontBuilder {

    private final WriteFont writeFont;

    public WriteFontBuilder() {
        writeFont = new WriteFont();
    }

    public WriteFontBuilder fontHeightInPoints(Short fontHeightInPoints) {
        writeFont.setFontHeightInPoints(fontHeightInPoints);
        return this;
    }

    public WriteFont build() {
        return writeFont;
    }
}

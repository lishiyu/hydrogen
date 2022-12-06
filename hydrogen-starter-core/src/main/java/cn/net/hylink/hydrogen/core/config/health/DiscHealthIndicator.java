package cn.net.hylink.hydrogen.core.config.health;

import cn.hutool.core.util.NumberUtil;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import oshi.SystemInfo;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiscHealthIndicator extends AbstractHealthIndicator {

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {

        SystemInfo si = new SystemInfo();
        OperatingSystem os = si.getOperatingSystem();
        FileSystem fileSystem = os.getFileSystem();
        List<OSFileStore> fsArray = fileSystem.getFileStores();
        for (OSFileStore fs : fsArray) {
            long free = fs.getUsableSpace();
            long total = fs.getTotalSpace();
            long used = total - free;

            Map<String, Object> detailMap = new HashMap<>();
            String devName = fs.getMount(); // 盘符名称
            String sysTypeName = fs.getType(); // 盘符文件系统类型
            if ("overlay".equals(sysTypeName)) {
                continue;
            }
            detailMap.put("devName", devName);
            detailMap.put("sysTypeName", sysTypeName);
            // TYPE_LOCAL_DISK : 本地硬盘
            detailMap.put("total", convertFileSize(total));// 文件系统总大小
            detailMap.put("free", convertFileSize(free));// 文件系统剩余大小
            detailMap.put("used", convertFileSize(used));// 文件系统已经使用量
            detailMap.put("usePercent", NumberUtil.round(NumberUtil.div(used, total, 4), 100).doubleValue());// 文件系统资源的利用率
            builder.withDetail(devName, detailMap);
        }
        builder.up();
    }

    /**
     * 字节转换
     */
    public String convertFileSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;
        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else {
            return String.format("%d B", size);
        }
    }
}


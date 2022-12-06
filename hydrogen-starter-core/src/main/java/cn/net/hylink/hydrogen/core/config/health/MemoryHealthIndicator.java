package cn.net.hylink.hydrogen.core.config.health;

import cn.hutool.core.util.NumberUtil;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import oshi.SystemInfo;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;

public class MemoryHealthIndicator extends AbstractHealthIndicator {

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();
        GlobalMemory memory = hal.getMemory();
        // 内存总量
        double total = memory.getTotal();
        // 已用内存
        double used = memory.getTotal() - memory.getAvailable();
        builder.withDetail("total", memory.getTotal() / 1024L / 1024L + "M")
                .withDetail("used", (memory.getTotal() - memory.getAvailable()) / 1024L / 1024L + "M")
                .withDetail("free", memory.getAvailable() / 1024L / 1024L + "M")
                .withDetail("usedRate",String.format("%.2f", NumberUtil.mul(NumberUtil.div(used, total, 4), 100))+'%')
                .up();
    }
}

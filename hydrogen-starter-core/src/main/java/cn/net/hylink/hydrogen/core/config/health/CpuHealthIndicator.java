package cn.net.hylink.hydrogen.core.config.health;

import cn.hutool.core.util.NumberUtil;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health.*;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor.*;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.util.Util;

public class CpuHealthIndicator extends AbstractHealthIndicator {

    private static final int OSHI_WAIT_SECOND = 1000;

    @Override
    protected void doHealthCheck(Builder builder) throws Exception {
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();
        CentralProcessor processor = hal.getProcessor();

        /*
         * CPU信息
         */
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        Util.sleep(OSHI_WAIT_SECOND);
        long[] ticks = processor.getSystemCpuLoadTicks();
        long nice = ticks[TickType.NICE.getIndex()] - prevTicks[TickType.NICE.getIndex()];
        long irq = ticks[TickType.IRQ.getIndex()] - prevTicks[TickType.IRQ.getIndex()];
        long softIrq = ticks[TickType.SOFTIRQ.getIndex()] - prevTicks[TickType.SOFTIRQ.getIndex()];
        long steal = ticks[TickType.STEAL.getIndex()] - prevTicks[TickType.STEAL.getIndex()];
        long cSys = ticks[TickType.SYSTEM.getIndex()] - prevTicks[TickType.SYSTEM.getIndex()];
        long user = ticks[TickType.USER.getIndex()] - prevTicks[TickType.USER.getIndex()];
        long ioWait = ticks[TickType.IOWAIT.getIndex()] - prevTicks[TickType.IOWAIT.getIndex()];
        long idle = ticks[TickType.IDLE.getIndex()] - prevTicks[TickType.IDLE.getIndex()];
        long totalCpu = user + nice + cSys + idle + ioWait + irq + softIrq + steal;
        /*
         * CPU总的使用率
         */
        /*
         * CPU系统使用率
         */
        double sys = NumberUtil.round(NumberUtil.mul(cSys / (double) totalCpu, 100), 2).doubleValue();
        /*
         * CPU用户使用率
         */
        double used = NumberUtil.round(NumberUtil.mul(user / (double) totalCpu, 100), 2).doubleValue();
        /*
         * CPU当前等待率
         */
        double wait = NumberUtil.round(NumberUtil.mul(ioWait / (double) totalCpu, 100), 2).doubleValue();
        /*
         * CPU当前空闲率
         */
        double free = NumberUtil.round(NumberUtil.mul(idle / (double) totalCpu, 100), 2).doubleValue();
        /*
         * CPU当前错误率
         */
        double niceN = NumberUtil.round(NumberUtil.mul(nice / (double) totalCpu, 100), 2).doubleValue();

        builder.withDetail("user", String.format("%.2f", used) + '%')
                .withDetail("sys", String.format("%.2f", sys) + '%')
                .withDetail("wait", String.format("%.2f", wait) + '%')
                .withDetail("nice", String.format("%.2f", niceN) + '%')
                .withDetail("idle", String.format("%.2f", free) + '%')
                .withDetail("combined", String.format("%.2f", (used + sys)) + '%').up();

    }
}

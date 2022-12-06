package cn.net.hylink.hydrogen.core.config.health;

import cn.hutool.core.util.StrUtil;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.NetworkIF;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MacHealthIndicator extends AbstractHealthIndicator {

    @Override
    protected void doHealthCheck(Health.Builder builder) {
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();
        List<NetworkIF> networkIFs = hal.getNetworkIFs();
        for (NetworkIF net : networkIFs) {
            StringBuffer address = new StringBuffer();
            for (String s : net.getIPv4addr()) {
                address.append(s);
            }
            if (StrUtil.isBlank(address)) {
                continue;
            }
            net.getIPv4addr();
            if ("00:00:00:00:00:00".equals(net.getMacaddr())) {
                continue;
            }
            Map<String, Object> detailMap = new HashMap<>();
            detailMap.put("ipAddr", net.getIPv4addr());// IP地址
            detailMap.put("macAddr", net.getMacaddr());// 网卡MAC地址
            builder.withDetail(net.getName(), detailMap);
        }
        builder.up();
    }

}

package cn.net.hylink;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@MapperScan(basePackages = {"cn.net.hylink.**.mapper"})
@SpringBootApplication(scanBasePackages = {"cn.net.hylink"})
public class HydrogenStarterGeneratorExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(HydrogenStarterGeneratorExampleApplication.class, args);
        log.info("启动成功");
    }

}

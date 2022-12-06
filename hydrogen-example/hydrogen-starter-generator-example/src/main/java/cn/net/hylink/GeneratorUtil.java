package cn.net.hylink;

import cn.net.hylink.hydrogen.generator.HydrogenGenerator;

public class GeneratorUtil {
    public static void main(String[] args) {
        HydrogenGenerator.get()
                .setAuthor("李同学")
                .setUrl("jdbc:postgresql://192.168.3.249:5432/qzyqd")
                .setUsername("postgres")
                .setPassword("Hylink2014@postgres")
                .setSchemaName("integrated")
                .generateMVC();
    }
}

package cn.net.hylink.hydrogen.mybatis.config;

import cn.net.hylink.hydrogen.mybatis.base.BasicPo;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Slf4j
@Configuration
public class MybatisPlusConfig {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.POSTGRE_SQL));
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        //interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        return interceptor;
    }

    @Bean
    public MetaObjectHandler metaObjectHandler() {

        return new MetaObjectHandler() {
            @Override
            public void insertFill(MetaObject metaObject) {
                Object operatorId = operatorId();
                this.setFieldValByName(BasicPo.Fields.updatedAt, LocalDateTime.now(), metaObject);
                this.setFieldValByName(BasicPo.Fields.createdAt, LocalDateTime.now(), metaObject);
                this.setFieldValByName(BasicPo.Fields.deleted, 0, metaObject);
                this.setFieldValByName(BasicPo.Fields.createdBy, operatorId, metaObject);
                this.setFieldValByName(BasicPo.Fields.updatedBy, operatorId, metaObject);
            }

            @Override
            public void updateFill(MetaObject metaObject) {
                this.setFieldValByName(BasicPo.Fields.updatedAt, LocalDateTime.now(), metaObject);
                this.setFieldValByName(BasicPo.Fields.updatedBy, operatorId(), metaObject);
            }

            private Object operatorId() {
                try {
                    return Long.valueOf(Common.CURRENT_USER_ID.get());
                } catch (NumberFormatException e) {
                    log.error("错误信息：{}", e.getMessage(), e);
                    return Common.CURRENT_USER_ID.get();
                }
            }
        };
    }

}

package cn.ctcc.quartzjobmanager.config.mybatis;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @Author: zk
 * @Date: 2019/6/17 14:15
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@Configuration
//@MapperScan("com.baomidou.springboot.mapper*")//这个注解，作用相当于下面的@Bean MapperScannerConfigurer，2者配置1份即可
public class MybatisPlusConfig {

    /**
     * mybatis-plus分页插件<br>
     * 文档：http://mp.baomidou.com<br>
     *
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor page = new PaginationInterceptor();
        // 开启 PageHelper 的支持    3.0-RELEASE 版本已没有这个setLocalPage属性了
        //page.setLocalPage(true);
        // 用来解决pages和total都为0的问题---gamma版本已经没有这个问题了
        page.setDialectType("mysql");
        return page;
    }

    /**
     * 相当于顶部的：
     * {@code @MapperScan("com.houpu.springboot.module.*.mapper*")}
     * 这里可以扩展，比如使用配置文件来配置扫描Mapper的路径 @Mapper可以替代
     * 用这个的原因是：逆向生成的代码没有@Mapper注解
     */
    //@Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer scannerConfigurer = new MapperScannerConfigurer();
        scannerConfigurer.setBasePackage("cn.ctyun.**,com.baomidou");
        return scannerConfigurer;
    }

    /**
     * mybatis-plus SQL执行效率插件【生产环境可以关闭】--还可以查看sql
     */
    @Bean
    @Profile({"dev","test"})// 设置 dev test 环境开启
    public PerformanceInterceptor performanceInterceptor() {
        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
        /*<!-- maxTime SQL 执行最大时长，超过自动停止运行，有助于发现问题。 maxTime 指的是 sql 最大执行时长 -->*/
        //performanceInterceptor.setMaxTime(1000);
        /*<!--SQL是否格式化 默认false-->*/
        //performanceInterceptor.setFormat(true);
        return performanceInterceptor;
    }


}

package cn.ctcc.quartzjobmanager.mp;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.config.rules.PropertyInfo;

/**
 * @Auther: zhangkui
 * @Date: 2018/7/19 13:59
 * @Description: MP代码生成器,只需要改数据库连接，表名，可生成后台三层代码
 */
public class MpGenerator {

    /**
     * <p>
     * MySQL 生成演示
     * </p>
     */
    public static void main(String[] args) {

        AutoGenerator mpg = new AutoGenerator();
        // 选择 freemarker 引擎，默认 Veloctiy
        //mpg.setTemplateEngine(new FreemarkerTemplateEngine());

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        //C:/mySofts/2018-08-24-project/flpaas/busi-portal/src/main/java
        gc.setOutputDir("E:/java");
        gc.setFileOverride(true);
        gc.setActiveRecord(false);// 不需要ActiveRecord特性的请改为false,个人不建议使用AR模式，因为违背了javaBean的意义
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(false);// XML ResultMap,可以不要，我们尽量保持实体字段和数据库字段一致，尽量都驼峰命名
        gc.setBaseColumnList(false);// XML columList
        // .setKotlin(true) 是否生成 kotlin 代码
        gc.setAuthor("zhangkui");

        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setControllerName("%sController");
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setTypeConvert(new MySqlTypeConvert() {
            // 自定义数据库表字段类型转换【可选】
            @Override
            public PropertyInfo processTypeConvert(GlobalConfig globalConfig, String fieldType) {
                System.out.println("转换类型：" + fieldType);
                //测试结果：并没什么作用
                if("java.time.LocalDateTime".equals(fieldType)){
                    fieldType="java.util.Date";
                }
                // 注意！！processTypeConvert 存在默认类型转换，如果不是你要的效果请自定义返回、非如下直接返回。
                return super.processTypeConvert(gc, fieldType);
            }
        });
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");
        dsc.setUrl("jdbc:mysql://localhost:3306/quartz?useSSL=true&useUnicode=true&characterEncoding=utf-8");
        mpg.setDataSource(dsc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        // strategy.setCapitalMode(true);// 数据库大写下划线转换 ORACLE 注意
        //strategy.setTablePrefix(new String[]{"tb_"});// 此处可以修改为您的表前缀
        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
        strategy.setInclude(new String[]{"t_task"}); // 需要生成的表
        // strategy.setExclude(new String[]{"test"}); // 排除生成的表
        // 自定义实体父类
        // strategy.setSuperEntityClass("com.baomidou.demo.TestEntity");
        // 自定义实体，公共字段
        // strategy.setSuperEntityColumns(new String[] { "test_id", "age" });
        // 自定义 mapper 父类
        //strategy.setSuperMapperClass("com.baomidou.mybatisplus.core.mapper.BaseMapper");
        // 自定义 service 父类
        //strategy.setSuperServiceClass("com.baomidou.mybatisplus.extension.service.IService");
        // 自定义 service 实现类父类
        //strategy.setSuperServiceImplClass("com.baomidou.mybatisplus.extension.service.impl.ServiceImpl");
        // 自定义 controller 父类
        // strategy.setSuperControllerClass("com.baomidou.demo.TestController");
        // 【实体】是否生成字段常量（默认 false）
        // public static final String ID = "test_id";
           strategy.setEntityColumnConstant(false);
        // 【实体】是否为构建者模型（默认 false）
        // public User setName(String name) {this.name = name; return this;}
        // strategy.setEntityBuilderModel(true);
        mpg.setStrategy(strategy);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("cn.ctcc.quartzjobmanager");
        pc.setModuleName("task");
        mpg.setPackageInfo(pc);

        // 注入自定义配置，可以在 VM 中使用 cfg.abc 【可无】
//        InjectionConfig cfg = new InjectionConfig() {
//            @Override
//            public void initMap() {
//                Map<String, Object> map = new HashMap<String, Object>();
//                map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
//                this.setMap(map);
//            }
//        };

        // 自定义 xxList.ftl 生成
//        List<FileOutConfig> focList = new ArrayList<FileOutConfig>();
//        focList.add(new FileOutConfig("/templates/list.html.vm") {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                // 自定义输入文件名称
//                return "D://com//ctcc//module//" + tableInfo.getEntityName() + ".ftl";
//            }
//        });
//        cfg.setFileOutConfigList(focList);
//        mpg.setCfg(cfg);

        // 自定义  xxAdd.html 生成
//        focList.add(new FileOutConfig("/templates/add.html.vm") {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                return "D://com//ctcc//module//" + tableInfo.getEntityName() + ".ftl";
//            }
//        });
//        cfg.setFileOutConfigList(focList);
//        mpg.setCfg(cfg);

        // 定义 xxUpdate.html生成
//        focList.add(new FileOutConfig("/templates/update.html.vm") {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                return "D://com//ctcc//module//" + tableInfo.getEntityName() + ".ftl";
//            }
//        });
//        cfg.setFileOutConfigList(focList);
//        mpg.setCfg(cfg);

        // 关闭默认 xml 生成，调整生成 至 根目录
//        TemplateConfig tc = new TemplateConfig();
//        tc.setXml(null);
//        mpg.setTemplate(tc);

        // 自定义模板配置，可以 copy 源码 mybatis-plus/src/main/resources/templates 下面内容修改，
        // 放置自己项目的 src/main/resources/templates 目录下, 默认名称一下可以不配置，也可以自定义模板名称
//         TemplateConfig tc = new TemplateConfig();
//         tc.setController("/templates/controller.java.vm");
//         tc.setService("/templates/service.java.vm");
//         tc.setServiceImpl("/templates/serviceImpl.java.vm");
//         tc.setEntity("/templates/entity.java.vm");
//         tc.setMapper("/templates/mapper.java.vm");
//         tc.setXml("/templates/mapper.xml.vm");
        //如上任何一个模块如果设置 空 OR Null 将不生成该模块。
//         mpg.setTemplate(tc);

        // 执行生成
        mpg.execute();

    }


}

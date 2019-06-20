package cn.ctcc.quartzjobmanager.config.druid;

import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @Author: zk
 * @Date: 2019/2/27 17:09
 * @Description: druid数据库连接池配置
 * @Modified:
 * @version: V1.0
 */
@Configuration
public class DruidDataSourceConfig {


    private Logger logger= LoggerFactory.getLogger(DruidDataSourceConfig.class);

    /**
     *  jdbc Url
     */
    @Value("${spring.datasource.url}")
    private String dbUrl;

    /**
     * jdbc username
     */
    @Value("${spring.datasource.username}")
    private String username;

    /**
     * jdbc password
     */
    @Value("${spring.datasource.password}")
    private String password;

    /**
     * jdbc driverClassName
     */
    @Value("${spring.datasource.driverClassName}")
    private String driverClassName;

    /**
     * 初始化连接池的连接数量大小
     */
    @Value("${spring.datasource.initialSize}")
    private int initialSize;

    /**
     * 最小连接数
     */
    @Value("${spring.datasource.minIdle}")
    private int minIdle;

    /**
     * 最大连接数
     */
    @Value("${spring.datasource.maxActive}")
    private int maxActive;

    /**
     * 获取连接的超时时间
     */
    @Value("${spring.datasource.maxWait}")
    private int maxWait;

    /**
     * 配置多长时间进行一次检测，检测需要关闭的空闲连接，单位是毫秒
     */
    @Value("${spring.datasource.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;

    /**
     *配置连接池的连接的最小存活时间，单位是毫秒
     */
    @Value("${spring.datasource.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;

    /**
     * 用来验证数据库连接的查询语句，这个查询语句必须是至少返回一条数据的SELECT语句
     */
    @Value("${spring.datasource.validationQuery}")
    private String validationQuery;

    /**
     * 每隔一定的时间，测试连接是否可用，如果不可用，毁掉。销毁连接后，连接数量就少了，如果小于minIdle数量，就新建连接。防止取到的连接不可用
     */
    @Value("${spring.datasource.testWhileIdle}")
    private boolean testWhileIdle;

    /**
     * false表示每次从连接池中取出连接时，不需要执行validationQuery = "SELECT 1" 中的SQL进行测试。若配置为true,对性能有非常大的影响，性能会下降7-10倍。所在一定要配置为false.
     */
    @Value("${spring.datasource.testOnBorrow}")
    private boolean testOnBorrow;

    /**
     * 指明是否在归还到池中前进行检验。注意，生产环境请务必概为false
     */
    @Value("${spring.datasource.testOnReturn}")
    private boolean testOnReturn;

    /**
     * 打开PSCache
     */
    @Value("${spring.datasource.poolPreparedStatements}")
    private boolean poolPreparedStatements;

    /**
     * 每个连接上PSCache的大小
     */
    @Value("${spring.datasource.maxPoolPreparedStatementPerConnectionSize}")
    private int maxPoolPreparedStatementPerConnectionSize;

    /**
     * 配置监控统计拦截的filters，去掉后监控界面sql无法统计，'wall'用于防火墙
     */
    @Value("${spring.datasource.filters}")
    private String filters;

    /**
     * 合并多个DruidDataSource的监控数据
     */
    @Value("${spring.datasource.connectionProperties}")
    private String connectionProperties;

  /**
    * @Author: zk
    * @Date:  2019/2/27 17:09
    * @Param
    * @Return:
    * @Throws
    * @Description: 声明其为Bean实例,不指明数据源的情况下，优先使用带@Primary注解的数据源
    */
    @Bean
    @Primary
    public DataSource dataSource(){
        DruidDataSource datasource = new DruidDataSource();

        datasource.setUrl(this.dbUrl);
        datasource.setUsername(username);
        datasource.setPassword(password);
        datasource.setDriverClassName(driverClassName);

        //configuration
        datasource.setInitialSize(initialSize);
        datasource.setMinIdle(minIdle);
        datasource.setMaxActive(maxActive);
        datasource.setMaxWait(maxWait);
        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        datasource.setValidationQuery(validationQuery);
        datasource.setTestWhileIdle(testWhileIdle);
        datasource.setTestOnBorrow(testOnBorrow);
        datasource.setTestOnReturn(testOnReturn);
        datasource.setPoolPreparedStatements(poolPreparedStatements);
        datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        datasource.setConnectionProperties(connectionProperties);

        try {
            datasource.setFilters(filters);
            //启动项目的时候，初始化连接池，防止第一次查询再初始化datasource导致慢查询
            datasource.init();
        } catch (SQLException e) {
            logger.error("druid configuration initialization filter", e);
        }

        return datasource;
    }

}

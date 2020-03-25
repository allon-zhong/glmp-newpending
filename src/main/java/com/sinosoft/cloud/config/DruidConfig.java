package com.sinosoft.cloud.config;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.transaction.UserTransaction;
import java.util.Properties;

/**
 * Druid配置
 *
 *
 */
@Configuration
public class DruidConfig {

    @NacosValue(value = "${spring.datasource.druid.ZTDB.url:}",autoRefreshed = true)
    private String zturl;
    @NacosValue(value = "${spring.datasource.druid.ZTDB.username:}",autoRefreshed = true)
    private String ztusername;
    @NacosValue(value = "${spring.datasource.druid.ZTDB.password:}",autoRefreshed = true)
    private String ztpassword;
    @NacosValue(value = "${spring.datasource.druid.ZTDB.driverClassName:}",autoRefreshed = true)
    private String ztdriverClassName;
    @NacosValue(value = "${spring.datasource.druid.HXDB.url:}",autoRefreshed = true)
    private String hxurl;
    @NacosValue(value = "${spring.datasource.druid.HXDB.username:}",autoRefreshed = true)
    private String hxusername;
    @NacosValue(value = "${spring.datasource.druid.HXDB.password:}",autoRefreshed = true)
    private String hxpassword;
    @NacosValue(value = "${spring.datasource.druid.HXDB.driverClassName:}",autoRefreshed = true)
    private String hxdriverClassName;

    @NacosValue(value = "${spring.datasource.druid.type:}",autoRefreshed = true)
    private String type;
    @NacosValue(value = "${spring.datasource.druid.initialSize:}",autoRefreshed = true)
    private Integer initialSize;
    @NacosValue(value = "${spring.datasource.druid.minIdle:}",autoRefreshed = true)
    private Integer minIdle;
    @NacosValue(value = "${spring.datasource.druid.maxActive:}",autoRefreshed = true)
    private Integer maxActive;
    @NacosValue(value = "${spring.datasource.druid.maxWait:}",autoRefreshed = true)
    private Integer maxWait;
    @NacosValue(value = "${spring.datasource.druid.timeBetweenEvictionRunsMillis:}",autoRefreshed = true)
    private Integer timeBetweenEvictionRunsMillis;
    @NacosValue(value = "${spring.datasource.druid.minEvictableIdleTimeMillis:}",autoRefreshed = true)
    private Integer minEvictableIdleTimeMillis;
    @NacosValue(value = "${spring.datasource.druid.validationQuery:}",autoRefreshed = true)
    private String validationQuery;
    @NacosValue(value = "${spring.datasource.druid.testWhileIdle:}",autoRefreshed = true)
    private Boolean testWhileIdle;
    @NacosValue(value = "${spring.datasource.druid.testOnBorrow:}",autoRefreshed = true)
    private Boolean testOnBorrow;
    @NacosValue(value = "${spring.datasource.druid.testOnReturn:}",autoRefreshed = true)
    private Boolean testOnReturn;
    @NacosValue(value = "${spring.datasource.druid.poolPreparedStatements:}",autoRefreshed = true)
    private Boolean poolPreparedStatements;
    @NacosValue(value = "${spring.datasource.druid.maxPoolPreparedStatementPerConnectionSize:}",autoRefreshed = true)
    private Integer maxPoolPreparedStatementPerConnectionSize;
    @NacosValue(value = "${spring.datasource.druid.filters:}",autoRefreshed = true)
    private String filters;


    @Bean(name = "HXDataSource")
    @Primary
    @Autowired
    public AtomikosDataSourceBean systemDataSource(Environment env) {
        AtomikosDataSourceBean ds = new AtomikosDataSourceBean();
        Properties prop = hxbuild();
        ds.setXaDataSourceClassName("com.alibaba.druid.pool.xa.DruidXADataSource");
        ds.setUniqueResourceName("HXDB");
        ds.setPoolSize(5);
        ds.setXaProperties(prop);
        return ds;
    }

    @Autowired
    @Bean(name = "ZTDataSource")
    public AtomikosDataSourceBean businessDataSource(Environment env) {

        AtomikosDataSourceBean ds = new AtomikosDataSourceBean();
        Properties prop = ztbuild();
        ds.setXaDataSourceClassName("com.alibaba.druid.pool.xa.DruidXADataSource");
        ds.setUniqueResourceName("ZTDB");
        ds.setPoolSize(5);
        ds.setXaProperties(prop);

        return ds;
    }


    /**
     * 注入事物管理器
     * @return
     */
    @Bean(name = "xatx")
    public JtaTransactionManager regTransactionManager () {
        UserTransactionManager userTransactionManager = new UserTransactionManager();
        UserTransaction userTransaction = new UserTransactionImp();
        return new JtaTransactionManager(userTransaction, userTransactionManager);
    }

    private Properties ztbuild() {

        Properties prop = new Properties();
        prop.put("url", zturl);
        prop.put("username", ztusername);
        prop.put("password", ztpassword);
        prop.put("driverClassName", ztdriverClassName);
        Properties commonbuild = commonbuild(prop);
        return commonbuild;
    }
    private Properties hxbuild() {

        Properties prop = new Properties();
        prop.put("url", hxurl);
        prop.put("username", hxusername);
        prop.put("password", hxpassword);
        prop.put("driverClassName", hxdriverClassName);
        Properties commonbuild = commonbuild(prop);
        return commonbuild;
    }
    private Properties commonbuild(Properties prop) {

        prop.put("initialSize", initialSize);
        prop.put("maxActive", maxActive);
        prop.put("minIdle", minIdle);
        prop.put("maxWait", maxWait);
        prop.put("poolPreparedStatements", poolPreparedStatements);

        prop.put("maxPoolPreparedStatementPerConnectionSize",
                maxPoolPreparedStatementPerConnectionSize);

        prop.put("maxPoolPreparedStatementPerConnectionSize",
                maxPoolPreparedStatementPerConnectionSize);
        prop.put("validationQuery", validationQuery);
        prop.put("testOnBorrow", testOnBorrow);
        prop.put("testOnReturn", testOnReturn);
        prop.put("testWhileIdle", testWhileIdle);
        prop.put("timeBetweenEvictionRunsMillis",
                timeBetweenEvictionRunsMillis);
        prop.put("minEvictableIdleTimeMillis", minEvictableIdleTimeMillis);
        return prop;
    }

    /*@Bean
    public ServletRegistrationBean druidServlet() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");

        //控制台管理用户，加入下面2行 进入druid后台就需要登录
        //servletRegistrationBean.addInitParameter("loginUsername", "admin");
        //servletRegistrationBean.addInitParameter("loginPassword", "admin");
        return servletRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        filterRegistrationBean.addInitParameter("profileEnable", "true");
        return filterRegistrationBean;
    }

    @Bean
    public StatFilter statFilter(){
        StatFilter statFilter = new StatFilter();
        statFilter.setLogSlowSql(true); //slowSqlMillis用来配置SQL慢的标准，执行时间超过slowSqlMillis的就是慢。
        statFilter.setMergeSql(true); //SQL合并配置
        statFilter.setSlowSqlMillis(1000);//slowSqlMillis的缺省值为3000，也就是3秒。
        return statFilter;
    }

    @Bean
    public WallFilter wallFilter(){
        WallFilter wallFilter = new WallFilter();
        //允许执行多条SQL
        WallConfig config = new WallConfig();
        config.setMultiStatementAllow(true);
        wallFilter.setConfig(config);
        return wallFilter;
    }*/




}

package com.sinosoft.cloud;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@Slf4j
@EnableDiscoveryClient
@ComponentScan({"com.sinosoft"})
@NacosPropertySource(dataId="glmp-newpending",autoRefreshed = true)
@SpringBootApplication
public class CloudNewpendingMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudNewpendingMicroserviceApplication.class, args);
        log.info("新照会服务启动成功!");
    }

}

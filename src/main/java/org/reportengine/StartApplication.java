package org.reportengine;

import org.reportengine.properties.FileStorageProperties;
import org.reportengine.properties.InfluxDbProps;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EntityScan(basePackageClasses = {
        StartApplication.class
})
@EnableConfigurationProperties({
        FileStorageProperties.class,
        InfluxDbProps.class
})
@SpringBootApplication
public class StartApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(StartApplication.class);
        app.run(args);
    }
}

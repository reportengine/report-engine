package org.re.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(prefix = "file")
@Data
public class FileStorageProperties {

    private String uploadRoot;

    private String suiteLogs;
    
    private String tmp;

}
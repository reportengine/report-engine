package org.reportengine.properties;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "file")
@Data
public class FileStorageProperties {

    private String uploadRoot;

    private String suiteLogs;

}
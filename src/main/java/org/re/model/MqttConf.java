package org.re.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.ToString;

@Component
@Data
@ConfigurationProperties("mqtt.broker")
@ToString(exclude = { "password" })
public class MqttConf {
    private String host;
    private Integer port;
    private String user;
    private String password;

    public String getHostUrl() {
        return String.format("tcp://%s:%d", this.host, this.port);
    }
}

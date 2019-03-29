package org.re.handler;

import org.re.BeanUtil;
import org.re.model.MqttConf;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/system")
public class SystemHandler {

    @GetMapping("/mqttconfig")
    public MqttConf getMqttSettings() {
        return BeanUtil.getBean(MqttConf.class);
    }

}

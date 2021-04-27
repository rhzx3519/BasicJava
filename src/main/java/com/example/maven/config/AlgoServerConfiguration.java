package com.example.maven.config;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author ZhengHao Lou
 * @date 2020/8/26
 */
@Configuration
@Slf4j
public class AlgoServerConfiguration {

    public class AlgoConfig {

        @Value("#{'${apollo.bootstrap.namespaces}'.split(',')}")
        private List<String> namespaces;

        private Config apolloConfig;

        @Getter
        private boolean quoteSwitch;

        // 添加配置改动监听
        private void someOnChange(ConfigChangeEvent changeEvent) {
            if (changeEvent == null) {
                return;
            }
//            if (changeEvent.isChanged(AlgoConfigKeyEnum.QUOTE_SWITCH.name())) {
//                String onOff = changeEvent.getChange(AlgoConfigKeyEnum.QUOTE_SWITCH.name()).getNewValue();
//                this.quoteSwitch = Boolean.valueOf(onOff);
//            }
            log.info("[Apollo] - Algo changed, apolloConfig: {}", apolloConfig.toString());
        }

        @PostConstruct
        private void setup() {
            //apolloConfig = ConfigService.getConfig(namespaces.get(0));
            //apolloConfig.addChangeListener(this::someOnChange);
            //final String name = apolloConfig.getProperty("SYS_NAME", "error");
            //log.info("[Apollo] - Algo config setup, name: {}", name);
        }
    }

    @Bean
    public AlgoConfig algoConfig() {
        return new AlgoConfig();
    }
}

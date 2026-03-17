package kr.co.knuserver.global.property;

import kr.co.knuserver.global.config.LikeProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(LikeProperties.class)
public class PropertiesConfig {
}

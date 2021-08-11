package bot.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;


@Setter
@Getter
@Component
@PropertySource("classpath:vkConfig.properties")
@ConfigurationProperties(prefix = "vk.api")
public class VkConfig {
    @NotBlank
    private String access_token;
    @NotBlank
    private String secret;
    @NotBlank
    private String confirmation;
    @NotBlank
    private String v;
    @NotBlank
    private Integer group_id;
}

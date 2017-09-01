package vkCoverChanger.config.properties.vk;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by Nikolay V. Petrov on 02.09.2017.
 */

@PropertySource(value = "file:${config.directory}/vk-client.properties")
@ConfigurationProperties(prefix = "vk.user")
@Configuration
public class VkUserProperties {

    private Integer id;
    private String accessToken;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}

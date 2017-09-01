package vkCoverChanger.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import vkCoverChanger.config.properties.vk.VkGroupProperties;
import vkCoverChanger.config.properties.vk.VkUserProperties;

/**
 * Created by Nikolay V. Petrov on 02.09.2017.
 */

@PropertySource(value = "file:${config.directory}/vk-client.properties")
@ConfigurationProperties(prefix = "vk")
@Configuration
public class VkProperties {

    private VkUserProperties vkUserProperties;
    private VkGroupProperties groupProperties;

    public VkUserProperties getVkUserProperties() {
        return vkUserProperties;
    }

    public void setVkUserProperties(VkUserProperties vkUserProperties) {
        this.vkUserProperties = vkUserProperties;
    }

    public VkGroupProperties getGroupProperties() {
        return groupProperties;
    }

    public void setGroupProperties(VkGroupProperties groupProperties) {
        this.groupProperties = groupProperties;
    }
}

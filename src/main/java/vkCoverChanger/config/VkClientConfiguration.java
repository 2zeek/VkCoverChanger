package vkCoverChanger.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import vkCoverChanger.config.properties.VkClientProperties;
import vkCoverChanger.vk.VkClient;
import vkCoverChanger.vk.VkClientInstance;

/**
 * Created by Nikolay V. Petrov on 02.09.2017.
 */

@SuppressWarnings("SpringJavaAutowiringInspection")
@Configuration
@Import({
        VkClientProperties.class
})
public class VkClientConfiguration {

    @Autowired
    VkClientProperties vkClientProperties;

    @Bean
    VkClientInstance vkClientInstance() {
        return VkClient.createNewClient(vkClientProperties);
    }

}

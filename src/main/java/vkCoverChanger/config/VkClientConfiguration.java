package vkCoverChanger.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import vkCoverChanger.config.properties.VkClientProperties;
import vkCoverChanger.vk.VkClientInstance;
import vkCoverChanger.vk.VkClientInstanceImpl;

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
        return VkClientInstanceImpl.createNewClient(vkClientProperties);
    }

}

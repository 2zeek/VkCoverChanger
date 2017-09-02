package vkCoverChanger.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import vkCoverChanger.config.properties.StorageServiceProperties;
import vkCoverChanger.storage.StorageServiceInstance;
import vkCoverChanger.storage.StorageServiceInstanceImpl;

/**
 * Created by Nikolay V. Petrov on 02.09.2017.
 */

@SuppressWarnings("SpringJavaAutowiringInspection")
@Configuration
@Import({
        StorageServiceProperties.class
})
public class StorageServiceConfiguration {

    @Autowired
    StorageServiceProperties storageServiceProperties;

    @Bean
    StorageServiceInstance storageServiceInstance() {
        return StorageServiceInstanceImpl.createStorageService(storageServiceProperties);
    }

}

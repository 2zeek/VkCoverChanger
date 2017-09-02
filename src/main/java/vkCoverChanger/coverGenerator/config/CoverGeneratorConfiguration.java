package vkCoverChanger.coverGenerator.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import vkCoverChanger.coverGenerator.CoverGeneratorController;
import vkCoverChanger.coverGenerator.config.properties.CoverGeneratorProperties;

/**
 * Created by Nikolay V. Petrov on 02.09.2017.
 */

@SuppressWarnings("SpringJavaAutowiringInspection")
@Configuration
@Import({
    CoverGeneratorProperties.class
})
public class CoverGeneratorConfiguration {

    @Autowired
    CoverGeneratorProperties picGeneratorProperties;

    @Bean
    CoverGeneratorController weatherPicGenerator() {
        return CoverGeneratorController.getGenerator(picGeneratorProperties);
    }
}

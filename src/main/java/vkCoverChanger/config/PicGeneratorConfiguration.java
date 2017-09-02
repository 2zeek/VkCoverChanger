package vkCoverChanger.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import vkCoverChanger.WeatherPicGenerator;
import vkCoverChanger.config.properties.PicGeneratorProperties;

/**
 * Created by Nikolay V. Petrov on 02.09.2017.
 */

@SuppressWarnings("SpringJavaAutowiringInspection")
@Configuration
@Import({
    PicGeneratorProperties.class
})
public class PicGeneratorConfiguration {

    @Autowired
    PicGeneratorProperties picGeneratorProperties;

    @Bean
    WeatherPicGenerator weatherPicGenerator() {
        return WeatherPicGenerator.getGenerator(picGeneratorProperties);
    }
}

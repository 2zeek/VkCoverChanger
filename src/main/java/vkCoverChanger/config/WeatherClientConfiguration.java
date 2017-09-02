package vkCoverChanger.config;

import vkCoverChanger.weather.WeatherClientInstance;
import vkCoverChanger.config.properties.WeatherClientProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import vkCoverChanger.weather.WeatherClientInstanceImpl;

@SuppressWarnings("SpringJavaAutowiringInspection")
@Configuration
@Import({
        WeatherClientProperties.class
})
public class WeatherClientConfiguration {

    @Autowired
    WeatherClientProperties weatherClientProperties;

    @Bean
    WeatherClientInstance weatherClientInstanceImpl() {
        return WeatherClientInstanceImpl.createNewClient(weatherClientProperties);
    }

}

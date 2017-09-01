package vkCoverChanger.config;

import vkCoverChanger.weather.WeatherClient;
import vkCoverChanger.weather.WeatherClientInstance;
import vkCoverChanger.config.properties.WeatherClientProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

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
        System.out.println(weatherClientProperties);
        return WeatherClient.createNewClient(weatherClientProperties);
    }

    @Bean
    WeatherClientInstance weatherClientInstance() {
        return weatherClientInstanceImpl();
    }
}

package vkCoverChanger.weather;

import vkCoverChanger.config.properties.WeatherClientProperties;

public class WeatherClient {

    public static WeatherClientInstance createNewClient(WeatherClientProperties weatherClientProperties) {
        return new WeatherClientInstanceImpl(weatherClientProperties);
    }

}

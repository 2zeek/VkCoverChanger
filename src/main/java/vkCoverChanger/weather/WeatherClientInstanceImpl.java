package vkCoverChanger.weather;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import vkCoverChanger.weather.config.properties.WeatherClientProperties;
import vkCoverChanger.weather.response.WeatherResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Nikolay V. Petrov on 02.09.2017.
 */

public class WeatherClientInstanceImpl implements WeatherClientInstance {

    private Logger log = LoggerFactory.getLogger(WeatherClientInstanceImpl.class);

    private final WeatherClientProperties weatherClientProperties;
    private RestTemplate restTemplate;
    private ObjectMapper objectMapper;


    private WeatherClientInstanceImpl(WeatherClientProperties weatherClientProperties) {
        this.weatherClientProperties = weatherClientProperties;
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    public static WeatherClientInstance createNewClient(WeatherClientProperties weatherClientProperties) {
        return new WeatherClientInstanceImpl(weatherClientProperties);
    }


    public WeatherResponse getWeatherResponse() {
        WeatherResponse weatherResponse = restTemplate.getForObject(weatherClientProperties.getUrl() +
                weatherClientProperties.getParams(), WeatherResponse.class);
        ObjectNode jsonObject = objectMapper.createObjectNode();
        jsonObject.put("temp", weatherResponse.getMain().getTemp().intValue());
        jsonObject.put("weather", weatherResponse.getWeather().get(0).getMain());
        return weatherResponse;
    }
}

package vkCoverChanger;

import vkCoverChanger.config.WeatherClientConfiguration;
import vkCoverChanger.weather.WeatherClientInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Import(WeatherClientConfiguration.class)
public class HelloController {

    @Autowired
    protected WeatherClientInstance weatherClientInstance;

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World!";
    }

    @RequestMapping("/hello")
    @ResponseBody
    String hello(@RequestParam Map<String,String> request) {
        return request.get("greeting") + ", " + request.get("name");
    }

    @RequestMapping("/test")
    @ResponseBody
    String test() {
        return weatherClientInstance.getWeatherResponse().getMain().getTemp().toString();
    }
}

package vkCoverChanger;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.wall.responses.GetResponse;
import vkCoverChanger.config.WeatherClientConfiguration;
import vkCoverChanger.vk.VkClientInstance;
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

    @Autowired
    protected VkClientInstance vkClientInstance;

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

    @RequestMapping("/aass")
    @ResponseBody
    GetResponse aass() throws ClientException, ApiException {
        return vkClientInstance.getWall();
    }

}

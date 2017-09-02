package vkCoverChanger.controllers;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.wall.responses.GetResponse;
import vkCoverChanger.coverGenerator.CoverGeneratorController;
import vkCoverChanger.vk.VkClientInstance;
import vkCoverChanger.weather.WeatherClientInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by Nikolay V. Petrov on 02.09.2017.
 */

@RestController
public class MainController {

    @Autowired
    protected WeatherClientInstance weatherClientInstance;

    @Autowired
    protected VkClientInstance vkClientInstance;

    @Autowired
    protected CoverGeneratorController weatherPicGenerator;


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

    @RequestMapping("/tryIt")
    @ResponseBody
    String tryIt() throws ClientException, ApiException {
        vkClientInstance.setGroupCover(weatherPicGenerator.generatePic());
        return "success";
    }

}

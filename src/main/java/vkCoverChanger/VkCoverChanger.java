package vkCoverChanger;

import vkCoverChanger.application.Application;
import vkCoverChanger.config.WeatherClientConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(WeatherClientConfiguration.class)
public class VkCoverChanger {

    static Logger log = LoggerFactory.getLogger(VkCoverChanger.class);

    public static void main(String... args) {
        Application.start(VkCoverChanger.class, args);
    }
}

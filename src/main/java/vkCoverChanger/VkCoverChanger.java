package vkCoverChanger;

import org.springframework.context.ApplicationContext;
import vkCoverChanger.application.Application;
import vkCoverChanger.config.WeatherClientConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import vkCoverChanger.config.properties.VkClientProperties;

@SpringBootApplication
@Import({
        WeatherClientConfiguration.class,
        VkClientProperties.class
})
public class VkCoverChanger {

    static Logger log = LoggerFactory.getLogger(VkCoverChanger.class);

    public static void main(String... args) {
        ApplicationContext context = Application.start(VkCoverChanger.class, args);
    }
}

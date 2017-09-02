package vkCoverChanger;

import org.springframework.context.ApplicationContext;
import vkCoverChanger.application.Application;
import vkCoverChanger.config.PicGeneratorConfiguration;
import vkCoverChanger.config.VkClientConfiguration;
import vkCoverChanger.config.WeatherClientConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * Created by Nikolay V. Petrov on 02.09.2017.
 */

@SpringBootApplication
@Import({
        WeatherClientConfiguration.class,
        VkClientConfiguration.class,
        PicGeneratorConfiguration.class
})
public class VkCoverChanger {

    static Logger log = LoggerFactory.getLogger(VkCoverChanger.class);

    public static void main(String... args) {
        ApplicationContext context = Application.start(VkCoverChanger.class, args);
    }
}

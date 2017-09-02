package vkCoverChanger;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import vkCoverChanger.application.Application;
import vkCoverChanger.coverGenerator.CoverGeneratorController;
import vkCoverChanger.vk.VkClientInstance;
import vkCoverChanger.vk.config.VkClientConfiguration;
import vkCoverChanger.weather.config.WeatherClientConfiguration;
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
})
@EnableScheduling
public class VkCoverChanger {

    private static Logger log = LoggerFactory.getLogger(VkCoverChanger.class);

    public static void main(String... args) {
        ApplicationContext context = Application.start(VkCoverChanger.class, args);

    }

    @Autowired
    protected VkClientInstance vkClientInstance;

    @Autowired
    protected CoverGeneratorController coverGeneratorController;

    @Scheduled(fixedDelay = 1800000) // 30 minutes delay
    public void generateAndPost() throws ClientException, ApiException {
        log.info("ping");
        //vkClientInstance.setGroupCover(coverGeneratorController.generatePic());
    }
}

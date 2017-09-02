package vkCoverChanger.controllers;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import vkCoverChanger.coverGenerator.CoverGeneratorController;
import vkCoverChanger.vk.VkClientInstance;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Nikolay V. Petrov on 02.09.2017.
 */

@Component
public class MainController {

    Logger log = LoggerFactory.getLogger(MainController.class);

    @Autowired
    protected VkClientInstance vkClientInstance;

    @Autowired
    protected CoverGeneratorController coverGeneratorController;

    @Scheduled(fixedDelay = 1800000)
    public void main() throws ClientException, ApiException {
        log.info("ping");
        vkClientInstance.setGroupCover(coverGeneratorController.generatePic());
    }
}

package vkCoverChanger.vk;

import vkCoverChanger.config.properties.VkClientProperties;

/**
 * Created by Nikolay V. Petrov on 02.09.2017.
 */
public class VkClient {

    public static VkClientInstance createNewClient(VkClientProperties vkClientProperties) {
        return new VkClientInstanceImpl(vkClientProperties);
    }
}

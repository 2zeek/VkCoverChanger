package vkCoverChanger.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Nikolay V. Petrov on 27.08.2017.
 */

@PropertySource(value = "file:${config.directory}/storage.properties")
@ConfigurationProperties(prefix = "storage")
@Configuration
public class StorageServiceProperties {

    /**
     * Folder location for storing files
     */
    private String main;

    public String getMain() {
        return main;
    }

    public void setMain(String location) {
        this.main = location;
    }

    public Path getMainPath() {
        return Paths.get(main);
    }
}
package vkCoverChanger.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.net.URI;

@PropertySource(value = "file:${config.directory}/owm-client.properties")
@ConfigurationProperties(prefix = "weather")
@Configuration
public class WeatherClientProperties {

    private URI url;

    private String params;

    public URI getUrl() {
        return url;
    }

    public void setUrl(URI url) {
        this.url = url;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }
}

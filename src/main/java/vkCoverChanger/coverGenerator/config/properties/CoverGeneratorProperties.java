package vkCoverChanger.coverGenerator.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.awt.*;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Nikolay V. Petrov on 02.09.2017.
 */

@PropertySource(value = "file:${config.directory}/pic-generator.properties")
    @ConfigurationProperties(prefix = "pic-generator")
@Configuration
public class CoverGeneratorProperties {

    private String iconsFolder;
    private Result result;
    private Background background;
    private String weatherIconRequired;
    private DegreesPosition degreesPosition;
    private TextFont textFont;
    private Icon icon;

    public String getIconsFolder() {
        return iconsFolder;
    }

    public void setIconsFolder(String iconsFolder) {
        this.iconsFolder = iconsFolder;
    }

    public Path getIconFolderPath() {
        return Paths.get(iconsFolder);
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Background getBackground() {
        return background;
    }

    public void setBackground(Background background) {
        this.background = background;
    }

    public String getWeatherIconRequired() {
        return weatherIconRequired;
    }

    public void setWeatherIconRequired(String  weatherIconRequired) {
        this.weatherIconRequired = weatherIconRequired;
    }

    public Boolean isWeatherIconRequired() {
        return Boolean.valueOf(weatherIconRequired);
    }

    public DegreesPosition getDegreesPosition() {
        return degreesPosition;
    }

    public void setDegreesPosition(DegreesPosition degreesPosition) {
        this.degreesPosition = degreesPosition;
    }

    public TextFont getTextFont() {
        return textFont;
    }

    public void setTextFont(TextFont textFont) {
        this.textFont = textFont;
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public static class Result {

        private String folderName;
        private String fileName;

        public String getFolderName() {
            return folderName;
        }

        public void setFolderName(String folderName) {
            this.folderName = folderName;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public Path getFolderPath() {
            return Paths.get(folderName);
        }

        public Path getFilePath() {
            return Paths.get(fileName);
        }

    }

    public static class Background {

        private String folderName;
        private String fileName;

        public String getFolderName() {
            return folderName;
        }

        public void setFolderName(String folderName) {
            this.folderName = folderName;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public Path getFolderPath() {
            return Paths.get(folderName);
        }

        public Path getFilePath() {
            return Paths.get(fileName);
        }

    }

    public static class DegreesPosition {
        Integer x;
        Integer y;

        public Integer getX() {
            return x;
        }

        public void setX(Integer x) {
            this.x = x;
        }

        public Integer getY() {
            return y;
        }

        public void setY(Integer y) {
            this.y = y;
        }
    }

    public static class TextFont {
        String name;
        Integer style;
        Integer size;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getStyle() {
            return style;
        }

        public void setStyle(Integer style) {
            this.style = style;
        }

        public Integer getSize() {
            return size;
        }

        public void setSize(Integer size) {
            this.size = size;
        }

    }

    public static class Icon {
        Integer width;
        Integer height;

        public Integer getWidth() {
            return width;
        }

        public void setWidth(Integer width) {
            this.width = width;
        }

        public Integer getHeight() {
            return height;
        }

        public void setHeight(Integer height) {
            this.height = height;
        }
    }

}

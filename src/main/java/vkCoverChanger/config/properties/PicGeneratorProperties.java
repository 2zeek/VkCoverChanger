package vkCoverChanger.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Nikolay V. Petrov on 02.09.2017.
 */

@PropertySource(value = "file:${config.directory}/pic-generator.properties")
@ConfigurationProperties(prefix = "pic-generator")
@Configuration
public class PicGeneratorProperties {

    private String iconsFolder;
    private Result result;
    private Background background;

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
}

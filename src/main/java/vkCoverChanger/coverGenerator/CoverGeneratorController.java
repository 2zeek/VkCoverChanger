package vkCoverChanger.coverGenerator;

import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import vkCoverChanger.coverGenerator.config.properties.CoverGeneratorProperties;
import vkCoverChanger.weather.WeatherClientInstance;
import vkCoverChanger.weather.response.WeatherResponse;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Nikolay V. Petrov on 30.08.2017.
 */

public class CoverGeneratorController {

    private Logger log = LoggerFactory.getLogger(CoverGeneratorController.class);

    @Autowired
    private WeatherClientInstance weatherClientInstance;

    private CoverGeneratorProperties picGeneratorProperties;

    public CoverGeneratorController(CoverGeneratorProperties picGeneratorProperties) {
        this.picGeneratorProperties = picGeneratorProperties;
    }

    public static CoverGeneratorController getGenerator(CoverGeneratorProperties picGeneratorProperties) {
        return new CoverGeneratorController(picGeneratorProperties);
    }

    public File generatePic() {

        picGeneratorProperties.getResult().getFolderPath().toFile().mkdirs();
        WeatherResponse weatherData = weatherClientInstance.getWeatherResponse();

        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(new Date());
        Integer currentHour = calendar.get(Calendar.HOUR_OF_DAY);

        //String weather = weatherData.get("weather").asText().toLowerCase();
        String weather = "clear";
        String dayTime = currentHour > 6 && currentHour < 20 ? "day" : "night";
        String weatherPicName = weather + "_" + dayTime;

        String tempText =weatherData.getMain().getTemp() > 0
                ? "+" + weatherData.getMain().getTemp().toString() + "°"
                : "1" + weatherData.getMain().getTemp().toString() + "°";

        BufferedImage text = convertTextToGraphic(tempText, new Font("Arial", Font.PLAIN, 25));
        BufferedImage icon = null;
        try {
            icon = Scalr.resize(ImageIO.read(
                    new File(picGeneratorProperties.getIconsFolder() + "/" + weatherPicName + ".png")),
                    50);
        } catch (IOException e) {
            try {
                icon = Scalr.resize(ImageIO.read(
                        new File(picGeneratorProperties.getIconsFolder() + "/celsius.png")),
                        50);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        int w = text.getWidth() + icon.getWidth() + 10;
        int h = Math.max(text.getHeight(), icon.getHeight()) + 10;
        BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        Graphics g = combined.getGraphics();
        g.drawImage(text, 0, 10, null);
        g.drawImage(icon, text.getWidth(), 0, null);

        BufferedImage background = null;
        try {
            background = ImageIO.read(
                    new File( picGeneratorProperties.getBackground().getFolderName() + "/background.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
/*        BufferedImage background = Scalr.resize(
                ImageIO.read(
                        new File( System.getProperty("user.dir") + "\\icons\\oneSet\\background.jpg")),
                Scalr.Mode.FIT_EXACT, combined.getWidth(), combined.getHeight());*/

        BufferedImage combinedWithBack = new BufferedImage(background.getWidth(), background.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics gg = combinedWithBack.getGraphics();
        gg.drawImage(background, 0, 0, null);
//        gg.drawImage(combined, background.getWidth() - combined.getWidth() - 15, 0, null);
        gg.drawImage(combined, 215, 0, null);

        File resultFile = new File(picGeneratorProperties.getResult().getFolderName()
                + "/" + picGeneratorProperties.getResult().getFileName());

        try {
            ImageIO.write(combinedWithBack, "PNG",resultFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resultFile;
    }

    private static BufferedImage convertTextToGraphic(String text, Font font) {

        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();

        g2d.setFont(font);
        FontMetrics fm = g2d.getFontMetrics();
        int width = fm.stringWidth(text);
        int height = fm.getHeight();
        g2d.dispose();

        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        g2d = img.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g2d.setFont(font);
        fm = g2d.getFontMetrics();
        g2d.setColor(Color.WHITE);
        g2d.drawString(text, 0, fm.getAscent());
        g2d.dispose();
        return img;
    }
}

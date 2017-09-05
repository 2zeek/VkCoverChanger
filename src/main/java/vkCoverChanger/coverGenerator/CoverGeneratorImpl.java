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

public class CoverGeneratorImpl implements CoverGeneratorInstance {

    private Logger log = LoggerFactory.getLogger(CoverGeneratorImpl.class);

    @Autowired
    private WeatherClientInstance weatherClientInstance;

    private CoverGeneratorProperties picGeneratorProperties;

    private CoverGeneratorImpl(CoverGeneratorProperties picGeneratorProperties) {
        this.picGeneratorProperties = picGeneratorProperties;
    }

    public static CoverGeneratorImpl getGenerator(CoverGeneratorProperties picGeneratorProperties) {
        return new CoverGeneratorImpl(picGeneratorProperties);
    }

    public File generatePic() {

        picGeneratorProperties.getResult().getFolderPath().toFile().mkdirs();
        WeatherResponse weatherData = weatherClientInstance.getWeatherResponse();

        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(new Date());
        Integer currentHour = calendar.get(Calendar.HOUR_OF_DAY);

        String dayTime = currentHour > 6 && currentHour < 20 ? "day" : "night";

        String tempText = weatherData.getMain().getTemp() > 0
                ? "+" + weatherData.getMain().getTemp().toString().split("\\.")[0] + "°"
                : "1" + weatherData.getMain().getTemp().toString().split("\\.")[0] + "°";

        log.info("Current weather: " + tempText + ", " + weatherData.getWeather().get(0).getMain() + ", " +
                weatherData.getWeather().get(0).getDescription());

        BufferedImage text = convertTextToGraphic(tempText,
                new Font(picGeneratorProperties.getTextFont().getName(),
                        picGeneratorProperties.getTextFont().getStyle(),
                        picGeneratorProperties.getTextFont().getSize()));
        BufferedImage combined;

        if (picGeneratorProperties.isWeatherIconRequired()) {
            BufferedImage icon = null;
            try {
                icon = Scalr.resize(ImageIO.read(
                        new File(picGeneratorProperties.getIconsFolder() + "/" + dayTime + "/" +
                                weatherData.getWeather().get(0).getMain() + "/" +
                                weatherData.getWeather().get(0).getDescription().replace(" ", "_") + ".png")),
                        Scalr.Mode.FIT_EXACT,
                        picGeneratorProperties.getIcon().getWidth(), picGeneratorProperties.getIcon().getHeight());
            } catch (IOException e) {
                try {
                    icon = Scalr.resize(ImageIO.read(
                            new File(picGeneratorProperties.getIconsFolder() + "/celsius.png")),
                            Scalr.Mode.FIT_EXACT,
                            picGeneratorProperties.getIcon().getWidth(), picGeneratorProperties.getIcon().getHeight());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            int w = text.getWidth() + icon.getWidth();
            int h = Math.max(text.getHeight(), icon.getHeight());
            combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

            Graphics g = combined.getGraphics();
            g.drawImage(text, 0, (h - text.getHeight()) / 2, null);
            g.drawImage(icon, text.getWidth(), (h - icon.getHeight()) / 2, null);
        } else {
            combined = text;
        }

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
        gg.drawImage(combined, picGeneratorProperties.getDegreesPosition().getX(), picGeneratorProperties.getDegreesPosition().getY(),
                null);

        File resultFile = new File(picGeneratorProperties.getResult().getFolderName()
                + "/" + picGeneratorProperties.getResult().getFileName());

        try {
            ImageIO.write(combinedWithBack, "PNG", resultFile);
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

package br.udesc.ceavi.dsd.util;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author davib
 */
public class Image {

    public static Map<String, BufferedImage> images = new HashMap<>();
    public static final String ZERO = "img/0.png";
    public static final String UP = "img/1.png";
    public static final String DOWN = "img/3.png";
    public static final String LEFT = "img/4.png";
    public static final String RIGHT = "img/2.png";
    public static final String DEFAULT = "img/default.png";
    public static final String CAR = "img/car.png";

    private static void initAll() throws IOException {
        images.put(ZERO, ImageIO.read(new File(Image.ZERO)));
        images.put(UP, ImageIO.read(new File(Image.UP)));
        images.put(DOWN, ImageIO.read(new File(Image.DOWN)));
        images.put(LEFT, ImageIO.read(new File(Image.LEFT)));
        images.put(RIGHT, ImageIO.read(new File(Image.RIGHT)));
        images.put(DEFAULT, ImageIO.read(new File(Image.DEFAULT)));
        images.put(CAR, ImageIO.read(new File(Image.CAR)));
    }

    public static BufferedImage getImage(int in) {
        switch (in) {
            case 0:
                return getImage(ZERO);
            case 1:
                return getImage(UP);
            case 2:
                return getImage(RIGHT);
            case 3:
                return getImage(DOWN);
            case 4:
                return getImage(LEFT);
            default:
                return getImage(DEFAULT);
        }
    }

    public synchronized static BufferedImage getImage(String imagem) {
        if (images.isEmpty()) {
            try {
                initAll();
            } catch (IOException ex) {
                Logger.getLogger(Image.class.getName()).log(Level.ALL, null, ex);
            }
        }
        return images.get(imagem);
    }

    public static BufferedImage replaceColor(BufferedImage image, int preferred) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage newImage = new BufferedImage(width, height, image.getType());
        int color;
        int target = Color.black.getRGB();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                color = image.getRGB(i, j);
                if (color == target) {
                    newImage.setRGB(i, j, preferred);
                } else {
                    newImage.setRGB(i, j, color);
                }
            }
        }

        return newImage;
    }

    public static Color generateRandomColor() {
        Random randColor = new Random();
        int r = randColor.nextInt(256);
        int g = randColor.nextInt(256);
        int b = randColor.nextInt(256);
        return new Color(r, g, b);

    }

    public static int generateRGB() {
        return generateRandomColor().getRGB();
    }
}

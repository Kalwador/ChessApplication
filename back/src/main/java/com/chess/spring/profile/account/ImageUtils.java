package com.chess.spring.profile.account;

import sun.awt.image.BufferedImageGraphicsConfig;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class ImageUtils {

    public static BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();
        return bimage;
    }

    public static BufferedImage resizeTrick(BufferedImage image, int width, int height) {
        image = createCompatibleImage(image);
        image = resize(image, 100, 100);
        image = blurImage(image);
        return resize(image, width, height);
    }

    private static BufferedImage resize(BufferedImage image, int width, int height) {
        int type = image.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : image.getType();
        BufferedImage resizedImage = new BufferedImage(width, height, type);
        Graphics2D g = resizedImage.createGraphics();
        g.setComposite(AlphaComposite.Src);
        g.drawImage(image, 0, 0, width, height, null);
        g.dispose();
        return resizedImage;
    }

    private static BufferedImage blurImage(BufferedImage image) {
        float ninth = 1.0f / 9.0f;
        float[] blurKernel = {
                ninth, ninth, ninth,
                ninth, ninth, ninth,
                ninth, ninth, ninth
        };

        Map<RenderingHints.Key, Object> map = new HashMap<>();
        map.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        map.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        map.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        RenderingHints hints = new RenderingHints(map);
        BufferedImageOp op = new ConvolveOp(new Kernel(3, 3, blurKernel), ConvolveOp.EDGE_NO_OP, hints);
        return op.filter(image, null);
    }

    private static BufferedImage createCompatibleImage(BufferedImage image) {
        GraphicsConfiguration gc = BufferedImageGraphicsConfig.getConfig(image);
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage result = gc.createCompatibleImage(w, h, Transparency.TRANSLUCENT);
        Graphics2D g2 = result.createGraphics();
        g2.drawRenderedImage(image, null);
        g2.dispose();
        return result;
    }

    public static String encodeImage(BufferedImage image) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", byteArrayOutputStream);
        byteArrayOutputStream.flush();
        byte[] imageInByteFull = byteArrayOutputStream.toByteArray();
        return Base64.getEncoder().encodeToString(imageInByteFull);
    }

    public static BufferedImage resizeImage(BufferedImage sourceImage, int size) {
        int width = sourceImage.getWidth();
        int height = sourceImage.getHeight();
        if (width > height) {
            float percentWidth = getPrecentSizeForThumbnail(height, width, size);
            BufferedImage img = new BufferedImage((int) percentWidth, size, BufferedImage.TYPE_INT_RGB);
            Image scaledImage = sourceImage.getScaledInstance((int) percentWidth, size, Image.SCALE_FAST);
            img.createGraphics().drawImage(scaledImage, 0, 0, null);
            return img.getSubimage((int) ((percentWidth - size) / 2), 0, size, size);
        } else {
            float percentHight = getPrecentSizeForThumbnail(width, height, size);
            BufferedImage img = new BufferedImage(size, (int) percentHight, BufferedImage.TYPE_INT_RGB);
            Image scaledImage = sourceImage.getScaledInstance(size, (int) percentHight, Image.SCALE_FAST);
            img.createGraphics().drawImage(scaledImage, 0, 0, null);
            return img.getSubimage(0, (int) ((percentHight - size) / 2), size, size);
        }
    }

    public static BufferedImage decreaseQuality(BufferedImage image) {
        BufferedImage resizedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
        g.dispose();
        g.setComposite(AlphaComposite.Src);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        return resizedImage;
    }

    private static float getPrecentSizeForThumbnail(int size1, int size2, int destinateSize) {
        float extraSize = size1 - destinateSize;
        float percentHight = (extraSize / size1) * destinateSize;
        return size2 - ((size2 / destinateSize) * percentHight);
    }

    public static byte[] imageToByteArray(BufferedImage originalImage) {
        byte[] imageInByte = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(originalImage, "jpg", baos);
            baos.flush();
            imageInByte = baos.toByteArray();
            baos.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return imageInByte;
    }
}

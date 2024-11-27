package com.cn.util;

/***            : TODO
 * @Copyright All rights Reserved, Designed By Circle Harmony Medical Insurance
 * @Project     ：SpringCloud
 * @Title       ：ThumbnailsUtils
 * @Description ：TODO
 * @Version     ：Ver1.0
 * @Author      ：lz
 * @Date        ：2024-11-27 14:00
 * @Dept        ：Information Technology Department
 * @Company     ：Circle Harmony Medical Insurance
 ***/
import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ThumbnailsUtils {

    private static final Logger logger = LoggerFactory.getLogger(ThumbnailsUtils.class);

    /**
     * 通过BufferedImage图片流调整图片大小
     */
    public static BufferedImage resizeImageOne(BufferedImage originalImage, int targetWidth, int targetHeight) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Thumbnails.of(originalImage)
                .size(targetWidth, targetHeight)
                .outputFormat("JPEG")
                .outputQuality(1)
                .toOutputStream(outputStream);
        byte[] data = outputStream.toByteArray();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        return ImageIO.read(inputStream);
    }

    /**
     * BufferedImage图片流转byte[]数组
     */
    public static byte[] imageToBytes(BufferedImage bImage) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ImageIO.write(bImage, "jpg", out);
        } catch (IOException e) {
            logger.error("错误信息: ", e);
        }
        return out.toByteArray();
    }


    /**
     * byte[]数组转BufferedImage图片流
     */
    private static BufferedImage bytesToBufferedImage(byte[] ImageByte) {
        ByteArrayInputStream in = new ByteArrayInputStream(ImageByte);
        BufferedImage image = null;
        try {
            image = ImageIO.read(in);
        } catch (IOException e) {
            logger.error("错误信息: ", e);
        }
        return image;
    }
}


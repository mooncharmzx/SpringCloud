package com.cn.test;

import com.cn.util.ThumbnailsUtils;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.util.Base64;

/***            : 图片压缩
 * @Copyright All rights Reserved, Designed By Circle Harmony Medical Insurance
 * @Project     ：SpringCloud
 * @Title       ：TestImage
 * @Description ：TODO
 * @Version     ：Ver1.0
 * @Author      ：lz
 * @Date        ：2024-11-27 14:00
 * @Dept        ：Information Technology Department
 * @Company     ：Circle Harmony Medical Insurance
 ***/
@Slf4j
public class TestImage {

    public static void main(String[] args) throws IOException {

        String oldFilePath = "E:\\photo\\家人\\李\\微信图片_20241127144222.jpg";
        String newFilePath = "E:\\photo\\家人\\压缩后\\微信图片_20241127144222.jpg";
        File file = new File(oldFilePath);
        byte[] fileContent = Files.readAllBytes(file.toPath());

        // Encode the byte array to Base64
        String base64String = Base64.getEncoder().encodeToString(fileContent);
        String newBase64String = compressImage(base64String);

        byte[] decodedBytes = Base64.getDecoder().decode(newBase64String);

        // Write the decoded bytes to a file
        try (FileOutputStream fos = new FileOutputStream(newFilePath)) {
            fos.write(decodedBytes);
        }
    }

    /**
     * 图片无损压缩
     * @param image
     * @return
     */
    public static String compressImage(String image) {
        // 实现图片无损压缩的逻辑

        //1.读取文件
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = convert(image);
        } catch (IOException e) {
            log.info("base64转换BufferedImage失败");
        }
        //压缩文件
        if (bufferedImage != null) {
            //模拟压缩 压缩质量（0.0f 最差质量，1.0f 最佳质量） 0.8
            bufferedImage = compressImage(bufferedImage, 0.25);
        }
        //保存文件
        if (bufferedImage != null) {

            try {
                image = convertImageToBase64(bufferedImage);
            } catch (Exception e) {
                log.info("BufferedImage转base64失败");
            }
        }

        return image;
    }


    //base64转BufferedImage
    public static BufferedImage convert(String base64Image) throws IOException {
        // 解码Base64字符串
        byte[] imageBytes = Base64.getDecoder().decode(base64Image);
        // 创建字节数组输入流
        ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
        // 通过ImageIO读取图片
        BufferedImage image = ImageIO.read(bis);
        // 关闭输入流
        bis.close();
        return image;
    }

    // 进行无损压缩
    public static BufferedImage compressImage(BufferedImage originalImage, double scale) {
        int newWidth = (int) (originalImage.getWidth() * scale);
        int newHeight = (int) (originalImage.getHeight() * scale);

        // 创建压缩后的图片
        BufferedImage compressedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = compressedImage.createGraphics();

        // 绘制原始图片到压缩后的图片上
        g2d.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
        g2d.dispose(); // 释放资源

        return compressedImage;
    }


    //BufferedImage转base64
    public static String convertImageToBase64(BufferedImage image) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", outputStream); // 假设我们使用PNG格式
        byte[] imageBytes = outputStream.toByteArray();
        String base64String = Base64.getEncoder().encodeToString(imageBytes);
        outputStream.close();
        return base64String;
    }


    public String resizeImage(String image, int width, int height) {
        // 实现图片大小调整的逻辑

        //1.读取文件
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = convert(image);
        } catch (IOException e) {
            log.info("base64转换BufferedImage失败");
        }

        try {
            bufferedImage = ThumbnailsUtils.resizeImageOne(bufferedImage, width, height);
        } catch (Exception e) {
            log.info("图片大小调整失败");
        }

        //保存文件
        if (bufferedImage != null) {
//            try {
//                saveImage(bufferedImage,downPath);
//            } catch (IOException e) {
//                log.info("图片压缩后保存失败");
//            }

            try {
                image = convertImageToBase64(bufferedImage);
            } catch (Exception e) {
                log.info("BufferedImage转base64失败");
            }
        }

        return image;
    }


}

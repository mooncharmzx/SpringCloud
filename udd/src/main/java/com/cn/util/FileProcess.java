package com.cn.util;

import com.cn.sce.dto.FileDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/***            : TODO
 * @Copyright All rights Reserved, Designed By Circle Harmony Medical Insurance
 * @Project     ：dsp
 * @Title       ：FileProcess
 * @Description ：TODO
 * @Version     ：Ver1.0
 * @Author      ：ChenWen
 * @Date        ：2024-09-06 11:06
 * @Dept        ：Information Technology Department
 * @Company     ：Circle Harmony Medical Insurance
 * @Copyright Copyright (C), All rights Reserved,2016-2024, 圆和医疗集团有限公司
 ***/
@Slf4j
public class FileProcess {

    /***            : TODO
         *@funcName     : getReportDetail
         *@description  : description
         *@Param1       : java.lang.String  userId
         *@Param2       : java.lang.String  avatarPath  /opt/avatar 服务器地址
         *@Param2       : java.lang.String  serverIp    http://www.aa.bb:host/ 访问资源域名
         *@Param2       : java.lang.String  avatarVisit /photo/image/avatar 访问资源路径
         *@return       : ResponseVO
         *@version      :
         *@author       : LiZhen
         *@date         : 2024-10-17 13:14
         *@revision     : Ver1.0
         *@reviser      : LiZhen
         *@reviseDate   : 2024-10-17 13:14
         ***/
    public static Map<String, Object> uploadFile(String userId, FileDTO fileDTO, String avatarPath, String serverIp, String avatarVisit) throws IOException {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String tempFileName = now.format(formatter);
        String suffix = ".png";
        MultipartFile uploadFile = convert(fileDTO.getBase64Code(),tempFileName+suffix);

        return uploadFile(userId,uploadFile,avatarPath,serverIp,avatarVisit);
    }

    public static Map<String, Object> uploadFile(String userId, MultipartFile file, String avatarPath,String serverIp,String avatarVisit) {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("code", "0");
            String dirStr= avatarPath + userId;; // 文件夹目录
            String imgPath = ""; // 图片地址
            String newFileName = ""; // 文件名字
            String returnPath = ""; // 返回路径
            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            newFileName= UUID.randomUUID().toString().replaceAll("-", "")+suffix;

            imgPath = System.getProperty("user.dir") +  avatarPath + userId + "/";
            returnPath =  userId + "/" + newFileName;

            log.debug("path:{}" ,dirStr);
            File directory = new File(dirStr);
            if(!directory.exists()){

                log.debug("=======path========={}", dirStr);
                boolean f = directory.mkdirs();
                log.debug("=======创建目录结束========={}", f);

            }

            if(file == null){
                map.put("code", "0");
                log.debug("=======000000000========={}", 0);
            }else{
                log.debug("=======111111111========={}", 1);
                File newFile = new File(imgPath + newFileName);
                try {
                    log.debug("=======newFile========={}", imgPath + newFileName);
                    file.transferTo(newFile);
                    map.put("code", "1");
                    map.put("imageUrl", serverIp + avatarVisit + returnPath);
                    map.put("fileName", returnPath);
                    map.put("suffix",suffix);
                    log.debug("=======returnPath========={}", returnPath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return map;
        }catch (Exception e) {
            log.error("", e);
            Map<String, Object> map = new HashMap<>();
            map.put("error", e);
            return map;
        }
    }

    public static MultipartFile convert(String base64String, String fileName) throws IOException {
        // Remove the prefix if present (e.g., "data:image/png;base64,")
        String[] parts = base64String.split(",");
        String fileData = parts.length > 1 ? parts[1] : parts[0];

        // Decode the Base64 string
        byte[] fileBytes = Base64.getDecoder().decode(fileData);

        // Create a MultipartFile instance
        return new MockMultipartFile(fileName, fileName, "image/png", fileBytes);
    }
}

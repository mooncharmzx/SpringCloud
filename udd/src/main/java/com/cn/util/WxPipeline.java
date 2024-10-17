package com.cn.util;

import com.cn.sce.entity.HealthDocEntity;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;


/***            : TODO
 * @Copyright All rights Reserved, Designed By Circle Harmony Medical Insurance
 * @Project     ：vigorbox
 * @Title       ：WxPipeline
 * @Description ：TODO
 * @Version     ：Ver1.0
 * @Author      ：ChenWen
 * @Date        ：2024-08-06 17:27
 * @Dept        ：Information Technology Department
 * @Company     ：Circle Harmony Medical Insurance
 * @Copyright Copyright (C), All rights Reserved,2016-2024, 圆和医疗集团有限公司
 ***/
@Component
@Slf4j
public class WxPipeline {

    @Value("${temp.upload}")
    String TEMP_UPLOAD_PATH;

    /***            : 初始化
         *@funcName     : getReportDetail
         *@description  : description
         *@return       : String[]
         *@version      :
         *@author       : LiZhen
         *@date         : 2024-08-16 13:51
         *@revision     : Ver1.0
         *@reviser      : LiZhen
         *@reviseDate   : 2024-08-16 13:51
         ***/
    static String[] userAgent = {
            "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_8; en-us) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50",
            "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-us) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50",
            "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:38.0) Gecko/20100101 Firefox/38.0",
            "Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; .NET4.0C; .NET4.0E; .NET CLR 2.0.50727; .NET CLR 3.0.30729; .NET CLR 3.5.30729; InfoPath.3; rv:11.0) like Gecko",
            "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)",
            "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0; Trident/4.0)",
            "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.0)",
            "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.6; rv:2.0.1) Gecko/20100101 Firefox/4.0.1",
            "Mozilla/5.0 (Windows NT 6.1; rv:2.0.1) Gecko/20100101 Firefox/4.0.1",
            "Opera/9.80 (Macintosh; Intel Mac OS X 10.6.8; U; en) Presto/2.8.131 Version/11.11",
            "Opera/9.80 (Windows NT 6.1; U; en) Presto/2.8.131 Version/11.11",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_0) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.56 Safari/535.11",
            "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Maxthon 2.0)",
            "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; TencentTraveler 4.0)",
            "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)",
            "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; The World)",
            "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Trident/4.0; SE 2.X MetaSr 1.0; SE 2.X MetaSr 1.0; .NET CLR 2.0.50727; SE 2.X MetaSr 1.0)",
            "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; 360SE)",
            "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Avant Browser)",
            "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)",
            "Mozilla/5.0 (iPhone; U; CPU iPhone OS 4_3_3 like Mac OS X; en-us) AppleWebKit/533.17.9 (KHTML, like Gecko) Version/5.0.2 Mobile/8J2 Safari/6533.18.5",
            "Mozilla/5.0 (iPod; U; CPU iPhone OS 4_3_3 like Mac OS X; en-us) AppleWebKit/533.17.9 (KHTML, like Gecko) Version/5.0.2 Mobile/8J2 Safari/6533.18.5",
            "Mozilla/5.0 (iPad; U; CPU OS 4_3_3 like Mac OS X; en-us) AppleWebKit/533.17.9 (KHTML, like Gecko) Version/5.0.2 Mobile/8J2 Safari/6533.18.5",
            "Mozilla/5.0 (Linux; U; Android 2.3.7; en-us; Nexus One Build/FRF91) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1",
            "MQQBrowser/26 Mozilla/5.0 (Linux; U; Android 2.3.7; zh-cn; MB200 Build/GRJ22; CyanogenMod-7) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1",
            "Opera/9.80 (Android 2.3.4; Linux; Opera Mobi/build-1107180945; U; en-GB) Presto/2.8.149 Version/11.10",
            "Mozilla/5.0 (Linux; U; Android 3.0; en-us; Xoom Build/HRI39) AppleWebKit/534.13 (KHTML, like Gecko) Version/4.0 Safari/534.13",
            "Mozilla/5.0 (BlackBerry; U; BlackBerry 9800; en) AppleWebKit/534.1+ (KHTML, like Gecko) Version/6.0.0.337 Mobile Safari/534.1+",
            "Mozilla/5.0 (hp-tablet; Linux; hpwOS/3.0.0; U; en-US) AppleWebKit/534.6 (KHTML, like Gecko) wOSBrowser/233.70 Safari/534.6 TouchPad/1.0",
            "Mozilla/5.0 (SymbianOS/9.4; Series60/5.0 NokiaN97-1/20.0.019; Profile/MIDP-2.1 Configuration/CLDC-1.1) AppleWebKit/525 (KHTML, like Gecko) BrowserNG/7.1.18124",
            "Mozilla/5.0 (compatible; MSIE 9.0; Windows Phone OS 7.5; Trident/5.0; IEMobile/9.0; HTC; Titan)",
            "UCWEB7.0.2.37/28/999",
            "NOKIA5700/ UCWEB7.0.2.37/28/999",
            "Openwave/ UCWEB7.0.2.37/28/999",
            "Mozilla/4.0 (compatible; MSIE 6.0; ) Opera/UCWEB7.0.2.37/28/999",
            "Mozilla/6.0 (iPhone; CPU iPhone OS 8_0 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Version/8.0 Mobile/10A5376e Safari/8536.25",
    };

//    static BrowserVersion browser = new BrowserVersion.BrowserVersionBuilder(BrowserVersion.CHROME)
//            .setUserAgent(userAgent[new Random().nextInt(userAgent.length)])
//            .build();
static BrowserVersion browser = BrowserVersion.getDefault();
    private static final WebClient webClient = new WebClient(browser);
    
    /***            : TODO
         *@funcName     : getReportDetail
         *@description  : description
         *@Param1       : java.lang.String  healthDocUrl
         *@return       : ResponseVO
         *@version      :
         *@author       : LiZhen
         *@date         : 2024-08-16 14:03
         *@revision     : Ver1.0
         *@reviser      : LiZhen
         *@reviseDate   : 2024-08-16 14:03
         ***/
    public Map<String,Object> process(String healthDocUrl) {

        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setActiveXNative(false);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        HtmlPage pageTmp = null;
        String pageXml = "";
        try {

//            if(healthDocUrl.contains("token=")){
//                try {
//                    pageXml = getHtml(healthDocUrl);
//                    if(StringUtils.isNotEmpty(pageXml)){
//                        pageXml = pageXml.replaceAll("reportloaderror","").replaceAll("&","&amp;");
//
//                    }
//                } catch (Exception e) {
//                    throw new RuntimeException(e);
//                }
//            }else{
                pageTmp = webClient.getPage(healthDocUrl);
                pageXml = pageTmp.asXml();
//            }


        } catch (Exception e) {

            log.error("解析文章链接出错,文章地址:{},错误信息:{}",healthDocUrl,e.getMessage());
        } finally {
            webClient.close();
        }

        HealthDocEntity healthDoc = domParserXML(pageXml);
        String mediaId = setHealthDocMediaId(healthDocUrl);
    //    String mediaId = SnowflakeIdUtil.getNextId("HDC");
        if(null != healthDoc){
            healthDoc.setMediaId(mediaId);
        }
        Map<String,Object> result = new HashMap<>();
        result.put("pageXml",pageXml);
        result.put("healthDoc",healthDoc);
        return result;
    }

    /***            : 直接获取不规范链接网页
         *@funcName     : getReportDetail
         *@description  : description
         *@Param1       : java.lang.String  target
         *@Param2       : java.lang.String  registerEntity
         *@return       : ResponseVO
         *@version      :
         *@author       : LiZhen
         *@date         : 2024-08-23 16:22
         *@revision     : Ver1.0
         *@reviser      : LiZhen
         *@reviseDate   : 2024-08-23 16:22
         ***/
    public String getHtml(String healthDocUrl) throws Exception {

        URL url = new URL(healthDocUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        connection.connect();
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            connection.disconnect();
            return content.toString();
        } else {
            throw new Exception("Failed to get HTML content, response code: " + connection.getResponseCode());
        }

    }


    /***            : 截取链接中的最后一个斜杠后的内容,用作文章的编号
         *@funcName     : setHealthDocMediaId
         *@description  : description
         *@Param1       : java.lang.String  healthDocUrl
         *@return       : String
         *@version      :
         *@author       : LiZhen
         *@date         : 2024-08-16 13:47
         *@revision     : Ver1.0
         *@reviser      : LiZhen
         *@reviseDate   : 2024-08-16 13:47
         ***/
    private String setHealthDocMediaId(String healthDocUrl) {

        if(!StringUtils.isEmpty(healthDocUrl)){
            return healthDocUrl.substring(healthDocUrl.lastIndexOf("/")+1);
        }
        return null;

    }

    private HealthDocEntity domParserXML(String pageXml){
        HealthDocEntity healthDoc = new HealthDocEntity();

        try {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.parse(new ByteArrayInputStream(pageXml.getBytes(StandardCharsets.UTF_8)));
            //根据body标签名称获取被body标签包裹的html
            Node jsArticle = findNodeById(document.getDocumentElement(), null,"body");
            String title = getNode(jsArticle,"activity-name");
            String content = jsoupHtml(pageXml,"js_content");
            String author = getNode(jsArticle,"js_name");

            List<String> imageUrls = jsoupImg(pageXml,"js_content","img");
            String publishTime = getNode(jsArticle,"publish_time");
            healthDoc.setAuthor(author.trim());
            healthDoc.setTitle(title.trim());
            healthDoc.setThumbUrls(imageUrls);
            healthDoc.setContent(content);
            healthDoc.setCreateTime(DateUtil.chineseFormat(publishTime.trim()));
            return healthDoc;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("文章解析失败!{}",e.getMessage());
        }
        return null;
    }

    /***            : 获取指定html 中的指定id的标签包裹的html内容
         *@funcName     : jsoup
         *@description  : description
         *@Param1       : java.lang.String  htmlString
         *@Param2       : java.lang.String  id 标签id
         *@Param2       : java.lang.String  tagName 标签名
         *@return       : String
         *@version      :
         *@author       : LiZhen
         *@date         : 2024-08-16 10:10
         *@revision     : Ver1.0
         *@reviser      : LiZhen
         *@reviseDate   : 2024-08-16 10:10
         ***/
    public String jsoupHtml(String htmlString,String id){
        org.jsoup.nodes.Document document = Jsoup.parse(htmlString);

        org.jsoup.nodes.Element foundElement = document.getElementById(id);

        return foundElement.outerHtml();
    }


    public List<String> jsoupImg(String htmlString,String id,String tagName) throws IOException {

        org.jsoup.nodes.Document document = Jsoup.parse(htmlString);
        org.jsoup.nodes.Element foundElement = document.getElementById(id);
        org.jsoup.select.Elements elements = foundElement.select(tagName);
        return getValueElement(elements);
    }

    /***            : 获取html中的某个标签内包含的所有图片
         *@funcName     : getValueElement
         *@description  : description
         *@Param1       : org.jsoup.select.Elements  imgTags
         *@return       : String
         *@version      :
         *@author       : LiZhen
         *@date         : 2024-08-16 13:15
         *@revision     : Ver1.0
         *@reviser      : LiZhen
         *@reviseDate   : 2024-08-16 13:15
         ***/
    public List<String> getValueElement(org.jsoup.select.Elements imgTags) throws IOException {

        List<String> tagNameValue = new ArrayList<>();

        if (!imgTags.isEmpty()) {

            for (org.jsoup.nodes.Element img : imgTags) {
                String imgSrc = img.attr("data-src"); // Get the src attribute
                if(tagNameValue.size()<=2){

                    tagNameValue.add(StringUtils.isEmpty(imgSrc)?imgSrc:imgSrc.trim());
//                    URL url = new URL(StringUtils.isEmpty(imgSrc)?imgSrc:imgSrc.trim());
//                    InputStream in = url.openStream();
//
//                    byte[] bytes = IOUtils.toByteArray(in);
//                    String fileName = UUID.randomUUID().toString()+".png";
//                    // 导出路径和文件格式
//
//                    SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
//                    String today = format.format(new Date()); // 今日日期
//                    String dirStr = TEMP_UPLOAD_PATH + today;
//                    String imgPath = System.getProperty("user.dir") +  TEMP_UPLOAD_PATH + today + "/";
//                    List<ConfigEntity> configEntities = configMapper.selectAll();
//                    String serverIp = configEntities.get(0).getConfigValue();
//                    String popup_image_path = configEntities.get(2).getConfigValue();
//                    String returnPath =  today + "/" + fileName;
//                    Path path = Paths.get(dirStr);
//                    if(!Files.exists(path)){ // 查看是否存在文件夹
//                        Files.createDirectory(path);
//                    }
//                    FileUtils.writeByteArrayToFile(new File(imgPath+fileName),bytes);
//                    log.debug("上传的图片放温暖路径:{}",serverIp + popup_image_path + returnPath);
                    log.debug("图片上传完成");
                }else{
                    return tagNameValue;
                }

            }
        }

        return tagNameValue;
    }


    /***            : TODO
         *@funcName     : getReportDetail
         *@description  : description
         *@Param1       : Node node
         *@Param2       : java.lang.String  elementId
         *@return       : String
         *@version      :
         *@author       : LiZhen
         *@date         : 2024-08-16 9:05
         *@revision     : Ver1.0
         *@reviser      : LiZhen
         *@reviseDate   : 2024-08-16 9:05
         ***/
    public String getNode(Node node,String elementId){

        return Objects.requireNonNull(findNodeById(node, elementId,null)).getTextContent();
    }
    
    /***            : TODO
         *@funcName     : findNodeById
         *@description  : description
         *@Param1       : Node node
         *@Param2       : java.lang.String  id
         *@Param2       : java.lang.String  tagName
         *@return       : Node
         *@version      :
         *@author       : LiZhen
         *@date         : 2024-08-16 9:05
         *@revision     : Ver1.0
         *@reviser      : LiZhen
         *@reviseDate   : 2024-08-16 9:05
         ***/
    private static Node findNodeById(Node node, String id,String tagName) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
//            System.out.println(element.getTagName());
            if (element.getAttribute("id").equals(id) || (!StringUtils.isEmpty(tagName) && tagName.equals(element.getTagName()))) {
                return element;
            }
        }

        NodeList childNodes = node.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node foundNode = findNodeById(childNodes.item(i), id,tagName);
            if (foundNode != null) {
                return foundNode;
            }
        }

        return null;
    }

}
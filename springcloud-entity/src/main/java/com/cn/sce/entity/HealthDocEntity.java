package com.cn.sce.entity;

import lombok.Data;
import java.util.List;

@Data
public class HealthDocEntity {

    private Long id;//表ID
    private String mediaId;//公众号文章在草稿箱的id或文章链接中的最后一个斜杠后的字符串
    private String title;//文章在公众号的唯一标识
    private String thumbUrl;//标题
    private String author;//文章作者
    private String content;//文章内容
    private String createTime;//创建时间，公众号中的文章发布时间
    private String status;//文章状态0 下架 1上架
    private String loadTimeStamp;//加载时间

    private String healthDocForm; //文章来源:yhjk、圆和健康;xyjk、新永健康;xtmz、新太门诊;sdtb、手动同步
    private String healthDocLabel; //文章标签
    private String publishTime; //发布时间

    private String updateTime;//公众号端文章更新时间
    private String healthDocCode;//数据库文章编号
    private List<String> thumbUrls;
}

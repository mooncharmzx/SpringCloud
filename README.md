已经集成 技术
redis 
mysql 
mybatis-plus 
gateway
***************************************************
C:\Windows\System32\drivers\etc 修改如下内容
192.168.192.1 safe
127.0.0.1 view-localhost # view localhost server
192.168.192.1 windows10.microdone.cn
***************************************************

#########################

@Bean
@LoadBalanced //spring cloud 默认 负载均衡 
public RestTemplate restTemplate() {
    return new RestTemplate();
}

public static void main(String[] args) {
    SpringApplication.run(UDDApplication.class, args);
}

#############################

调用其他服务的接口时 
@Autowired
RestTemplate restTemplate;

public void test(){
    //restTemplate.postForEntity()
}

#############################
*******************************************************

redis 环境安装 
https://www.cnblogs.com/wmy666/p/15148686.html

*******************************************************

创建新子服务步奏
右键项目名SpringCloudTest1 》 new 》 module 》 maven 》maven-archetype-quickstart 
》 补充子服务名name /groupId 、选择maven 仓库 》 finish 》 等待项目加载完 
》 右键 main 》 new 》 directory 》 resource 》 创建yml文件 》 pom.xml添加依赖
》 

*******************************************************
计划还会集成spring cloud 的其他组件

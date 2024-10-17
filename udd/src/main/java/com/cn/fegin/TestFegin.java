package com.cn.fegin;

import com.cn.fegin.reponse.TestDemo;
import com.cn.fegin.request.TestQueryParams;
import com.cn.sce.api.ApiResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/***            : TODO
  * @Copyright All rights Reserved, Designed By Circle Harmony Medical Group Ltd.
  * @Project     ：Vgiorbox
  * @Title       ：
  * @Description ：spring.cloud.consul.discovery.fegin-server.same-service 注册中心需要调用的服务名
  * @Version     ：Ver1.0
  * @Author      ：LiZhen
  * @Date        ：2024-10-17 15:09
  * @Dept        ：Information Technology Department
  * @Company     ：Circle Harmony Medical Group Ltd.
  ***/
@Component
@FeignClient(name = "${spring.cloud.consul.discovery.fegin-server.same-service}",path = "")
public interface TestFegin {
    @PostMapping("/test/list")
    ApiResult<List<TestDemo>>  getList(@RequestBody TestQueryParams query);

    @PostMapping("/test/updateProcess")
    ApiResult<Boolean> updateProcess(@RequestBody TestQueryParams updateProcess);
}

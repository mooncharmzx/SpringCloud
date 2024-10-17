package com.cn.sce.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCodeEnum {
    OK(200, "操作成功"),
    FAIL(-1, "操作失败"),
    BAD_REQUEST(400, "请求参数错误"),
    VALIDATE_FAILED(404, "参数检验失败"),
    UNAUTHENTICATED(401, "认证失败，请重新登录"),
    FORBIDDEN(403, "没有访问权限"),
    METHOD_ERROR(405, "请求方式错误"),
    SERVER_ERROR(500, "内部服务异常"),
    CARD_NO(402, "服务或卡不存在"),
    FAILURE(5001,  "必填参数缺失！"),
    NICKDATA(5002,  "获取用户头像失败！"),
    REPEAT(5003,  "工单重复添加！");
    private final Integer code;
    private final String message;
}

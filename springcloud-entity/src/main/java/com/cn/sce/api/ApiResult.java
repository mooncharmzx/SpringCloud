package com.cn.sce.api;

import com.cn.sce.enums.ResponseCodeEnum;
import lombok.Data;
import org.slf4j.MDC;

import java.io.Serializable;

@Data
public class ApiResult<T> implements Serializable {

    //请求ID
    private String requestId = MDC.get(Constants.TRACE_ID);
    //返回状态码
    private Integer code;
    //返回消息
    private String message;
    /**
     * 返回数据
     */
    private T data;

    private static ApiResult apiResult;

    private ApiResult() {}

    public static <T> ApiResult<T> code(Integer code) {
        apiResult = new ApiResult();
        apiResult.setCode(code);
        return apiResult;
    }

    public <T> ApiResult<T> message(String message) {
        apiResult.setMessage(message);
        return apiResult;
    }

    public <T> ApiResult<T> data(T data) {
        apiResult.setData(data);
        return apiResult;
    }

    public static <T> ApiResult<T> ok() {
        return code(ResponseCodeEnum.OK.getCode()).message(ResponseCodeEnum.OK.getMessage());
    }

    public static <T> ApiResult<T> ok(T data) {
        return ok().data(data);
    }

    public static <T> ApiResult<T> fail() {
        return code(ResponseCodeEnum.FAIL.getCode()).message(ResponseCodeEnum.FAIL.getMessage());
    }

    public static <T> ApiResult<T> fail(String message) {
        return code(ResponseCodeEnum.FAIL.getCode()).message(message);
    }

    public static <T> ApiResult<T> fail(Integer code, String message) {
        return code(code).message(message);
    }


    /**
     * 参数验证失败返回结果
     */
    public static <T> ApiResult<T> validateFailed() {
        return code(ResponseCodeEnum.VALIDATE_FAILED.getCode()).message(ResponseCodeEnum.VALIDATE_FAILED.getMessage());
    }

    /**
     * 参数验证失败返回结果
     * @param message 提示信息
     */
    public static <T> ApiResult<T> validateFailed(String message) {
        return code(ResponseCodeEnum.VALIDATE_FAILED.getCode()).message(message);
    }

    /**
     * 未登录返回结果
     */
    public static <T> ApiResult<T> unauthorized() {
        return code(ResponseCodeEnum.UNAUTHENTICATED.getCode()).message(ResponseCodeEnum.UNAUTHENTICATED.getMessage());
    }

    /**
     * 未授权返回结果
     */
    public static <T> ApiResult<T> forbidden() {
        return code(ResponseCodeEnum.FORBIDDEN.getCode()).message(ResponseCodeEnum.FORBIDDEN.getMessage());
    }
}

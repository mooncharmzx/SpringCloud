package com.cn.admin;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;

@Data
public class AjaxResult {
    private Boolean success = true;
    private String message;
    private User data;

    public AjaxResult(){};

    public static AjaxResult error(Integer code,String message){
        AjaxResult ajaxResult = new AjaxResult();
        switch (code){
            case 200:
                ajaxResult.success = true;
                break;
            default:
                ajaxResult.success = false;
        }
        ajaxResult.message = message;
        return ajaxResult;
    }
}

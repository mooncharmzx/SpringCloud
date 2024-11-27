package com.cn.sce.entity;

import com.cn.sce.util.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * <p>
 * 
 * </p>
 *
 * @author lz
 * @since 2022-04-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Scuser extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String username;

    private String name;

    private Integer age;

    private BigDecimal balance;

    private String password;

    private String token;

    private Integer loginState;

    private Integer multipleLoginStatus;

    public Scuser(){}

    public Scuser(Integer id,String username,String name,String token){
        this.id = id;
        this.username = username;
        this.name = name;
        this.token = token;
    }
}

package com.cn.sce.entity;

import com.cn.sce.util.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

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

    private Integer loginState;

    private String token;

    private Integer desensitization;

    private Integer expire;

    private Date updateTime;

    public Scuser(){}

    public Scuser(Integer id, String username, String name){
        this.id = id;
        this.username = username;
        this.name = name;
    }
}

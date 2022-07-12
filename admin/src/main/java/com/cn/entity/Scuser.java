package com.cn.entity;

import com.cn.test.util.BaseEntity;
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


}

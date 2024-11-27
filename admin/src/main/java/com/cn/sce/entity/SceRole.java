package com.cn.sce.entity;

import com.cn.sce.util.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author lz
 * @since 2022-07-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SceRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

      private Integer id;

    private String code;

    private Integer idx;

    private String name;

    private Integer paraentId;


}

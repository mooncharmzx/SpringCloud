package com.cn.sce.entity;

import com.cn.sce.test.util.BaseEntity;
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
public class SceUserRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private Integer userId;

    private Integer roleId;


}

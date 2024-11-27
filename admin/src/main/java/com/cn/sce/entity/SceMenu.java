package com.cn.sce.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.cn.sce.util.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class SceMenu extends BaseEntity {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String icon;

    private Integer idx;

    private String name;

    private String url;

    private Integer paraentId;


}

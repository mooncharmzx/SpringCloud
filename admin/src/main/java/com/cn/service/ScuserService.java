package com.cn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cn.admin.Result;
import com.cn.entity.Scuser;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lz
 * @since 2022-04-25
 */
public interface ScuserService extends IService<Scuser> {
    Result getScuserById(String id);
}

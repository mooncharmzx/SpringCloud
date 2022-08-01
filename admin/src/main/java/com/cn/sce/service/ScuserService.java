package com.cn.sce.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cn.sce.admin.Result;
import com.cn.sce.entity.Scuser;

import java.util.Map;

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

    Result getScuserByName(String name);

    Result updateScuserByUserName(String username,Scuser newUser);

    Result getScuserByParams(Map<String,Object> params);

    Result updateScuserByUserId(Integer userId,Scuser newUser);
}

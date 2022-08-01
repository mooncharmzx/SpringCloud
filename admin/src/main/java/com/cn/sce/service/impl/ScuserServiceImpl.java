package com.cn.sce.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.UpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cn.sce.admin.Result;
import com.cn.sce.dao.ScuserMapper;
import com.cn.sce.entity.Scuser;
import com.cn.sce.service.ScuserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lz
 * @since 2022-04-25
 */
@Service
public class ScuserServiceImpl extends ServiceImpl<ScuserMapper, Scuser> implements ScuserService {

    @Autowired
    private ScuserMapper scuserMapper;

    @Override
    public Result getScuserById(String id) {
        Result result = new Result();
        result.setData(scuserMapper.selectById(id));
        result.setCode(0);
        return result;
    }

    @Override
    public Result getScuserByName(String name) {
        Result result = new Result();
        result.setCode(0);

        Map<String,Object> params = new HashMap<>();
        params.put("username",name);
        result.setData(scuserMapper.selectByMap(params));
        return result;
    }

    @Override
    public Result updateScuserByUserName(String username,Scuser newUser) {

        Result result = new Result();
        result.setCode(0);
        LambdaUpdateWrapper<Scuser> wrapper = new LambdaUpdateWrapper<Scuser>()
                .eq(Scuser::getUsername,username);

        int flag = scuserMapper.update(newUser,wrapper);

        if(flag > 0){

            result.setMsg("更新成功!");

        }else{

            result.setCode(-999);
            result.setMsg("更新失败！");

        }
        return result;
    }

    @Override
    public Result getScuserByParams(Map<String, Object> params) {

        Result result = new Result();
        result.setCode(0);
        result.setData(scuserMapper.selectByMap(params));
        return result;

    }

    @Override
    public Result updateScuserByUserId(Integer userId, Scuser newUser) {

        Result result = new Result();
        result.setCode(0);
        LambdaUpdateWrapper<Scuser> wrapper = new LambdaUpdateWrapper<Scuser>()
                .eq(Scuser::getId,userId);

        int flag = scuserMapper.update(newUser,wrapper);

        if(flag > 0){

            result.setMsg("更新成功!");

        }else{

            result.setCode(-999);
            result.setMsg("更新失败！");

        }
        return result;
    }

}

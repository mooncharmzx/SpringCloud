package com.cn.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cn.admin.Result;
import com.cn.dao.ScuserMapper;
import com.cn.entity.Scuser;
import com.cn.service.ScuserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}

package org.example.store.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

public class BaseService<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> {
    public IPage<T> selectByPage(Integer page, Integer size, QueryWrapper<T> queryWrapper) {
        Page<T> p = new Page<>(page, size);
        return this.getBaseMapper().selectPage(p, queryWrapper);
    }
}
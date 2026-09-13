package com.maven.tc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maven.tc.entity.Admin;
import com.maven.tc.mapper.AdminMapper;
import com.maven.tc.service.AdminService;
import com.maven.tc.vo.AdminVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Override
    //管理员登录
    public AdminVO login(String username, String password) {
        LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<>();//创建 LambdaQueryWrapper 实例
        wrapper.eq(Admin::getUsername, username)
                .eq(Admin::getPassword, password);//添加两个等值查询
        Admin admin = this.getOne(wrapper);//根据 LambdaQueryWrapper 查询单条记录
        if (admin == null) {
            return null;
        }
        AdminVO vo = new AdminVO();//创建 AdminVO 实例
        BeanUtils.copyProperties(admin, vo);//将 Admin 实体中的数据复制到 AdminVO 实例中
        return vo;
    }
}
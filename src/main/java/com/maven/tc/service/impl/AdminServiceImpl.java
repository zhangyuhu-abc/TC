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
    public AdminVO login(String username, String password) {
        LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Admin::getUsername, username)
                .eq(Admin::getPassword, password);
        Admin admin = this.getOne(wrapper);
        if (admin == null) {
            return null;
        }
        AdminVO vo = new AdminVO();
        BeanUtils.copyProperties(admin, vo);
        return vo;
    }
}
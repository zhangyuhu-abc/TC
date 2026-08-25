package com.maven.tc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.maven.tc.entity.Admin;
import com.maven.tc.vo.AdminVO;

public interface AdminService extends IService<Admin> {

    AdminVO login(String username, String password);
}
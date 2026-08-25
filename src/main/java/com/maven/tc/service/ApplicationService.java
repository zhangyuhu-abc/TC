package com.maven.tc.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.maven.tc.entity.Application;
import com.maven.tc.vo.ApplicationVO;

public interface ApplicationService extends IService<Application> {

    IPage<ApplicationVO> getApplications(Integer current, Integer size, String direction, String interviewTime);
}
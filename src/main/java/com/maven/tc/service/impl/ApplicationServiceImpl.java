package com.maven.tc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maven.tc.entity.Application;
import com.maven.tc.mapper.ApplicationMapper;
import com.maven.tc.service.ApplicationService;
import com.maven.tc.vo.ApplicationVO;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ApplicationServiceImpl extends ServiceImpl<ApplicationMapper, Application> implements ApplicationService {

    @Override
    public IPage<ApplicationVO> getApplications(Integer current, Integer size, String direction, String interviewTime) {
        Page<Application> page = new Page<>(current, size);
        LambdaQueryWrapper<Application> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(direction)) {
            wrapper.eq(Application::getDirection, direction);
        }
        if (StringUtils.hasText(interviewTime)) {
            wrapper.eq(Application::getInterviewTime, interviewTime);
        }
        Page<Application> resultPage = this.page(page, wrapper);
        return resultPage.convert(application -> {
            ApplicationVO vo = new ApplicationVO();
            org.springframework.beans.BeanUtils.copyProperties(application, vo);
            return vo;
        });
    }
}
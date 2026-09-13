package com.maven.tc.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.maven.tc.dto.AdminApplicationDTO;
import com.maven.tc.dto.ApplicationDTO;
import com.maven.tc.entity.Application;
import com.maven.tc.vo.ApplicationVO;
import com.maven.tc.vo.DirectionStatisticsVO;

import java.util.List;

public interface ApplicationService extends IService<Application> {

    IPage<ApplicationVO> getApplications(Integer current, Integer size, String direction, String interviewTime);

    List<DirectionStatisticsVO> getDirectionStatistics();

    void apply(ApplicationDTO dto);

    void addApplication(AdminApplicationDTO dto);

    void updateApplication(Long id, AdminApplicationDTO dto);

    void deleteApplication(Long id);
}
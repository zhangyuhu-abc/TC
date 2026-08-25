package com.maven.tc.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.maven.tc.entity.InterviewResult;
import com.maven.tc.vo.InterviewResultVO;

public interface InterviewResultService extends IService<InterviewResult> {

    IPage<InterviewResultVO> getInterviewResults(Integer current, Integer size, String direction);
}
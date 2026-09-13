package com.maven.tc.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.maven.tc.dto.SecondInterviewDTO;
import com.maven.tc.dto.UpdateSecondInterviewDTO;
import com.maven.tc.entity.SecondInterview;
import com.maven.tc.vo.InterviewResultVO;
import com.maven.tc.vo.SecondInterviewVO;

import java.util.List;

public interface SecondInterviewService extends IService<SecondInterview> {

    IPage<SecondInterviewVO> getSecondInterviews(Integer current, Integer size, String direction);

    void addSecondInterview(SecondInterviewDTO dto);

    String batchAddSecondInterview();

    List<InterviewResultVO> getPassedFirstNotSecond();

    void updateSecondInterview(Long id, UpdateSecondInterviewDTO dto);

    void deleteSecondInterview(Long id);
}
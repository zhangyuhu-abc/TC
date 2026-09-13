package com.maven.tc.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.maven.tc.dto.UpdateWalkInInterviewDTO;
import com.maven.tc.dto.WalkInInterviewDTO;
import com.maven.tc.entity.WalkInInterview;
import com.maven.tc.vo.WalkInInterviewVO;

public interface WalkInInterviewService extends IService<WalkInInterview> {

    IPage<WalkInInterviewVO> getWalkInInterviews(Integer current, Integer size, String direction);

    void addWalkInInterview(WalkInInterviewDTO dto);

    void studentWalkInApply(WalkInInterviewDTO dto);

    void updateWalkInInterview(Long id, UpdateWalkInInterviewDTO dto);

    void deleteWalkInInterview(Long id);
}
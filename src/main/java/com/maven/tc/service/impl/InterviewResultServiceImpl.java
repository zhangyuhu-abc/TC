package com.maven.tc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maven.tc.entity.InterviewResult;
import com.maven.tc.mapper.InterviewResultMapper;
import com.maven.tc.service.InterviewResultService;
import com.maven.tc.vo.InterviewResultVO;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class InterviewResultServiceImpl extends ServiceImpl<InterviewResultMapper, InterviewResult> implements InterviewResultService {

    @Override
    public IPage<InterviewResultVO> getInterviewResults(Integer current, Integer size, String direction) {
        Page<InterviewResult> page = new Page<>(current, size);
        LambdaQueryWrapper<InterviewResult> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(direction)) {
            wrapper.eq(InterviewResult::getDirection, direction);
        }
        Page<InterviewResult> resultPage = this.page(page, wrapper);
        return resultPage.convert(interviewResult -> {
            InterviewResultVO vo = new InterviewResultVO();
            org.springframework.beans.BeanUtils.copyProperties(interviewResult, vo);
            return vo;
        });
    }
}
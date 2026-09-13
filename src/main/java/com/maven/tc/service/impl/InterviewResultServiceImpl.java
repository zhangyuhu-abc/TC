package com.maven.tc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maven.tc.entity.Admission;
import com.maven.tc.entity.InterviewResult;
import com.maven.tc.entity.SecondInterview;
import com.maven.tc.entity.WalkInInterview;
import com.maven.tc.mapper.AdmissionMapper;
import com.maven.tc.mapper.InterviewResultMapper;
import com.maven.tc.service.InterviewResultService;
import com.maven.tc.service.SecondInterviewService;
import com.maven.tc.service.WalkInInterviewService;
import com.maven.tc.dto.AddInterviewResultDTO;
import com.maven.tc.dto.UpdateInterviewResultDTO;
import com.maven.tc.vo.InterviewResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class InterviewResultServiceImpl extends ServiceImpl<InterviewResultMapper, InterviewResult> implements InterviewResultService {

    @Autowired
    @Lazy
    private SecondInterviewService secondInterviewService;

    @Autowired
    @Lazy
    private WalkInInterviewService walkInInterviewService;

    @Autowired
    private AdmissionMapper admissionMapper;

    @Override
    //查看面试结果
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
            
            // 查询二面结果
            LambdaQueryWrapper<SecondInterview> secondWrapper = new LambdaQueryWrapper<>();
            secondWrapper.eq(SecondInterview::getStudentId, interviewResult.getStudentId());
            SecondInterview secondInterview = secondInterviewService.getOne(secondWrapper);
            if (secondInterview != null) {
                vo.setSecondStatus(secondInterview.getStatus());
                vo.setSecondRemark(secondInterview.getRemark());
            }
            
            // 查询霸面结果
            LambdaQueryWrapper<WalkInInterview> walkInWrapper = new LambdaQueryWrapper<>();
            walkInWrapper.eq(WalkInInterview::getStudentId, interviewResult.getStudentId());
            WalkInInterview walkInInterview = walkInInterviewService.getOne(walkInWrapper);
            if (walkInInterview != null) {
                vo.setWalkInStatus(walkInInterview.getStatus());
                vo.setWalkInRemark(walkInInterview.getRemark());
            }
            
            // 查询录取结果
            LambdaQueryWrapper<Admission> admissionWrapper = new LambdaQueryWrapper<>();
            admissionWrapper.eq(Admission::getStudentId, interviewResult.getStudentId());
            Admission admission = admissionMapper.selectOne(admissionWrapper);
            if (admission != null) {
                vo.setAdmitted(true);
            } else {
                vo.setAdmitted(false);
            }
            
            return vo;
        });
    }

    @Override
    //添加面试结果
    public void addInterviewResult(AddInterviewResultDTO dto) {
        if ("一面".equals(dto.getType())) {
            addFirstInterview(dto);
        } else if ("二面".equals(dto.getType())) {
            addSecondInterview(dto);
        } else if ("霸面".equals(dto.getType())) {
            addWalkInInterview(dto);
        } else {
            throw new RuntimeException("面试类型错误，只能是一面、二面或霸面");
        }
    }

    //录入一面
    private void addFirstInterview(AddInterviewResultDTO dto) {
        LambdaQueryWrapper<InterviewResult> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(InterviewResult::getStudentId, dto.getStudentId());
        if (this.count(wrapper) > 0) {
            throw new RuntimeException("该学生已有一面结果");
        }
        
        InterviewResult interviewResult = new InterviewResult();
        BeanUtils.copyProperties(dto, interviewResult);
        this.save(interviewResult);
    }

    //录入二面
    private void addSecondInterview(AddInterviewResultDTO dto) {
        LambdaQueryWrapper<InterviewResult> firstWrapper = new LambdaQueryWrapper<>();
        firstWrapper.eq(InterviewResult::getStudentId, dto.getStudentId());
        InterviewResult firstResult = this.getOne(firstWrapper);
        
        if (firstResult == null) {
            throw new RuntimeException("该学生暂无一面结果");
        }
        if (!"已通过".equals(firstResult.getStatus())) {
            throw new RuntimeException("该学生一面未通过，无法录入二面");
        }
        
        LambdaQueryWrapper<SecondInterview> secondWrapper = new LambdaQueryWrapper<>();
        secondWrapper.eq(SecondInterview::getStudentId, dto.getStudentId());
        if (secondInterviewService.count(secondWrapper) > 0) {
            throw new RuntimeException("该学生已有二面结果");
        }
        
        SecondInterview secondInterview = new SecondInterview();
        BeanUtils.copyProperties(dto, secondInterview);
        secondInterviewService.save(secondInterview);
    }

    //录入霸面
    private void addWalkInInterview(AddInterviewResultDTO dto) {
        LambdaQueryWrapper<WalkInInterview> walkInWrapper = new LambdaQueryWrapper<>();
        walkInWrapper.eq(WalkInInterview::getStudentId, dto.getStudentId());
        if (walkInInterviewService.count(walkInWrapper) > 0) {
            throw new RuntimeException("该学生已有霸面结果");
        }
        
        WalkInInterview walkInInterview = new WalkInInterview();
        BeanUtils.copyProperties(dto, walkInInterview);
        walkInInterviewService.save(walkInInterview);
    }

    @Override
    //修改一面信息
    public void updateInterviewResult(Long id, UpdateInterviewResultDTO dto) {
        InterviewResult interviewResult = new InterviewResult();
        interviewResult.setId(id);
        org.springframework.beans.BeanUtils.copyProperties(dto, interviewResult);
        this.updateById(interviewResult);
    }
}
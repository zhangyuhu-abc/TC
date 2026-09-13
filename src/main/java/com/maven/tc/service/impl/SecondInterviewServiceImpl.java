package com.maven.tc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maven.tc.dto.SecondInterviewDTO;
import com.maven.tc.dto.UpdateSecondInterviewDTO;
import com.maven.tc.entity.Admission;
import com.maven.tc.entity.InterviewResult;
import com.maven.tc.entity.SecondInterview;
import com.maven.tc.mapper.SecondInterviewMapper;
import com.maven.tc.service.AdmissionService;
import com.maven.tc.service.InterviewResultService;
import com.maven.tc.service.SecondInterviewService;
import com.maven.tc.vo.InterviewResultVO;
import com.maven.tc.vo.SecondInterviewVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class SecondInterviewServiceImpl extends ServiceImpl<SecondInterviewMapper, SecondInterview> implements SecondInterviewService {

    @Autowired
    private InterviewResultService interviewResultService;

    @Autowired
    private AdmissionService admissionService;

    @Override
    //查看二面学生
    public IPage<SecondInterviewVO> getSecondInterviews(Integer current, Integer size, String direction) {
        Page<SecondInterview> page = new Page<>(current, size);
        LambdaQueryWrapper<SecondInterview> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(direction)) //条件判断语句，用于检查 direction 参数是否有效
        {
            wrapper.eq(SecondInterview::getDirection, direction);
        }
        Page<SecondInterview> resultPage = this.page(page, wrapper);
        return resultPage.convert(secondInterview -> {
            SecondInterviewVO vo = new SecondInterviewVO();
            org.springframework.beans.BeanUtils.copyProperties(secondInterview, vo);
            return vo;
        });
    }

    @Override
    //添加二面学生
    public void addSecondInterview(SecondInterviewDTO dto) {
        LambdaQueryWrapper<InterviewResult> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(InterviewResult::getStudentId, dto.getStudentId());
        InterviewResult firstResult = interviewResultService.getOne(wrapper);
        
        if (firstResult == null) {
            throw new RuntimeException("该学生暂无一面结果");
        }
        if (!"已通过".equals(firstResult.getStatus())) {
            throw new RuntimeException("该学生一面未通过，无法参加二面");
        }
        
        LambdaQueryWrapper<SecondInterview> checkWrapper = new LambdaQueryWrapper<>();
        checkWrapper.eq(SecondInterview::getStudentId, dto.getStudentId());//根据学号查询二面学生
        if (this.count(checkWrapper) > 0) {
            throw new RuntimeException("该学生已在二面名单中");
        }
        
        SecondInterview secondInterview = new SecondInterview();
        BeanUtils.copyProperties(dto, secondInterview);
        secondInterview.setStatus("待面试");
        secondInterview.setRemark("");
        this.save(secondInterview);
    }

    @Override
    public String batchAddSecondInterview() {
        LambdaQueryWrapper<InterviewResult> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(InterviewResult::getStatus, "已通过");
        List<InterviewResult> passedStudents = interviewResultService.list(wrapper);
        
        if (passedStudents.isEmpty()) {
            throw new RuntimeException("暂无一面通过的学生");
        }
        
        int addedCount = 0;
        for (InterviewResult result : passedStudents) {
            LambdaQueryWrapper<SecondInterview> checkWrapper = new LambdaQueryWrapper<>();
            checkWrapper.eq(SecondInterview::getStudentId, result.getStudentId());
            if (this.count(checkWrapper) == 0) {
                SecondInterview secondInterview = new SecondInterview();
                secondInterview.setStudentId(result.getStudentId());
                secondInterview.setStudentName(result.getStudentName());
                secondInterview.setDirection(result.getDirection());
                this.save(secondInterview);
                addedCount++;
            }
        }
        
        return "成功添加 " + addedCount + " 名学生到二面";
    }

    @Override
    //查看一面通过但未参加二面的学生
    public List<InterviewResultVO> getPassedFirstNotSecond() {
        LambdaQueryWrapper<InterviewResult> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(InterviewResult::getStatus, "已通过");
        List<InterviewResult> passedStudents = interviewResultService.list(wrapper);
        
        List<InterviewResultVO> result = new ArrayList<>();
        for (InterviewResult interviewResult : passedStudents) {
            LambdaQueryWrapper<SecondInterview> checkWrapper = new LambdaQueryWrapper<>();
            checkWrapper.eq(SecondInterview::getStudentId, interviewResult.getStudentId());
            if (this.count(checkWrapper) == 0) {
                InterviewResultVO vo = new InterviewResultVO();
                BeanUtils.copyProperties(interviewResult, vo);
                result.add(vo);
            }
        }
        
        return result;
    }

    @Override
    public void updateSecondInterview(Long id, UpdateSecondInterviewDTO dto) {
        SecondInterview secondInterview = this.getById(id);
        if (secondInterview == null) {
            throw new RuntimeException("二面记录不存在");
        }
        
        secondInterview.setId(id);
        BeanUtils.copyProperties(dto, secondInterview);
        this.updateById(secondInterview);
        
        if ("已通过".equals(dto.getStatus())) {
            LambdaQueryWrapper<Admission> checkWrapper = new LambdaQueryWrapper<>();
            checkWrapper.eq(Admission::getStudentId, secondInterview.getStudentId());
            if (this.admissionService.count(checkWrapper) == 0) {
                Admission admission = new Admission();
                admission.setStudentId(secondInterview.getStudentId());
                admission.setStudentName(secondInterview.getStudentName());
                admission.setEmail(secondInterview.getEmail());
                admission.setPhone(secondInterview.getPhone());
                admission.setDirection(secondInterview.getDirection());
                admission.setRemark(dto.getRemark());
                this.admissionService.save(admission);
            }
        }
    }

    @Override
    public void deleteSecondInterview(Long id) {
        this.removeById(id);
    }
}
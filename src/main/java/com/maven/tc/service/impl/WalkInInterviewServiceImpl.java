package com.maven.tc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maven.tc.dto.UpdateWalkInInterviewDTO;
import com.maven.tc.dto.WalkInInterviewDTO;
import com.maven.tc.entity.Admission;
import com.maven.tc.entity.WalkInInterview;
import com.maven.tc.mapper.WalkInInterviewMapper;
import com.maven.tc.service.AdmissionService;
import com.maven.tc.service.WalkInInterviewService;
import com.maven.tc.vo.WalkInInterviewVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class WalkInInterviewServiceImpl extends ServiceImpl<WalkInInterviewMapper, WalkInInterview> implements WalkInInterviewService {

    @Autowired
    private AdmissionService admissionService;

    @Override
    //查看霸面学生
    public IPage<WalkInInterviewVO> getWalkInInterviews(Integer current, Integer size, String direction) {
        Page<WalkInInterview> page = new Page<>(current, size);
        LambdaQueryWrapper<WalkInInterview> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(direction)) {
            wrapper.eq(WalkInInterview::getDirection, direction);
        }
        wrapper.orderByDesc(WalkInInterview::getId);
        Page<WalkInInterview> resultPage = this.page(page, wrapper);
        return resultPage.convert(walkIn -> {
            WalkInInterviewVO vo = new WalkInInterviewVO();
            org.springframework.beans.BeanUtils.copyProperties(walkIn, vo);
            return vo;
        });
    }

    @Override
    //添加霸面学生
    public void addWalkInInterview(WalkInInterviewDTO dto) {
        LambdaQueryWrapper<WalkInInterview> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WalkInInterview::getStudentId, dto.getStudentId());
        if (this.count(wrapper) > 0) {
            throw new RuntimeException("该学生已在霸面名单中");
        }
        
        WalkInInterview walkInInterview = new WalkInInterview();
        BeanUtils.copyProperties(dto, walkInInterview);
        walkInInterview.setStatus("待面试");
        walkInInterview.setRemark("");
        this.save(walkInInterview);
    }

    @Override
    //霸面报名
    public void studentWalkInApply(WalkInInterviewDTO dto) {
        LambdaQueryWrapper<WalkInInterview> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WalkInInterview::getStudentId, dto.getStudentId());
        if (this.count(wrapper) > 0) {
            throw new RuntimeException("您已在霸面名单中");
        }
        
        WalkInInterview walkInInterview = new WalkInInterview();
        BeanUtils.copyProperties(dto, walkInInterview);
        walkInInterview.setStatus("待面试");
        walkInInterview.setRemark("");
        this.save(walkInInterview);
    }

    @Override
    //修改霸面信息
    public void updateWalkInInterview(Long id, UpdateWalkInInterviewDTO dto) {
        WalkInInterview walkInInterview = this.getById(id);
        if (walkInInterview == null) {
            throw new RuntimeException("霸面记录不存在");
        }
        
        walkInInterview.setId(id);
        BeanUtils.copyProperties(dto, walkInInterview);
        this.updateById(walkInInterview);
        
        if ("已通过".equals(dto.getStatus())) {
            LambdaQueryWrapper<Admission> checkWrapper = new LambdaQueryWrapper<>();
            checkWrapper.eq(Admission::getStudentId, walkInInterview.getStudentId());
            if (this.admissionService.count(checkWrapper) == 0) {
                Admission admission = new Admission();
                admission.setStudentId(walkInInterview.getStudentId());
                admission.setStudentName(walkInInterview.getStudentName());
                admission.setEmail(walkInInterview.getEmail());
                admission.setPhone(walkInInterview.getPhone());
                admission.setDirection(walkInInterview.getDirection());
                admission.setRemark(dto.getRemark());
                this.admissionService.save(admission);
            }
        }
    }

    @Override
    //删除霸面信息
    public void deleteWalkInInterview(Long id) {
        this.removeById(id);
    }
}
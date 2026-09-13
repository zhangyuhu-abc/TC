package com.maven.tc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maven.tc.dto.NoticeDTO;
import com.maven.tc.entity.Notice;
import com.maven.tc.mapper.NoticeMapper;
import com.maven.tc.service.NoticeService;
import com.maven.tc.vo.NoticeVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {

    @Override
    //查看通知
    public IPage<NoticeVO> getNotices(Integer current, Integer size, String type) {
        Page<Notice> page = new Page<>(current, size);//创建分页对象
        LambdaQueryWrapper<Notice> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(type)) {
            wrapper.eq(Notice::getType, type);
        }
        wrapper.orderByDesc(Notice::getPublishTime);
        Page<Notice> resultPage = this.page(page, wrapper);
        return resultPage.convert(notice -> {
            NoticeVO vo = new NoticeVO();
            org.springframework.beans.BeanUtils.copyProperties(notice, vo);
            return vo;
        });
    }

    @Override
    public void addNotice(NoticeDTO dto) {
        Notice notice = new Notice();
        BeanUtils.copyProperties(dto, notice);
        this.save(notice);
    }

    @Override
    public void updateNotice(Long id, NoticeDTO dto) {
        Notice notice = new Notice();
        notice.setId(id);
        BeanUtils.copyProperties(dto, notice);
        this.updateById(notice);
    }

    @Override
    public void deleteNotice(Long id) {
        this.removeById(id);
    }

    @Override
    public NoticeVO getNoticeDetail(Long id) {
        Notice notice = this.getById(id);
        if (notice == null) {
            return null;
        }
        NoticeVO vo = new NoticeVO();
        BeanUtils.copyProperties(notice, vo);
        return vo;
    }
}
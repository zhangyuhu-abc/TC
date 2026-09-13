package com.maven.tc.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.maven.tc.dto.NoticeDTO;
import com.maven.tc.entity.Notice;
import com.maven.tc.vo.NoticeVO;

public interface NoticeService extends IService<Notice> {

    IPage<NoticeVO> getNotices(Integer current, Integer size, String type);

    void addNotice(NoticeDTO dto);

    void updateNotice(Long id, NoticeDTO dto);

    void deleteNotice(Long id);

    NoticeVO getNoticeDetail(Long id);
}
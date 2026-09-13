package com.maven.tc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maven.tc.entity.Notice;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NoticeMapper extends BaseMapper<Notice> {
}
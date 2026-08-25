-- =============================================
-- ThoughtCoding 实验室纳新系统 - 数据库初始化
-- =============================================

CREATE DATABASE IF NOT EXISTS tc_lab DEFAULT CHARACTER SET utf8mb4;
USE tc_lab;

-- 学生表
CREATE TABLE IF NOT EXISTS student (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
    name VARCHAR(50) NOT NULL COMMENT '姓名',
    student_id VARCHAR(20) NOT NULL UNIQUE COMMENT '学号',
    password VARCHAR(100) NOT NULL COMMENT '密码',
    major VARCHAR(100) NOT NULL COMMENT '专业',
    email VARCHAR(100) NOT NULL COMMENT '邮箱',
    phone VARCHAR(20) NOT NULL COMMENT '电话'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学生表';

-- 管理员表
CREATE TABLE IF NOT EXISTS admin (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(100) NOT NULL COMMENT '密码',
    phone VARCHAR(20) COMMENT '电话'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='管理员表';

-- 报名表（面试学生表）
CREATE TABLE IF NOT EXISTS application (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
    name VARCHAR(50) NOT NULL COMMENT '姓名',
    email VARCHAR(100) NOT NULL COMMENT '邮箱',
    direction VARCHAR(50) NOT NULL COMMENT '意向方向',
    phone VARCHAR(20) NOT NULL COMMENT '电话',
    interview_time VARCHAR(50) COMMENT '面试时间',
    student_id VARCHAR(20) COMMENT '学号'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='报名表';

-- 面试结果表
CREATE TABLE IF NOT EXISTS interview_result (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
    student_id VARCHAR(20) NOT NULL COMMENT '学号',
    student_name VARCHAR(50) NOT NULL COMMENT '姓名',
    status VARCHAR(20) NOT NULL DEFAULT '待面试' COMMENT '面试结果',
    direction VARCHAR(50) COMMENT '方向',
    remark VARCHAR(255) COMMENT '备注'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='面试结果表';

-- 默认管理员账号: admin / admin123
INSERT INTO admin (username, password, phone) VALUES ('admin', 'admin123', '13800000000');
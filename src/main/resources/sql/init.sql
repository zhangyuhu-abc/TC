-- =============================================
-- ThoughtCoding 实验室纳新系统 - 数据库初始化
-- =============================================

USE tc_lab;

-- 学生表 (对应 Student.java)
CREATE TABLE student (
                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                         name VARCHAR(50) NOT NULL COMMENT '姓名',
                         student_id VARCHAR(20) NOT NULL UNIQUE COMMENT '学号',
                         password VARCHAR(255) NOT NULL COMMENT '密码',
                         major VARCHAR(50) NOT NULL COMMENT '专业',
                         email VARCHAR(100) NOT NULL COMMENT '邮箱',
                         phone VARCHAR(11) NOT NULL COMMENT '手机号'
);

-- 管理员表 (对应 Admin.java)
CREATE TABLE admin (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
                       password VARCHAR(255) NOT NULL COMMENT '密码',
                       phone VARCHAR(11) NOT NULL COMMENT '手机号'
);

-- 报名表 (对应 Application.java)
CREATE TABLE application (
                             id BIGINT PRIMARY KEY AUTO_INCREMENT,
                             name VARCHAR(50) NOT NULL COMMENT '姓名',
                             email VARCHAR(100) NOT NULL COMMENT '邮箱',
                             direction VARCHAR(50) NOT NULL COMMENT '面试方向',
                             phone VARCHAR(11) NOT NULL COMMENT '手机号',
                             interview_time VARCHAR(50) NOT NULL COMMENT '面试时间',
                             student_id VARCHAR(20) NOT NULL COMMENT '学号'
);

-- 面试结果表 (对应 InterviewResult.java)
CREATE TABLE interview_result (
                                  id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                  student_id VARCHAR(20) NOT NULL COMMENT '学号',
                                  student_name VARCHAR(50) NOT NULL COMMENT '学生姓名',
                                  status VARCHAR(20) NOT NULL COMMENT '一面状态',
                                  direction VARCHAR(50) NOT NULL COMMENT '面试方向',
                                  remark TEXT COMMENT '备注'
);

-- 二面信息表 (对应 SecondInterview.java)
CREATE TABLE second_interview (
                                  id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                  student_id VARCHAR(20) NOT NULL COMMENT '学号',
                                  student_name VARCHAR(50) NOT NULL COMMENT '学生姓名',
                                  email VARCHAR(100) NOT NULL COMMENT '邮箱',
                                  phone VARCHAR(11) NOT NULL COMMENT '手机号',
                                  direction VARCHAR(50) NOT NULL COMMENT '面试方向',
                                  status VARCHAR(20) NOT NULL COMMENT '二面状态',
                                  remark TEXT COMMENT '二面备注'
);

-- 通知表 (对应 Notice.java)
CREATE TABLE notice (
                        id BIGINT PRIMARY KEY AUTO_INCREMENT,
                        title VARCHAR(100) NOT NULL COMMENT '通知标题',
                        content TEXT NOT NULL COMMENT '通知内容',
                        type VARCHAR(50) NOT NULL COMMENT '通知类型',
                        publish_time VARCHAR(50) NOT NULL COMMENT '发布时间'
);

-- 霸面表 (对应 WalkInInterview.java)
CREATE TABLE walk_in_interview (
                                  id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                  student_id VARCHAR(20) NOT NULL COMMENT '学号',
                                  student_name VARCHAR(50) NOT NULL COMMENT '学生姓名',
                                  email VARCHAR(100) NOT NULL COMMENT '邮箱',
                                  phone VARCHAR(11) NOT NULL COMMENT '手机号',
                                  direction VARCHAR(50) NOT NULL COMMENT '面试方向',
                                  status VARCHAR(20) NOT NULL COMMENT '霸面状态',
                                  remark TEXT COMMENT '备注'
);

-- 录取表 (对应 Admission.java)
CREATE TABLE admission (
                           id BIGINT PRIMARY KEY AUTO_INCREMENT,
                           student_id VARCHAR(20) NOT NULL COMMENT '学号',
                           student_name VARCHAR(50) NOT NULL COMMENT '学生姓名',
                           email VARCHAR(100) NOT NULL COMMENT '邮箱',
                           phone VARCHAR(11) NOT NULL COMMENT '手机号',
                           direction VARCHAR(50) NOT NULL COMMENT '面试方向',
                           remark TEXT COMMENT '备注'
);

-- 示例通知数据
INSERT INTO notice (title, content, type, publish_time) VALUES 
('宣讲会通知', 'ThoughtCoding实验室宣讲会将于本周五下午2点在学术报告厅举行，欢迎全体报名同学参加。', '宣讲会', '2026-09-01 10:00:00'),
('一面面试安排', '一面面试将于本周六上午9点开始，请报名同学携带学生证准时到达实验楼301教室。', '面试安排', '2026-09-05 08:00:00'),
('二面面试通知', '恭喜通过一面的同学！二面将于下周一上午9点在实验楼402教室进行，请做好准备。', '面试安排', '2026-09-10 14:00:00');

-- 默认管理员账号: admin / admin123
INSERT INTO admin (username, password, phone) VALUES ('admin', 'admin123', '13800000000');
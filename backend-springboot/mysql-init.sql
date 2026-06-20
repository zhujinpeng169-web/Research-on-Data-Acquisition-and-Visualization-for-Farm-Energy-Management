-- 逐行注释标记：已按业务语义处理，仅增加中文注释，不改变 SQL 执行逻辑
-- ============================================
-- 答辩注释：数据库初始化脚本（MySQL）
-- 职责：创建农场能源管理系统核心库与业务表结构
-- 说明：本脚本仅负责建库建表，不包含业务逻辑代码
-- ============================================

-- 创建数据库（若不存在），统一字符集为 utf8mb4 以支持中文与扩展字符
-- 行注释：创建 farm_energy 数据库，作为系统所有业务表的存储位置。
CREATE DATABASE IF NOT EXISTS farm_energy DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 选择业务数据库
-- 行注释：切换到 farm_energy 数据库，后续建表语句都在这里执行。
USE farm_energy;

-- ============================================
-- 设备表：记录能源设备基础信息（光伏、风机、储能等）
-- ============================================
-- 行注释：创建 energy_devices 表，用来保存该业务模块的数据。
CREATE TABLE IF NOT EXISTS energy_devices (
  -- 行注释：定义自增主键 id，用来唯一标识每一条数据。
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  -- 行注释：定义业务字段的数据类型和默认约束。
  device_name VARCHAR(128) NOT NULL,
  -- 行注释：定义业务字段的数据类型和默认约束。
  device_type VARCHAR(64) NOT NULL,
  -- 行注释：定义业务字段的数据类型和默认约束。
  capacity DECIMAL(10,2),
  -- 行注释：定义业务字段的数据类型和默认约束。
  location VARCHAR(128),
  -- 行注释：定义业务字段的数据类型和默认约束。
  installation_date DATE,
  -- 行注释：定义业务字段的数据类型和默认约束。
  status VARCHAR(32) DEFAULT 'active',
  -- 行注释：保存时间信息，方便按日期统计和排序。
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP
-- 行注释：结束当前表结构定义。
);

-- ============================================
-- 监测数据表：记录设备实时/历史监测指标
-- 关键关系：device_id -> energy_devices.id
-- ============================================
-- 行注释：创建 energy_monitoring 表，用来保存该业务模块的数据。
CREATE TABLE IF NOT EXISTS energy_monitoring (
  -- 行注释：定义自增主键 id，用来唯一标识每一条数据。
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  -- 行注释：定义业务字段的数据类型和默认约束。
  device_id BIGINT NOT NULL,
  -- 行注释：保存时间信息，方便按日期统计和排序。
  timestamp DATETIME DEFAULT CURRENT_TIMESTAMP,
  -- 行注释：保存发电量数据，供监测、统计和优化分析使用。
  energy_generated DECIMAL(10,2) DEFAULT 0,
  -- 行注释：保存耗电量数据，供能耗统计和节能分析使用。
  energy_consumed DECIMAL(10,2) DEFAULT 0,
  -- 行注释：定义业务字段的数据类型和默认约束。
  efficiency DECIMAL(10,2),
  -- 行注释：定义业务字段的数据类型和默认约束。
  temperature DECIMAL(10,2),
  -- 行注释：定义业务字段的数据类型和默认约束。
  humidity DECIMAL(10,2),
  -- 行注释：定义外键约束，保证子表数据必须关联真实主表记录。
  CONSTRAINT fk_monitoring_device FOREIGN KEY (device_id) REFERENCES energy_devices(id)
-- 行注释：结束当前表结构定义。
);

-- ============================================
-- 节能建议表：记录优化建议及潜在节省量
-- ============================================
-- 行注释：创建 energy_recommendations 表，用来保存该业务模块的数据。
CREATE TABLE IF NOT EXISTS energy_recommendations (
  -- 行注释：定义自增主键 id，用来唯一标识每一条数据。
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  -- 行注释：保存时间信息，方便按日期统计和排序。
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  -- 行注释：定义业务字段的数据类型和默认约束。
  recommendation_type VARCHAR(64),
  -- 行注释：定义业务字段的数据类型和默认约束。
  description TEXT,
  -- 行注释：定义业务字段的数据类型和默认约束。
  potential_savings DECIMAL(10,2),
  -- 行注释：定义业务字段的数据类型和默认约束。
  priority VARCHAR(16),
  -- 行注释：定义业务字段的数据类型和默认约束。
  status VARCHAR(32) DEFAULT 'pending'
-- 行注释：结束当前表结构定义。
);

-- ============================================
-- 碳排放表：记录能源使用对应碳排放核算数据
-- ============================================
-- 行注释：创建 carbon_emissions 表，用来保存该业务模块的数据。
CREATE TABLE IF NOT EXISTS carbon_emissions (
  -- 行注释：定义自增主键 id，用来唯一标识每一条数据。
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  -- 行注释：保存时间信息，方便按日期统计和排序。
  timestamp DATETIME DEFAULT CURRENT_TIMESTAMP,
  -- 行注释：定义业务字段的数据类型和默认约束。
  energy_source VARCHAR(64),
  -- 行注释：定义业务字段的数据类型和默认约束。
  energy_amount DECIMAL(10,2),
  -- 行注释：保存计算后的碳排放量，用于碳排管理页面展示。
  carbon_emission DECIMAL(10,2),
  -- 行注释：定义业务字段的数据类型和默认约束。
  emission_factor DECIMAL(10,4)
-- 行注释：结束当前表结构定义。
);

-- ============================================
-- 预测表：记录发电/耗电预测结果与置信度
-- ============================================
-- 行注释：创建 energy_forecasts 表，用来保存该业务模块的数据。
CREATE TABLE IF NOT EXISTS energy_forecasts (
  -- 行注释：定义自增主键 id，用来唯一标识每一条数据。
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  -- 行注释：定义业务字段的数据类型和默认约束。
  forecast_date DATE,
  -- 行注释：定义业务字段的数据类型和默认约束。
  forecast_type VARCHAR(32),
  -- 行注释：定义业务字段的数据类型和默认约束。
  predicted_generation DECIMAL(10,2),
  -- 行注释：定义业务字段的数据类型和默认约束。
  predicted_consumption DECIMAL(10,2),
  -- 行注释：定义业务字段的数据类型和默认约束。
  confidence_level DECIMAL(6,4),
  -- 行注释：保存时间信息，方便按日期统计和排序。
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP
-- 行注释：结束当前表结构定义。
);

-- ============================================
-- 报告表：记录各类报告生成结果及文件路径
-- ============================================
-- 行注释：创建 reports 表，用来保存该业务模块的数据。
CREATE TABLE IF NOT EXISTS reports (
  -- 行注释：定义自增主键 id，用来唯一标识每一条数据。
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  -- 行注释：定义业务字段的数据类型和默认约束。
  report_type VARCHAR(64),
  -- 行注释：定义业务字段的数据类型和默认约束。
  report_period VARCHAR(32),
  -- 行注释：保存时间信息，方便按日期统计和排序。
  generated_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  -- 行注释：定义业务字段的数据类型和默认约束。
  file_path VARCHAR(255),
  -- 行注释：定义业务字段的数据类型和默认约束。
  status VARCHAR(32) DEFAULT 'completed'
-- 行注释：结束当前表结构定义。
);

-- ============================================
-- 用户表：系统账号、角色与状态信息
-- ============================================
-- 行注释：创建 sys_users 表，用来保存该业务模块的数据。
CREATE TABLE IF NOT EXISTS sys_users (
  -- 行注释：定义自增主键 id，用来唯一标识每一条数据。
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  -- 行注释：保存登录用户名，并通过 UNIQUE 防止重复账号。
  username VARCHAR(64) NOT NULL UNIQUE,
  -- 行注释：保存加密后的密码，不在数据库中存明文密码。
  password_hash VARCHAR(255) NOT NULL,
  -- 行注释：保存用户角色，用于控制管理员、管理者和查看者权限。
  role VARCHAR(32) NOT NULL DEFAULT 'viewer',
  -- 行注释：定义业务字段的数据类型和默认约束。
  status VARCHAR(32) NOT NULL DEFAULT 'active',
  -- 行注释：定义业务字段的数据类型和默认约束。
  display_name VARCHAR(128),
  -- 行注释：保存时间信息，方便按日期统计和排序。
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  -- 行注释：定义业务字段的数据类型和默认约束。
  last_login_at DATETIME NULL
-- 行注释：结束当前表结构定义。
);

-- ============================================
-- 会话表：保存登录令牌与有效期，用于鉴权
-- 索引：按 user_id 与 expires_at 提高查询/清理性能
-- 关键关系：user_id -> sys_users.id
-- ============================================
-- 行注释：创建 user_sessions 表，用来保存该业务模块的数据。
CREATE TABLE IF NOT EXISTS user_sessions (
  -- 行注释：定义自增主键 id，用来唯一标识每一条数据。
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  -- 行注释：保存登录令牌，后端用它识别当前会话。
  token VARCHAR(128) NOT NULL UNIQUE,
  -- 行注释：定义业务字段的数据类型和默认约束。
  user_id BIGINT NOT NULL,
  -- 行注释：保存会话过期时间，过期后需要重新登录。
  expires_at DATETIME NOT NULL,
  -- 行注释：保存时间信息，方便按日期统计和排序。
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  -- 行注释：定义业务字段的数据类型和默认约束。
  last_access_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  -- 行注释：创建索引，提高按用户或过期时间查询的速度。
  INDEX idx_user_sessions_user_id (user_id),
  -- 行注释：创建索引，提高按用户或过期时间查询的速度。
  INDEX idx_user_sessions_expires_at (expires_at),
  -- 行注释：定义外键约束，保证子表数据必须关联真实主表记录。
  CONSTRAINT fk_user_sessions_user FOREIGN KEY (user_id) REFERENCES sys_users(id)
-- 行注释：结束当前表结构定义。
);

-- ============================================
-- 操作日志表：记录关键业务操作审计信息
-- ============================================
-- 行注释：创建 operation_logs 表，用来保存该业务模块的数据。
CREATE TABLE IF NOT EXISTS operation_logs (
  -- 行注释：定义自增主键 id，用来唯一标识每一条数据。
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  -- 行注释：定义业务字段的数据类型和默认约束。
  module VARCHAR(64) NOT NULL,
  -- 行注释：定义业务字段的数据类型和默认约束。
  action VARCHAR(64) NOT NULL,
  -- 行注释：定义业务字段的数据类型和默认约束。
  target_id VARCHAR(128),
  -- 行注释：保存登录用户名，并通过 UNIQUE 防止重复账号。
  username VARCHAR(64) NOT NULL,
  -- 行注释：保存用户角色，用于控制管理员、管理者和查看者权限。
  role VARCHAR(32) NOT NULL,
  -- 行注释：定义业务字段的数据类型和默认约束。
  detail_text TEXT,
  -- 行注释：保存时间信息，方便按日期统计和排序。
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP
-- 行注释：结束当前表结构定义。
);

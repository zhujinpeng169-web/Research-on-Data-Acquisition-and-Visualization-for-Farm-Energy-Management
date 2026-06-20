# 农场能源管理系统答辩讲解手册

本手册用于配合源码中的 `答辩注释` 与 `答辩讲解` 注释，帮助你按模块讲清楚系统设计、代码分层和调用流程。

## 1. 项目定位

- 技术架构：`Vue 3 + Ant Design Vue + Spring Boot + MySQL`
- 目标：实现农场能源场景下的监测、优化、碳排放、预测、报表、权限与审计。
- 原则：前后端职责分离，接口统一 JSON 响应，关键写操作带角色控制与审计日志。

## 2. 分层结构（答辩高频）

- `controller`：对外 API 入口，接收参数、做角色校验、调用 service、组装响应。
- `service`：核心业务逻辑层，做校验、计算、聚合、调用 repository/jdbcTemplate。
- `repository`：Spring Data JPA 数据访问接口。
- `entity`：数据库表映射对象（JPA）。
- `dto`：请求/响应参数结构定义。
- `security`：拦截器 + 鉴权上下文（token 解析与角色判断）。
- `config`：CORS、异常统一处理、拦截器注册、密码加密器、启动数据初始化。
- `client-vue/src/pages`：页面级状态管理与 CRUD 交互。
- `client-vue/src/api/http.js`：统一请求客户端与错误拦截。
- `client-vue/src/auth/session.js`：本地会话与角色能力判断。
- `client-vue/src/router/index.js`：路由和前端访问守卫。

## 3. 五大业务模块如何对应代码

### 3.1 能源监测与数据采集

- 后端：
  - `MonitoringController`：设备与监测数据接口（增删改查 + 统计 + 实时数据）。
  - `MonitoringService`：设备管理、监测记录管理、按设备/时间统计。
- 前端：
  - `MonitoringPage.vue`：设备表 + 监测记录表 + 实时图表联动。

### 3.2 能源利用优化与节能管理

- 后端：
  - `OptimizationController`：优化分析与建议管理接口。
  - `OptimizationService`：近 7 天分析、自动建议、分配策略、节省统计。
- 前端：
  - `OptimizationPage.vue`：分析指标、节能建议 CRUD、分配图表。

### 3.3 碳排放监测与减排措施

- 后端：
  - `CarbonController`：排放记录、趋势、分解、策略、碳中和进度。
  - `CarbonService`：按能源因子计算排放，输出趋势和策略建议。
- 前端：
  - `CarbonPage.vue`：碳排统计、饼图、趋势图、策略表、记录 CRUD。

### 3.4 预测与计划

- 后端：
  - `ForecastController`：预测接口与预测记录 CRUD。
  - `ForecastService`：线性趋势 + 季节系数预测，需求计划建议输出。
- 前端：
  - `ForecastPage.vue`：预测设置、预测曲线、需求计划、记录管理。

### 3.5 用户界面与报告生成

- 后端：
  - `ReportsController`：报表数据、PDF 生成、下载、报表记录 CRUD。
  - `ReportService`：聚合统计 + OpenPDF 生成文件 + 路径安全校验。
- 前端：
  - `ReportsPage.vue`：报表总览、生成 PDF、下载、记录管理。

## 4. 认证、权限、审计（老师常问）

### 4.1 登录流程

1. 前端 `LoginPage.vue` 调用 `/api/auth/login`。
2. 后端 `AuthService.login` 校验用户名、密码、状态，创建 `user_sessions` token。
3. 前端 `setSession()` 存 token 到 `localStorage`。
4. `http.js` 请求拦截器自动加 `Authorization: Bearer <token>`。

### 4.2 接口鉴权

1. `AuthInterceptor.preHandle` 拦截 `/api/**` 请求。
2. 从 `Authorization` 或 `X-Token` 提取 token。
3. `AuthService.resolveValidSession` 校验是否过期/用户是否 active。
4. 校验通过后写入 `AuthContext`（userId/username/role）。

### 4.3 角色控制

- `AuthContext.requireRole(request, "admin"...)` 在 controller 写操作中限制权限。
- 前端也有 `router` + `canWrite()` 进行页面级/按钮级控制。

### 4.4 审计日志

- 每次关键写操作都调用 `OperationLogService.log`。
- `AuditLogController` 提供管理员查看最近日志的能力。

## 5. 数据库实体（可口述）

- `sys_users`：用户表（角色、状态、密码哈希）。
- `user_sessions`：登录会话 token 表（过期时间）。
- `energy_devices`：能源设备信息。
- `energy_monitoring`：监测采集记录。
- `energy_recommendations`：节能优化建议。
- `carbon_emissions`：碳排放记录。
- `energy_forecasts`：预测记录。
- `report_records`：报表生成记录。
- `operation_logs`：操作审计日志。

## 6. 你可以这样回答“为什么这么设计”

- 为什么分层：降低耦合，页面变化不影响核心业务，便于测试和维护。
- 为什么 token + 拦截器：统一鉴权入口，避免每个接口重复写认证逻辑。
- 为什么 service 里做计算：把业务规则收敛到后端，保证前后端结果一致。
- 为什么写操作记录审计：可追溯谁在什么时候改了什么，符合管理系统要求。
- 为什么报表要落库：保留历史生成记录，支持下载和后续追踪。

## 7. 建议你答辩时的讲解顺序

1. 先讲整体架构和分层职责。
2. 再按五大业务模块逐页演示（监测 → 优化 → 碳排 → 预测 → 报表）。
3. 最后讲认证/权限/审计作为“系统安全与可运维保障”。

## 8. 快速定位源码

- 后端入口：`backend-springboot/src/main/java/com/farm/energy/FarmEnergyApplication.java`
- 鉴权：`security/AuthInterceptor.java`, `service/AuthService.java`
- 五大模块 Service：`MonitoringService.java`, `OptimizationService.java`, `CarbonService.java`, `ForecastService.java`, `ReportService.java`
- 前端入口：`client-vue/src/main.js`
- 页面路由：`client-vue/src/router/index.js`
- 统一请求：`client-vue/src/api/http.js`


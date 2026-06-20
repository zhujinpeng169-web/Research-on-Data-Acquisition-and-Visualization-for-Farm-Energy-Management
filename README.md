# 农场能源管理系统（Vue + Spring Boot + MySQL）

本项目已清理为纯新架构代码，仅包含：

- `client-vue`：Vue 3 前端
- `backend-springboot`：Spring Boot 后端

## 启动方式

### 1. 启动后端

```bash
cd backend-springboot
mvn spring-boot:run
```

默认端口：`9000`

### 2. 启动前端

```bash
cd client-vue
npm install
npm run dev
```

默认端口：`3001`

### 3. 访问

- 前端页面：`http://localhost:3001`
- 后端接口：`http://localhost:9000/api/...`

## 数据库

- 数据库：MySQL
- 默认库名：`farm_energy`
- 初始化脚本：`backend-springboot/mysql-init.sql`

也可直接使用 Spring Boot 的 JPA 自动建表（`ddl-auto=update`）。
# Vue + Spring Boot + MySQL Refactor Guide

## New Architecture

- Frontend: `client-vue` (Vue 3 + Ant Design Vue + ECharts)
- Backend: `backend-springboot` (Spring Boot 3 + MySQL)
- Database: MySQL (`farm_energy`)

## Keep Existing Pages Consistent

The Vue pages preserve original route and module structure:

- `/` 控制台
- `/monitoring` 能源监测
- `/optimization` 节能管理
- `/carbon` 碳排放
- `/forecast` 预测计划
- `/reports` 报告中心

## Start Services

1. Start backend:

```bash
cd backend-springboot
mvn spring-boot:run
```

2. Start frontend:

```bash
cd client-vue
npm install
npm run dev
```

3. Access:

- Vue app: `http://localhost:3001`
- Spring Boot API: `http://localhost:9000`

## Notes

- API paths remain `/api/**` for compatibility.
- Seed data is auto-initialized on first startup when tables are empty.
- PDF reports are generated into `backend-springboot/reports` by default.

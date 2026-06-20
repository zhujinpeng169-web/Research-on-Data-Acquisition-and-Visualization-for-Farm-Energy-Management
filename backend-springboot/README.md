# Spring Boot Backend (MySQL)

## Environment Variables

- `DB_URL` default: `jdbc:mysql://localhost:3306/farm_energy?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true&useSSL=false`
- `DB_USERNAME` default: `root`
- `DB_PASSWORD` default: `root`
- `SERVER_PORT` default: `9000`
- `REPORT_DIR` default: `reports`

## Start

```bash
mvn spring-boot:run
```

## Build

```bash
mvn -DskipTests compile
```

## Optional MySQL Initialization

```sql
source mysql-init.sql;
```

> Tables can also be auto-created by JPA (`ddl-auto=update`).

// 逐行注释标记：已按业务语义处理，仅增加中文注释，不改变程序逻辑
/**
 * 答辩注释:
 * - 文件： UserSessionRepository
 * - 层级：仓储层
 * - 职责：基于 Spring Data JPA 提供持久层访问方法。
 * - 此注释仅用于说明，不会改变运行行为。
 */
// 行注释：声明 UserSessionRepository 所在的包路径，体现它在项目中的模块位置。
package com.farm.energy.repository;

// 行注释：引入数据库实体类，用于读取或保存业务表记录。
import com.farm.energy.entity.SysUser;
// 行注释：引入数据库实体类，用于读取或保存业务表记录。
import com.farm.energy.entity.UserSession;
// 行注释：引入数据访问接口，用于操作对应数据库表。
import org.springframework.data.jpa.repository.JpaRepository;

// 行注释：引入时间类型，用于记录日期、创建时间或预测日期。
import java.time.LocalDateTime;
// 行注释：引入当前文件要使用的类或注解，保证后续代码可以编译。
import java.util.Optional;

// 行注释：定义 UserSessionRepository 数据访问接口，交给 Spring Data JPA 自动实现。
// 类注释：这是 用户管理 模块的数据访问接口，Spring Data JPA 会根据它自动提供增删改查能力。
public interface UserSessionRepository extends JpaRepository<UserSession, Long> {
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    Optional<UserSession> findByToken(String token);

    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    void deleteByToken(String token);

    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    void deleteByUser(SysUser user);

    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    void deleteByExpiresAtBefore(LocalDateTime time);
// 行注释：结束当前代码块，表示这段逻辑处理完成。
}




// 逐行注释标记：已按业务语义处理，仅增加中文注释，不改变程序逻辑
/**
 * 答辩注释:
 * - 文件： OperationLogService
 * - 层级：服务层
 * - 职责：实现领域业务逻辑、数据聚合、校验与持久化编排。
 * - 此注释仅用于说明，不会改变运行行为。
 */
// 行注释：声明 OperationLogService 所在的包路径，体现它在项目中的模块位置。
package com.farm.energy.service;

// 行注释：引入数据库实体类，用于读取或保存业务表记录。
import com.farm.energy.entity.OperationLog;
// 行注释：引入数据访问接口，用于操作对应数据库表。
import com.farm.energy.repository.OperationLogRepository;
// 行注释：引入当前文件要使用的类或注解，保证后续代码可以编译。
import org.springframework.stereotype.Service;

// 行注释：引入 Map 结构，用于组装接口返回的键值数据。
import java.util.LinkedHashMap;
// 行注释：引入 List 结构，用于返回表格或图表列表数据。
import java.util.List;
// 行注释：引入 Map 结构，用于组装接口返回的键值数据。
import java.util.Map;

// 行注释：把当前类标记为业务服务层，专门处理模块业务逻辑。
@Service
// 行注释：定义 OperationLogService 业务服务，承载该模块的主要代码。
// 类注释：这是 操作审计 模块的业务服务类，负责处理核心业务逻辑、数据校验、统计计算和数据库读写编排。
public class OperationLogService {

    // 行注释：注入仓储接口，用于访问对应数据库表。
    private final OperationLogRepository operationLogRepository;

    // 行注释：声明 OperationLogService 方法，承接当前模块的接口请求或内部业务处理。
    // 方法用法：Spring 创建 OperationLogService 对象时调用这个构造方法，用来注入本类需要的依赖。
    public OperationLogService(OperationLogRepository operationLogRepository) {
        // 行注释：把构造方法传入的 operationLogRepository 保存到成员变量，后续方法会继续调用它。
        this.operationLogRepository = operationLogRepository;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： log
     * - 作用：支撑当前模块流程的业务辅助方法。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 log 方法，完成 OperationLogService 中对应的一步业务处理。
    // 方法用法：服务层内部或控制器调用 log，用于完成 OperationLog 模块的一段核心业务逻辑。
    public void log(String module, String action, String targetId, String username, String role, String detailText) {
        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        OperationLog entity = new OperationLog();
        // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
        entity.setModule(module);
        // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
        entity.setAction(action);
        // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
        entity.setTargetId(targetId);
        // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
        entity.setUsername(username);
        // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
        entity.setRole(role);
        // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
        entity.setDetailText(detailText);
        // 行注释：调用 JPA 保存实体，把数据写入数据库。
        operationLogRepository.save(entity);
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： listRecent
     * - 作用：查询并返回集合数据，用于界面表格/图表渲染。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 listRecent 方法，完成 OperationLogService 中对应的一步业务处理。
    // 方法用法：控制器加载列表时调用 listRecent，负责查询并转换成前端表格需要的数据结构。
    public List<Map<String, Object>> listRecent() {
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return operationLogRepository.findTop200ByOrderByCreatedAtDesc()
                // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                .stream()
                // 行注释：遍历列表数据，把后端结果转换成页面或图表需要的结构。
                .map(this::toMap)
                // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                .toList();
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： toMap
     * - 作用：将实体对象转换为接口响应 Map，并保持字段命名稳定。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 toMap 方法，完成 OperationLogService 中对应的一步业务处理。
    // 方法用法：返回接口数据前调用它，把实体对象转换成前端更容易读取的 Map 结构。
    private Map<String, Object> toMap(OperationLog entity) {
        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        Map<String, Object> row = new LinkedHashMap<>();
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        row.put("id", entity.getId());
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        row.put("module", entity.getModule());
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        row.put("action", entity.getAction());
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        row.put("target_id", entity.getTargetId());
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        row.put("username", entity.getUsername());
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        row.put("role", entity.getRole());
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        row.put("detail", entity.getDetailText());
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        row.put("created_at", entity.getCreatedAt());
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return row;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }
// 行注释：结束当前代码块，表示这段逻辑处理完成。
}



// 逐行注释标记：已按业务语义处理，仅增加中文注释，不改变程序逻辑
/**
 * 答辩注释:
 * - 文件： AuditLogController
 * - 层级：控制器层
 * - 职责：暴露 REST 接口，接收请求参数，执行角色校验，并调用服务层。
 * - 此注释仅用于说明，不会改变运行行为。
 */
// 行注释：声明 AuditLogController 所在的包路径，体现它在项目中的模块位置。
package com.farm.energy.controller;

// 行注释：引入鉴权上下文或安全工具，用于识别当前登录用户。
import com.farm.energy.security.AuthContext;
// 行注释：引入业务服务类，把具体业务处理交给服务层完成。
import com.farm.energy.service.OperationLogService;
// 行注释：引入请求对象，用于读取请求头、路径和登录上下文。
import jakarta.servlet.http.HttpServletRequest;
// 行注释：引入 GET 接口注解，用于声明查询类接口地址。
import org.springframework.web.bind.annotation.GetMapping;
// 行注释：引入路由前缀注解，用于统一设置接口地址。
import org.springframework.web.bind.annotation.RequestMapping;
// 行注释：引入 REST 控制器注解，让接口直接返回 JSON。
import org.springframework.web.bind.annotation.RestController;

// 行注释：引入 Map 结构，用于组装接口返回的键值数据。
import java.util.Map;

// 行注释：声明这是 REST 接口控制器，返回 JSON 数据给前端。
@RestController
// 行注释：给当前控制器统一添加接口前缀 /api/audit，前端请求都会以它开头。
@RequestMapping("/api/audit")
// 行注释：定义 AuditLogController 控制器，承载该模块的主要代码。
// 类注释：这是 操作审计 模块的控制器类，负责接收前端 HTTP 请求、调用 Service，并把结果以 JSON 返回。
public class AuditLogController {

    // 行注释：注入操作日志服务，用于记录登录、修改、删除等关键行为。
    private final OperationLogService operationLogService;

    // 行注释：声明 AuditLogController 方法，承接当前模块的接口请求或内部业务处理。
    // 方法用法：Spring 创建 AuditLogController 对象时调用这个构造方法，用来注入本类需要的依赖。
    public AuditLogController(OperationLogService operationLogService) {
        // 行注释：把构造方法传入的 operationLogService 保存到成员变量，后续方法会继续调用它。
        this.operationLogService = operationLogService;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：声明 GET 查询接口 /logs，用于前端读取数据。
    @GetMapping("/logs")
    /**
     * 答辩讲解:
     * - 方法： list
     * - 作用：查询并返回集合数据，用于界面表格/图表渲染。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 list 方法，完成 AuditLogController 中对应的一步业务处理。
    // 方法用法：前端打开列表页或刷新表格时调用，用来返回 AuditLog 模块的列表数据。
    public Map<String, Object> list(HttpServletRequest request) {
        // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
        AuthContext.requireRole(request, "admin");
        // 行注释：组装接口返回数据，Spring 会自动转换成 JSON。
        return Map.of("logs", operationLogService.listRecent());
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }
// 行注释：结束当前代码块，表示这段逻辑处理完成。
}



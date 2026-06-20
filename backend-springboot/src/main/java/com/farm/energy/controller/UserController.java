// 逐行注释标记：已按业务语义处理，仅增加中文注释，不改变程序逻辑
/**
 * 答辩注释:
 * - 文件： UserController
 * - 层级：控制器层
 * - 职责：暴露 REST 接口，接收请求参数，执行角色校验，并调用服务层。
 * - 此注释仅用于说明，不会改变运行行为。
 */
// 行注释：声明 UserController 所在的包路径，体现它在项目中的模块位置。
package com.farm.energy.controller;

// 行注释：引入前端请求参数对象，便于 Spring 自动接收表单或 JSON 数据。
import com.farm.energy.dto.UserCreateRequest;
// 行注释：引入前端请求参数对象，便于 Spring 自动接收表单或 JSON 数据。
import com.farm.energy.dto.UserUpdateRequest;
// 行注释：引入鉴权上下文或安全工具，用于识别当前登录用户。
import com.farm.energy.security.AuthContext;
// 行注释：引入业务服务类，把具体业务处理交给服务层完成。
import com.farm.energy.service.AuthService;
// 行注释：引入业务服务类，把具体业务处理交给服务层完成。
import com.farm.energy.service.OperationLogService;
// 行注释：引入请求对象，用于读取请求头、路径和登录上下文。
import jakarta.servlet.http.HttpServletRequest;
// 行注释：引入 DELETE 接口注解，用于声明删除类接口地址。
import org.springframework.web.bind.annotation.DeleteMapping;
// 行注释：引入 GET 接口注解，用于声明查询类接口地址。
import org.springframework.web.bind.annotation.GetMapping;
// 行注释：引入当前文件要使用的类或注解，保证后续代码可以编译。
import org.springframework.web.bind.annotation.PathVariable;
// 行注释：引入 POST 接口注解，用于声明提交类接口地址。
import org.springframework.web.bind.annotation.PostMapping;
// 行注释：引入 PUT 接口注解，用于声明更新类接口地址。
import org.springframework.web.bind.annotation.PutMapping;
// 行注释：引入请求体注解，用于把前端 JSON 转成 Java 对象。
import org.springframework.web.bind.annotation.RequestBody;
// 行注释：引入路由前缀注解，用于统一设置接口地址。
import org.springframework.web.bind.annotation.RequestMapping;
// 行注释：引入 REST 控制器注解，让接口直接返回 JSON。
import org.springframework.web.bind.annotation.RestController;

// 行注释：引入 Map 结构，用于组装接口返回的键值数据。
import java.util.LinkedHashMap;
// 行注释：引入 Map 结构，用于组装接口返回的键值数据。
import java.util.Map;

// 行注释：声明这是 REST 接口控制器，返回 JSON 数据给前端。
@RestController
// 行注释：给当前控制器统一添加接口前缀 /api/users，前端请求都会以它开头。
@RequestMapping("/api/users")
// 行注释：定义 UserController 控制器，承载该模块的主要代码。
// 类注释：这是 用户管理 模块的控制器类，负责接收前端 HTTP 请求、调用 Service，并把结果以 JSON 返回。
public class UserController {

    // 行注释：注入认证服务，用于登录、登出、查当前用户和修改密码。
    private final AuthService authService;
    // 行注释：注入操作日志服务，用于记录登录、修改、删除等关键行为。
    private final OperationLogService operationLogService;

    // 行注释：声明 UserController 方法，承接当前模块的接口请求或内部业务处理。
    // 方法用法：Spring 创建 UserController 对象时调用这个构造方法，用来注入本类需要的依赖。
    public UserController(AuthService authService, OperationLogService operationLogService) {
        // 行注释：把构造方法传入的 authService 保存到成员变量，后续方法会继续调用它。
        this.authService = authService;
        // 行注释：把构造方法传入的 operationLogService 保存到成员变量，后续方法会继续调用它。
        this.operationLogService = operationLogService;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：声明 GET 查询接口 ，用于前端读取数据。
    @GetMapping
    /**
     * 答辩讲解:
     * - 方法： listUsers
     * - 作用：查询并返回集合数据，用于界面表格/图表渲染。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 listUsers 方法，完成 UserController 中对应的一步业务处理。
    // 方法用法：前端打开列表页或刷新表格时调用，用来返回 User 模块的列表数据。
    public Map<String, Object> listUsers(HttpServletRequest request) {
        // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
        AuthContext.requireRole(request, "admin");
        // 行注释：组装接口返回数据，Spring 会自动转换成 JSON。
        return Map.of("users", authService.listUsers());
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：声明 GET 查询接口 /{id}，用于前端读取数据。
    @GetMapping("/{id}")
    /**
     * 答辩讲解:
     * - 方法： getUser
     * - 作用：加载并返回前端视图所需的模块数据。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 getUser 查询方法，给前端返回当前模块数据。
    // 方法用法：前端查询 User 模块数据时调用，用来读取数据库并返回 JSON。
    public Map<String, Object> getUser(HttpServletRequest request, @PathVariable Long id) {
        // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
        AuthContext.requireRole(request, "admin");
        // 行注释：组装接口返回数据，Spring 会自动转换成 JSON。
        return Map.of("user", authService.getUser(id));
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：声明 POST 提交接口 ，用于前端新增或提交业务数据。
    @PostMapping
    /**
     * 答辩讲解:
     * - 方法： createUser
     * - 作用：校验请求参数并持久化新增业务记录。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：声明 createUser 方法，承接当前模块的接口请求或内部业务处理。
    // 方法用法：前端点击新增并提交表单时调用，用来创建 User 模块的新记录。
    public Map<String, Object> createUser(HttpServletRequest request,
                                          // 行注释：说明该参数来自前端 JSON 请求体，Spring 会自动封装成对象。
                                          @RequestBody UserCreateRequest createRequest) {
        // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
        AuthContext.requireRole(request, "admin");
        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        Map<String, Object> response = new LinkedHashMap<>();
        // 行注释：声明变量 user，保存本行计算或查询得到的结果，供后续逻辑使用。
        Map<String, Object> user = authService.createUser(createRequest);
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        response.put("user", user);
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        response.put("message", "User created");
        // 行注释：记录本次业务操作，方便后台审计和答辩说明安全性。
        operationLogService.log(
                // 行注释：传入字符串参数 “users”，作为当前方法调用的业务标识或显示文本。
                "users",
                // 行注释：传入字符串参数 “create”，作为当前方法调用的业务标识或显示文本。
                "create",
                // 行注释：发起接口请求或读取对象数据，获取页面需要的业务信息。
                String.valueOf(user.get("id")),
                // 行注释：从请求上下文中取出当前用户名，用于操作日志记录。
                AuthContext.username(request),
                // 行注释：从请求上下文中取出当前用户角色，用于权限判断或日志记录。
                AuthContext.role(request),
                // 行注释：发起接口请求或读取对象数据，获取页面需要的业务信息。
                "Create user " + user.get("username")
        // 行注释：结束当前方法调用，把上面准备好的参数一次性提交执行。
        );
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return response;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：声明 PUT 更新接口 /{id}，用于前端修改已有数据。
    @PutMapping("/{id}")
    /**
     * 答辩讲解:
     * - 方法： updateUser
     * - 作用：在输入规范化后，对现有记录执行部分字段更新。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：声明 updateUser 方法，承接当前模块的接口请求或内部业务处理。
    // 方法用法：前端编辑已有记录并保存时调用，用来更新 User 模块的数据。
    public Map<String, Object> updateUser(HttpServletRequest request,
                                          // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                                          @PathVariable Long id,
                                          // 行注释：说明该参数来自前端 JSON 请求体，Spring 会自动封装成对象。
                                          @RequestBody UserUpdateRequest updateRequest) {
        // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
        AuthContext.requireRole(request, "admin");
        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        Map<String, Object> response = new LinkedHashMap<>();
        // 行注释：声明变量 user，保存本行计算或查询得到的结果，供后续逻辑使用。
        Map<String, Object> user = authService.updateUser(id, updateRequest);
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        response.put("user", user);
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        response.put("message", "User updated");
        // 行注释：记录本次业务操作，方便后台审计和答辩说明安全性。
        operationLogService.log(
                // 行注释：传入字符串参数 “users”，作为当前方法调用的业务标识或显示文本。
                "users",
                // 行注释：传入字符串参数 “update”，作为当前方法调用的业务标识或显示文本。
                "update",
                // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                String.valueOf(id),
                // 行注释：从请求上下文中取出当前用户名，用于操作日志记录。
                AuthContext.username(request),
                // 行注释：从请求上下文中取出当前用户角色，用于权限判断或日志记录。
                AuthContext.role(request),
                // 行注释：发起接口请求或读取对象数据，获取页面需要的业务信息。
                "Update user " + user.get("username")
        // 行注释：结束当前方法调用，把上面准备好的参数一次性提交执行。
        );
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return response;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：声明 DELETE 删除接口 /{id}，用于前端删除指定记录。
    @DeleteMapping("/{id}")
    /**
     * 答辩讲解:
     * - 方法： deleteUser
     * - 作用：删除或逻辑移除目标记录，并维护数据一致性。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 deleteUser 删除方法，删除前端指定的数据记录。
    // 方法用法：前端确认删除时调用，用来删除 User 模块的指定记录。
    public Map<String, Object> deleteUser(HttpServletRequest request, @PathVariable Long id) {
        // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
        AuthContext.requireRole(request, "admin");
        // 行注释：从请求上下文中取出当前登录用户 ID，用于后续鉴权或业务操作。
        authService.deleteUser(id, AuthContext.userId(request));
        // 行注释：记录本次业务操作，方便后台审计和答辩说明安全性。
        operationLogService.log(
                // 行注释：传入字符串参数 “users”，作为当前方法调用的业务标识或显示文本。
                "users",
                // 行注释：传入字符串参数 “delete”，作为当前方法调用的业务标识或显示文本。
                "delete",
                // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                String.valueOf(id),
                // 行注释：从请求上下文中取出当前用户名，用于操作日志记录。
                AuthContext.username(request),
                // 行注释：从请求上下文中取出当前用户角色，用于权限判断或日志记录。
                AuthContext.role(request),
                // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                "Delete user id " + id
        // 行注释：结束当前方法调用，把上面准备好的参数一次性提交执行。
        );
        // 行注释：组装接口返回数据，Spring 会自动转换成 JSON。
        return Map.of("message", "User deleted");
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }
// 行注释：结束当前代码块，表示这段逻辑处理完成。
}



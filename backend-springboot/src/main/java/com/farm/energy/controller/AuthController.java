// 逐行注释标记：已按业务语义处理，仅增加中文注释，不改变程序逻辑
/**
 * 答辩注释:
 * - 文件： AuthController
 * - 层级：控制器层
 * - 职责：暴露 REST 接口，接收请求参数，执行角色校验，并调用服务层。
 * - 此注释仅用于说明，不会改变运行行为。
 */
// 行注释：声明 AuthController 所在的包路径，体现它在项目中的模块位置。
package com.farm.energy.controller;

// 行注释：引入前端请求参数对象，便于 Spring 自动接收表单或 JSON 数据。
import com.farm.energy.dto.ChangePasswordRequest;
// 行注释：引入前端请求参数对象，便于 Spring 自动接收表单或 JSON 数据。
import com.farm.energy.dto.LoginRequest;
// 行注释：引入数据库实体类，用于读取或保存业务表记录。
import com.farm.energy.entity.SysUser;
// 行注释：引入鉴权上下文或安全工具，用于识别当前登录用户。
import com.farm.energy.security.AuthContext;
// 行注释：引入业务服务类，把具体业务处理交给服务层完成。
import com.farm.energy.service.AuthService;
// 行注释：引入业务服务类，把具体业务处理交给服务层完成。
import com.farm.energy.service.OperationLogService;
// 行注释：引入请求对象，用于读取请求头、路径和登录上下文。
import jakarta.servlet.http.HttpServletRequest;
// 行注释：引入 GET 接口注解，用于声明查询类接口地址。
import org.springframework.web.bind.annotation.GetMapping;
// 行注释：引入 POST 接口注解，用于声明提交类接口地址。
import org.springframework.web.bind.annotation.PostMapping;
// 行注释：引入请求体注解，用于把前端 JSON 转成 Java 对象。
import org.springframework.web.bind.annotation.RequestBody;
// 行注释：引入路由前缀注解，用于统一设置接口地址。
import org.springframework.web.bind.annotation.RequestMapping;
// 行注释：引入 REST 控制器注解，让接口直接返回 JSON。
import org.springframework.web.bind.annotation.RestController;

// 行注释：引入 Map 结构，用于组装接口返回的键值数据。
import java.util.Map;

// 行注释：声明这是 REST 接口控制器，返回 JSON 数据给前端。
@RestController
// 行注释：给当前控制器统一添加接口前缀 /api/auth，前端请求都会以它开头。
@RequestMapping("/api/auth")
// 行注释：定义 AuthController 控制器，承载该模块的主要代码。
// 类注释：这是 认证登录 模块的控制器类，负责接收前端 HTTP 请求、调用 Service，并把结果以 JSON 返回。
public class AuthController {

    // 行注释：注入认证服务，用于登录、登出、查当前用户和修改密码。
    private final AuthService authService; 
    // 行注释：注入操作日志服务，用于记录登录、修改、删除等关键行为。
    private final OperationLogService operationLogService;

    // 行注释：声明认证控制器的构造方法，Spring 启动时会把认证服务和日志服务传进来。
    // 方法用法：Spring 创建 AuthController 对象时调用这个构造方法，用来注入本类需要的依赖。
    public AuthController(AuthService authService, OperationLogService operationLogService) {
        // 行注释：把构造方法传入的 authService 保存到成员变量，后续方法会继续调用它。
        this.authService = authService;
        // 行注释：把构造方法传入的 operationLogService 保存到成员变量，后续方法会继续调用它。
        this.operationLogService = operationLogService;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：声明登录接口 /login，用于前端提交账号密码并换取登录 token。
    @PostMapping("/login")
    /**
     * 答辩讲解:
     * - 方法： login
     * - 作用：校验用户凭据，创建会话令牌，并返回当前用户信息。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义登录接口方法，接收用户名密码并返回 token 与用户信息。
    // 方法用法：前端登录页提交用户名和密码时调用，用来校验身份并返回 token 和用户信息。
    public Map<String, Object> login(@RequestBody LoginRequest request) {
        // 行注释：调用认证服务完成账号密码校验，并生成登录会话 token。
        return authService.login(request);
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：声明退出接口 /logout，用于前端注销当前登录会话。
    @PostMapping("/logout")
    /**
     * 答辩讲解:
     * - 方法： logout
     * - 作用：使令牌会话失效，要求客户端重新认证。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义退出登录接口方法，注销当前 token 并记录操作日志。
    // 方法用法：用户点击退出登录或前端清理会话时调用，用来注销当前 token 并记录退出日志。
    public Map<String, Object> logout(HttpServletRequest request) {
        // 行注释：读取 Authorization 请求头，准备解析前端传来的 Bearer token。
        String authorization = request.getHeader("Authorization");
        // 行注释：先把 token 初始化为空，后面会从 Authorization 请求头里解析真实令牌。
        String token = null;
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (authorization != null && authorization.startsWith("Bearer ")) {
            // 行注释：截取 Bearer 后面的真实 token 字符串。
            token = authorization.substring("Bearer ".length()).trim();
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：从请求上下文读取当前用户名，用于退出登录和操作日志。
        String username = String.valueOf(request.getAttribute(AuthContext.ATTR_USERNAME));
        // 行注释：从请求上下文读取当前用户角色，用于记录审计日志。
        String role = String.valueOf(request.getAttribute(AuthContext.ATTR_ROLE));
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (username != null && !username.equals("null")) {
            // 行注释：记录本次业务操作，方便后台审计和答辩说明安全性。
            operationLogService.log("auth", "logout", null, username, role, "Logout");
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：调用认证服务注销 token，让当前登录会话失效。
        authService.logout(token);
        // 行注释：组装接口返回数据，Spring 会自动转换成 JSON。
        return Map.of("message", "Logout successful");
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：声明 GET 查询接口 /me，用于前端读取数据。
    @GetMapping("/me")
    /**
     * 答辩讲解:
     * - 方法： me
     * - 作用：支撑当前模块流程的业务辅助方法。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义获取当前用户接口方法，根据 token 返回登录用户资料。
    // 方法用法：前端需要显示当前登录用户资料时调用，用 token 找到用户并返回用户信息。
    public Map<String, Object> me(HttpServletRequest request) {
        // 行注释：从请求上下文中取出当前登录用户 ID，用于后续鉴权或业务操作。
        Long userId = AuthContext.userId(request);
        // 行注释：根据当前用户 ID 查询用户实体，确保用户仍然存在。
        SysUser user = authService.requireUserById(userId);
        // 行注释：组装接口返回数据，Spring 会自动转换成 JSON。
        return Map.of("user", authService.getCurrentUserProfile(user));
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：声明修改密码接口 /change-password，用于前端提交旧密码和新密码。
    @PostMapping("/change-password")
    /**
     * 答辩讲解:
     * - 方法： changePassword
     * - 作用：支撑当前模块流程的业务辅助方法。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：声明 changePassword 方法，承接当前模块的接口请求或内部业务处理。
    // 方法用法：用户在个人账户中修改密码时调用，用来校验旧密码、保存新密码并写入审计日志。
    public Map<String, Object> changePassword(HttpServletRequest request,
                                              // 行注释：说明该参数来自前端 JSON 请求体，Spring 会自动封装成对象。
                                              @RequestBody ChangePasswordRequest body) {
        // 行注释：从请求上下文中取出当前登录用户 ID，用于后续鉴权或业务操作。
        Long userId = AuthContext.userId(request);
        // 行注释：调用认证服务校验旧密码并写入新密码。
        authService.changeOwnPassword(userId, body.getOldPassword(), body.getNewPassword());
        // 行注释：记录本次业务操作，方便后台审计和答辩说明安全性。
        operationLogService.log(
                // 行注释：传入日志模块名 auth，表示这条记录属于认证模块。
                "auth",
                // 行注释：传入操作类型 change_password，表示用户修改了自己的密码。
                "change_password",
                // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                String.valueOf(userId),
                // 行注释：从请求上下文中取出当前用户名，用于操作日志记录。
                AuthContext.username(request),
                // 行注释：从请求上下文中取出当前用户角色，用于权限判断或日志记录。
                AuthContext.role(request),
                // 行注释：传入日志描述，说明本次审计记录是用户修改密码。
                "User changed own password"
        // 行注释：结束当前方法调用，把上面准备好的参数一次性提交执行。
        );
        // 行注释：组装接口返回数据，Spring 会自动转换成 JSON。
        return Map.of("message", "Password changed. Please login again.");
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }
// 行注释：结束当前代码块，表示这段逻辑处理完成。
}



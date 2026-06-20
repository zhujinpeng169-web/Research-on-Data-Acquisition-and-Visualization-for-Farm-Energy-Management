// 逐行注释标记：已按业务语义处理，仅增加中文注释，不改变程序逻辑
/**
 * 答辩注释:
 * - 文件： AuthInterceptor
 * - 层级：安全层
 * - 职责：处理令牌认证上下文与请求鉴权拦截。
 * - 此注释仅用于说明，不会改变运行行为。
 */
// 行注释：声明 AuthInterceptor 所在的包路径，体现它在项目中的模块位置。
package com.farm.energy.security;

// 行注释：引入数据库实体类，用于读取或保存业务表记录。
import com.farm.energy.entity.UserSession;
// 行注释：引入业务服务类，把具体业务处理交给服务层完成。
import com.farm.energy.service.AuthService;
// 行注释：引入请求对象，用于读取请求头、路径和登录上下文。
import jakarta.servlet.http.HttpServletRequest;
// 行注释：引入响应对象，用于设置接口返回状态和内容。
import jakarta.servlet.http.HttpServletResponse;
// 行注释：引入当前文件要使用的类或注解，保证后续代码可以编译。
import org.springframework.stereotype.Component;
// 行注释：引入当前文件要使用的类或注解，保证后续代码可以编译。
import org.springframework.web.servlet.HandlerInterceptor;

// 行注释：引入当前文件要使用的类或注解，保证后续代码可以编译。
import java.util.Optional;

// 行注释：把当前类注册为 Spring 组件，启动时自动交给容器管理。
@Component
// 行注释：定义 AuthInterceptor 鉴权安全组件，承载该模块的主要代码。
// 类注释：这是 认证登录 模块的安全类，负责 token 鉴权、用户上下文读取或接口权限控制。
public class AuthInterceptor implements HandlerInterceptor {

    // 行注释：注入认证服务，用于登录、登出、查当前用户和修改密码。
    private final AuthService authService;

    // 行注释：声明 AuthInterceptor 方法，承接当前模块的接口请求或内部业务处理。
    // 方法用法：Spring 创建 AuthInterceptor 对象时调用这个构造方法，用来注入本类需要的依赖。
    public AuthInterceptor(AuthService authService) {
        // 行注释：把构造方法传入的 authService 保存到成员变量，后续方法会继续调用它。
        this.authService = authService;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：说明这里重写父类或接口的方法，例如拦截器入口方法。
    @Override
    /**
     * 答辩讲解:
     * - 方法： preHandle
     * - 作用：全局 HTTP 拦截，鉴权 API 请求并注入认证上下文属性。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：声明 preHandle 方法，承接当前模块的接口请求或内部业务处理。
    // 方法用法：每个受保护接口执行前由 Spring MVC 自动调用，用来校验 token 并注入用户上下文。
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 行注释：声明变量 path，保存本行计算或查询得到的结果，供后续逻辑使用。
        String path = request.getRequestURI();
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (path.equals("/health")
                // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                || path.startsWith("/api/auth/login")
                // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                || path.startsWith("/api/auth/public")
                // 行注释：读取对象字段值，参与业务判断、计算或返回。
                || "OPTIONS".equalsIgnoreCase(request.getMethod())) {
            // 行注释：放行当前请求，让它继续进入后端接口处理。
            return true;
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }

        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (!path.startsWith("/api/")) {
            // 行注释：放行当前请求，让它继续进入后端接口处理。
            return true;
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }

        // 行注释：声明变量 token，保存本行计算或查询得到的结果，供后续逻辑使用。
        String token = resolveToken(request);
        // 行注释：声明变量 session，保存本行计算或查询得到的结果，供后续逻辑使用。
        Optional<UserSession> session = authService.resolveValidSession(token);
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (session.isEmpty()) {
            // 行注释：设置接口响应状态码，告诉前端当前请求未通过认证。
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            // 行注释：设置响应内容类型为 JSON，保证前端能正确解析错误信息。
            response.setContentType("application/json;charset=UTF-8");
            // 行注释：向前端写出未登录提示，统一返回 401 错误。
            response.getWriter().write("{\"status\":401,\"message\":\"Unauthorized or session expired\"}");
            // 行注释：拦截当前请求，阻止它继续访问受保护接口。
            return false;
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }

        // 行注释：声明变量 current，保存本行计算或查询得到的结果，供后续逻辑使用。
        UserSession current = session.get();
        // 行注释：把解析出的用户信息写入请求上下文，后续控制器可直接读取。
        request.setAttribute(AuthContext.ATTR_USER_ID, current.getUser().getId());
        // 行注释：把解析出的用户信息写入请求上下文，后续控制器可直接读取。
        request.setAttribute(AuthContext.ATTR_USERNAME, current.getUser().getUsername());
        // 行注释：把解析出的用户信息写入请求上下文，后续控制器可直接读取。
        request.setAttribute(AuthContext.ATTR_ROLE, current.getUser().getRole());
        // 行注释：放行当前请求，让它继续进入后端接口处理。
        return true;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： resolveToken
     * - 作用：支撑当前模块流程的业务辅助方法。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 resolveToken 方法，完成 AuthInterceptor 中对应的一步业务处理。
    // 方法用法：鉴权时调用它从请求头提取 token，支持 Authorization Bearer 和 X-Token。
    private String resolveToken(HttpServletRequest request) {
        // 行注释：读取 Authorization 请求头，准备解析前端传来的 Bearer token。
        String authorization = request.getHeader("Authorization");
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (authorization != null && authorization.startsWith("Bearer ")) {
            // 行注释：截取 Bearer 后面的真实 token 字符串。
            return authorization.substring("Bearer ".length()).trim();
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return request.getHeader("X-Token");
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }
// 行注释：结束当前代码块，表示这段逻辑处理完成。
}



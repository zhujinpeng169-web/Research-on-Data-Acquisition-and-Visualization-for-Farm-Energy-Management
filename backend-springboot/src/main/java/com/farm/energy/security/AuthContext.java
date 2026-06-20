// 逐行注释标记：已按业务语义处理，仅增加中文注释，不改变程序逻辑
/**
 * 答辩注释:
 * - 文件： AuthContext
 * - 层级：安全层
 * - 职责：处理令牌认证上下文与请求鉴权拦截。
 * - 此注释仅用于说明，不会改变运行行为。
 */
// 行注释：声明 AuthContext 所在的包路径，体现它在项目中的模块位置。
package com.farm.energy.security;

// 行注释：引入请求对象，用于读取请求头、路径和登录上下文。
import jakarta.servlet.http.HttpServletRequest;
// 行注释：引入当前文件要使用的类或注解，保证后续代码可以编译。
import org.springframework.http.HttpStatus;
// 行注释：引入当前文件要使用的类或注解，保证后续代码可以编译。
import org.springframework.web.server.ResponseStatusException;

// 行注释：引入当前文件要使用的类或注解，保证后续代码可以编译。
import java.util.Arrays;

// 行注释：定义 AuthContext 鉴权安全组件，承载该模块的主要代码。
public final class AuthContext {

    // 行注释：声明变量 ATTR_USER_ID，保存本行计算或查询得到的结果，供后续逻辑使用。
    public static final String ATTR_USER_ID = "auth.userId";
    // 行注释：声明变量 ATTR_USERNAME，保存本行计算或查询得到的结果，供后续逻辑使用。
    public static final String ATTR_USERNAME = "auth.username";
    // 行注释：声明变量 ATTR_ROLE，保存本行计算或查询得到的结果，供后续逻辑使用。
    public static final String ATTR_ROLE = "auth.role";

    // 行注释：声明 AuthContext 方法，承接当前模块的接口请求或内部业务处理。
    // 方法用法：Spring 创建 AuthContext 对象时调用这个构造方法，用来注入本类需要的依赖。
    private AuthContext() {
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： userId
     * - 作用：支撑当前模块流程的业务辅助方法。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 userId 方法，完成 AuthContext 中对应的一步业务处理。
    // 方法用法：控制器需要当前登录用户信息时调用 userId，从 request 上下文读取拦截器写入的值。
    public static Long userId(HttpServletRequest request) {
        // 行注释：声明变量 value，保存本行计算或查询得到的结果，供后续逻辑使用。
        Object value = request.getAttribute(ATTR_USER_ID);
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (value instanceof Long id) {
            // 行注释：返回处理结果，最终会交给前端或上层方法使用。
            return id;
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：抛出明确异常，让前端收到规范的错误提示。
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： username
     * - 作用：支撑当前模块流程的业务辅助方法。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 username 方法，完成 AuthContext 中对应的一步业务处理。
    // 方法用法：控制器需要当前登录用户信息时调用 username，从 request 上下文读取拦截器写入的值。
    public static String username(HttpServletRequest request) {
        // 行注释：声明变量 value，保存本行计算或查询得到的结果，供后续逻辑使用。
        Object value = request.getAttribute(ATTR_USERNAME);
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (value instanceof String name) {
            // 行注释：返回处理结果，最终会交给前端或上层方法使用。
            return name;
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：抛出明确异常，让前端收到规范的错误提示。
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： role
     * - 作用：支撑当前模块流程的业务辅助方法。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 role 方法，完成 AuthContext 中对应的一步业务处理。
    // 方法用法：控制器需要当前登录用户信息时调用 role，从 request 上下文读取拦截器写入的值。
    public static String role(HttpServletRequest request) {
        // 行注释：声明变量 value，保存本行计算或查询得到的结果，供后续逻辑使用。
        Object value = request.getAttribute(ATTR_ROLE);
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (value instanceof String role) {
            // 行注释：返回处理结果，最终会交给前端或上层方法使用。
            return role;
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：抛出明确异常，让前端收到规范的错误提示。
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： requireRole
     * - 作用：加载必需实体，未找到时抛出明确异常。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 requireRole 方法，完成 AuthContext 中对应的一步业务处理。
    // 方法用法：控制器做权限校验时调用它，当前用户角色不满足要求就直接拒绝访问。
    public static void requireRole(HttpServletRequest request, String... roles) {
        // 行注释：声明变量 currentRole，保存本行计算或查询得到的结果，供后续逻辑使用。
        String currentRole = role(request);
        // 行注释：声明变量 matched，保存本行计算或查询得到的结果，供后续逻辑使用。
        boolean matched = Arrays.stream(roles).anyMatch(it -> it.equalsIgnoreCase(currentRole));
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (!matched) {
            // 行注释：抛出明确异常，让前端收到规范的错误提示。
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Forbidden");
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }
// 行注释：结束当前代码块，表示这段逻辑处理完成。
}



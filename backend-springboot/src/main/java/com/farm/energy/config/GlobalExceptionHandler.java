// 逐行注释标记：已按业务语义处理，仅增加中文注释，不改变程序逻辑
/**
 * 答辩注释:
 * - 文件： GlobalExceptionHandler
 * - 层级：配置层
 * - 职责：定义框架级 Bean、拦截器、跨域、异常处理与启动初始化逻辑。
 * - 此注释仅用于说明，不会改变运行行为。
 */
// 行注释：声明 GlobalExceptionHandler 所在的包路径，体现它在项目中的模块位置。
package com.farm.energy.config;

// 行注释：引入当前文件要使用的类或注解，保证后续代码可以编译。
import org.springframework.http.HttpStatus;
// 行注释：引入当前文件要使用的类或注解，保证后续代码可以编译。
import org.springframework.http.ResponseEntity;
// 行注释：引入当前文件要使用的类或注解，保证后续代码可以编译。
import org.springframework.web.bind.annotation.ExceptionHandler;
// 行注释：引入 REST 控制器注解，让接口直接返回 JSON。
import org.springframework.web.bind.annotation.RestControllerAdvice;
// 行注释：引入当前文件要使用的类或注解，保证后续代码可以编译。
import org.springframework.web.server.ResponseStatusException;

// 行注释：引入时间类型，用于记录日期、创建时间或预测日期。
import java.time.LocalDateTime;
// 行注释：引入 Map 结构，用于组装接口返回的键值数据。
import java.util.LinkedHashMap;
// 行注释：引入 Map 结构，用于组装接口返回的键值数据。
import java.util.Map;

// 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
@RestControllerAdvice
// 行注释：定义 GlobalExceptionHandler 系统配置类，承载该模块的主要代码。
// 类注释：这是全局异常处理类，负责把后端异常统一转换成前端可读的 JSON 错误信息。
public class GlobalExceptionHandler {

    // 行注释：定义 GlobalExceptionHandler 系统配置类，承载该模块的主要代码。
    @ExceptionHandler(ResponseStatusException.class)
    /**
     * 答辩讲解:
     * - 方法： handleResponseStatus
     * - 作用：支撑当前模块流程的业务辅助方法。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 handleResponseStatus 方法，完成 GlobalExceptionHandler 中对应的一步业务处理。
    // 方法用法：Spring Boot 启动时调用或识别 handleResponseStatus，用于注册系统配置、跨域、拦截器或异常处理。
    public ResponseEntity<Map<String, Object>> handleResponseStatus(ResponseStatusException ex) {
        // 行注释：声明变量 error，保存本行计算或查询得到的结果，供后续逻辑使用。
        Map<String, Object> error = errorBody(
                // 行注释：读取对象字段值，参与业务判断、计算或返回。
                ex.getStatusCode().value(),
                // 行注释：读取对象字段值，参与业务判断、计算或返回。
                ex.getReason(),
                // 行注释：读取对象字段值，参与业务判断、计算或返回。
                ex.getClass().getSimpleName()
        // 行注释：结束当前方法调用，把上面准备好的参数一次性提交执行。
        );
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return ResponseEntity.status(ex.getStatusCode()).body(error);
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：定义 GlobalExceptionHandler 系统配置类，承载该模块的主要代码。
    @ExceptionHandler(IllegalArgumentException.class)
    /**
     * 答辩讲解:
     * - 方法： handleIllegalArg
     * - 作用：支撑当前模块流程的业务辅助方法。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 handleIllegalArg 方法，完成 GlobalExceptionHandler 中对应的一步业务处理。
    // 方法用法：Spring Boot 启动时调用或识别 handleIllegalArg，用于注册系统配置、跨域、拦截器或异常处理。
    public ResponseEntity<Map<String, Object>> handleIllegalArg(IllegalArgumentException ex) {
        // 行注释：声明变量 error，保存本行计算或查询得到的结果，供后续逻辑使用。
        Map<String, Object> error = errorBody(
                // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                HttpStatus.BAD_REQUEST.value(),
                // 行注释：读取对象字段值，参与业务判断、计算或返回。
                ex.getMessage(),
                // 行注释：读取对象字段值，参与业务判断、计算或返回。
                ex.getClass().getSimpleName()
        // 行注释：结束当前方法调用，把上面准备好的参数一次性提交执行。
        );
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return ResponseEntity.badRequest().body(error);
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：定义 GlobalExceptionHandler 系统配置类，承载该模块的主要代码。
    @ExceptionHandler(Exception.class)
    /**
     * 答辩讲解:
     * - 方法： handleException
     * - 作用：支撑当前模块流程的业务辅助方法。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 handleException 方法，完成 GlobalExceptionHandler 中对应的一步业务处理。
    // 方法用法：Spring Boot 启动时调用或识别 handleException，用于注册系统配置、跨域、拦截器或异常处理。
    public ResponseEntity<Map<String, Object>> handleException(Exception ex) {
        // 行注释：声明变量 error，保存本行计算或查询得到的结果，供后续逻辑使用。
        Map<String, Object> error = errorBody(
                // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                // 行注释：读取对象字段值，参与业务判断、计算或返回。
                ex.getMessage() == null ? "Internal server error" : ex.getMessage(),
                // 行注释：读取对象字段值，参与业务判断、计算或返回。
                ex.getClass().getSimpleName()
        // 行注释：结束当前方法调用，把上面准备好的参数一次性提交执行。
        );
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： errorBody
     * - 作用：支撑当前模块流程的业务辅助方法。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 errorBody 方法，完成 GlobalExceptionHandler 中对应的一步业务处理。
    // 方法用法：Spring Boot 启动时调用或识别 errorBody，用于注册系统配置、跨域、拦截器或异常处理。
    private Map<String, Object> errorBody(int status, String message, String errorType) {
        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        Map<String, Object> body = new LinkedHashMap<>();
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        body.put("status", status);
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        body.put("error", errorType);
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        body.put("message", message);
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        body.put("timestamp", LocalDateTime.now());
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return body;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }
// 行注释：结束当前代码块，表示这段逻辑处理完成。
}



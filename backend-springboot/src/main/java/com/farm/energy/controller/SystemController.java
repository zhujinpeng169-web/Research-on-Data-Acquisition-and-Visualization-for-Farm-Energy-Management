// 逐行注释标记：已按业务语义处理，仅增加中文注释，不改变程序逻辑
/**
 * 答辩注释:
 * - 文件： SystemController
 * - 层级：控制器层
 * - 职责：暴露 REST 接口，接收请求参数，执行角色校验，并调用服务层。
 * - 此注释仅用于说明，不会改变运行行为。
 */
// 行注释：声明 SystemController 所在的包路径，体现它在项目中的模块位置。
package com.farm.energy.controller;

// 行注释：引入 GET 接口注解，用于声明查询类接口地址。
import org.springframework.web.bind.annotation.GetMapping;
// 行注释：引入 REST 控制器注解，让接口直接返回 JSON。
import org.springframework.web.bind.annotation.RestController;

// 行注释：引入时间类型，用于记录日期、创建时间或预测日期。
import java.time.LocalDateTime;
// 行注释：引入 Map 结构，用于组装接口返回的键值数据。
import java.util.Map;

// 行注释：声明这是 REST 接口控制器，返回 JSON 数据给前端。
@RestController
// 行注释：定义 SystemController 控制器，承载该模块的主要代码。
// 类注释：这是 System 模块的控制器类，负责接收前端 HTTP 请求、调用 Service，并把结果以 JSON 返回。
public class SystemController {

    // 行注释：声明 GET 查询接口 /health，用于前端读取数据。
    @GetMapping("/health")
    /**
     * 答辩讲解:
     * - 方法： health
     * - 作用：提供服务可用性检查的健康探针接口。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 health 方法，完成 SystemController 中对应的一步业务处理。
    // 方法用法：前端访问 System 模块接口时调用，用来完成对应业务请求并返回 JSON。
    public Map<String, Object> health() {
        // 行注释：组装接口返回数据，Spring 会自动转换成 JSON。
        return Map.of(
                // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                "status", "ok",
                // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                "message", "farm-energy-springboot is running",
                // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                "timestamp", LocalDateTime.now()
        // 行注释：结束当前方法调用，把上面准备好的参数一次性提交执行。
        );
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }
// 行注释：结束当前代码块，表示这段逻辑处理完成。
}



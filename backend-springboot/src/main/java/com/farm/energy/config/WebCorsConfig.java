// 逐行注释标记：已按业务语义处理，仅增加中文注释，不改变程序逻辑
/**
 * 答辩注释:
 * - 文件： WebCorsConfig
 * - 层级：配置层
 * - 职责：定义框架级 Bean、拦截器、跨域、异常处理与启动初始化逻辑。
 * - 此注释仅用于说明，不会改变运行行为。
 */
// 行注释：声明 WebCorsConfig 所在的包路径，体现它在项目中的模块位置。
package com.farm.energy.config;

// 行注释：引入当前文件要使用的类或注解，保证后续代码可以编译。
import org.springframework.context.annotation.Bean;
// 行注释：引入当前文件要使用的类或注解，保证后续代码可以编译。
import org.springframework.context.annotation.Configuration;
// 行注释：引入当前文件要使用的类或注解，保证后续代码可以编译。
import org.springframework.web.cors.CorsConfiguration;
// 行注释：引入当前文件要使用的类或注解，保证后续代码可以编译。
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
// 行注释：引入当前文件要使用的类或注解，保证后续代码可以编译。
import org.springframework.web.filter.CorsFilter;

// 行注释：引入 List 结构，用于返回表格或图表列表数据。
import java.util.List;

// 行注释：把当前类标记为配置类，用来声明系统级 Bean 或 Web 配置。
@Configuration
// 行注释：定义 WebCorsConfig 系统配置类，承载该模块的主要代码。
// 类注释：这是跨域配置类，负责允许前端页面访问后端接口，解决浏览器跨域限制。
public class WebCorsConfig {

    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    @Bean
    /**
     * 答辩讲解:
     * - 方法： corsFilter
     * - 作用：创建 CORS 过滤器 Bean，允许前端跨域访问接口。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 corsFilter 方法，完成 WebCorsConfig 中对应的一步业务处理。
    // 方法用法：Spring Boot 启动时调用或识别 corsFilter，用于注册系统配置、跨域、拦截器或异常处理。
    public CorsFilter corsFilter() {
        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        CorsConfiguration config = new CorsConfiguration();
        // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
        config.setAllowCredentials(true);
        // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
        config.setAllowedOriginPatterns(List.of("*"));
        // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
        config.setAllowedHeaders(List.of("*"));
        // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));

        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
        source.registerCorsConfiguration("/**", config);
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return new CorsFilter(source);
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }
// 行注释：结束当前代码块，表示这段逻辑处理完成。
}



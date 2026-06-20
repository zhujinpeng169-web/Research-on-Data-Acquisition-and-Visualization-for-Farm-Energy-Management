// 逐行注释标记：已按业务语义处理，仅增加中文注释，不改变程序逻辑
/**
 * 答辩注释:
 * - 文件： WebMvcAuthConfig
 * - 层级：配置层
 * - 职责：定义框架级 Bean、拦截器、跨域、异常处理与启动初始化逻辑。
 * - 此注释仅用于说明，不会改变运行行为。
 */
// 行注释：声明 WebMvcAuthConfig 所在的包路径，体现它在项目中的模块位置。
package com.farm.energy.config;

// 行注释：引入鉴权上下文或安全工具，用于识别当前登录用户。
import com.farm.energy.security.AuthInterceptor;
// 行注释：引入当前文件要使用的类或注解，保证后续代码可以编译。
import org.springframework.context.annotation.Configuration;
// 行注释：引入当前文件要使用的类或注解，保证后续代码可以编译。
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
// 行注释：引入当前文件要使用的类或注解，保证后续代码可以编译。
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 行注释：把当前类标记为配置类，用来声明系统级 Bean 或 Web 配置。
@Configuration
// 行注释：定义 WebMvcAuthConfig 系统配置类，承载该模块的主要代码。
// 类注释：这是 Web MVC 配置类，负责注册鉴权拦截器并配置哪些接口需要登录。
public class WebMvcAuthConfig implements WebMvcConfigurer {

    // 行注释：声明字段 authInterceptor，保存当前对象需要的数据或依赖。
    private final AuthInterceptor authInterceptor;

    // 行注释：声明 WebMvcAuthConfig 方法，承接当前模块的接口请求或内部业务处理。
    // 方法用法：Spring 创建 WebMvcAuthConfig 对象时调用这个构造方法，用来注入本类需要的依赖。
    public WebMvcAuthConfig(AuthInterceptor authInterceptor) {
        // 行注释：把构造方法传入的 authInterceptor 保存到成员变量，后续方法会继续调用它。
        this.authInterceptor = authInterceptor;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：说明这里重写父类或接口的方法，例如拦截器入口方法。
    @Override
    /**
     * 答辩讲解:
     * - 方法： addInterceptors
     * - 作用：将自定义认证拦截器注册到 Spring MVC 请求链。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 addInterceptors 方法，完成 WebMvcAuthConfig 中对应的一步业务处理。
    // 方法用法：Spring Boot 启动时调用或识别 addInterceptors，用于注册系统配置、跨域、拦截器或异常处理。
    public void addInterceptors(InterceptorRegistry registry) {
        // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
        registry.addInterceptor(authInterceptor).addPathPatterns("/**");
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }
// 行注释：结束当前代码块，表示这段逻辑处理完成。
}




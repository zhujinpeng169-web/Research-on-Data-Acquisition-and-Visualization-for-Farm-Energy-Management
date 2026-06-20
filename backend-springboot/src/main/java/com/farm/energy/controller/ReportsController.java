// 逐行注释标记：已按业务语义处理，仅增加中文注释，不改变程序逻辑
/**
 * 答辩注释:
 * - 文件： ReportsController
 * - 层级：控制器层
 * - 职责：暴露 REST 接口，接收请求参数，执行角色校验，并调用服务层。
 * - 此注释仅用于说明，不会改变运行行为。
 */
// 行注释：声明 ReportsController 所在的包路径，体现它在项目中的模块位置。
package com.farm.energy.controller;

// 行注释：引入前端请求参数对象，便于 Spring 自动接收表单或 JSON 数据。
import com.farm.energy.dto.ReportGenerateRequest;
// 行注释：引入前端请求参数对象，便于 Spring 自动接收表单或 JSON 数据。
import com.farm.energy.dto.ReportUpdateRequest;
// 行注释：引入鉴权上下文或安全工具，用于识别当前登录用户。
import com.farm.energy.security.AuthContext;
// 行注释：引入业务服务类，把具体业务处理交给服务层完成。
import com.farm.energy.service.OperationLogService;
// 行注释：引入业务服务类，把具体业务处理交给服务层完成。
import com.farm.energy.service.ReportService;
// 行注释：引入请求对象，用于读取请求头、路径和登录上下文。
import jakarta.servlet.http.HttpServletRequest;
// 行注释：引入当前文件要使用的类或注解，保证后续代码可以编译。
import org.springframework.core.io.FileSystemResource;
// 行注释：引入当前文件要使用的类或注解，保证后续代码可以编译。
import org.springframework.core.io.Resource;
// 行注释：引入当前文件要使用的类或注解，保证后续代码可以编译。
import org.springframework.http.HttpHeaders;
// 行注释：引入当前文件要使用的类或注解，保证后续代码可以编译。
import org.springframework.http.HttpStatus;
// 行注释：引入当前文件要使用的类或注解，保证后续代码可以编译。
import org.springframework.http.MediaType;
// 行注释：引入当前文件要使用的类或注解，保证后续代码可以编译。
import org.springframework.http.ResponseEntity;
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
// 行注释：引入当前文件要使用的类或注解，保证后续代码可以编译。
import org.springframework.web.bind.annotation.RequestParam;
// 行注释：引入 REST 控制器注解，让接口直接返回 JSON。
import org.springframework.web.bind.annotation.RestController;

// 行注释：引入当前文件要使用的类或注解，保证后续代码可以编译。
import java.nio.file.Files;
// 行注释：引入当前文件要使用的类或注解，保证后续代码可以编译。
import java.nio.file.Path;
// 行注释：引入 Map 结构，用于组装接口返回的键值数据。
import java.util.Map;

// 行注释：声明这是 REST 接口控制器，返回 JSON 数据给前端。
@RestController
// 行注释：给当前控制器统一添加接口前缀 /api/reports，前端请求都会以它开头。
@RequestMapping("/api/reports")
// 行注释：定义 ReportsController 控制器，承载该模块的主要代码。
// 类注释：这是 报表生成 模块的控制器类，负责接收前端 HTTP 请求、调用 Service，并把结果以 JSON 返回。
public class ReportsController {

    // 行注释：声明字段 reportService，保存当前对象需要的数据或依赖。
    private final ReportService reportService;
    // 行注释：注入操作日志服务，用于记录登录、修改、删除等关键行为。
    private final OperationLogService operationLogService;

    // 行注释：声明 ReportsController 方法，承接当前模块的接口请求或内部业务处理。
    // 方法用法：Spring 创建 ReportsController 对象时调用这个构造方法，用来注入本类需要的依赖。
    public ReportsController(ReportService reportService, OperationLogService operationLogService) {
        // 行注释：把构造方法传入的 reportService 保存到成员变量，后续方法会继续调用它。
        this.reportService = reportService;
        // 行注释：把构造方法传入的 operationLogService 保存到成员变量，后续方法会继续调用它。
        this.operationLogService = operationLogService;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：声明 GET 查询接口 /energy，用于前端读取数据。
    @GetMapping("/energy")
    /**
     * 答辩讲解:
     * - 方法： getEnergyReport
     * - 作用：加载并返回前端视图所需的模块数据。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 getEnergyReport 查询方法，给前端返回当前模块数据。
    // 方法用法：前端查询 Reports 模块数据时调用，用来读取数据库并返回 JSON。
    public Map<String, Object> getEnergyReport(@RequestParam(defaultValue = "month") String period) {
        // 行注释：组装接口返回数据，Spring 会自动转换成 JSON。
        return Map.of("report", reportService.getEnergyReport(period));
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：声明 GET 查询接口 /carbon，用于前端读取数据。
    @GetMapping("/carbon")
    /**
     * 答辩讲解:
     * - 方法： getCarbonReport
     * - 作用：加载并返回前端视图所需的模块数据。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 getCarbonReport 查询方法，给前端返回当前模块数据。
    // 方法用法：前端查询 Reports 模块数据时调用，用来读取数据库并返回 JSON。
    public Map<String, Object> getCarbonReport(@RequestParam(defaultValue = "month") String period) {
        // 行注释：组装接口返回数据，Spring 会自动转换成 JSON。
        return Map.of("report", reportService.getCarbonReport(period));
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：声明 GET 查询接口 /optimization，用于前端读取数据。
    @GetMapping("/optimization")
    /**
     * 答辩讲解:
     * - 方法： getOptimizationReport
     * - 作用：加载并返回前端视图所需的模块数据。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 getOptimizationReport 查询方法，给前端返回当前模块数据。
    // 方法用法：前端查询 Reports 模块数据时调用，用来读取数据库并返回 JSON。
    public Map<String, Object> getOptimizationReport() {
        // 行注释：组装接口返回数据，Spring 会自动转换成 JSON。
        return Map.of("report", reportService.getOptimizationReport());
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：声明 GET 查询接口 /comprehensive，用于前端读取数据。
    @GetMapping("/comprehensive")
    /**
     * 答辩讲解:
     * - 方法： getComprehensiveReport
     * - 作用：加载并返回前端视图所需的模块数据。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 getComprehensiveReport 查询方法，给前端返回当前模块数据。
    // 方法用法：前端查询 Reports 模块数据时调用，用来读取数据库并返回 JSON。
    public Map<String, Object> getComprehensiveReport(@RequestParam(defaultValue = "month") String period) {
        // 行注释：组装接口返回数据，Spring 会自动转换成 JSON。
        return Map.of("report", reportService.getComprehensiveReport(period));
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：声明 POST 接口 /generate-pdf，用于前端提交该模块的业务数据。
    @PostMapping("/generate-pdf")
    /**
     * 答辩讲解:
     * - 方法： generatePdf
     * - 作用：支撑当前模块流程的业务辅助方法。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：声明 generatePdf 方法，承接当前模块的接口请求或内部业务处理。
    // 方法用法：前端点击生成报告时调用，用来汇总数据并生成 PDF 报告记录。
    public Map<String, Object> generatePdf(HttpServletRequest request,
                                           // 行注释：说明该参数来自前端 JSON 请求体，Spring 会自动封装成对象。
                                           @RequestBody(required = false) ReportGenerateRequest body) throws Exception {
        // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
        AuthContext.requireRole(request, "manager", "admin");
        // 行注释：声明变量 reportType，保存本行计算或查询得到的结果，供后续逻辑使用。
        String reportType = body == null ? "comprehensive" : body.getReportType();
        // 行注释：声明变量 period，保存本行计算或查询得到的结果，供后续逻辑使用。
        String period = body == null ? "month" : body.getPeriod();
        // 行注释：声明变量 output，保存本行计算或查询得到的结果，供后续逻辑使用。
        Map<String, Object> output = reportService.generatePdf(reportType, period);
        // 行注释：记录本次业务操作，方便后台审计和答辩说明安全性。
        operationLogService.log(
                // 行注释：传入字符串参数 “reports”，作为当前方法调用的业务标识或显示文本。
                "reports",
                // 行注释：传入字符串参数 “generate_pdf”，作为当前方法调用的业务标识或显示文本。
                "generate_pdf",
                // 行注释：发起接口请求或读取对象数据，获取页面需要的业务信息。
                String.valueOf(output.get("report_id")),
                // 行注释：从请求上下文中取出当前用户名，用于操作日志记录。
                AuthContext.username(request),
                // 行注释：从请求上下文中取出当前用户角色，用于权限判断或日志记录。
                AuthContext.role(request),
                // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                "Generate " + reportType + " report"
        // 行注释：结束当前方法调用，把上面准备好的参数一次性提交执行。
        );
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return output;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：声明 GET 查询接口 /download/{filename}，用于前端读取数据。
    @GetMapping("/download/{filename}")
    /**
     * 答辩讲解:
     * - 方法： downloadReport
     * - 作用：支撑当前模块流程的业务辅助方法。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 downloadReport 方法，完成 ReportsController 中对应的一步业务处理。
    // 方法用法：前端下载报告文件时调用，用来把服务器上的 PDF 文件返回给浏览器。
    public ResponseEntity<Resource> downloadReport(@PathVariable String filename) {
        // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
        try {
            // 行注释：声明变量 path，保存本行计算或查询得到的结果，供后续逻辑使用。
            Path path = reportService.resolveReportPath(filename);
            // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
            if (!Files.exists(path) || !Files.isRegularFile(path)) {
                // 行注释：返回处理结果，最终会交给前端或上层方法使用。
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            // 行注释：结束当前代码块，表示这段逻辑处理完成。
            }
            // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
            FileSystemResource resource = new FileSystemResource(path);
            // 行注释：返回处理结果，最终会交给前端或上层方法使用。
            return ResponseEntity.ok()
                    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                    .body(resource);
        // 行注释：结束 try 代码块并进入异常处理流程。
        } catch (Exception ex) {
            // 行注释：返回处理结果，最终会交给前端或上层方法使用。
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：声明 GET 查询接口 /list，用于前端读取数据。
    @GetMapping("/list")
    /**
     * 答辩讲解:
     * - 方法： getReportList
     * - 作用：加载并返回前端视图所需的模块数据。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 getReportList 查询方法，给前端返回当前模块数据。
    // 方法用法：前端查询 Reports 模块数据时调用，用来读取数据库并返回 JSON。
    public Map<String, Object> getReportList() {
        // 行注释：组装接口返回数据，Spring 会自动转换成 JSON。
        return Map.of("reports", reportService.getReportsList());
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：声明 GET 查询接口 /{id}，用于前端读取数据。
    @GetMapping("/{id}")
    /**
     * 答辩讲解:
     * - 方法： getReportRecord
     * - 作用：加载并返回前端视图所需的模块数据。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 getReportRecord 查询方法，给前端返回当前模块数据。
    // 方法用法：前端查询 Reports 模块数据时调用，用来读取数据库并返回 JSON。
    public Map<String, Object> getReportRecord(@PathVariable Long id) {
        // 行注释：组装接口返回数据，Spring 会自动转换成 JSON。
        return Map.of("report", reportService.getReportRecord(id));
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：声明 PUT 更新接口 /{id}，用于前端修改已有数据。
    @PutMapping("/{id}")
    /**
     * 答辩讲解:
     * - 方法： updateReportRecord
     * - 作用：在输入规范化后，对现有记录执行部分字段更新。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：声明 updateReportRecord 方法，承接当前模块的接口请求或内部业务处理。
    // 方法用法：前端编辑已有记录并保存时调用，用来更新 Reports 模块的数据。
    public Map<String, Object> updateReportRecord(HttpServletRequest request,
                                                  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                                                  @PathVariable Long id,
                                                  // 行注释：说明该参数来自前端 JSON 请求体，Spring 会自动封装成对象。
                                                  @RequestBody ReportUpdateRequest body) {
        // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
        AuthContext.requireRole(request, "manager", "admin");
        // 行注释：声明变量 report，保存本行计算或查询得到的结果，供后续逻辑使用。
        Map<String, Object> report = reportService.updateReportRecord(id, body);
        // 行注释：记录本次业务操作，方便后台审计和答辩说明安全性。
        operationLogService.log(
                // 行注释：传入字符串参数 “reports”，作为当前方法调用的业务标识或显示文本。
                "reports",
                // 行注释：传入字符串参数 “update”，作为当前方法调用的业务标识或显示文本。
                "update",
                // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                String.valueOf(id),
                // 行注释：从请求上下文中取出当前用户名，用于操作日志记录。
                AuthContext.username(request),
                // 行注释：从请求上下文中取出当前用户角色，用于权限判断或日志记录。
                AuthContext.role(request),
                // 行注释：传入字符串参数 “Update report record”，作为当前方法调用的业务标识或显示文本。
                "Update report record"
        // 行注释：结束当前方法调用，把上面准备好的参数一次性提交执行。
        );
        // 行注释：组装接口返回数据，Spring 会自动转换成 JSON。
        return Map.of("report", report, "message", "Report updated");
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：声明 DELETE 删除接口 /{id}，用于前端删除指定记录。
    @DeleteMapping("/{id}")
    /**
     * 答辩讲解:
     * - 方法： deleteReportRecord
     * - 作用：删除或逻辑移除目标记录，并维护数据一致性。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 deleteReportRecord 删除方法，删除前端指定的数据记录。
    // 方法用法：前端确认删除时调用，用来删除 Reports 模块的指定记录。
    public Map<String, Object> deleteReportRecord(HttpServletRequest request, @PathVariable Long id) {
        // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
        AuthContext.requireRole(request, "manager", "admin");
        // 行注释：调用 JPA 删除数据或清理过期记录。
        reportService.deleteReportRecord(id);
        // 行注释：记录本次业务操作，方便后台审计和答辩说明安全性。
        operationLogService.log(
                // 行注释：传入字符串参数 “reports”，作为当前方法调用的业务标识或显示文本。
                "reports",
                // 行注释：传入字符串参数 “delete”，作为当前方法调用的业务标识或显示文本。
                "delete",
                // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                String.valueOf(id),
                // 行注释：从请求上下文中取出当前用户名，用于操作日志记录。
                AuthContext.username(request),
                // 行注释：从请求上下文中取出当前用户角色，用于权限判断或日志记录。
                AuthContext.role(request),
                // 行注释：传入字符串参数 “Delete report record”，作为当前方法调用的业务标识或显示文本。
                "Delete report record"
        // 行注释：结束当前方法调用，把上面准备好的参数一次性提交执行。
        );
        // 行注释：组装接口返回数据，Spring 会自动转换成 JSON。
        return Map.of("message", "Report deleted");
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }
// 行注释：结束当前代码块，表示这段逻辑处理完成。
}



// 逐行注释标记：已按业务语义处理，仅增加中文注释，不改变程序逻辑
/**
 * 答辩注释:
 * - 文件： ReportService
 * - 层级：服务层
 * - 职责：实现领域业务逻辑、数据聚合、校验与持久化编排。
 * - 此注释仅用于说明，不会改变运行行为。
 */
// 行注释：声明 ReportService 所在的包路径，体现它在项目中的模块位置。
package com.farm.energy.service;

// 行注释：引入前端请求参数对象，便于 Spring 自动接收表单或 JSON 数据。
import com.farm.energy.dto.ReportUpdateRequest;
// 行注释：引入数据库实体类，用于读取或保存业务表记录。
import com.farm.energy.entity.ReportRecord;
// 行注释：引入数据访问接口，用于操作对应数据库表。
import com.farm.energy.repository.ReportRecordRepository;
// 行注释：引入当前文件要使用的类或注解，保证后续代码可以编译。
import com.lowagie.text.Document;
// 行注释：引入当前文件要使用的类或注解，保证后续代码可以编译。
import com.lowagie.text.FontFactory;
// 行注释：引入当前文件要使用的类或注解，保证后续代码可以编译。
import com.lowagie.text.Paragraph;
// 行注释：引入当前文件要使用的类或注解，保证后续代码可以编译。
import com.lowagie.text.pdf.PdfWriter;
// 行注释：引入当前文件要使用的类或注解，保证后续代码可以编译。
import org.springframework.beans.factory.annotation.Value;
// 行注释：引入当前文件要使用的类或注解，保证后续代码可以编译。
import org.springframework.http.HttpStatus;
// 行注释：引入当前文件要使用的类或注解，保证后续代码可以编译。
import org.springframework.jdbc.core.JdbcTemplate;
// 行注释：引入当前文件要使用的类或注解，保证后续代码可以编译。
import org.springframework.stereotype.Service;
// 行注释：引入当前文件要使用的类或注解，保证后续代码可以编译。
import org.springframework.transaction.annotation.Transactional;
// 行注释：引入当前文件要使用的类或注解，保证后续代码可以编译。
import org.springframework.web.server.ResponseStatusException;

// 行注释：引入当前文件要使用的类或注解，保证后续代码可以编译。
import java.io.File;
// 行注释：引入当前文件要使用的类或注解，保证后续代码可以编译。
import java.io.FileOutputStream;
// 行注释：引入高精度数字类型，用于能源、碳排等小数计算。
import java.math.BigDecimal;
// 行注释：引入当前文件要使用的类或注解，保证后续代码可以编译。
import java.math.RoundingMode;
// 行注释：引入当前文件要使用的类或注解，保证后续代码可以编译。
import java.nio.file.Path;
// 行注释：引入时间类型，用于记录日期、创建时间或预测日期。
import java.time.LocalDateTime;
// 行注释：引入当前文件要使用的类或注解，保证后续代码可以编译。
import java.time.format.DateTimeFormatter;
// 行注释：引入 Map 结构，用于组装接口返回的键值数据。
import java.util.LinkedHashMap;
// 行注释：引入 List 结构，用于返回表格或图表列表数据。
import java.util.List;
// 行注释：引入 Map 结构，用于组装接口返回的键值数据。
import java.util.Map;

// 行注释：把当前类标记为业务服务层，专门处理模块业务逻辑。
@Service
// 行注释：定义 ReportService 业务服务，承载该模块的主要代码。
// 类注释：这是 报表生成 模块的业务服务类，负责处理核心业务逻辑、数据校验、统计计算和数据库读写编排。
public class ReportService {

    // 行注释：注入 JDBC 查询工具，用于执行统计分析 SQL。
    private final JdbcTemplate jdbcTemplate;
    // 行注释：注入仓储接口，用于访问对应数据库表。
    private final ReportRecordRepository reportRecordRepository;
    // 行注释：声明字段 reportDirectory，保存当前对象需要的数据或依赖。
    private final Path reportDirectory;

    // 行注释：声明 ReportService 方法，承接当前模块的接口请求或内部业务处理。
    // 方法用法：Spring 创建 ReportService 对象时调用这个构造方法，用来注入本类需要的依赖。
    public ReportService(JdbcTemplate jdbcTemplate,
                         // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                         ReportRecordRepository reportRecordRepository,
                         // 行注释：读取配置文件或环境变量中的值，作为运行参数使用。
                         @Value("${app.report.dir:reports}") String reportDir) {
        // 行注释：把构造方法传入的 jdbcTemplate 保存到成员变量，后续方法会继续调用它。
        this.jdbcTemplate = jdbcTemplate;
        // 行注释：把构造方法传入的 reportRecordRepository 保存到成员变量，后续方法会继续调用它。
        this.reportRecordRepository = reportRecordRepository;
        // 行注释：把构造方法传入的 reportDirectory 保存到成员变量，后续方法会继续调用它。
        this.reportDirectory = Path.of(reportDir).toAbsolutePath().normalize();
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： getEnergyReport
     * - 作用：加载并返回前端视图所需的模块数据。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 getEnergyReport 查询方法，给前端返回当前模块数据。
    // 方法用法：控制器查询数据时调用 getEnergyReport，负责从数据库聚合并整理 Report 模块结果。
    public Map<String, Object> getEnergyReport(String period) {
        // 行注释：声明变量 dateFilter，保存本行计算或查询得到的结果，供后续逻辑使用。
        String dateFilter = reportDateFilter(period, "em.timestamp");
        // 行注释：声明变量 sql，保存本行计算或查询得到的结果，供后续逻辑使用。
        String sql = """
                SELECT
                  DATE(em.timestamp) AS date,
                  COALESCE(SUM(em.energy_generated), 0) AS total_generated,
                  COALESCE(SUM(em.energy_consumed), 0) AS total_consumed,
                  COALESCE(AVG(em.efficiency), 0) AS avg_efficiency,
                  ed.device_type
                FROM energy_monitoring em
                JOIN energy_devices ed ON em.device_id = ed.id
                WHERE
                """ + dateFilter + " GROUP BY DATE(em.timestamp), ed.device_type ORDER BY date DESC";

        // 行注释：执行 SQL 查询，并把数据库结果转换成 Java 数据结构。
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        // 行注释：声明变量 totalGenerated，保存本行计算或查询得到的结果，供后续逻辑使用。
        BigDecimal totalGenerated = sum(rows, "total_generated");
        // 行注释：声明变量 totalConsumed，保存本行计算或查询得到的结果，供后续逻辑使用。
        BigDecimal totalConsumed = sum(rows, "total_consumed");
        // 行注释：声明变量 avgEfficiency，保存本行计算或查询得到的结果，供后续逻辑使用。
        BigDecimal avgEfficiency = rows.isEmpty() ? BigDecimal.ZERO
                // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                : rows.stream()
                // 行注释：发起接口请求或读取对象数据，获取页面需要的业务信息。
                .map(row -> toDecimal(row.get("avg_efficiency")))
                // 行注释：汇总列表数据，计算统计值或累计结果。
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                .divide(BigDecimal.valueOf(rows.size()), 2, RoundingMode.HALF_UP);

        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        Map<String, Object> summary = new LinkedHashMap<>();
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        summary.put("total_generated", totalGenerated.setScale(2, RoundingMode.HALF_UP));
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        summary.put("total_consumed", totalConsumed.setScale(2, RoundingMode.HALF_UP));
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        summary.put("avg_efficiency", avgEfficiency.setScale(2, RoundingMode.HALF_UP));
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        summary.put("period", period);
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        summary.put("data_points", rows.size());
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        summary.put("details", rows);
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return summary;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： getCarbonReport
     * - 作用：加载并返回前端视图所需的模块数据。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 getCarbonReport 查询方法，给前端返回当前模块数据。
    // 方法用法：控制器查询数据时调用 getCarbonReport，负责从数据库聚合并整理 Report 模块结果。
    public Map<String, Object> getCarbonReport(String period) {
        // 行注释：声明变量 dateFilter，保存本行计算或查询得到的结果，供后续逻辑使用。
        String dateFilter = reportDateFilter(period, "timestamp");
        // 行注释：声明变量 sql，保存本行计算或查询得到的结果，供后续逻辑使用。
        String sql = """
                SELECT
                  DATE(timestamp) AS date,
                  energy_source,
                  COALESCE(SUM(carbon_emission), 0) AS total_emission,
                  COALESCE(SUM(energy_amount), 0) AS total_energy
                FROM carbon_emissions
                WHERE
                """ + dateFilter + " GROUP BY DATE(timestamp), energy_source ORDER BY date DESC";

        // 行注释：执行 SQL 查询，并把数据库结果转换成 Java 数据结构。
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        Map<String, Object> summary = new LinkedHashMap<>();
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        summary.put("total_emission", sum(rows, "total_emission").setScale(2, RoundingMode.HALF_UP));
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        summary.put("total_energy", sum(rows, "total_energy").setScale(2, RoundingMode.HALF_UP));
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        summary.put("period", period);
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        summary.put("breakdown", rows);
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return summary;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： getOptimizationReport
     * - 作用：加载并返回前端视图所需的模块数据。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 getOptimizationReport 查询方法，给前端返回当前模块数据。
    // 方法用法：控制器查询数据时调用 getOptimizationReport，负责从数据库聚合并整理 Report 模块结果。
    public Map<String, Object> getOptimizationReport() {
        // 行注释：声明变量 sql，保存本行计算或查询得到的结果，供后续逻辑使用。
        String sql = """
                SELECT id, created_at, recommendation_type, description, potential_savings, priority, status
                FROM energy_recommendations
                WHERE created_at >= DATE_SUB(NOW(), INTERVAL 30 DAY)
                ORDER BY priority DESC, created_at DESC
                """;
        // 行注释：执行 SQL 查询，并把数据库结果转换成 Java 数据结构。
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        // 行注释：声明变量 high，保存本行计算或查询得到的结果，供后续逻辑使用。
        long high = rows.stream().filter(r -> "high".equals(String.valueOf(r.get("priority")))).count();
        // 行注释：声明变量 medium，保存本行计算或查询得到的结果，供后续逻辑使用。
        long medium = rows.stream().filter(r -> "medium".equals(String.valueOf(r.get("priority")))).count();
        // 行注释：声明变量 low，保存本行计算或查询得到的结果，供后续逻辑使用。
        long low = rows.stream().filter(r -> "low".equals(String.valueOf(r.get("priority")))).count();

        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        Map<String, Object> summary = new LinkedHashMap<>();
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        summary.put("total_recommendations", rows.size());
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        summary.put("total_potential_savings", sum(rows, "potential_savings").setScale(2, RoundingMode.HALF_UP));
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        summary.put("high_priority", high);
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        summary.put("medium_priority", medium);
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        summary.put("low_priority", low);
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        summary.put("recommendations", rows);
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return summary;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： getComprehensiveReport
     * - 作用：加载并返回前端视图所需的模块数据。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 getComprehensiveReport 查询方法，给前端返回当前模块数据。
    // 方法用法：控制器查询数据时调用 getComprehensiveReport，负责从数据库聚合并整理 Report 模块结果。
    public Map<String, Object> getComprehensiveReport(String period) {
        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        Map<String, Object> report = new LinkedHashMap<>();
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        report.put("generated_at", LocalDateTime.now());
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        report.put("period", period);
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        report.put("energy_summary", getEnergyReport(period));
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        report.put("carbon_summary", getCarbonReport(period));
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        report.put("optimization_summary", getOptimizationReport());
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return report;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：开启事务，保证该方法内的数据库操作要么全部成功要么回滚。
    @Transactional
    /**
     * 答辩讲解:
     * - 方法： generatePdf
     * - 作用：支撑当前模块流程的业务辅助方法。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：声明 generatePdf 方法，承接当前模块的接口请求或内部业务处理。
    // 方法用法：服务层内部或控制器调用 generatePdf，用于完成 Report 模块的一段核心业务逻辑。
    public Map<String, Object> generatePdf(String reportType, String period) throws Exception {
        // 行注释：声明变量 type，保存本行计算或查询得到的结果，供后续逻辑使用。
        String type = reportType == null || reportType.isBlank() ? "comprehensive" : reportType;
        // 行注释：声明变量 reportPeriod，保存本行计算或查询得到的结果，供后续逻辑使用。
        String reportPeriod = period == null || period.isBlank() ? "month" : period;

        // 行注释：声明变量 energy，保存本行计算或查询得到的结果，供后续逻辑使用。
        Map<String, Object> energy = getEnergyReport(reportPeriod);
        // 行注释：声明变量 carbon，保存本行计算或查询得到的结果，供后续逻辑使用。
        Map<String, Object> carbon = getCarbonReport(reportPeriod);
        // 行注释：声明变量 optimization，保存本行计算或查询得到的结果，供后续逻辑使用。
        Map<String, Object> optimization = getOptimizationReport();

        // 行注释：声明变量 dir，保存本行计算或查询得到的结果，供后续逻辑使用。
        File dir = reportDirectory.toFile();
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (!dir.exists() && !dir.mkdirs()) {
            // 行注释：抛出明确异常，让前端收到规范的错误提示。
            throw new IllegalStateException("Unable to create report directory: " + reportDirectory);
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }

        // 行注释：声明变量 filename，保存本行计算或查询得到的结果，供后续逻辑使用。
        String filename = "report_" + type + "_" + System.currentTimeMillis() + ".pdf";
        // 行注释：声明变量 output，保存本行计算或查询得到的结果，供后续逻辑使用。
        File output = reportDirectory.resolve(filename).toFile();

        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        Document document = new Document();
        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        PdfWriter.getInstance(document, new FileOutputStream(output));
        // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
        document.open();

        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        document.add(new Paragraph("Farm Energy Management Report", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18)));
        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        document.add(new Paragraph("Generated at: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        document.add(new Paragraph("Report type: " + type + " | Period: " + reportPeriod));
        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        document.add(new Paragraph(" "));

        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        document.add(new Paragraph("Energy Summary", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        document.add(new Paragraph("Total generated: " + energy.get("total_generated") + " kWh"));
        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        document.add(new Paragraph("Total consumed: " + energy.get("total_consumed") + " kWh"));
        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        document.add(new Paragraph("Average efficiency: " + energy.get("avg_efficiency") + "%"));
        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        document.add(new Paragraph(" "));

        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        document.add(new Paragraph("Carbon Summary", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        document.add(new Paragraph("Total emission: " + carbon.get("total_emission") + " kg CO2"));
        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        document.add(new Paragraph("Total energy: " + carbon.get("total_energy") + " kWh"));
        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        document.add(new Paragraph(" "));

        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        document.add(new Paragraph("Optimization Summary", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        document.add(new Paragraph("Total recommendations: " + optimization.get("total_recommendations")));
        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        document.add(new Paragraph("Potential savings: " + optimization.get("total_potential_savings") + " kWh"));

        // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
        @SuppressWarnings("unchecked")
        // 行注释：声明变量 recommendations，保存本行计算或查询得到的结果，供后续逻辑使用。
        List<Map<String, Object>> recommendations = (List<Map<String, Object>>) optimization.get("recommendations");
        // 行注释：声明变量 count，保存本行计算或查询得到的结果，供后续逻辑使用。
        int count = Math.min(5, recommendations.size());
        // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
        for (int i = 0; i < count; i++) {
            // 行注释：声明变量 rec，保存本行计算或查询得到的结果，供后续逻辑使用。
            Map<String, Object> rec = recommendations.get(i);
            // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
            document.add(new Paragraph((i + 1) + ". " + rec.get("description")
                    // 行注释：发起接口请求或读取对象数据，获取页面需要的业务信息。
                    + " (priority: " + rec.get("priority")
                    // 行注释：发起接口请求或读取对象数据，获取页面需要的业务信息。
                    + ", saving: " + rec.get("potential_savings") + " kWh)"));
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }

        // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
        document.close();

        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        ReportRecord record = new ReportRecord();
        // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
        record.setReportType(type);
        // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
        record.setReportPeriod(reportPeriod);
        // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
        record.setFilePath(filename);
        // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
        record.setStatus("completed");
        // 行注释：调用 JPA 保存实体，把数据写入数据库。
        reportRecordRepository.save(record);

        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        Map<String, Object> response = new LinkedHashMap<>();
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        response.put("message", "Report generated");
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        response.put("report_id", record.getId());
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        response.put("filename", filename);
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        response.put("download_url", "/api/reports/download/" + filename);
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return response;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： getReportsList
     * - 作用：加载并返回前端视图所需的模块数据。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 getReportsList 查询方法，给前端返回当前模块数据。
    // 方法用法：控制器查询数据时调用 getReportsList，负责从数据库聚合并整理 Report 模块结果。
    public List<Map<String, Object>> getReportsList() {
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return reportRecordRepository.findTop50ByOrderByGeneratedAtDesc()
                // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                .stream()
                // 行注释：遍历列表数据，把后端结果转换成页面或图表需要的结构。
                .map(this::toMap)
                // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                .toList();
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： getReportRecord
     * - 作用：加载并返回前端视图所需的模块数据。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 getReportRecord 查询方法，给前端返回当前模块数据。
    // 方法用法：控制器查询数据时调用 getReportRecord，负责从数据库聚合并整理 Report 模块结果。
    public Map<String, Object> getReportRecord(Long id) {
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return toMap(requireReport(id));
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：开启事务，保证该方法内的数据库操作要么全部成功要么回滚。
    @Transactional
    /**
     * 答辩讲解:
     * - 方法： updateReportRecord
     * - 作用：在输入规范化后，对现有记录执行部分字段更新。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 updateReportRecord 更新方法，修改数据库中的已有记录。
    // 方法用法：控制器修改数据时调用 updateReportRecord，负责找到原记录、更新字段并保存。
    public Map<String, Object> updateReportRecord(Long id, ReportUpdateRequest request) {
        // 行注释：声明变量 entity，保存本行计算或查询得到的结果，供后续逻辑使用。
        ReportRecord entity = requireReport(id);
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (request.getStatus() != null && !request.getStatus().isBlank()) {
            // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
            entity.setStatus(request.getStatus().trim().toLowerCase());
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (request.getReportType() != null && !request.getReportType().isBlank()) {
            // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
            entity.setReportType(request.getReportType().trim().toLowerCase());
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (request.getReportPeriod() != null && !request.getReportPeriod().isBlank()) {
            // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
            entity.setReportPeriod(request.getReportPeriod().trim().toLowerCase());
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：调用 JPA 保存实体，把数据写入数据库。
        return toMap(reportRecordRepository.save(entity));
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：开启事务，保证该方法内的数据库操作要么全部成功要么回滚。
    @Transactional
    /**
     * 答辩讲解:
     * - 方法： deleteReportRecord
     * - 作用：删除或逻辑移除目标记录，并维护数据一致性。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 deleteReportRecord 删除方法，删除前端指定的数据记录。
    // 方法用法：控制器删除数据时调用 deleteReportRecord，负责校验记录存在并执行删除。
    public void deleteReportRecord(Long id) {
        // 行注释：调用 JPA 删除数据或清理过期记录。
        reportRecordRepository.delete(requireReport(id));
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： resolveReportPath
     * - 作用：支撑当前模块流程的业务辅助方法。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 resolveReportPath 方法，完成 ReportService 中对应的一步业务处理。
    // 方法用法：服务层内部或控制器调用 resolveReportPath，用于完成 Report 模块的一段核心业务逻辑。
    public Path resolveReportPath(String filename) {
        // 行注释：声明变量 normalized，保存本行计算或查询得到的结果，供后续逻辑使用。
        Path normalized = reportDirectory.resolve(filename).normalize();
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (!normalized.startsWith(reportDirectory)) {
            // 行注释：抛出明确异常，让前端收到规范的错误提示。
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid file path");
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return normalized;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： requireReport
     * - 作用：加载必需实体，未找到时抛出明确异常。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 requireReport 方法，完成 ReportService 中对应的一步业务处理。
    // 方法用法：业务处理前调用它确认目标记录存在，不存在时抛出明确错误。
    private ReportRecord requireReport(Long id) {
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return reportRecordRepository.findById(id)
                // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Report not found"));
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： toMap
     * - 作用：将实体对象转换为接口响应 Map，并保持字段命名稳定。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 toMap 方法，完成 ReportService 中对应的一步业务处理。
    // 方法用法：返回接口数据前调用它，把实体对象转换成前端更容易读取的 Map 结构。
    private Map<String, Object> toMap(ReportRecord entity) {
        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        Map<String, Object> row = new LinkedHashMap<>();
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        row.put("id", entity.getId());
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        row.put("report_type", entity.getReportType());
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        row.put("report_period", entity.getReportPeriod());
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        row.put("generated_at", entity.getGeneratedAt());
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        row.put("file_path", entity.getFilePath());
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        row.put("status", entity.getStatus());
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return row;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： sum
     * - 作用：支撑当前模块流程的业务辅助方法。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 sum 方法，完成 ReportService 中对应的一步业务处理。
    // 方法用法：统计计算时调用它，统一处理数字转换、保留小数和空值。
    private BigDecimal sum(List<Map<String, Object>> rows, String key) {
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return rows.stream()
                // 行注释：发起接口请求或读取对象数据，获取页面需要的业务信息。
                .map(row -> toDecimal(row.get(key)))
                // 行注释：汇总列表数据，计算统计值或累计结果。
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： toDecimal
     * - 作用：数值辅助方法，用于避免业务计算中的空值和精度问题。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 toDecimal 方法，完成 ReportService 中对应的一步业务处理。
    // 方法用法：服务层内部或控制器调用 toDecimal，用于完成 Report 模块的一段核心业务逻辑。
    private BigDecimal toDecimal(Object value) {
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (value == null) {
            // 行注释：返回处理结果，最终会交给前端或上层方法使用。
            return BigDecimal.ZERO;
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (value instanceof BigDecimal decimal) {
            // 行注释：返回处理结果，最终会交给前端或上层方法使用。
            return decimal;
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (value instanceof Number number) {
            // 行注释：返回处理结果，最终会交给前端或上层方法使用。
            return BigDecimal.valueOf(number.doubleValue());
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return new BigDecimal(value.toString());
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： reportDateFilter
     * - 作用：支撑当前模块流程的业务辅助方法。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 reportDateFilter 方法，完成 ReportService 中对应的一步业务处理。
    // 方法用法：服务层内部或控制器调用 reportDateFilter，用于完成 Report 模块的一段核心业务逻辑。
    private String reportDateFilter(String period, String column) {
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if ("week".equalsIgnoreCase(period)) {
            // 行注释：返回处理结果，最终会交给前端或上层方法使用。
            return "DATE(" + column + ") >= DATE_SUB(CURDATE(), INTERVAL 7 DAY)";
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if ("year".equalsIgnoreCase(period)) {
            // 行注释：返回处理结果，最终会交给前端或上层方法使用。
            return "DATE(" + column + ") >= DATE_SUB(CURDATE(), INTERVAL 365 DAY)";
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return "DATE(" + column + ") >= DATE_SUB(CURDATE(), INTERVAL 30 DAY)";
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }
// 行注释：结束当前代码块，表示这段逻辑处理完成。
}



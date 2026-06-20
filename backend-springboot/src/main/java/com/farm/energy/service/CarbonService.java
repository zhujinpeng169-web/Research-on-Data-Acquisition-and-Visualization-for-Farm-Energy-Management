// 逐行注释标记：已按业务语义处理，仅增加中文注释，不改变程序逻辑
/**
 * 答辩注释:
 * - 文件： CarbonService
 * - 层级：服务层
 * - 职责：实现领域业务逻辑、数据聚合、校验与持久化编排。
 * - 此注释仅用于说明，不会改变运行行为。
 */
// 行注释：声明 CarbonService 所在的包路径，体现它在项目中的模块位置。
package com.farm.energy.service;

// 行注释：引入前端请求参数对象，便于 Spring 自动接收表单或 JSON 数据。
import com.farm.energy.dto.CarbonEmissionRequest;
// 行注释：引入前端请求参数对象，便于 Spring 自动接收表单或 JSON 数据。
import com.farm.energy.dto.CarbonEmissionUpdateRequest;
// 行注释：引入数据库实体类，用于读取或保存业务表记录。
import com.farm.energy.entity.CarbonEmission;
// 行注释：引入数据访问接口，用于操作对应数据库表。
import com.farm.energy.repository.CarbonEmissionRepository;
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

// 行注释：引入高精度数字类型，用于能源、碳排等小数计算。
import java.math.BigDecimal;
// 行注释：引入当前文件要使用的类或注解，保证后续代码可以编译。
import java.math.RoundingMode;
// 行注释：引入时间类型，用于记录日期、创建时间或预测日期。
import java.time.LocalDateTime;
// 行注释：引入 List 结构，用于返回表格或图表列表数据。
import java.util.ArrayList;
// 行注释：引入 Map 结构，用于组装接口返回的键值数据。
import java.util.LinkedHashMap;
// 行注释：引入 List 结构，用于返回表格或图表列表数据。
import java.util.List;
// 行注释：引入 Map 结构，用于组装接口返回的键值数据。
import java.util.Map;

// 行注释：把当前类标记为业务服务层，专门处理模块业务逻辑。
@Service
// 行注释：定义 CarbonService 业务服务，承载该模块的主要代码。
// 类注释：这是 碳排放管理 模块的业务服务类，负责处理核心业务逻辑、数据校验、统计计算和数据库读写编排。
public class CarbonService {

    // 行注释：组装接口返回数据，Spring 会自动转换成 JSON。
    private static final Map<String, BigDecimal> EMISSION_FACTORS = Map.of(
            // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
            "solar", new BigDecimal("0.05"),
            // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
            "wind", new BigDecimal("0.02"),
            // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
            "biomass", new BigDecimal("0.23"),
            // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
            "grid", new BigDecimal("0.85"),
            // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
            "diesel", new BigDecimal("2.68"),
            // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
            "natural_gas", new BigDecimal("0.45")
    // 行注释：结束当前方法调用，把上面准备好的参数一次性提交执行。
    );

    // 行注释：注入 JDBC 查询工具，用于执行统计分析 SQL。
    private final JdbcTemplate jdbcTemplate;
    // 行注释：注入仓储接口，用于访问对应数据库表。
    private final CarbonEmissionRepository carbonEmissionRepository;

    // 行注释：声明 CarbonService 方法，承接当前模块的接口请求或内部业务处理。
    // 方法用法：Spring 创建 CarbonService 对象时调用这个构造方法，用来注入本类需要的依赖。
    public CarbonService(JdbcTemplate jdbcTemplate, CarbonEmissionRepository carbonEmissionRepository) {
        // 行注释：把构造方法传入的 jdbcTemplate 保存到成员变量，后续方法会继续调用它。
        this.jdbcTemplate = jdbcTemplate;
        // 行注释：把构造方法传入的 carbonEmissionRepository 保存到成员变量，后续方法会继续调用它。
        this.carbonEmissionRepository = carbonEmissionRepository;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： listEmissions
     * - 作用：查询并返回集合数据，用于界面表格/图表渲染。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 listEmissions 方法，完成 CarbonService 中对应的一步业务处理。
    // 方法用法：控制器加载列表时调用 listEmissions，负责查询并转换成前端表格需要的数据结构。
    public List<Map<String, Object>> listEmissions(String source, Integer limit) {
        // 行注释：声明变量 realLimit，保存本行计算或查询得到的结果，供后续逻辑使用。
        int realLimit = (limit == null || limit <= 0 || limit > 1000) ? 200 : limit;
        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        StringBuilder sql = new StringBuilder("""
                SELECT id, timestamp, energy_source, energy_amount, carbon_emission, emission_factor
                FROM carbon_emissions
                """);
        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        List<Object> params = new ArrayList<>();
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (source != null && !source.isBlank()) {
            // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
            sql.append(" WHERE energy_source = ? ");
            // 行注释：向列表中追加一项数据，用于表格、图表或返回结果。
            params.add(source.trim().toLowerCase());
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
        sql.append(" ORDER BY timestamp DESC LIMIT ? ");
        // 行注释：向列表中追加一项数据，用于表格、图表或返回结果。
        params.add(realLimit);
        // 行注释：执行 SQL 查询，并把数据库结果转换成 Java 数据结构。
        return jdbcTemplate.queryForList(sql.toString(), params.toArray());
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： getEmission
     * - 作用：加载并返回前端视图所需的模块数据。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 getEmission 查询方法，给前端返回当前模块数据。
    // 方法用法：控制器查询数据时调用 getEmission，负责从数据库聚合并整理 Carbon 模块结果。
    public Map<String, Object> getEmission(Long id) {
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return toMap(requireEmission(id));
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：开启事务，保证该方法内的数据库操作要么全部成功要么回滚。
    @Transactional
    /**
     * 答辩讲解:
     * - 方法： recordEmission
     * - 作用：支撑当前模块流程的业务辅助方法。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 recordEmission 方法，完成 CarbonService 中对应的一步业务处理。
    // 方法用法：服务层内部或控制器调用 recordEmission，用于完成 Carbon 模块的一段核心业务逻辑。
    public Map<String, Object> recordEmission(CarbonEmissionRequest request) {
        // 行注释：声明变量 source，保存本行计算或查询得到的结果，供后续逻辑使用。
        String source = normalizeSource(request.getEnergySource());
        // 行注释：声明变量 amount，保存本行计算或查询得到的结果，供后续逻辑使用。
        BigDecimal amount = request.getEnergyAmount() == null ? BigDecimal.ZERO : request.getEnergyAmount();
        // 行注释：声明变量 factor，保存本行计算或查询得到的结果，供后续逻辑使用。
        BigDecimal factor = EMISSION_FACTORS.getOrDefault(source, EMISSION_FACTORS.get("grid"));
        // 行注释：声明变量 emission，保存本行计算或查询得到的结果，供后续逻辑使用。
        BigDecimal emission = amount.multiply(factor).setScale(2, RoundingMode.HALF_UP);

        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        CarbonEmission entity = new CarbonEmission();
        // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
        entity.setEnergySource(source);
        // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
        entity.setEnergyAmount(amount);
        // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
        entity.setEmissionFactor(factor);
        // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
        entity.setCarbonEmission(emission);
        // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
        entity.setTimestamp(LocalDateTime.now());
        // 行注释：调用 JPA 保存实体，把数据写入数据库。
        return toMap(carbonEmissionRepository.save(entity));
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：开启事务，保证该方法内的数据库操作要么全部成功要么回滚。
    @Transactional
    /**
     * 答辩讲解:
     * - 方法： updateEmission
     * - 作用：在输入规范化后，对现有记录执行部分字段更新。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 updateEmission 更新方法，修改数据库中的已有记录。
    // 方法用法：控制器修改数据时调用 updateEmission，负责找到原记录、更新字段并保存。
    public Map<String, Object> updateEmission(Long id, CarbonEmissionUpdateRequest request) {
        // 行注释：声明变量 entity，保存本行计算或查询得到的结果，供后续逻辑使用。
        CarbonEmission entity = requireEmission(id);
        // 行注释：声明变量 source，保存本行计算或查询得到的结果，供后续逻辑使用。
        String source = entity.getEnergySource();
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (request.getEnergySource() != null && !request.getEnergySource().isBlank()) {
            // 行注释：给变量 source 赋值，更新当前业务流程中的临时状态。
            source = normalizeSource(request.getEnergySource());
            // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
            entity.setEnergySource(source);
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (request.getEnergyAmount() != null) {
            // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
            entity.setEnergyAmount(request.getEnergyAmount());
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (request.getTimestamp() != null) {
            // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
            entity.setTimestamp(request.getTimestamp());
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }

        // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
        BigDecimal factor;
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (request.getEmissionFactor() != null) {
            // 行注释：给变量 factor 赋值，更新当前业务流程中的临时状态。
            factor = request.getEmissionFactor();
        // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
        } else {
            // 行注释：给变量 factor 赋值，更新当前业务流程中的临时状态。
            factor = EMISSION_FACTORS.getOrDefault(source, EMISSION_FACTORS.get("grid"));
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
        entity.setEmissionFactor(factor);
        // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
        entity.setCarbonEmission(entity.getEnergyAmount().multiply(factor).setScale(2, RoundingMode.HALF_UP));

        // 行注释：调用 JPA 保存实体，把数据写入数据库。
        return toMap(carbonEmissionRepository.save(entity));
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：开启事务，保证该方法内的数据库操作要么全部成功要么回滚。
    @Transactional
    /**
     * 答辩讲解:
     * - 方法： deleteEmission
     * - 作用：删除或逻辑移除目标记录，并维护数据一致性。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 deleteEmission 删除方法，删除前端指定的数据记录。
    // 方法用法：控制器删除数据时调用 deleteEmission，负责校验记录存在并执行删除。
    public void deleteEmission(Long id) {
        // 行注释：调用 JPA 删除数据或清理过期记录。
        carbonEmissionRepository.delete(requireEmission(id));
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： getStatistics
     * - 作用：加载并返回前端视图所需的模块数据。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 getStatistics 查询方法，给前端返回当前模块数据。
    // 方法用法：控制器查询数据时调用 getStatistics，负责从数据库聚合并整理 Carbon 模块结果。
    public Map<String, Object> getStatistics(String period) {
        // 行注释：声明变量 dateFilter，保存本行计算或查询得到的结果，供后续逻辑使用。
        String dateFilter = buildDateFilter(period, true);
        // 行注释：声明变量 sql，保存本行计算或查询得到的结果，供后续逻辑使用。
        String sql = """
                SELECT
                  COALESCE(SUM(carbon_emission), 0) AS total_emission,
                  COALESCE(AVG(carbon_emission), 0) AS avg_emission,
                  COUNT(*) AS record_count
                FROM carbon_emissions
                WHERE
                """ + dateFilter;

        // 行注释：执行 SQL 查询，并把数据库结果转换成 Java 数据结构。
        Map<String, Object> map = jdbcTemplate.queryForMap(sql);
        // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
        normalize(map, "total_emission", "avg_emission");
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return map;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： getBreakdown
     * - 作用：加载并返回前端视图所需的模块数据。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 getBreakdown 查询方法，给前端返回当前模块数据。
    // 方法用法：控制器查询数据时调用 getBreakdown，负责从数据库聚合并整理 Carbon 模块结果。
    public List<Map<String, Object>> getBreakdown(String period) {
        // 行注释：声明变量 dateFilter，保存本行计算或查询得到的结果，供后续逻辑使用。
        String dateFilter = buildDateFilter(period, false);
        // 行注释：声明变量 sql，保存本行计算或查询得到的结果，供后续逻辑使用。
        String sql = """
                SELECT
                  energy_source,
                  COALESCE(SUM(carbon_emission), 0) AS total_emission,
                  COALESCE(SUM(energy_amount), 0) AS total_energy,
                  COALESCE(AVG(emission_factor), 0) AS avg_factor
                FROM carbon_emissions
                WHERE
                """ + dateFilter + " GROUP BY energy_source ORDER BY total_emission DESC";
        // 行注释：执行 SQL 查询，并把数据库结果转换成 Java 数据结构。
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
        rows.forEach(row -> normalize(row, "total_emission", "total_energy", "avg_factor"));
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return rows;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： getTrends
     * - 作用：加载并返回前端视图所需的模块数据。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 getTrends 查询方法，给前端返回当前模块数据。
    // 方法用法：控制器查询数据时调用 getTrends，负责从数据库聚合并整理 Carbon 模块结果。
    public Map<String, Object> getTrends() {
        // 行注释：声明变量 sql，保存本行计算或查询得到的结果，供后续逻辑使用。
        String sql = """
                SELECT
                  DATE(timestamp) AS date,
                  COALESCE(SUM(carbon_emission), 0) AS carbon_emission
                FROM carbon_emissions
                WHERE DATE(timestamp) >= DATE_SUB(CURDATE(), INTERVAL 30 DAY)
                GROUP BY DATE(timestamp)
                ORDER BY date ASC
                """;
        // 行注释：执行 SQL 查询，并把数据库结果转换成 Java 数据结构。
        List<Map<String, Object>> data = jdbcTemplate.queryForList(sql);
        // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
        data.forEach(row -> normalize(row, "carbon_emission"));

        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        Map<String, Object> response = new LinkedHashMap<>();
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        response.put("data", data);
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        response.put("analysis", analyzeTrend(data));
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return response;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： getReductionStrategies
     * - 作用：加载并返回前端视图所需的模块数据。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 getReductionStrategies 查询方法，给前端返回当前模块数据。
    // 方法用法：控制器查询数据时调用 getReductionStrategies，负责从数据库聚合并整理 Carbon 模块结果。
    public Map<String, Object> getReductionStrategies() {
        // 行注释：声明变量 totalSql，保存本行计算或查询得到的结果，供后续逻辑使用。
        String totalSql = """
                SELECT COALESCE(SUM(carbon_emission), 0) AS total
                FROM carbon_emissions
                WHERE DATE(timestamp) >= DATE_SUB(CURDATE(), INTERVAL 30 DAY)
                """;

        // 行注释：执行 SQL 查询，并把数据库结果转换成 Java 数据结构。
        BigDecimal totalEmission = toDecimal(jdbcTemplate.queryForMap(totalSql).get("total"));
        // 行注释：声明变量 breakdownSql，保存本行计算或查询得到的结果，供后续逻辑使用。
        String breakdownSql = """
                SELECT
                  energy_source,
                  COALESCE(SUM(carbon_emission), 0) AS total_emission
                FROM carbon_emissions
                WHERE DATE(timestamp) >= DATE_SUB(CURDATE(), INTERVAL 30 DAY)
                GROUP BY energy_source
                """;

        // 行注释：执行 SQL 查询，并把数据库结果转换成 Java 数据结构。
        List<Map<String, Object>> breakdown = jdbcTemplate.queryForList(breakdownSql);
        // 行注释：声明变量 strategies，保存本行计算或查询得到的结果，供后续逻辑使用。
        List<Map<String, Object>> strategies = generateStrategies(totalEmission, breakdown);

        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        Map<String, Object> response = new LinkedHashMap<>();
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        response.put("total_emission", totalEmission.setScale(2, RoundingMode.HALF_UP));
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        response.put("strategies", strategies);
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return response;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： getCarbonNeutralProgress
     * - 作用：加载并返回前端视图所需的模块数据。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 getCarbonNeutralProgress 查询方法，给前端返回当前模块数据。
    // 方法用法：控制器查询数据时调用 getCarbonNeutralProgress，负责从数据库聚合并整理 Carbon 模块结果。
    public Map<String, Object> getCarbonNeutralProgress() {
        // 行注释：声明变量 sql，保存本行计算或查询得到的结果，供后续逻辑使用。
        String sql = """
                SELECT
                  COALESCE(SUM(CASE WHEN energy_source IN ('solar', 'wind') THEN energy_amount ELSE 0 END), 0) AS renewable_energy,
                  COALESCE(SUM(energy_amount), 0) AS total_energy,
                  COALESCE(SUM(CASE WHEN energy_source IN ('solar', 'wind') THEN carbon_emission ELSE 0 END), 0) AS renewable_emission,
                  COALESCE(SUM(carbon_emission), 0) AS total_emission
                FROM carbon_emissions
                WHERE DATE(timestamp) >= DATE_SUB(CURDATE(), INTERVAL 30 DAY)
                """;

        // 行注释：执行 SQL 查询，并把数据库结果转换成 Java 数据结构。
        Map<String, Object> row = jdbcTemplate.queryForMap(sql);
        // 行注释：声明变量 renewableEnergy，保存本行计算或查询得到的结果，供后续逻辑使用。
        BigDecimal renewableEnergy = toDecimal(row.get("renewable_energy"));
        // 行注释：声明变量 totalEnergy，保存本行计算或查询得到的结果，供后续逻辑使用。
        BigDecimal totalEnergy = toDecimal(row.get("total_energy"));
        // 行注释：声明变量 renewableEmission，保存本行计算或查询得到的结果，供后续逻辑使用。
        BigDecimal renewableEmission = toDecimal(row.get("renewable_emission"));
        // 行注释：声明变量 totalEmission，保存本行计算或查询得到的结果，供后续逻辑使用。
        BigDecimal totalEmission = toDecimal(row.get("total_emission"));

        // 行注释：声明变量 renewablePercentage，保存本行计算或查询得到的结果，供后续逻辑使用。
        BigDecimal renewablePercentage = totalEnergy.compareTo(BigDecimal.ZERO) == 0
                // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                ? BigDecimal.ZERO
                // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                : renewableEnergy.divide(totalEnergy, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));

        // 行注释：声明变量 reductionPercentage，保存本行计算或查询得到的结果，供后续逻辑使用。
        BigDecimal reductionPercentage = totalEmission.compareTo(BigDecimal.ZERO) == 0
                // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                ? BigDecimal.ZERO
                // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                : totalEmission.subtract(renewableEmission)
                // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                .divide(totalEmission, 4, RoundingMode.HALF_UP)
                // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                .multiply(BigDecimal.valueOf(100));

        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        Map<String, Object> response = new LinkedHashMap<>();
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        response.put("renewable_percentage", renewablePercentage.setScale(2, RoundingMode.HALF_UP));
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        response.put("emission_reduction_percentage", reductionPercentage.setScale(2, RoundingMode.HALF_UP));
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        response.put("total_emission", totalEmission.setScale(2, RoundingMode.HALF_UP));
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        response.put("renewable_emission", renewableEmission.setScale(2, RoundingMode.HALF_UP));
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        response.put("target", 100);
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        response.put("progress", renewablePercentage.setScale(2, RoundingMode.HALF_UP));
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return response;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： requireEmission
     * - 作用：加载必需实体，未找到时抛出明确异常。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 requireEmission 方法，完成 CarbonService 中对应的一步业务处理。
    // 方法用法：业务处理前调用它确认目标记录存在，不存在时抛出明确错误。
    private CarbonEmission requireEmission(Long id) {
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return carbonEmissionRepository.findById(id)
                // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Emission record not found"));
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： normalizeSource
     * - 作用：将输入值规范化并校验为系统使用的标准格式。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 normalizeSource 方法，完成 CarbonService 中对应的一步业务处理。
    // 方法用法：写入数据前调用 normalizeSource，用于把前端输入统一成后端认可的标准值。
    private String normalizeSource(String source) {
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (source == null || source.isBlank()) {
            // 行注释：返回处理结果，最终会交给前端或上层方法使用。
            return "grid";
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return source.trim().toLowerCase();
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： toMap
     * - 作用：将实体对象转换为接口响应 Map，并保持字段命名稳定。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 toMap 方法，完成 CarbonService 中对应的一步业务处理。
    // 方法用法：返回接口数据前调用它，把实体对象转换成前端更容易读取的 Map 结构。
    private Map<String, Object> toMap(CarbonEmission entity) {
        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        Map<String, Object> row = new LinkedHashMap<>();
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        row.put("id", entity.getId());
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        row.put("timestamp", entity.getTimestamp());
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        row.put("energy_source", entity.getEnergySource());
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        row.put("energy_amount", entity.getEnergyAmount());
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        row.put("carbon_emission", entity.getCarbonEmission());
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        row.put("emission_factor", entity.getEmissionFactor());
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return row;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： buildDateFilter
     * - 作用：基于底层数据组装衍生 SQL 过滤条件、响应对象或推荐结果。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 buildDateFilter 方法，完成 CarbonService 中对应的一步业务处理。
    // 方法用法：组装接口响应或推荐内容时调用它，把计算结果整理成前端可展示的数据。
    private String buildDateFilter(String period, boolean includeTodayDefault) {
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if ("week".equalsIgnoreCase(period)) {
            // 行注释：返回处理结果，最终会交给前端或上层方法使用。
            return "DATE(timestamp) >= DATE_SUB(CURDATE(), INTERVAL 7 DAY)";
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if ("month".equalsIgnoreCase(period)) {
            // 行注释：返回处理结果，最终会交给前端或上层方法使用。
            return "DATE(timestamp) >= DATE_SUB(CURDATE(), INTERVAL 30 DAY)";
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return includeTodayDefault
                // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                ? "DATE(timestamp) = CURDATE()"
                // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                : "DATE(timestamp) >= DATE_SUB(CURDATE(), INTERVAL 7 DAY)";
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： analyzeTrend
     * - 作用：执行分析计算，产出趋势或优化洞察结果。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 analyzeTrend 方法，完成 CarbonService 中对应的一步业务处理。
    // 方法用法：服务层内部或控制器调用 analyzeTrend，用于完成 Carbon 模块的一段核心业务逻辑。
    private Map<String, Object> analyzeTrend(List<Map<String, Object>> data) {
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (data.size() < 2) {
            // 行注释：组装接口返回数据，Spring 会自动转换成 JSON。
            return Map.of("trend", "insufficient_data");
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }

        // 行注释：声明变量 mid，保存本行计算或查询得到的结果，供后续逻辑使用。
        int mid = data.size() / 2;
        // 行注释：声明变量 first，保存本行计算或查询得到的结果，供后续逻辑使用。
        List<Map<String, Object>> first = data.subList(0, mid);
        // 行注释：声明变量 second，保存本行计算或查询得到的结果，供后续逻辑使用。
        List<Map<String, Object>> second = data.subList(mid, data.size());
        // 行注释：声明变量 avgFirst，保存本行计算或查询得到的结果，供后续逻辑使用。
        double avgFirst = first.stream().mapToDouble(item -> toDouble(item.get("carbon_emission"))).average().orElse(0.0);
        // 行注释：声明变量 avgSecond，保存本行计算或查询得到的结果，供后续逻辑使用。
        double avgSecond = second.stream().mapToDouble(item -> toDouble(item.get("carbon_emission"))).average().orElse(0.0);

        // 行注释：声明变量 change，保存本行计算或查询得到的结果，供后续逻辑使用。
        double change = avgFirst == 0
                // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                ? (avgSecond == 0 ? 0 : 100)
                // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                : ((avgSecond - avgFirst) / avgFirst) * 100;

        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        Map<String, Object> trend = new LinkedHashMap<>();
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        trend.put("trend", change > 0 ? "increasing" : "decreasing");
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        trend.put("changePercentage", scaled(Math.abs(change)));
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        trend.put("avgFirstPeriod", scaled(avgFirst));
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        trend.put("avgSecondPeriod", scaled(avgSecond));
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return trend;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： generateStrategies
     * - 作用：支撑当前模块流程的业务辅助方法。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：声明 generateStrategies 方法，承接当前模块的接口请求或内部业务处理。
    // 方法用法：服务层内部或控制器调用 generateStrategies，用于完成 Carbon 模块的一段核心业务逻辑。
    private List<Map<String, Object>> generateStrategies(BigDecimal totalEmission,
                                                         // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                                                         List<Map<String, Object>> breakdown) {
        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        List<Map<String, Object>> strategies = new ArrayList<>();
        // 行注释：声明变量 total，保存本行计算或查询得到的结果，供后续逻辑使用。
        double total = totalEmission.doubleValue();

        // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
        for (Map<String, Object> item : breakdown) {
            // 行注释：声明变量 source，保存本行计算或查询得到的结果，供后续逻辑使用。
            String source = String.valueOf(item.get("energy_source"));
            // 行注释：声明变量 sourceEmission，保存本行计算或查询得到的结果，供后续逻辑使用。
            double sourceEmission = toDouble(item.get("total_emission"));
            // 行注释：声明变量 percentage，保存本行计算或查询得到的结果，供后续逻辑使用。
            double percentage = total == 0 ? 0 : sourceEmission / total * 100;

            // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
            if ("grid".equals(source) && percentage > 30) {
                // 行注释：向列表中追加一项数据，用于表格、图表或返回结果。
                strategies.add(strategy(
                        // 行注释：传入字符串参数 “Increase renewable ratio”，作为当前方法调用的业务标识或显示文本。
                        "Increase renewable ratio",
                        // 行注释：传入字符串参数 “Grid energy takes a large share. Increase solar and wind use.”，作为当前方法调用的业务标识或显示文本。
                        "Grid energy takes a large share. Increase solar and wind use.",
                        // 行注释：传入字符串参数 “high”，作为当前方法调用的业务标识或显示文本。
                        "high",
                        // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                        sourceEmission * 0.70
                // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                ));
            // 行注释：结束当前代码块，表示这段逻辑处理完成。
            }
            // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
            if ("diesel".equals(source) && percentage > 20) {
                // 行注释：向列表中追加一项数据，用于表格、图表或返回结果。
                strategies.add(strategy(
                        // 行注释：传入字符串参数 “Replace diesel equipment”，作为当前方法调用的业务标识或显示文本。
                        "Replace diesel equipment",
                        // 行注释：传入字符串参数 “Diesel emits heavily. Replace with electric or renewable equipment.”，作为当前方法调用的业务标识或显示文本。
                        "Diesel emits heavily. Replace with electric or renewable equipment.",
                        // 行注释：传入字符串参数 “high”，作为当前方法调用的业务标识或显示文本。
                        "high",
                        // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                        sourceEmission * 0.80
                // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                ));
            // 行注释：结束当前代码块，表示这段逻辑处理完成。
            }
            // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
            if ("biomass".equals(source) && percentage > 25) {
                // 行注释：向列表中追加一项数据，用于表格、图表或返回结果。
                strategies.add(strategy(
                        // 行注释：传入字符串参数 “Optimize biomass system”，作为当前方法调用的业务标识或显示文本。
                        "Optimize biomass system",
                        // 行注释：传入字符串参数 “Improve combustion and heat recovery efficiency.”，作为当前方法调用的业务标识或显示文本。
                        "Improve combustion and heat recovery efficiency.",
                        // 行注释：传入字符串参数 “medium”，作为当前方法调用的业务标识或显示文本。
                        "medium",
                        // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                        sourceEmission * 0.30
                // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                ));
            // 行注释：结束当前代码块，表示这段逻辑处理完成。
            }
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }

        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (total > 1000) {
            // 行注释：向列表中追加一项数据，用于表格、图表或返回结果。
            strategies.add(strategy(
                    // 行注释：传入字符串参数 “Launch annual carbon plan”，作为当前方法调用的业务标识或显示文本。
                    "Launch annual carbon plan",
                    // 行注释：传入字符串参数 “Set phased carbon reduction targets and monthly checkpoints.”，作为当前方法调用的业务标识或显示文本。
                    "Set phased carbon reduction targets and monthly checkpoints.",
                    // 行注释：传入字符串参数 “high”，作为当前方法调用的业务标识或显示文本。
                    "high",
                    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                    total * 0.50
            // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
            ));
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }

        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return strategies;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： strategy
     * - 作用：支撑当前模块流程的业务辅助方法。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：声明 strategy 方法，承接当前模块的接口请求或内部业务处理。
    // 方法用法：服务层内部或控制器调用 strategy，用于完成 Carbon 模块的一段核心业务逻辑。
    private Map<String, Object> strategy(String strategy,
                                         // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                                         String description,
                                         // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                                         String priority,
                                         // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                                         double potentialReduction) {
        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        Map<String, Object> item = new LinkedHashMap<>();
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        item.put("strategy", strategy);
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        item.put("description", description);
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        item.put("priority", priority);
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        item.put("potential_reduction", scaled(Math.max(0, potentialReduction)));
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return item;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： normalize
     * - 作用：将输入值规范化并校验为系统使用的标准格式。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 normalize 方法，完成 CarbonService 中对应的一步业务处理。
    // 方法用法：写入数据前调用 normalize，用于把前端输入统一成后端认可的标准值。
    private void normalize(Map<String, Object> map, String... keys) {
        // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
        for (String key : keys) {
            // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
            if (map.get(key) == null) {
                // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
                map.put(key, BigDecimal.ZERO);
            // 行注释：结束当前代码块，表示这段逻辑处理完成。
            }
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： toDouble
     * - 作用：数值辅助方法，用于避免业务计算中的空值和精度问题。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 toDouble 方法，完成 CarbonService 中对应的一步业务处理。
    // 方法用法：统计计算时调用它，统一处理数字转换、保留小数和空值。
    private double toDouble(Object value) {
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (value == null) {
            // 行注释：返回处理结果，最终会交给前端或上层方法使用。
            return 0.0;
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (value instanceof Number number) {
            // 行注释：返回处理结果，最终会交给前端或上层方法使用。
            return number.doubleValue();
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return Double.parseDouble(value.toString());
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： toDecimal
     * - 作用：数值辅助方法，用于避免业务计算中的空值和精度问题。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 toDecimal 方法，完成 CarbonService 中对应的一步业务处理。
    // 方法用法：服务层内部或控制器调用 toDecimal，用于完成 Carbon 模块的一段核心业务逻辑。
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
     * - 方法： scaled
     * - 作用：数值辅助方法，用于避免业务计算中的空值和精度问题。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 scaled 方法，完成 CarbonService 中对应的一步业务处理。
    // 方法用法：统计计算时调用它，统一处理数字转换、保留小数和空值。
    private BigDecimal scaled(double value) {
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP);
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }
// 行注释：结束当前代码块，表示这段逻辑处理完成。
}



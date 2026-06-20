// 逐行注释标记：已按业务语义处理，仅增加中文注释，不改变程序逻辑
/**
 * 答辩注释:
 * - 文件： ForecastService
 * - 层级：服务层
 * - 职责：实现领域业务逻辑、数据聚合、校验与持久化编排。
 * - 此注释仅用于说明，不会改变运行行为。
 */
// 行注释：声明 ForecastService 所在的包路径，体现它在项目中的模块位置。
package com.farm.energy.service;

// 行注释：引入前端请求参数对象，便于 Spring 自动接收表单或 JSON 数据。
import com.farm.energy.dto.ForecastRecordRequest;
// 行注释：引入前端请求参数对象，便于 Spring 自动接收表单或 JSON 数据。
import com.farm.energy.dto.ForecastSaveRequest;
// 行注释：引入数据库实体类，用于读取或保存业务表记录。
import com.farm.energy.entity.EnergyForecast;
// 行注释：引入数据访问接口，用于操作对应数据库表。
import com.farm.energy.repository.EnergyForecastRepository;
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
import java.time.LocalDate;
// 行注释：引入 List 结构，用于返回表格或图表列表数据。
import java.util.ArrayList;
// 行注释：引入 Map 结构，用于组装接口返回的键值数据。
import java.util.HashMap;
// 行注释：引入 Map 结构，用于组装接口返回的键值数据。
import java.util.LinkedHashMap;
// 行注释：引入 List 结构，用于返回表格或图表列表数据。
import java.util.List;
// 行注释：引入 Map 结构，用于组装接口返回的键值数据。
import java.util.Map;

// 行注释：把当前类标记为业务服务层，专门处理模块业务逻辑。
@Service
// 行注释：定义 ForecastService 业务服务，承载该模块的主要代码。
// 类注释：这是 能源预测 模块的业务服务类，负责处理核心业务逻辑、数据校验、统计计算和数据库读写编排。
public class ForecastService {

    // 行注释：注入 JDBC 查询工具，用于执行统计分析 SQL。
    private final JdbcTemplate jdbcTemplate;
    // 行注释：注入仓储接口，用于访问对应数据库表。
    private final EnergyForecastRepository energyForecastRepository;

    // 行注释：声明 ForecastService 方法，承接当前模块的接口请求或内部业务处理。
    // 方法用法：Spring 创建 ForecastService 对象时调用这个构造方法，用来注入本类需要的依赖。
    public ForecastService(JdbcTemplate jdbcTemplate, EnergyForecastRepository energyForecastRepository) {
        // 行注释：把构造方法传入的 jdbcTemplate 保存到成员变量，后续方法会继续调用它。
        this.jdbcTemplate = jdbcTemplate;
        // 行注释：把构造方法传入的 energyForecastRepository 保存到成员变量，后续方法会继续调用它。
        this.energyForecastRepository = energyForecastRepository;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： getEnergyForecast
     * - 作用：加载并返回前端视图所需的模块数据。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 getEnergyForecast 查询方法，给前端返回当前模块数据。
    // 方法用法：控制器查询数据时调用 getEnergyForecast，负责从数据库聚合并整理 Forecast 模块结果。
    public Map<String, Object> getEnergyForecast(int days) {
        // 行注释：声明变量 sql，保存本行计算或查询得到的结果，供后续逻辑使用。
        String sql = """
                SELECT
                  DATE(timestamp) AS date,
                  COALESCE(SUM(energy_generated), 0) AS total_generated,
                  COALESCE(SUM(energy_consumed), 0) AS total_consumed
                FROM energy_monitoring
                WHERE DATE(timestamp) >= DATE_SUB(CURDATE(), INTERVAL 30 DAY)
                GROUP BY DATE(timestamp)
                ORDER BY date ASC
                """;

        // 行注释：执行 SQL 查询，并把数据库结果转换成 Java 数据结构。
        List<Map<String, Object>> historicalData = jdbcTemplate.queryForList(sql);
        // 行注释：声明变量 forecasts，保存本行计算或查询得到的结果，供后续逻辑使用。
        List<Map<String, Object>> forecasts = comprehensiveForecast(historicalData, days);

        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        Map<String, Object> result = new LinkedHashMap<>();
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        result.put("historical_data", historicalData);
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        result.put("forecasts", forecasts);
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        result.put("forecast_period", days + " days");
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return result;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： listRecords
     * - 作用：查询并返回集合数据，用于界面表格/图表渲染。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 listRecords 方法，完成 ForecastService 中对应的一步业务处理。
    // 方法用法：控制器加载列表时调用 listRecords，负责查询并转换成前端表格需要的数据结构。
    public List<Map<String, Object>> listRecords(Integer limit) {
        // 行注释：声明变量 realLimit，保存本行计算或查询得到的结果，供后续逻辑使用。
        int realLimit = (limit == null || limit <= 0 || limit > 1000) ? 200 : limit;
        // 行注释：声明变量 sql，保存本行计算或查询得到的结果，供后续逻辑使用。
        String sql = """
                SELECT
                  id,
                  forecast_date,
                  forecast_type,
                  predicted_generation,
                  predicted_consumption,
                  confidence_level,
                  created_at
                FROM energy_forecasts
                ORDER BY created_at DESC
                LIMIT ?
                """;
        // 行注释：执行 SQL 查询，并把数据库结果转换成 Java 数据结构。
        return jdbcTemplate.queryForList(sql, realLimit);
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： getRecord
     * - 作用：加载并返回前端视图所需的模块数据。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 getRecord 查询方法，给前端返回当前模块数据。
    // 方法用法：控制器查询数据时调用 getRecord，负责从数据库聚合并整理 Forecast 模块结果。
    public Map<String, Object> getRecord(Long id) {
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return toMap(requireRecord(id));
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：开启事务，保证该方法内的数据库操作要么全部成功要么回滚。
    @Transactional
    /**
     * 答辩讲解:
     * - 方法： createRecord
     * - 作用：校验请求参数并持久化新增业务记录。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 createRecord 新增方法，把前端提交的数据保存到数据库。
    // 方法用法：控制器新增数据时调用 createRecord，负责校验参数并保存新记录。
    public Map<String, Object> createRecord(ForecastRecordRequest request) {
        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        EnergyForecast entity = new EnergyForecast();
        // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
        entity.setForecastDate(requireDate(request.getForecastDate()));
        // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
        entity.setForecastType(normalizeType(request.getForecastType()));
        // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
        entity.setPredictedGeneration(defaultDecimal(request.getPredictedGeneration()));
        // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
        entity.setPredictedConsumption(defaultDecimal(request.getPredictedConsumption()));
        // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
        entity.setConfidenceLevel(defaultConfidence(request.getConfidenceLevel()));
        // 行注释：调用 JPA 保存实体，把数据写入数据库。
        return toMap(energyForecastRepository.save(entity));
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：开启事务，保证该方法内的数据库操作要么全部成功要么回滚。
    @Transactional
    /**
     * 答辩讲解:
     * - 方法： updateRecord
     * - 作用：在输入规范化后，对现有记录执行部分字段更新。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 updateRecord 更新方法，修改数据库中的已有记录。
    // 方法用法：控制器修改数据时调用 updateRecord，负责找到原记录、更新字段并保存。
    public Map<String, Object> updateRecord(Long id, ForecastRecordRequest request) {
        // 行注释：声明变量 entity，保存本行计算或查询得到的结果，供后续逻辑使用。
        EnergyForecast entity = requireRecord(id);
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (request.getForecastDate() != null) {
            // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
            entity.setForecastDate(request.getForecastDate());
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (request.getForecastType() != null) {
            // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
            entity.setForecastType(normalizeType(request.getForecastType()));
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (request.getPredictedGeneration() != null) {
            // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
            entity.setPredictedGeneration(request.getPredictedGeneration());
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (request.getPredictedConsumption() != null) {
            // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
            entity.setPredictedConsumption(request.getPredictedConsumption());
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (request.getConfidenceLevel() != null) {
            // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
            entity.setConfidenceLevel(request.getConfidenceLevel());
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：调用 JPA 保存实体，把数据写入数据库。
        return toMap(energyForecastRepository.save(entity));
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：开启事务，保证该方法内的数据库操作要么全部成功要么回滚。
    @Transactional
    /**
     * 答辩讲解:
     * - 方法： deleteRecord
     * - 作用：删除或逻辑移除目标记录，并维护数据一致性。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 deleteRecord 删除方法，删除前端指定的数据记录。
    // 方法用法：控制器删除数据时调用 deleteRecord，负责校验记录存在并执行删除。
    public void deleteRecord(Long id) {
        // 行注释：调用 JPA 删除数据或清理过期记录。
        energyForecastRepository.delete(requireRecord(id));
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：开启事务，保证该方法内的数据库操作要么全部成功要么回滚。
    @Transactional
    /**
     * 答辩讲解:
     * - 方法： saveForecasts
     * - 作用：按模块校验规则将输入数据持久化到数据库。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 saveForecasts 方法，完成 ForecastService 中对应的一步业务处理。
    // 方法用法：服务层内部或控制器调用 saveForecasts，用于完成 Forecast 模块的一段核心业务逻辑。
    public void saveForecasts(List<ForecastSaveRequest.Item> items) {
        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        List<EnergyForecast> entities = new ArrayList<>();
        // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
        for (ForecastSaveRequest.Item item : items) {
            // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
            EnergyForecast entity = new EnergyForecast();
            // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
            entity.setForecastDate(item.getDate());
            // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
            entity.setForecastType("daily");
            // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
            entity.setPredictedGeneration(defaultDecimal(item.getPredictedGeneration()));
            // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
            entity.setPredictedConsumption(defaultDecimal(item.getPredictedConsumption()));
            // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
            entity.setConfidenceLevel(defaultConfidence(item.getConfidenceLevel()));
            // 行注释：向列表中追加一项数据，用于表格、图表或返回结果。
            entities.add(entity);
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
        energyForecastRepository.saveAll(entities);
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： getHistory
     * - 作用：加载并返回前端视图所需的模块数据。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 getHistory 查询方法，给前端返回当前模块数据。
    // 方法用法：控制器查询数据时调用 getHistory，负责从数据库聚合并整理 Forecast 模块结果。
    public List<Map<String, Object>> getHistory() {
        // 行注释：声明变量 sql，保存本行计算或查询得到的结果，供后续逻辑使用。
        String sql = """
                SELECT
                  id,
                  forecast_date,
                  forecast_type,
                  predicted_generation,
                  predicted_consumption,
                  confidence_level,
                  created_at
                FROM energy_forecasts
                WHERE forecast_date >= DATE_SUB(CURDATE(), INTERVAL 30 DAY)
                ORDER BY created_at DESC
                LIMIT 100
                """;
        // 行注释：执行 SQL 查询，并把数据库结果转换成 Java 数据结构。
        return jdbcTemplate.queryForList(sql);
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： getAccuracyAnalysis
     * - 作用：加载并返回前端视图所需的模块数据。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 getAccuracyAnalysis 查询方法，给前端返回当前模块数据。
    // 方法用法：控制器查询数据时调用 getAccuracyAnalysis，负责从数据库聚合并整理 Forecast 模块结果。
    public List<Map<String, Object>> getAccuracyAnalysis() {
        // 行注释：声明变量 sql，保存本行计算或查询得到的结果，供后续逻辑使用。
        String sql = """
                SELECT
                  ef.forecast_date,
                  ef.predicted_generation,
                  ef.predicted_consumption,
                  COALESCE(SUM(em.energy_generated), 0) AS actual_generation,
                  COALESCE(SUM(em.energy_consumed), 0) AS actual_consumption
                FROM energy_forecasts ef
                LEFT JOIN energy_monitoring em ON DATE(em.timestamp) = ef.forecast_date
                WHERE ef.forecast_date <= CURDATE()
                  AND ef.forecast_date >= DATE_SUB(CURDATE(), INTERVAL 30 DAY)
                GROUP BY ef.forecast_date, ef.predicted_generation, ef.predicted_consumption
                ORDER BY ef.forecast_date ASC
                """;

        // 行注释：执行 SQL 查询，并把数据库结果转换成 Java 数据结构。
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        List<Map<String, Object>> result = new ArrayList<>();

        // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
        for (Map<String, Object> row : rows) {
            // 行注释：声明变量 predictedGeneration，保存本行计算或查询得到的结果，供后续逻辑使用。
            double predictedGeneration = toDouble(row.get("predicted_generation"));
            // 行注释：声明变量 predictedConsumption，保存本行计算或查询得到的结果，供后续逻辑使用。
            double predictedConsumption = toDouble(row.get("predicted_consumption"));
            // 行注释：声明变量 actualGeneration，保存本行计算或查询得到的结果，供后续逻辑使用。
            double actualGeneration = toDouble(row.get("actual_generation"));
            // 行注释：声明变量 actualConsumption，保存本行计算或查询得到的结果，供后续逻辑使用。
            double actualConsumption = toDouble(row.get("actual_consumption"));

            // 行注释：声明变量 generationError，保存本行计算或查询得到的结果，供后续逻辑使用。
            double generationError = actualGeneration <= 0 ? 0
                    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                    : Math.abs((predictedGeneration - actualGeneration) / actualGeneration * 100);
            // 行注释：声明变量 consumptionError，保存本行计算或查询得到的结果，供后续逻辑使用。
            double consumptionError = actualConsumption <= 0 ? 0
                    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                    : Math.abs((predictedConsumption - actualConsumption) / actualConsumption * 100);

            // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
            Map<String, Object> item = new LinkedHashMap<>();
            // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
            item.put("date", row.get("forecast_date"));
            // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
            item.put("generation_accuracy", scaled(100 - generationError) + "%");
            // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
            item.put("consumption_accuracy", scaled(100 - consumptionError) + "%");
            // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
            item.put("generation_error", scaled(generationError) + "%");
            // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
            item.put("consumption_error", scaled(consumptionError) + "%");
            // 行注释：向列表中追加一项数据，用于表格、图表或返回结果。
            result.add(item);
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return result;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： getDemandPlan
     * - 作用：加载并返回前端视图所需的模块数据。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 getDemandPlan 查询方法，给前端返回当前模块数据。
    // 方法用法：控制器查询数据时调用 getDemandPlan，负责从数据库聚合并整理 Forecast 模块结果。
    public Map<String, Object> getDemandPlan() {
        // 行注释：声明变量 sql，保存本行计算或查询得到的结果，供后续逻辑使用。
        String sql = """
                SELECT
                  COALESCE(AVG(energy_consumed), 0) AS avg_consumption,
                  COALESCE(MAX(energy_consumed), 0) AS peak_consumption,
                  COALESCE(MIN(energy_consumed), 0) AS min_consumption
                FROM energy_monitoring
                WHERE DATE(timestamp) >= DATE_SUB(CURDATE(), INTERVAL 90 DAY)
                """;

        // 行注释：执行 SQL 查询，并把数据库结果转换成 Java 数据结构。
        Map<String, Object> row = jdbcTemplate.queryForMap(sql);
        // 行注释：声明变量 avg，保存本行计算或查询得到的结果，供后续逻辑使用。
        BigDecimal avg = toDecimal(row.get("avg_consumption"));
        // 行注释：声明变量 peak，保存本行计算或查询得到的结果，供后续逻辑使用。
        BigDecimal peak = toDecimal(row.get("peak_consumption"));
        // 行注释：声明变量 min，保存本行计算或查询得到的结果，供后续逻辑使用。
        BigDecimal min = toDecimal(row.get("min_consumption"));

        // 行注释：声明变量 dailyTarget，保存本行计算或查询得到的结果，供后续逻辑使用。
        BigDecimal dailyTarget = avg.multiply(BigDecimal.valueOf(1.10)).setScale(2, RoundingMode.HALF_UP);
        // 行注释：声明变量 peakPreparation，保存本行计算或查询得到的结果，供后续逻辑使用。
        BigDecimal peakPreparation = peak.multiply(BigDecimal.valueOf(1.20)).setScale(2, RoundingMode.HALF_UP);
        // 行注释：声明变量 minimumReserve，保存本行计算或查询得到的结果，供后续逻辑使用。
        BigDecimal minimumReserve = min.multiply(BigDecimal.valueOf(0.80)).setScale(2, RoundingMode.HALF_UP);

        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        List<Map<String, Object>> recommendations = new ArrayList<>();
        // 行注释：向列表中追加一项数据，用于表格、图表或返回结果。
        recommendations.add(recommendation("Daily reserve", "Keep around " + dailyTarget + " kWh reserve.", "medium"));
        // 行注释：向列表中追加一项数据，用于表格、图表或返回结果。
        recommendations.add(recommendation("Peak preparation", "Prepare " + peakPreparation + " kWh for peak loads.", "high"));
        // 行注释：向列表中追加一项数据，用于表格、图表或返回结果。
        recommendations.add(recommendation("Emergency backup", "Prepare backup source and load switching plan.", "high"));

        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        Map<String, Object> plan = new LinkedHashMap<>();
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        plan.put("daily_target", dailyTarget);
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        plan.put("peak_preparation", peakPreparation);
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        plan.put("minimum_reserve", minimumReserve);
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        plan.put("recommendations", recommendations);
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return plan;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： getCropCycle
     * - 作用：加载并返回前端视图所需的模块数据。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 getCropCycle 查询方法，给前端返回当前模块数据。
    // 方法用法：控制器查询数据时调用 getCropCycle，负责从数据库聚合并整理 Forecast 模块结果。
    public Map<String, Object> getCropCycle(String cropType, String growthStage) {
        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        Map<String, Map<String, Double>> factors = new HashMap<>();
        // 行注释：组装接口返回数据，Spring 会自动转换成 JSON。
        factors.put("vegetables", Map.of("seedling", 0.8, "growing", 1.2, "harvest", 1.0));
        // 行注释：组装接口返回数据，Spring 会自动转换成 JSON。
        factors.put("grains", Map.of("seedling", 0.7, "growing", 1.1, "harvest", 1.3));
        // 行注释：组装接口返回数据，Spring 会自动转换成 JSON。
        factors.put("fruits", Map.of("seedling", 0.9, "growing", 1.3, "harvest", 1.4));

        // 行注释：声明变量 sql，保存本行计算或查询得到的结果，供后续逻辑使用。
        String sql = """
                SELECT COALESCE(AVG(energy_consumed), 0) AS base_consumption
                FROM energy_monitoring
                WHERE DATE(timestamp) >= DATE_SUB(CURDATE(), INTERVAL 30 DAY)
                """;

        // 行注释：执行 SQL 查询，并把数据库结果转换成 Java 数据结构。
        BigDecimal base = toDecimal(jdbcTemplate.queryForMap(sql).get("base_consumption"));
        // 行注释：组装接口返回数据，Spring 会自动转换成 JSON。
        double factor = factors.getOrDefault(cropType, Map.of()).getOrDefault(growthStage, 1.0);
        // 行注释：声明变量 predicted，保存本行计算或查询得到的结果，供后续逻辑使用。
        BigDecimal predicted = base.multiply(BigDecimal.valueOf(factor)).setScale(2, RoundingMode.HALF_UP);

        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        Map<String, Object> response = new LinkedHashMap<>();
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        response.put("crop_type", cropType);
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        response.put("growth_stage", growthStage);
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        response.put("base_consumption", base.setScale(2, RoundingMode.HALF_UP));
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        response.put("adjustment_factor", factor);
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        response.put("predicted_consumption", predicted);
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        response.put("recommendation", "Estimated demand at this stage: " + predicted + " kWh/day");
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return response;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： saveForecastResponse
     * - 作用：按模块校验规则将输入数据持久化到数据库。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 saveForecastResponse 方法，完成 ForecastService 中对应的一步业务处理。
    // 方法用法：服务层内部或控制器调用 saveForecastResponse，用于完成 Forecast 模块的一段核心业务逻辑。
    public Map<String, Object> saveForecastResponse() {
        // 行注释：组装接口返回数据，Spring 会自动转换成 JSON。
        return Map.of("message", "Forecast records saved");
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： requireRecord
     * - 作用：加载必需实体，未找到时抛出明确异常。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 requireRecord 方法，完成 ForecastService 中对应的一步业务处理。
    // 方法用法：业务处理前调用它确认目标记录存在，不存在时抛出明确错误。
    private EnergyForecast requireRecord(Long id) {
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return energyForecastRepository.findById(id)
                // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Forecast record not found"));
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： toMap
     * - 作用：将实体对象转换为接口响应 Map，并保持字段命名稳定。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 toMap 方法，完成 ForecastService 中对应的一步业务处理。
    // 方法用法：返回接口数据前调用它，把实体对象转换成前端更容易读取的 Map 结构。
    private Map<String, Object> toMap(EnergyForecast entity) {
        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        Map<String, Object> row = new LinkedHashMap<>();
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        row.put("id", entity.getId());
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        row.put("forecast_date", entity.getForecastDate());
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        row.put("forecast_type", entity.getForecastType());
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        row.put("predicted_generation", entity.getPredictedGeneration());
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        row.put("predicted_consumption", entity.getPredictedConsumption());
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        row.put("confidence_level", entity.getConfidenceLevel());
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        row.put("created_at", entity.getCreatedAt());
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return row;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： requireDate
     * - 作用：加载必需实体，未找到时抛出明确异常。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 requireDate 方法，完成 ForecastService 中对应的一步业务处理。
    // 方法用法：业务处理前调用它确认目标记录存在，不存在时抛出明确错误。
    private LocalDate requireDate(LocalDate date) {
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (date == null) {
            // 行注释：抛出明确异常，让前端收到规范的错误提示。
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "forecast_date is required");
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return date;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： normalizeType
     * - 作用：将输入值规范化并校验为系统使用的标准格式。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 normalizeType 方法，完成 ForecastService 中对应的一步业务处理。
    // 方法用法：写入数据前调用 normalizeType，用于把前端输入统一成后端认可的标准值。
    private String normalizeType(String type) {
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (type == null || type.isBlank()) {
            // 行注释：返回处理结果，最终会交给前端或上层方法使用。
            return "daily";
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return type.trim().toLowerCase();
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： defaultDecimal
     * - 作用：数值辅助方法，用于避免业务计算中的空值和精度问题。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 defaultDecimal 方法，完成 ForecastService 中对应的一步业务处理。
    // 方法用法：服务层内部或控制器调用 defaultDecimal，用于完成 Forecast 模块的一段核心业务逻辑。
    private BigDecimal defaultDecimal(BigDecimal value) {
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return value == null ? BigDecimal.ZERO : value;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： defaultConfidence
     * - 作用：数值辅助方法，用于避免业务计算中的空值和精度问题。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 defaultConfidence 方法，完成 ForecastService 中对应的一步业务处理。
    // 方法用法：服务层内部或控制器调用 defaultConfidence，用于完成 Forecast 模块的一段核心业务逻辑。
    private BigDecimal defaultConfidence(BigDecimal value) {
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (value == null) {
            // 行注释：返回处理结果，最终会交给前端或上层方法使用。
            return new BigDecimal("0.80");
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return value;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： comprehensiveForecast
     * - 作用：支撑当前模块流程的业务辅助方法。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 comprehensiveForecast 方法，完成 ForecastService 中对应的一步业务处理。
    // 方法用法：服务层内部或控制器调用 comprehensiveForecast，用于完成 Forecast 模块的一段核心业务逻辑。
    private List<Map<String, Object>> comprehensiveForecast(List<Map<String, Object>> historicalData, int daysAhead) {
        // 行注释：声明变量 generationSeries，保存本行计算或查询得到的结果，供后续逻辑使用。
        List<Double> generationSeries = historicalData.stream()
                // 行注释：发起接口请求或读取对象数据，获取页面需要的业务信息。
                .map(item -> toDouble(item.get("total_generated")))
                // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                .toList();
        // 行注释：声明变量 consumptionSeries，保存本行计算或查询得到的结果，供后续逻辑使用。
        List<Double> consumptionSeries = historicalData.stream()
                // 行注释：发起接口请求或读取对象数据，获取页面需要的业务信息。
                .map(item -> toDouble(item.get("total_consumed")))
                // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                .toList();

        // 行注释：声明变量 generationPredictions，保存本行计算或查询得到的结果，供后续逻辑使用。
        List<Double> generationPredictions = linearForecast(generationSeries, daysAhead);
        // 行注释：声明变量 consumptionPredictions，保存本行计算或查询得到的结果，供后续逻辑使用。
        List<Double> consumptionPredictions = linearForecast(consumptionSeries, daysAhead);

        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        List<Map<String, Object>> forecasts = new ArrayList<>();
        // 行注释：声明变量 today，保存本行计算或查询得到的结果，供后续逻辑使用。
        LocalDate today = LocalDate.now();
        // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
        for (int i = 0; i < daysAhead; i++) {
            // 行注释：声明变量 date，保存本行计算或查询得到的结果，供后续逻辑使用。
            LocalDate date = today.plusDays(i + 1);
            // 行注释：声明变量 month，保存本行计算或查询得到的结果，供后续逻辑使用。
            int month = date.getMonthValue();

            // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
            Map<String, Object> item = new LinkedHashMap<>();
            // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
            item.put("date", date.toString());
            // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
            item.put("predicted_generation", applySeasonalAdjustment(generationPredictions.get(i), month));
            // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
            item.put("predicted_consumption", applySeasonalAdjustment(consumptionPredictions.get(i), month));
            // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
            item.put("confidence_level", calculateConfidence(i));
            // 行注释：向列表中追加一项数据，用于表格、图表或返回结果。
            forecasts.add(item);
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return forecasts;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： linearForecast
     * - 作用：支撑当前模块流程的业务辅助方法。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 linearForecast 方法，完成 ForecastService 中对应的一步业务处理。
    // 方法用法：服务层内部或控制器调用 linearForecast，用于完成 Forecast 模块的一段核心业务逻辑。
    private List<Double> linearForecast(List<Double> data, int daysAhead) {
        // 行注释：声明变量 series，保存本行计算或查询得到的结果，供后续逻辑使用。
        List<Double> series = data.isEmpty() ? List.of(0.0) : data;
        // 行注释：声明变量 trend，保存本行计算或查询得到的结果，供后续逻辑使用。
        double trend = analyzeTrend(series);
        // 行注释：声明变量 lastValue，保存本行计算或查询得到的结果，供后续逻辑使用。
        double lastValue = series.get(series.size() - 1);

        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        List<Double> predictions = new ArrayList<>();
        // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
        for (int i = 1; i <= daysAhead; i++) {
            // 行注释：向列表中追加一项数据，用于表格、图表或返回结果。
            predictions.add(Math.max(0, lastValue + trend * i));
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return predictions;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： analyzeTrend
     * - 作用：执行分析计算，产出趋势或优化洞察结果。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 analyzeTrend 方法，完成 ForecastService 中对应的一步业务处理。
    // 方法用法：服务层内部或控制器调用 analyzeTrend，用于完成 Forecast 模块的一段核心业务逻辑。
    private double analyzeTrend(List<Double> data) {
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (data.size() < 2) {
            // 行注释：返回处理结果，最终会交给前端或上层方法使用。
            return 0;
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }

        // 行注释：声明变量 sumX，保存本行计算或查询得到的结果，供后续逻辑使用。
        double sumX = 0;
        // 行注释：声明变量 sumY，保存本行计算或查询得到的结果，供后续逻辑使用。
        double sumY = 0;
        // 行注释：声明变量 sumXY，保存本行计算或查询得到的结果，供后续逻辑使用。
        double sumXY = 0;
        // 行注释：声明变量 sumX2，保存本行计算或查询得到的结果，供后续逻辑使用。
        double sumX2 = 0;
        // 行注释：声明变量 n，保存本行计算或查询得到的结果，供后续逻辑使用。
        int n = data.size();

        // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
        for (int i = 0; i < n; i++) {
            // 行注释：声明变量 x，保存本行计算或查询得到的结果，供后续逻辑使用。
            double x = i;
            // 行注释：声明变量 y，保存本行计算或查询得到的结果，供后续逻辑使用。
            double y = data.get(i);
            // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
            sumX += x;
            // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
            sumY += y;
            // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
            sumXY += x * y;
            // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
            sumX2 += x * x;
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }

        // 行注释：声明变量 denominator，保存本行计算或查询得到的结果，供后续逻辑使用。
        double denominator = n * sumX2 - sumX * sumX;
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (denominator == 0) {
            // 行注释：返回处理结果，最终会交给前端或上层方法使用。
            return 0;
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return (n * sumXY - sumX * sumY) / denominator;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： applySeasonalAdjustment
     * - 作用：支撑当前模块流程的业务辅助方法。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 applySeasonalAdjustment 方法，完成 ForecastService 中对应的一步业务处理。
    // 方法用法：服务层内部或控制器调用 applySeasonalAdjustment，用于完成 Forecast 模块的一段核心业务逻辑。
    private BigDecimal applySeasonalAdjustment(double value, int month) {
        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        Map<Integer, Double> seasonal = new HashMap<>();
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        seasonal.put(1, 1.10);
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        seasonal.put(2, 1.10);
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        seasonal.put(3, 0.95);
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        seasonal.put(4, 0.90);
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        seasonal.put(5, 0.85);
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        seasonal.put(6, 1.00);
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        seasonal.put(7, 1.05);
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        seasonal.put(8, 1.05);
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        seasonal.put(9, 0.95);
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        seasonal.put(10, 0.90);
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        seasonal.put(11, 1.00);
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        seasonal.put(12, 1.10);

        // 行注释：声明变量 adjusted，保存本行计算或查询得到的结果，供后续逻辑使用。
        double adjusted = value * seasonal.getOrDefault(month, 1.0);
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return scaled(adjusted);
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： calculateConfidence
     * - 作用：支撑当前模块流程的业务辅助方法。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 calculateConfidence 方法，完成 ForecastService 中对应的一步业务处理。
    // 方法用法：服务层内部或控制器调用 calculateConfidence，用于完成 Forecast 模块的一段核心业务逻辑。
    private BigDecimal calculateConfidence(int dayOffset) {
        // 行注释：声明变量 confidence，保存本行计算或查询得到的结果，供后续逻辑使用。
        double confidence = Math.max(0.5, 0.95 - dayOffset * 0.05);
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return scaled(confidence);
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： recommendation
     * - 作用：支撑当前模块流程的业务辅助方法。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 recommendation 方法，完成 ForecastService 中对应的一步业务处理。
    // 方法用法：服务层内部或控制器调用 recommendation，用于完成 Forecast 模块的一段核心业务逻辑。
    private Map<String, Object> recommendation(String title, String description, String priority) {
        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        Map<String, Object> item = new LinkedHashMap<>();
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        item.put("title", title);
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        item.put("description", description);
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        item.put("priority", priority);
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return item;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： toDouble
     * - 作用：数值辅助方法，用于避免业务计算中的空值和精度问题。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 toDouble 方法，完成 ForecastService 中对应的一步业务处理。
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
    // 行注释：定义 toDecimal 方法，完成 ForecastService 中对应的一步业务处理。
    // 方法用法：服务层内部或控制器调用 toDecimal，用于完成 Forecast 模块的一段核心业务逻辑。
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
    // 行注释：定义 scaled 方法，完成 ForecastService 中对应的一步业务处理。
    // 方法用法：统计计算时调用它，统一处理数字转换、保留小数和空值。
    private BigDecimal scaled(double value) {
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP);
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }
// 行注释：结束当前代码块，表示这段逻辑处理完成。
}



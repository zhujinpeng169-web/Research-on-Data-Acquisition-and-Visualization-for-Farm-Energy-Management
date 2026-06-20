// 逐行注释标记：已按业务语义处理，仅增加中文注释，不改变程序逻辑
/**
 * 答辩注释:
 * - 文件： OptimizationService
 * - 层级：服务层
 * - 职责：实现领域业务逻辑、数据聚合、校验与持久化编排。
 * - 此注释仅用于说明，不会改变运行行为。
 */
// 行注释：声明 OptimizationService 所在的包路径，体现它在项目中的模块位置。
package com.farm.energy.service;

// 行注释：引入前端请求参数对象，便于 Spring 自动接收表单或 JSON 数据。
import com.farm.energy.dto.RecommendationCreateRequest;
// 行注释：引入前端请求参数对象，便于 Spring 自动接收表单或 JSON 数据。
import com.farm.energy.dto.RecommendationUpdateRequest;
// 行注释：引入数据库实体类，用于读取或保存业务表记录。
import com.farm.energy.entity.EnergyRecommendation;
// 行注释：引入数据访问接口，用于操作对应数据库表。
import com.farm.energy.repository.EnergyRecommendationRepository;
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
// 行注释：定义 OptimizationService 业务服务，承载该模块的主要代码。
// 类注释：这是 节能优化 模块的业务服务类，负责处理核心业务逻辑、数据校验、统计计算和数据库读写编排。
public class OptimizationService {

    // 行注释：注入 JDBC 查询工具，用于执行统计分析 SQL。
    private final JdbcTemplate jdbcTemplate;
    // 行注释：注入仓储接口，用于访问对应数据库表。
    private final EnergyRecommendationRepository recommendationRepository;

    // 行注释：声明 OptimizationService 方法，承接当前模块的接口请求或内部业务处理。
    // 方法用法：Spring 创建 OptimizationService 对象时调用这个构造方法，用来注入本类需要的依赖。
    public OptimizationService(JdbcTemplate jdbcTemplate, EnergyRecommendationRepository recommendationRepository) {
        // 行注释：把构造方法传入的 jdbcTemplate 保存到成员变量，后续方法会继续调用它。
        this.jdbcTemplate = jdbcTemplate;
        // 行注释：把构造方法传入的 recommendationRepository 保存到成员变量，后续方法会继续调用它。
        this.recommendationRepository = recommendationRepository;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： getAnalysis
     * - 作用：加载并返回前端视图所需的模块数据。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 getAnalysis 查询方法，给前端返回当前模块数据。
    // 方法用法：控制器查询数据时调用 getAnalysis，负责从数据库聚合并整理 Optimization 模块结果。
    public Map<String, Object> getAnalysis() {
        // 行注释：声明变量 sql，保存本行计算或查询得到的结果，供后续逻辑使用。
        String sql = """
                SELECT energy_generated, energy_consumed, efficiency
                FROM energy_monitoring
                WHERE DATE(timestamp) >= DATE_SUB(CURDATE(), INTERVAL 7 DAY)
                ORDER BY timestamp DESC
                """;

        // 行注释：执行 SQL 查询，并把数据库结果转换成 Java 数据结构。
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        // 行注释：声明变量 totalGenerated，保存本行计算或查询得到的结果，供后续逻辑使用。
        double totalGenerated = rows.stream().mapToDouble(r -> toDouble(r.get("energy_generated"))).sum();
        // 行注释：声明变量 totalConsumed，保存本行计算或查询得到的结果，供后续逻辑使用。
        double totalConsumed = rows.stream().mapToDouble(r -> toDouble(r.get("energy_consumed"))).sum();
        // 行注释：声明变量 avgEfficiency，保存本行计算或查询得到的结果，供后续逻辑使用。
        double avgEfficiency = rows.isEmpty() ? 0.0
                // 行注释：发起接口请求或读取对象数据，获取页面需要的业务信息。
                : rows.stream().mapToDouble(r -> toDouble(r.get("efficiency"))).average().orElse(0.0);

        // 行注释：声明变量 surplus，保存本行计算或查询得到的结果，供后续逻辑使用。
        double surplus = totalGenerated - totalConsumed;
        // 行注释：声明变量 utilizationRate，保存本行计算或查询得到的结果，供后续逻辑使用。
        double utilizationRate = totalGenerated == 0 ? 0 : (totalConsumed / totalGenerated) * 100;

        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        Map<String, Object> pattern = new LinkedHashMap<>();
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        pattern.put("totalGenerated", scaled(totalGenerated));
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        pattern.put("totalConsumed", scaled(totalConsumed));
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        pattern.put("surplus", scaled(surplus));
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        pattern.put("avgEfficiency", scaled(avgEfficiency));
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        pattern.put("utilizationRate", scaled(utilizationRate));

        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        Map<String, Object> result = new LinkedHashMap<>();
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        result.put("pattern", pattern);
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        result.put("recommendations", buildAutoRecommendations(totalConsumed, surplus, avgEfficiency, utilizationRate));
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        result.put("dataPoints", rows.size());
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return result;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： getRecommendations
     * - 作用：加载并返回前端视图所需的模块数据。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 getRecommendations 查询方法，给前端返回当前模块数据。
    // 方法用法：控制器查询数据时调用 getRecommendations，负责从数据库聚合并整理 Optimization 模块结果。
    public List<Map<String, Object>> getRecommendations() {
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return recommendationRepository.findTop20ByOrderByCreatedAtDesc()
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
     * - 方法： getRecommendation
     * - 作用：加载并返回前端视图所需的模块数据。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 getRecommendation 查询方法，给前端返回当前模块数据。
    // 方法用法：控制器查询数据时调用 getRecommendation，负责从数据库聚合并整理 Optimization 模块结果。
    public Map<String, Object> getRecommendation(Long id) {
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return toMap(requireRecommendation(id));
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：开启事务，保证该方法内的数据库操作要么全部成功要么回滚。
    @Transactional
    /**
     * 答辩讲解:
     * - 方法： createRecommendation
     * - 作用：校验请求参数并持久化新增业务记录。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 createRecommendation 新增方法，把前端提交的数据保存到数据库。
    // 方法用法：控制器新增数据时调用 createRecommendation，负责校验参数并保存新记录。
    public Map<String, Object> createRecommendation(RecommendationCreateRequest request) {
        // 行注释：读取对象字段值，参与业务判断、计算或返回。
        validateRecommendation(request.getRecommendationType(), request.getDescription());
        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        EnergyRecommendation recommendation = new EnergyRecommendation();
        // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
        recommendation.setRecommendationType(request.getRecommendationType().trim().toLowerCase());
        // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
        recommendation.setDescription(request.getDescription().trim());
        // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
        recommendation.setPotentialSavings(request.getPotentialSavings() == null ? BigDecimal.ZERO : request.getPotentialSavings());
        // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
        recommendation.setPriority(normalizePriority(request.getPriority()));
        // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
        recommendation.setStatus("pending");
        // 行注释：调用 JPA 保存实体，把数据写入数据库。
        return toMap(recommendationRepository.save(recommendation));
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：开启事务，保证该方法内的数据库操作要么全部成功要么回滚。
    @Transactional
    /**
     * 答辩讲解:
     * - 方法： updateRecommendation
     * - 作用：在输入规范化后，对现有记录执行部分字段更新。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 updateRecommendation 更新方法，修改数据库中的已有记录。
    // 方法用法：控制器修改数据时调用 updateRecommendation，负责找到原记录、更新字段并保存。
    public Map<String, Object> updateRecommendation(Long id, RecommendationUpdateRequest request) {
        // 行注释：声明变量 recommendation，保存本行计算或查询得到的结果，供后续逻辑使用。
        EnergyRecommendation recommendation = requireRecommendation(id);
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (request.getRecommendationType() != null) {
            // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
            recommendation.setRecommendationType(request.getRecommendationType().trim().toLowerCase());
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (request.getDescription() != null) {
            // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
            recommendation.setDescription(request.getDescription().trim());
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (request.getPotentialSavings() != null) {
            // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
            recommendation.setPotentialSavings(request.getPotentialSavings());
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (request.getPriority() != null) {
            // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
            recommendation.setPriority(normalizePriority(request.getPriority()));
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (request.getStatus() != null && !request.getStatus().isBlank()) {
            // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
            recommendation.setStatus(request.getStatus().trim().toLowerCase());
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：调用 JPA 保存实体，把数据写入数据库。
        return toMap(recommendationRepository.save(recommendation));
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：开启事务，保证该方法内的数据库操作要么全部成功要么回滚。
    @Transactional
    /**
     * 答辩讲解:
     * - 方法： updateRecommendationStatus
     * - 作用：在输入规范化后，对现有记录执行部分字段更新。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 updateRecommendationStatus 更新方法，修改数据库中的已有记录。
    // 方法用法：控制器修改数据时调用 updateRecommendationStatus，负责找到原记录、更新字段并保存。
    public Map<String, Object> updateRecommendationStatus(Long id, String status) {
        // 行注释：声明变量 recommendation，保存本行计算或查询得到的结果，供后续逻辑使用。
        EnergyRecommendation recommendation = requireRecommendation(id);
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (status != null && !status.isBlank()) {
            // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
            recommendation.setStatus(status.trim().toLowerCase());
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：调用 JPA 保存实体，把数据写入数据库。
        return toMap(recommendationRepository.save(recommendation));
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：开启事务，保证该方法内的数据库操作要么全部成功要么回滚。
    @Transactional
    /**
     * 答辩讲解:
     * - 方法： deleteRecommendation
     * - 作用：删除或逻辑移除目标记录，并维护数据一致性。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 deleteRecommendation 删除方法，删除前端指定的数据记录。
    // 方法用法：控制器删除数据时调用 deleteRecommendation，负责校验记录存在并执行删除。
    public void deleteRecommendation(Long id) {
        // 行注释：声明变量 recommendation，保存本行计算或查询得到的结果，供后续逻辑使用。
        EnergyRecommendation recommendation = requireRecommendation(id);
        // 行注释：调用 JPA 删除数据或清理过期记录。
        recommendationRepository.delete(recommendation);
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： getAllocation
     * - 作用：加载并返回前端视图所需的模块数据。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 getAllocation 查询方法，给前端返回当前模块数据。
    // 方法用法：控制器查询数据时调用 getAllocation，负责从数据库聚合并整理 Optimization 模块结果。
    public List<Map<String, Object>> getAllocation() {
        // 行注释：声明变量 sql，保存本行计算或查询得到的结果，供后续逻辑使用。
        String sql = """
                SELECT
                  ed.device_type,
                  COALESCE(SUM(em.energy_generated), 0) AS total_generated,
                  COALESCE(SUM(em.energy_consumed), 0) AS total_consumed,
                  COALESCE(AVG(em.efficiency), 0) AS avg_efficiency
                FROM energy_monitoring em
                JOIN energy_devices ed ON em.device_id = ed.id
                WHERE DATE(em.timestamp) = CURDATE()
                GROUP BY ed.device_type
                """;

        // 行注释：执行 SQL 查询，并把数据库结果转换成 Java 数据结构。
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        // 行注释：声明变量 totalGenerated，保存本行计算或查询得到的结果，供后续逻辑使用。
        double totalGenerated = rows.stream().mapToDouble(r -> toDouble(r.get("total_generated"))).sum();
        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        List<Map<String, Object>> allocation = new ArrayList<>();
        // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
        for (Map<String, Object> row : rows) {
            // 行注释：声明变量 deviceGenerated，保存本行计算或查询得到的结果，供后续逻辑使用。
            double deviceGenerated = toDouble(row.get("total_generated"));
            // 行注释：声明变量 avgEfficiency，保存本行计算或查询得到的结果，供后续逻辑使用。
            double avgEfficiency = toDouble(row.get("avg_efficiency"));

            // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
            Map<String, Object> item = new LinkedHashMap<>();
            // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
            item.put("device_type", row.get("device_type"));
            // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
            item.put("current_generation", scaled(deviceGenerated));
            // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
            item.put("recommended_allocation", totalGenerated == 0
                    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                    ? "0.00%" : scaled((deviceGenerated / totalGenerated) * 100) + "%");
            // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
            item.put("efficiency", scaled(avgEfficiency));
            // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
            item.put("optimization_potential", scaled(deviceGenerated * (1 - (avgEfficiency / 100))));
            // 行注释：向列表中追加一项数据，用于表格、图表或返回结果。
            allocation.add(item);
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return allocation;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： getSavingsStatistics
     * - 作用：加载并返回前端视图所需的模块数据。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 getSavingsStatistics 查询方法，给前端返回当前模块数据。
    // 方法用法：控制器查询数据时调用 getSavingsStatistics，负责从数据库聚合并整理 Optimization 模块结果。
    public Map<String, Object> getSavingsStatistics() {
        // 行注释：声明变量 sql，保存本行计算或查询得到的结果，供后续逻辑使用。
        String sql = """
                SELECT
                  COALESCE(SUM(potential_savings), 0) AS total_potential_savings,
                  COUNT(*) AS total_recommendations,
                  COALESCE(SUM(CASE WHEN status = 'implemented' THEN potential_savings ELSE 0 END), 0) AS realized_savings
                FROM energy_recommendations
                """;
        // 行注释：执行 SQL 查询，并把数据库结果转换成 Java 数据结构。
        return jdbcTemplate.queryForMap(sql);
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： requireRecommendation
     * - 作用：加载必需实体，未找到时抛出明确异常。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 requireRecommendation 方法，完成 OptimizationService 中对应的一步业务处理。
    // 方法用法：业务处理前调用它确认目标记录存在，不存在时抛出明确错误。
    private EnergyRecommendation requireRecommendation(Long id) {
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return recommendationRepository.findById(id)
                // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Recommendation not found"));
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： validateRecommendation
     * - 作用：支撑当前模块流程的业务辅助方法。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 validateRecommendation 方法，完成 OptimizationService 中对应的一步业务处理。
    // 方法用法：保存数据前调用 validateRecommendation，用于检查必填项和业务规则，避免脏数据入库。
    private void validateRecommendation(String type, String description) {
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (type == null || type.isBlank()) {
            // 行注释：抛出明确异常，让前端收到规范的错误提示。
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "recommendation_type is required");
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (description == null || description.isBlank()) {
            // 行注释：抛出明确异常，让前端收到规范的错误提示。
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "description is required");
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： normalizePriority
     * - 作用：将输入值规范化并校验为系统使用的标准格式。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 normalizePriority 方法，完成 OptimizationService 中对应的一步业务处理。
    // 方法用法：写入数据前调用 normalizePriority，用于把前端输入统一成后端认可的标准值。
    private String normalizePriority(String priority) {
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (priority == null || priority.isBlank()) {
            // 行注释：返回处理结果，最终会交给前端或上层方法使用。
            return "medium";
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：声明变量 p，保存本行计算或查询得到的结果，供后续逻辑使用。
        String p = priority.trim().toLowerCase();
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (!List.of("high", "medium", "low").contains(p)) {
            // 行注释：抛出明确异常，让前端收到规范的错误提示。
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "priority must be high/medium/low");
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return p;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： toMap
     * - 作用：将实体对象转换为接口响应 Map，并保持字段命名稳定。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 toMap 方法，完成 OptimizationService 中对应的一步业务处理。
    // 方法用法：返回接口数据前调用它，把实体对象转换成前端更容易读取的 Map 结构。
    private Map<String, Object> toMap(EnergyRecommendation entity) {
        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        Map<String, Object> row = new LinkedHashMap<>();
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        row.put("id", entity.getId());
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        row.put("created_at", entity.getCreatedAt());
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        row.put("recommendation_type", entity.getRecommendationType());
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        row.put("description", entity.getDescription());
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        row.put("potential_savings", entity.getPotentialSavings());
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        row.put("priority", entity.getPriority());
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        row.put("status", entity.getStatus());
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return row;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： buildAutoRecommendations
     * - 作用：基于底层数据组装衍生 SQL 过滤条件、响应对象或推荐结果。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：声明 buildAutoRecommendations 方法，承接当前模块的接口请求或内部业务处理。
    // 方法用法：组装接口响应或推荐内容时调用它，把计算结果整理成前端可展示的数据。
    private List<Map<String, Object>> buildAutoRecommendations(double totalConsumed,
                                                               // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                                                               double surplus,
                                                               // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                                                               double avgEfficiency,
                                                               // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                                                               double utilizationRate) {
        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        List<Map<String, Object>> recommendations = new ArrayList<>();
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (avgEfficiency < 80) {
            // 行注释：向列表中追加一项数据，用于表格、图表或返回结果。
            recommendations.add(buildRecommendation(
                    // 行注释：传入字符串参数 “efficiency”，作为当前方法调用的业务标识或显示文本。
                    "efficiency",
                    // 行注释：传入字符串参数 “Average device efficiency is lower than 80%. Plan cleaning and maintenance.”，作为当前方法调用的业务标识或显示文本。
                    "Average device efficiency is lower than 80%. Plan cleaning and maintenance.",
                    // 行注释：传入字符串参数 “high”，作为当前方法调用的业务标识或显示文本。
                    "high",
                    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                    totalConsumed * 0.15
            // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
            ));
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (utilizationRate < 60) {
            // 行注释：向列表中追加一项数据，用于表格、图表或返回结果。
            recommendations.add(buildRecommendation(
                    // 行注释：传入字符串参数 “utilization”，作为当前方法调用的业务标识或显示文本。
                    "utilization",
                    // 行注释：传入字符串参数 “Energy utilization is low. Rebalance energy allocation and shift loads.”，作为当前方法调用的业务标识或显示文本。
                    "Energy utilization is low. Rebalance energy allocation and shift loads.",
                    // 行注释：传入字符串参数 “medium”，作为当前方法调用的业务标识或显示文本。
                    "medium",
                    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                    surplus * 0.30
            // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
            ));
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (surplus > totalConsumed * 0.3) {
            // 行注释：向列表中追加一项数据，用于表格、图表或返回结果。
            recommendations.add(buildRecommendation(
                    // 行注释：传入字符串参数 “storage”，作为当前方法调用的业务标识或显示文本。
                    "storage",
                    // 行注释：传入字符串参数 “Generation surplus is significant. Expand storage or move flexible loads.”，作为当前方法调用的业务标识或显示文本。
                    "Generation surplus is significant. Expand storage or move flexible loads.",
                    // 行注释：传入字符串参数 “medium”，作为当前方法调用的业务标识或显示文本。
                    "medium",
                    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                    surplus * 0.50
            // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
            ));
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return recommendations;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： buildRecommendation
     * - 作用：基于底层数据组装衍生 SQL 过滤条件、响应对象或推荐结果。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 buildRecommendation 方法，完成 OptimizationService 中对应的一步业务处理。
    // 方法用法：组装接口响应或推荐内容时调用它，把计算结果整理成前端可展示的数据。
    private Map<String, Object> buildRecommendation(String type, String description, String priority, double savings) {
        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        Map<String, Object> item = new LinkedHashMap<>();
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        item.put("recommendation_type", type);
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        item.put("description", description);
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        item.put("priority", priority);
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        item.put("potential_savings", scaled(Math.max(0, savings)));
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
    // 行注释：定义 toDouble 方法，完成 OptimizationService 中对应的一步业务处理。
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
     * - 方法： scaled
     * - 作用：数值辅助方法，用于避免业务计算中的空值和精度问题。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 scaled 方法，完成 OptimizationService 中对应的一步业务处理。
    // 方法用法：统计计算时调用它，统一处理数字转换、保留小数和空值。
    private BigDecimal scaled(double value) {
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP);
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }
// 行注释：结束当前代码块，表示这段逻辑处理完成。
}



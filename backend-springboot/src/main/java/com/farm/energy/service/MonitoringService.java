// 逐行注释标记：已按业务语义处理，仅增加中文注释，不改变程序逻辑
/**
 * 答辩注释:
 * - 文件： MonitoringService
 * - 层级：服务层
 * - 职责：实现领域业务逻辑、数据聚合、校验与持久化编排。
 * - 此注释仅用于说明，不会改变运行行为。
 */
// 行注释：声明 MonitoringService 所在的包路径，体现它在项目中的模块位置。
package com.farm.energy.service;

// 行注释：引入前端请求参数对象，便于 Spring 自动接收表单或 JSON 数据。
import com.farm.energy.dto.DeviceCreateRequest;
// 行注释：引入前端请求参数对象，便于 Spring 自动接收表单或 JSON 数据。
import com.farm.energy.dto.DeviceUpdateRequest;
// 行注释：引入前端请求参数对象，便于 Spring 自动接收表单或 JSON 数据。
import com.farm.energy.dto.MonitoringDataRequest;
// 行注释：引入前端请求参数对象，便于 Spring 自动接收表单或 JSON 数据。
import com.farm.energy.dto.MonitoringDataUpdateRequest;
// 行注释：引入数据库实体类，用于读取或保存业务表记录。
import com.farm.energy.entity.EnergyDevice;
// 行注释：引入数据库实体类，用于读取或保存业务表记录。
import com.farm.energy.entity.EnergyMonitoring;
// 行注释：引入数据访问接口，用于操作对应数据库表。
import com.farm.energy.repository.EnergyDeviceRepository;
// 行注释：引入数据访问接口，用于操作对应数据库表。
import com.farm.energy.repository.EnergyMonitoringRepository;
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
// 行注释：引入时间类型，用于记录日期、创建时间或预测日期。
import java.time.LocalDate;
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
// 行注释：定义 MonitoringService 业务服务，承载该模块的主要代码。
// 类注释：这是 能源监测 模块的业务服务类，负责处理核心业务逻辑、数据校验、统计计算和数据库读写编排。
public class MonitoringService {

    // 行注释：注入 JDBC 查询工具，用于执行统计分析 SQL。
    private final JdbcTemplate jdbcTemplate;
    // 行注释：注入仓储接口，用于访问对应数据库表。
    private final EnergyDeviceRepository energyDeviceRepository;
    // 行注释：注入仓储接口，用于访问对应数据库表。
    private final EnergyMonitoringRepository energyMonitoringRepository;

    // 行注释：声明 MonitoringService 方法，承接当前模块的接口请求或内部业务处理。
    // 方法用法：Spring 创建 MonitoringService 对象时调用这个构造方法，用来注入本类需要的依赖。
    public MonitoringService(JdbcTemplate jdbcTemplate,
                             // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                             EnergyDeviceRepository energyDeviceRepository,
                             // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                             EnergyMonitoringRepository energyMonitoringRepository) {
        // 行注释：把构造方法传入的 jdbcTemplate 保存到成员变量，后续方法会继续调用它。
        this.jdbcTemplate = jdbcTemplate;
        // 行注释：把构造方法传入的 energyDeviceRepository 保存到成员变量，后续方法会继续调用它。
        this.energyDeviceRepository = energyDeviceRepository;
        // 行注释：把构造方法传入的 energyMonitoringRepository 保存到成员变量，后续方法会继续调用它。
        this.energyMonitoringRepository = energyMonitoringRepository;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： getActiveDevices
     * - 作用：加载并返回前端视图所需的模块数据。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 getActiveDevices 查询方法，给前端返回当前模块数据。
    // 方法用法：控制器查询数据时调用 getActiveDevices，负责从数据库聚合并整理 Monitoring 模块结果。
    public List<Map<String, Object>> getActiveDevices() {
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return listDevices(false);
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： listDevices
     * - 作用：查询并返回集合数据，用于界面表格/图表渲染。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 listDevices 方法，完成 MonitoringService 中对应的一步业务处理。
    // 方法用法：控制器加载列表时调用 listDevices，负责查询并转换成前端表格需要的数据结构。
    public List<Map<String, Object>> listDevices(boolean includeInactive) {
        // 行注释：声明变量 sql，保存本行计算或查询得到的结果，供后续逻辑使用。
        String sql = """
                SELECT id, device_name, device_type, capacity, location, installation_date, status, created_at
                FROM energy_devices
                """ + (includeInactive ? "" : "WHERE status = 'active'") + """
                ORDER BY id ASC
                """;
        // 行注释：执行 SQL 查询，并把数据库结果转换成 Java 数据结构。
        return jdbcTemplate.queryForList(sql);
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： getDevice
     * - 作用：加载并返回前端视图所需的模块数据。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 getDevice 查询方法，给前端返回当前模块数据。
    // 方法用法：控制器查询数据时调用 getDevice，负责从数据库聚合并整理 Monitoring 模块结果。
    public Map<String, Object> getDevice(Long id) {
        // 行注释：声明变量 device，保存本行计算或查询得到的结果，供后续逻辑使用。
        EnergyDevice device = requireDevice(id);
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return toDeviceMap(device);
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：开启事务，保证该方法内的数据库操作要么全部成功要么回滚。
    @Transactional
    /**
     * 答辩讲解:
     * - 方法： createDevice
     * - 作用：校验请求参数并持久化新增业务记录。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 createDevice 新增方法，把前端提交的数据保存到数据库。
    // 方法用法：控制器新增数据时调用 createDevice，负责校验参数并保存新记录。
    public Map<String, Object> createDevice(DeviceCreateRequest request) {
        // 行注释：读取对象字段值，参与业务判断、计算或返回。
        validateDeviceRequest(request.getDeviceName(), request.getDeviceType());

        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        EnergyDevice device = new EnergyDevice();
        // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
        device.setDeviceName(request.getDeviceName().trim());
        // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
        device.setDeviceType(request.getDeviceType().trim().toLowerCase());
        // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
        device.setCapacity(request.getCapacity());
        // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
        device.setLocation(request.getLocation());
        // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
        device.setInstallationDate(request.getInstallationDate());
        // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
        device.setStatus("active");
        // 行注释：调用 JPA 保存实体，把数据写入数据库。
        return toDeviceMap(energyDeviceRepository.save(device));
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：开启事务，保证该方法内的数据库操作要么全部成功要么回滚。
    @Transactional
    /**
     * 答辩讲解:
     * - 方法： updateDevice
     * - 作用：在输入规范化后，对现有记录执行部分字段更新。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 updateDevice 更新方法，修改数据库中的已有记录。
    // 方法用法：控制器修改数据时调用 updateDevice，负责找到原记录、更新字段并保存。
    public Map<String, Object> updateDevice(Long id, DeviceUpdateRequest request) {
        // 行注释：声明变量 device，保存本行计算或查询得到的结果，供后续逻辑使用。
        EnergyDevice device = requireDevice(id);

        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (request.getDeviceName() != null) {
            // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
            if (request.getDeviceName().isBlank()) {
                // 行注释：抛出明确异常，让前端收到规范的错误提示。
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Device name cannot be blank");
            // 行注释：结束当前代码块，表示这段逻辑处理完成。
            }
            // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
            device.setDeviceName(request.getDeviceName().trim());
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (request.getDeviceType() != null) {
            // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
            if (request.getDeviceType().isBlank()) {
                // 行注释：抛出明确异常，让前端收到规范的错误提示。
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Device type cannot be blank");
            // 行注释：结束当前代码块，表示这段逻辑处理完成。
            }
            // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
            device.setDeviceType(request.getDeviceType().trim().toLowerCase());
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (request.getCapacity() != null) {
            // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
            device.setCapacity(request.getCapacity());
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (request.getLocation() != null) {
            // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
            device.setLocation(request.getLocation());
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (request.getInstallationDate() != null) {
            // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
            device.setInstallationDate(request.getInstallationDate());
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (request.getStatus() != null && !request.getStatus().isBlank()) {
            // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
            device.setStatus(request.getStatus().trim().toLowerCase());
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }

        // 行注释：调用 JPA 保存实体，把数据写入数据库。
        return toDeviceMap(energyDeviceRepository.save(device));
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：开启事务，保证该方法内的数据库操作要么全部成功要么回滚。
    @Transactional
    /**
     * 答辩讲解:
     * - 方法： deleteDevice
     * - 作用：删除或逻辑移除目标记录，并维护数据一致性。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 deleteDevice 删除方法，删除前端指定的数据记录。
    // 方法用法：控制器删除数据时调用 deleteDevice，负责校验记录存在并执行删除。
    public void deleteDevice(Long id) {
        // 行注释：声明变量 device，保存本行计算或查询得到的结果，供后续逻辑使用。
        EnergyDevice device = requireDevice(id);
        // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
        device.setStatus("deleted");
        // 行注释：调用 JPA 保存实体，把数据写入数据库。
        energyDeviceRepository.save(device);
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： listMonitoringData
     * - 作用：查询并返回集合数据，用于界面表格/图表渲染。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 listMonitoringData 方法，完成 MonitoringService 中对应的一步业务处理。
    // 方法用法：控制器加载列表时调用 listMonitoringData，负责查询并转换成前端表格需要的数据结构。
    public List<Map<String, Object>> listMonitoringData(Long deviceId, Integer limit) {
        // 行注释：声明变量 realLimit，保存本行计算或查询得到的结果，供后续逻辑使用。
        int realLimit = (limit == null || limit <= 0 || limit > 1000) ? 200 : limit;
        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        StringBuilder sql = new StringBuilder("""
                SELECT
                  em.id,
                  em.device_id,
                  ed.device_name,
                  ed.device_type,
                  em.timestamp,
                  em.energy_generated,
                  em.energy_consumed,
                  em.efficiency,
                  em.temperature,
                  em.humidity
                FROM energy_monitoring em
                JOIN energy_devices ed ON em.device_id = ed.id
                """);
        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        List<Object> params = new ArrayList<>();
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (deviceId != null) {
            // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
            sql.append(" WHERE em.device_id = ? ");
            // 行注释：向列表中追加一项数据，用于表格、图表或返回结果。
            params.add(deviceId);
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
        sql.append(" ORDER BY em.timestamp DESC LIMIT ? ");
        // 行注释：向列表中追加一项数据，用于表格、图表或返回结果。
        params.add(realLimit);
        // 行注释：执行 SQL 查询，并把数据库结果转换成 Java 数据结构。
        return jdbcTemplate.queryForList(sql.toString(), params.toArray());
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： getMonitoringRecord
     * - 作用：加载并返回前端视图所需的模块数据。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 getMonitoringRecord 查询方法，给前端返回当前模块数据。
    // 方法用法：控制器查询数据时调用 getMonitoringRecord，负责从数据库聚合并整理 Monitoring 模块结果。
    public Map<String, Object> getMonitoringRecord(Long id) {
        // 行注释：声明变量 row，保存本行计算或查询得到的结果，供后续逻辑使用。
        EnergyMonitoring row = energyMonitoringRepository.findById(id)
                // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Monitoring record not found"));
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return toMonitoringMap(row);
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：开启事务，保证该方法内的数据库操作要么全部成功要么回滚。
    @Transactional
    /**
     * 答辩讲解:
     * - 方法： saveMonitoringData
     * - 作用：按模块校验规则将输入数据持久化到数据库。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 saveMonitoringData 方法，完成 MonitoringService 中对应的一步业务处理。
    // 方法用法：服务层内部或控制器调用 saveMonitoringData，用于完成 Monitoring 模块的一段核心业务逻辑。
    public Map<String, Object> saveMonitoringData(MonitoringDataRequest request) {
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (request.getDeviceId() == null) {
            // 行注释：抛出明确异常，让前端收到规范的错误提示。
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "device_id is required");
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：声明变量 device，保存本行计算或查询得到的结果，供后续逻辑使用。
        EnergyDevice device = requireDevice(request.getDeviceId());
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (!"active".equalsIgnoreCase(device.getStatus())) {
            // 行注释：抛出明确异常，让前端收到规范的错误提示。
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only active device can receive new data");
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }

        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        EnergyMonitoring monitoring = new EnergyMonitoring();
        // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
        monitoring.setDevice(device);
        // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
        monitoring.setTimestamp(LocalDateTime.now());
        // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
        monitoring.setEnergyGenerated(defaultDecimal(request.getEnergyGenerated()));
        // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
        monitoring.setEnergyConsumed(defaultDecimal(request.getEnergyConsumed()));
        // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
        monitoring.setEfficiency(defaultDecimal(request.getEfficiency()));
        // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
        monitoring.setTemperature(defaultDecimal(request.getTemperature()));
        // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
        monitoring.setHumidity(defaultDecimal(request.getHumidity()));
        // 行注释：调用 JPA 保存实体，把数据写入数据库。
        return toMonitoringMap(energyMonitoringRepository.save(monitoring));
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：开启事务，保证该方法内的数据库操作要么全部成功要么回滚。
    @Transactional
    /**
     * 答辩讲解:
     * - 方法： updateMonitoringData
     * - 作用：在输入规范化后，对现有记录执行部分字段更新。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 updateMonitoringData 更新方法，修改数据库中的已有记录。
    // 方法用法：控制器修改数据时调用 updateMonitoringData，负责找到原记录、更新字段并保存。
    public Map<String, Object> updateMonitoringData(Long id, MonitoringDataUpdateRequest request) {
        // 行注释：声明变量 monitoring，保存本行计算或查询得到的结果，供后续逻辑使用。
        EnergyMonitoring monitoring = energyMonitoringRepository.findById(id)
                // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Monitoring record not found"));

        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (request.getDeviceId() != null) {
            // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
            monitoring.setDevice(requireDevice(request.getDeviceId()));
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (request.getTimestamp() != null) {
            // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
            monitoring.setTimestamp(request.getTimestamp());
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (request.getEnergyGenerated() != null) {
            // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
            monitoring.setEnergyGenerated(request.getEnergyGenerated());
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (request.getEnergyConsumed() != null) {
            // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
            monitoring.setEnergyConsumed(request.getEnergyConsumed());
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (request.getEfficiency() != null) {
            // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
            monitoring.setEfficiency(request.getEfficiency());
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (request.getTemperature() != null) {
            // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
            monitoring.setTemperature(request.getTemperature());
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (request.getHumidity() != null) {
            // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
            monitoring.setHumidity(request.getHumidity());
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }

        // 行注释：调用 JPA 保存实体，把数据写入数据库。
        return toMonitoringMap(energyMonitoringRepository.save(monitoring));
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：开启事务，保证该方法内的数据库操作要么全部成功要么回滚。
    @Transactional
    /**
     * 答辩讲解:
     * - 方法： deleteMonitoringData
     * - 作用：删除或逻辑移除目标记录，并维护数据一致性。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 deleteMonitoringData 删除方法，删除前端指定的数据记录。
    // 方法用法：控制器删除数据时调用 deleteMonitoringData，负责校验记录存在并执行删除。
    public void deleteMonitoringData(Long id) {
        // 行注释：声明变量 row，保存本行计算或查询得到的结果，供后续逻辑使用。
        EnergyMonitoring row = energyMonitoringRepository.findById(id)
                // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Monitoring record not found"));
        // 行注释：调用 JPA 删除数据或清理过期记录。
        energyMonitoringRepository.delete(row);
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： getRealtimeData
     * - 作用：加载并返回前端视图所需的模块数据。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 getRealtimeData 查询方法，给前端返回当前模块数据。
    // 方法用法：控制器查询数据时调用 getRealtimeData，负责从数据库聚合并整理 Monitoring 模块结果。
    public List<Map<String, Object>> getRealtimeData(int limit) {
        // 行注释：声明变量 sql，保存本行计算或查询得到的结果，供后续逻辑使用。
        String sql = """
                SELECT
                  em.id,
                  ed.device_name,
                  ed.device_type,
                  em.energy_generated,
                  em.energy_consumed,
                  em.efficiency,
                  em.temperature,
                  em.humidity,
                  em.timestamp
                FROM energy_monitoring em
                JOIN energy_devices ed ON em.device_id = ed.id
                ORDER BY em.timestamp DESC
                LIMIT ?
                """;
        // 行注释：执行 SQL 查询，并把数据库结果转换成 Java 数据结构。
        return jdbcTemplate.queryForList(sql, limit);
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： getHistory
     * - 作用：加载并返回前端视图所需的模块数据。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 getHistory 查询方法，给前端返回当前模块数据。
    // 方法用法：控制器查询数据时调用 getHistory，负责从数据库聚合并整理 Monitoring 模块结果。
    public List<Map<String, Object>> getHistory(Long deviceId, String startDate, String endDate) {
        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        StringBuilder sql = new StringBuilder("""
                SELECT
                  id,
                  device_id,
                  timestamp,
                  energy_generated,
                  energy_consumed,
                  efficiency,
                  temperature,
                  humidity
                FROM energy_monitoring
                WHERE device_id = ?
                """);

        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        List<Object> params = new ArrayList<>();
        // 行注释：向列表中追加一项数据，用于表格、图表或返回结果。
        params.add(deviceId);

        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (startDate != null && !startDate.isBlank() && endDate != null && !endDate.isBlank()) {
            // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
            sql.append(" AND timestamp BETWEEN ? AND ? ");
            // 行注释：向列表中追加一项数据，用于表格、图表或返回结果。
            params.add(startDate + " 00:00:00");
            // 行注释：向列表中追加一项数据，用于表格、图表或返回结果。
            params.add(endDate + " 23:59:59");
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }

        // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
        sql.append(" ORDER BY timestamp DESC LIMIT 1000 ");
        // 行注释：执行 SQL 查询，并把数据库结果转换成 Java 数据结构。
        return jdbcTemplate.queryForList(sql.toString(), params.toArray());
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： getTodayStatistics
     * - 作用：加载并返回前端视图所需的模块数据。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 getTodayStatistics 查询方法，给前端返回当前模块数据。
    // 方法用法：控制器查询数据时调用 getTodayStatistics，负责从数据库聚合并整理 Monitoring 模块结果。
    public Map<String, Object> getTodayStatistics() {
        // 行注释：优先取今天作为统计日期，今天没有记录时自动退回最新有数据的一天，避免首页显示 0。
        Object statisticsDate = getStatisticsDate();
        // 行注释：如果数据库完全没有监测记录，则返回明确的 0 值结构，避免前端取字段时报错。
        if (statisticsDate == null) {
            // 行注释：返回空统计结果，表示当前数据库还没有任何采集数据。
            return emptyStatistics();
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：声明变量 sql，保存本行计算或查询得到的结果，供后续逻辑使用。
        String sql = """
                SELECT
                  COALESCE(SUM(energy_generated), 0) as total_generated,
                  COALESCE(SUM(energy_consumed), 0) as total_consumed,
                  COALESCE(AVG(efficiency), 0) as avg_efficiency,
                  COUNT(*) as data_points
                FROM energy_monitoring
                WHERE DATE(timestamp) = ?
                """;

        // 行注释：执行 SQL 查询，并把数据库结果转换成 Java 数据结构。
        Map<String, Object> stats = jdbcTemplate.queryForMap(sql, statisticsDate);
        // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
        normalizeNumeric(stats, "total_generated", "total_consumed", "avg_efficiency");
        // 行注释：把本次实际统计日期返回给前端，方便前端判断标题显示“今日”还是“最新统计日”。
        stats.put("stat_date", statisticsDate.toString());
        // 行注释：判断统计日期是否就是今天，供首页统计卡片动态显示标题。
        stats.put("is_today", LocalDate.now().toString().equals(statisticsDate.toString()));
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return stats;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： getStatisticsByType
     * - 作用：加载并返回前端视图所需的模块数据。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 getStatisticsByType 查询方法，给前端返回当前模块数据。
    // 方法用法：控制器查询数据时调用 getStatisticsByType，负责从数据库聚合并整理 Monitoring 模块结果。
    public List<Map<String, Object>> getStatisticsByType() {
        // 行注释：优先取今天作为统计日期，今天没有记录时自动退回最新有数据的一天。
        Object statisticsDate = getStatisticsDate();
        // 行注释：如果数据库还没有任何监测记录，则返回空列表，图表会自然显示为空状态。
        if (statisticsDate == null) {
            // 行注释：返回空列表，表示暂时没有可用于分类统计的数据。
            return List.of();
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：声明变量 sql，保存本行计算或查询得到的结果，供后续逻辑使用。
        String sql = """
                SELECT
                  ed.device_type,
                  COALESCE(SUM(em.energy_generated), 0) as total_generated,
                  COALESCE(SUM(em.energy_consumed), 0) as total_consumed,
                  COALESCE(AVG(em.efficiency), 0) as avg_efficiency
                FROM energy_monitoring em
                JOIN energy_devices ed ON em.device_id = ed.id
                WHERE DATE(em.timestamp) = ?
                GROUP BY ed.device_type
                ORDER BY ed.device_type
                """;

        // 行注释：执行 SQL 查询，并把数据库结果转换成 Java 数据结构。
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, statisticsDate);
        // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
        rows.forEach(row -> normalizeNumeric(row, "total_generated", "total_consumed", "avg_efficiency"));
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return rows;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： getStatisticsDate
     * - 作用：确定首页统计应该使用哪一天的数据。
     * - 说明：优先使用今天；如果今天没有采集数据，则使用数据库中最新的一天，避免演示时统计卡片显示 0。
     */
    // 行注释：定义 getStatisticsDate 方法，负责选择统计卡片和分类图表使用的日期。
    // 方法用法：统计接口执行聚合 SQL 前调用，保证页面始终优先展示有效采集数据。
    private Object getStatisticsDate() {
        // 行注释：声明变量 sql，先判断今天是否有数据，没有则取监测表最新日期。
        String sql = """
                SELECT COALESCE(
                  (SELECT CURDATE() WHERE EXISTS (
                    SELECT 1 FROM energy_monitoring WHERE DATE(timestamp) = CURDATE()
                  )),
                  (SELECT DATE(MAX(timestamp)) FROM energy_monitoring)
                ) AS statistics_date
                """;
        // 行注释：执行日期选择 SQL，并把结果返回给上层统计方法使用。
        return jdbcTemplate.queryForObject(sql, Object.class);
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： emptyStatistics
     * - 作用：在数据库没有任何监测记录时返回安全默认统计值。
     * - 说明：这样前端不用额外判断空对象，页面也不会因为缺字段报错。
     */
    // 行注释：定义 emptyStatistics 方法，组装没有采集数据时的默认统计结构。
    // 方法用法：统计日期为空时调用，保证接口仍返回完整字段。
    private Map<String, Object> emptyStatistics() {
        // 行注释：创建有序 Map，保证返回给前端的统计字段稳定清晰。
        Map<String, Object> stats = new LinkedHashMap<>();
        // 行注释：设置总发电量为 0，表示暂无采集数据。
        stats.put("total_generated", BigDecimal.ZERO);
        // 行注释：设置总消耗量为 0，表示暂无采集数据。
        stats.put("total_consumed", BigDecimal.ZERO);
        // 行注释：设置平均效率为 0，表示暂无采集数据。
        stats.put("avg_efficiency", BigDecimal.ZERO);
        // 行注释：设置采集数据点为 0，表示暂无采集数据。
        stats.put("data_points", 0);
        // 行注释：统计日期为空，说明数据库中没有任何可统计记录。
        stats.put("stat_date", null);
        // 行注释：标记不是今天的真实采集统计，方便前端标题处理。
        stats.put("is_today", false);
        // 行注释：返回默认统计结果，交给控制器转换成 JSON。
        return stats;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： requireDevice
     * - 作用：加载必需实体，未找到时抛出明确异常。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 requireDevice 方法，完成 MonitoringService 中对应的一步业务处理。
    // 方法用法：业务处理前调用它确认目标记录存在，不存在时抛出明确错误。
    private EnergyDevice requireDevice(Long id) {
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return energyDeviceRepository.findById(id)
                // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Device not found"));
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： validateDeviceRequest
     * - 作用：支撑当前模块流程的业务辅助方法。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 validateDeviceRequest 方法，完成 MonitoringService 中对应的一步业务处理。
    // 方法用法：保存数据前调用 validateDeviceRequest，用于检查必填项和业务规则，避免脏数据入库。
    private void validateDeviceRequest(String name, String type) {
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (name == null || name.isBlank()) {
            // 行注释：抛出明确异常，让前端收到规范的错误提示。
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "device_name is required");
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (type == null || type.isBlank()) {
            // 行注释：抛出明确异常，让前端收到规范的错误提示。
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "device_type is required");
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： toDeviceMap
     * - 作用：支撑当前模块流程的业务辅助方法。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 toDeviceMap 方法，完成 MonitoringService 中对应的一步业务处理。
    // 方法用法：服务层内部或控制器调用 toDeviceMap，用于完成 Monitoring 模块的一段核心业务逻辑。
    private Map<String, Object> toDeviceMap(EnergyDevice entity) {
        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        Map<String, Object> row = new LinkedHashMap<>();
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        row.put("id", entity.getId());
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        row.put("device_name", entity.getDeviceName());
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        row.put("device_type", entity.getDeviceType());
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        row.put("capacity", entity.getCapacity());
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        row.put("location", entity.getLocation());
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        row.put("installation_date", entity.getInstallationDate());
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        row.put("status", entity.getStatus());
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        row.put("created_at", entity.getCreatedAt());
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return row;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： toMonitoringMap
     * - 作用：支撑当前模块流程的业务辅助方法。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 toMonitoringMap 方法，完成 MonitoringService 中对应的一步业务处理。
    // 方法用法：服务层内部或控制器调用 toMonitoringMap，用于完成 Monitoring 模块的一段核心业务逻辑。
    private Map<String, Object> toMonitoringMap(EnergyMonitoring entity) {
        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        Map<String, Object> row = new LinkedHashMap<>();
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        row.put("id", entity.getId());
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        row.put("device_id", entity.getDevice().getId());
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        row.put("device_name", entity.getDevice().getDeviceName());
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        row.put("device_type", entity.getDevice().getDeviceType());
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        row.put("timestamp", entity.getTimestamp());
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        row.put("energy_generated", entity.getEnergyGenerated());
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        row.put("energy_consumed", entity.getEnergyConsumed());
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        row.put("efficiency", entity.getEfficiency());
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        row.put("temperature", entity.getTemperature());
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        row.put("humidity", entity.getHumidity());
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return row;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： defaultDecimal
     * - 作用：数值辅助方法，用于避免业务计算中的空值和精度问题。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 defaultDecimal 方法，完成 MonitoringService 中对应的一步业务处理。
    // 方法用法：服务层内部或控制器调用 defaultDecimal，用于完成 Monitoring 模块的一段核心业务逻辑。
    private BigDecimal defaultDecimal(BigDecimal value) {
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return value == null ? BigDecimal.ZERO : value;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： normalizeNumeric
     * - 作用：将输入值规范化并校验为系统使用的标准格式。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 normalizeNumeric 方法，完成 MonitoringService 中对应的一步业务处理。
    // 方法用法：写入数据前调用 normalizeNumeric，用于把前端输入统一成后端认可的标准值。
    private void normalizeNumeric(Map<String, Object> map, String... keys) {
        // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
        for (String key : keys) {
            // 行注释：声明变量 value，保存本行计算或查询得到的结果，供后续逻辑使用。
            Object value = map.get(key);
            // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
            if (value == null) {
                // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
                map.put(key, BigDecimal.ZERO);
            // 行注释：结束当前代码块，表示这段逻辑处理完成。
            }
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }
// 行注释：结束当前代码块，表示这段逻辑处理完成。
}



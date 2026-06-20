// 逐行注释标记：已按业务语义处理，仅增加中文注释，不改变程序逻辑
/**
 * 答辩注释:
 * - 文件： DataSeeder
 * - 层级：配置层
 * - 职责：定义框架级 Bean、拦截器、跨域、异常处理与启动初始化逻辑。
 * - 此注释仅用于说明，不会改变运行行为。
 */
// 行注释：声明 DataSeeder 所在的包路径，体现它在项目中的模块位置。
package com.farm.energy.config;

// 行注释：引入数据库实体类，用于读取或保存业务表记录。
import com.farm.energy.entity.CarbonEmission;
// 行注释：引入数据库实体类，用于读取或保存业务表记录。
import com.farm.energy.entity.EnergyDevice;
// 行注释：引入数据库实体类，用于读取或保存业务表记录。
import com.farm.energy.entity.EnergyMonitoring;
// 行注释：引入数据库实体类，用于读取或保存业务表记录。
import com.farm.energy.entity.EnergyRecommendation;
// 行注释：引入数据访问接口，用于操作对应数据库表。
import com.farm.energy.repository.CarbonEmissionRepository;
// 行注释：引入数据访问接口，用于操作对应数据库表。
import com.farm.energy.repository.EnergyDeviceRepository;
// 行注释：引入数据访问接口，用于操作对应数据库表。
import com.farm.energy.repository.EnergyMonitoringRepository;
// 行注释：引入数据访问接口，用于操作对应数据库表。
import com.farm.energy.repository.EnergyRecommendationRepository;
// 行注释：引入业务服务类，把具体业务处理交给服务层完成。
import com.farm.energy.service.AuthService;
// 行注释：引入当前文件要使用的类或注解，保证后续代码可以编译。
import org.springframework.boot.CommandLineRunner;
// 行注释：引入 JDBC 查询工具，用于启动时修复旧演示数据和补充当天采集记录。
import org.springframework.jdbc.core.JdbcTemplate;
// 行注释：引入当前文件要使用的类或注解，保证后续代码可以编译。
import org.springframework.stereotype.Component;

// 行注释：引入高精度数字类型，用于能源、碳排等小数计算。
import java.math.BigDecimal;
// 行注释：引入当前文件要使用的类或注解，保证后续代码可以编译。
import java.math.RoundingMode;
// 行注释：引入时间类型，用于记录日期、创建时间或预测日期。
import java.time.LocalDate;
// 行注释：引入时间类型，用于记录日期、创建时间或预测日期。
import java.time.LocalDateTime;
// 行注释：引入 List 结构，用于返回表格或图表列表数据。
import java.util.ArrayList;
// 行注释：引入 List 结构，用于返回表格或图表列表数据。
import java.util.List;
// 行注释：引入当前文件要使用的类或注解，保证后续代码可以编译。
import java.util.Random;

// 行注释：把当前类注册为 Spring 组件，启动时自动交给容器管理。
@Component
// 行注释：定义 DataSeeder 系统配置类，承载该模块的主要代码。
// 类注释：这是数据初始化类，负责在系统首次启动或空表时写入演示数据，方便页面展示。
public class DataSeeder implements CommandLineRunner {

    // 行注释：注入仓储接口，用于访问对应数据库表。
    private final EnergyDeviceRepository deviceRepository;
    // 行注释：注入仓储接口，用于访问对应数据库表。
    private final EnergyMonitoringRepository monitoringRepository;
    // 行注释：注入仓储接口，用于访问对应数据库表。
    private final EnergyRecommendationRepository recommendationRepository;
    // 行注释：注入仓储接口，用于访问对应数据库表。
    private final CarbonEmissionRepository carbonEmissionRepository;
    // 行注释：注入认证服务，用于登录、登出、查当前用户和修改密码。
    private final AuthService authService;
    // 行注释：注入 JDBC 工具，用于执行少量启动修复 SQL，保证演示数据保持中文且当天有数据。
    private final JdbcTemplate jdbcTemplate;

    // 行注释：声明 DataSeeder 方法，承接当前模块的接口请求或内部业务处理。
    // 方法用法：Spring 创建 DataSeeder 对象时调用这个构造方法，用来注入本类需要的依赖。
    public DataSeeder(EnergyDeviceRepository deviceRepository,
                      // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                      EnergyMonitoringRepository monitoringRepository,
                      // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                      EnergyRecommendationRepository recommendationRepository,
                      // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                      CarbonEmissionRepository carbonEmissionRepository,
                      // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                      AuthService authService,
                      // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                      JdbcTemplate jdbcTemplate) {
        // 行注释：把构造方法传入的 deviceRepository 保存到成员变量，后续方法会继续调用它。
        this.deviceRepository = deviceRepository;
        // 行注释：把构造方法传入的 monitoringRepository 保存到成员变量，后续方法会继续调用它。
        this.monitoringRepository = monitoringRepository;
        // 行注释：把构造方法传入的 recommendationRepository 保存到成员变量，后续方法会继续调用它。
        this.recommendationRepository = recommendationRepository;
        // 行注释：把构造方法传入的 carbonEmissionRepository 保存到成员变量，后续方法会继续调用它。
        this.carbonEmissionRepository = carbonEmissionRepository;
        // 行注释：把构造方法传入的 authService 保存到成员变量，后续方法会继续调用它。
        this.authService = authService;
        // 行注释：把构造方法传入的 jdbcTemplate 保存到成员变量，后续方法会继续调用它。
        this.jdbcTemplate = jdbcTemplate;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：说明这里重写父类或接口的方法，例如拦截器入口方法。
    @Override
    /**
     * 答辩讲解:
     * - 方法： run
     * - 作用：应用启动后执行初始化钩子，写入必要的种子数据。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 run 方法，完成 DataSeeder 中对应的一步业务处理。
    // 方法用法：Spring Boot 启动时调用或识别 run，用于注册系统配置、跨域、拦截器或异常处理。
    public void run(String... args) {
        // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
        seedDefaultUsers();
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (deviceRepository.count() > 0) {
            // 行注释：已有旧演示数据时执行中文化修复，避免页面继续显示英文设备名。
            localizeExistingDemoData();
            // 行注释：已有旧演示数据但当天没有采集记录时补一组当天数据，避免首页统计为 0。
            seedTodayMonitoringDataIfMissing();
            // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
            return;
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }

        // 行注释：声明变量 devices，保存本行计算或查询得到的结果，供后续逻辑使用。
        List<EnergyDevice> devices = seedDevices();
        // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
        seedMonitoringData(devices);
        // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
        seedRecommendations();
        // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
        seedCarbonData();
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： seedDefaultUsers
     * - 作用：支撑当前模块流程的业务辅助方法。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 seedDefaultUsers 方法，完成 DataSeeder 中对应的一步业务处理。
    // 方法用法：Spring Boot 启动时调用或识别 seedDefaultUsers，用于注册系统配置、跨域、拦截器或异常处理。
    private void seedDefaultUsers() {
        // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
        authService.ensureUser("admin", "系统管理员", "admin", "admin123");
        // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
        authService.ensureUser("manager", "农场管理员", "manager", "manager123");
        // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
        authService.ensureUser("viewer", "只读用户", "viewer", "viewer123");
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： localizeExistingDemoData
     * - 作用：把旧版本数据库里已经存在的英文演示数据更新为中文。
     * - 说明：只修改演示名称、位置、用户显示名和建议说明，不改变设备类型编码等业务计算字段。
     */
    // 行注释：定义 localizeExistingDemoData 方法，启动时把旧英文演示数据修复成中文展示数据。
    // 方法用法：项目已经有数据时调用，保证不用重置数据库也能看到中文演示内容。
    private void localizeExistingDemoData() {
        // 行注释：更新设备名称和位置，把旧英文演示值改成中文值。
        jdbcTemplate.update("""
                UPDATE energy_devices
                SET
                  device_name = CASE device_name
                    WHEN 'Solar Panel A' THEN '光伏板A'
                    WHEN 'Solar Panel B' THEN '光伏板B'
                    WHEN 'Wind Turbine A' THEN '风力发电机A'
                    WHEN 'Biomass Unit A' THEN '生物质发电单元A'
                    ELSE device_name
                  END,
                  location = CASE location
                    WHEN 'East Zone' THEN '东区'
                    WHEN 'West Zone' THEN '西区'
                    WHEN 'North Zone' THEN '北区'
                    WHEN 'South Zone' THEN '南区'
                    ELSE location
                  END
                WHERE device_name IN ('Solar Panel A', 'Solar Panel B', 'Wind Turbine A', 'Biomass Unit A')
                   OR location IN ('East Zone', 'West Zone', 'North Zone', 'South Zone')
                """);
        // 行注释：更新默认用户显示名，让右上角登录用户显示中文身份。
        jdbcTemplate.update("""
                UPDATE sys_users
                SET display_name = CASE username
                  WHEN 'admin' THEN '系统管理员'
                  WHEN 'manager' THEN '农场管理员'
                  WHEN 'viewer' THEN '只读用户'
                  ELSE display_name
                END
                WHERE username IN ('admin', 'manager', 'viewer')
                """);
        // 行注释：更新节能建议说明，把旧英文建议改成中文，方便节能管理页面答辩讲解。
        jdbcTemplate.update("""
                UPDATE energy_recommendations
                SET description = CASE description
                  WHEN 'Low efficiency devices should be cleaned and calibrated every 2 weeks.'
                    THEN '低效率设备建议每两周清洁和校准一次。'
                  WHEN 'Charge storage before irrigation peak to keep supply stable.'
                    THEN '灌溉高峰前提前为储能设备充电，保持供能稳定。'
                  WHEN 'Expand battery capacity and discharge off-peak at night.'
                    THEN '扩展电池容量，并在夜间低谷时段放电。'
                  ELSE description
                END
                WHERE description IN (
                  'Low efficiency devices should be cleaned and calibrated every 2 weeks.',
                  'Charge storage before irrigation peak to keep supply stable.',
                  'Expand battery capacity and discharge off-peak at night.'
                )
                """);
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： seedTodayMonitoringDataIfMissing
     * - 作用：当天没有采集记录时自动补充一组演示监测数据。
     * - 说明：这样首页“今日总发电量、今日总消耗量、平均效率、采集数据点”不会因为日期变化显示 0。
     */
    // 行注释：定义 seedTodayMonitoringDataIfMissing 方法，启动时保证当天至少有一组监测数据。
    // 方法用法：旧数据库已有设备但今天没有采集记录时调用，用于修复首页统计为空的问题。
    private void seedTodayMonitoringDataIfMissing() {
        // 行注释：查询今天是否已有监测记录，已有则不重复插入。
        Integer todayCount = jdbcTemplate.queryForObject(
                // 行注释：统计 energy_monitoring 表中当天采集记录数量。
                "SELECT COUNT(*) FROM energy_monitoring WHERE DATE(timestamp) = CURDATE()",
                // 行注释：指定查询结果转换成 Integer 类型。
                Integer.class
        // 行注释：结束当前方法调用，把上面准备好的参数一次性提交执行。
        );
        // 行注释：如果今天已经有数据，直接返回，避免重复生成演示记录。
        if (todayCount != null && todayCount > 0) {
            // 行注释：结束当天数据补充流程。
            return;
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }

        // 行注释：读取当前所有设备，后续只给运行中的设备生成当天采集记录。
        List<EnergyDevice> devices = deviceRepository.findAll();
        // 行注释：创建随机数对象，用当前日期作为种子，让同一天生成的数据稳定可复现。
        Random random = new Random(LocalDate.now().toEpochDay());
        // 行注释：创建监测记录列表，用于一次性批量保存当天演示数据。
        List<EnergyMonitoring> records = new ArrayList<>();
        // 行注释：遍历设备列表，为每台运行中的设备补充一条当天采集记录。
        for (EnergyDevice device : devices) {
            // 行注释：跳过停用或已删除设备，避免无效设备参与首页统计。
            if (!"active".equalsIgnoreCase(device.getStatus())) {
                // 行注释：继续处理下一台设备。
                continue;
            // 行注释：结束当前代码块，表示这段逻辑处理完成。
            }
            // 行注释：把当前设备的当天演示数据加入待保存列表。
            records.add(buildMonitoringRecord(device, 0, random));
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：如果存在可用设备，则把当天演示记录批量写入数据库。
        if (!records.isEmpty()) {
            // 行注释：调用 JPA 批量保存当天监测记录。
            monitoringRepository.saveAll(records);
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： seedDevices
     * - 作用：支撑当前模块流程的业务辅助方法。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 seedDevices 方法，完成 DataSeeder 中对应的一步业务处理。
    // 方法用法：Spring Boot 启动时调用或识别 seedDevices，用于注册系统配置、跨域、拦截器或异常处理。
    private List<EnergyDevice> seedDevices() {
        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        List<EnergyDevice> devices = new ArrayList<>();
        // 行注释：向列表中追加一项数据，用于表格、图表或返回结果。
        devices.add(device("光伏板A", "solar", "50", "东区", LocalDate.now().minusMonths(14)));
        // 行注释：向列表中追加一项数据，用于表格、图表或返回结果。
        devices.add(device("光伏板B", "solar", "50", "西区", LocalDate.now().minusMonths(14)));
        // 行注释：向列表中追加一项数据，用于表格、图表或返回结果。
        devices.add(device("风力发电机A", "wind", "30", "北区", LocalDate.now().minusMonths(12)));
        // 行注释：向列表中追加一项数据，用于表格、图表或返回结果。
        devices.add(device("生物质发电单元A", "biomass", "40", "南区", LocalDate.now().minusMonths(10)));
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return deviceRepository.saveAll(devices);
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： seedMonitoringData
     * - 作用：支撑当前模块流程的业务辅助方法。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 seedMonitoringData 方法，完成 DataSeeder 中对应的一步业务处理。
    // 方法用法：Spring Boot 启动时调用或识别 seedMonitoringData，用于注册系统配置、跨域、拦截器或异常处理。
    private void seedMonitoringData(List<EnergyDevice> devices) {
        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        Random random = new Random(42);
        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        List<EnergyMonitoring> records = new ArrayList<>();

        // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
        for (int day = 0; day < 30; day++) {
            // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
            for (EnergyDevice device : devices) {
                // 行注释：声明变量 generationBase，保存本行计算或查询得到的结果，供后续逻辑使用。
                double generationBase = switch (device.getDeviceType()) {
                    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                    case "solar" -> 48;
                    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                    case "wind" -> 28;
                    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                    case "biomass" -> 22;
                    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                    default -> 20;
                // 行注释：结束当前代码块，表示这段逻辑处理完成。
                };

                // 行注释：声明变量 generated，保存本行计算或查询得到的结果，供后续逻辑使用。
                BigDecimal generated = scaled(generationBase + random.nextDouble() * 16);
                // 行注释：声明变量 consumed，保存本行计算或查询得到的结果，供后续逻辑使用。
                BigDecimal consumed = scaled(generated.doubleValue() * (0.20 + random.nextDouble() * 0.25));
                // 行注释：声明变量 efficiency，保存本行计算或查询得到的结果，供后续逻辑使用。
                BigDecimal efficiency = scaled(72 + random.nextDouble() * 22);
                // 行注释：声明变量 temperature，保存本行计算或查询得到的结果，供后续逻辑使用。
                BigDecimal temperature = scaled(16 + random.nextDouble() * 18);
                // 行注释：声明变量 humidity，保存本行计算或查询得到的结果，供后续逻辑使用。
                BigDecimal humidity = scaled(42 + random.nextDouble() * 30);

                // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
                EnergyMonitoring record = new EnergyMonitoring();
                // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
                record.setDevice(device);
                // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
                record.setTimestamp(
                        // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                        LocalDateTime.now()
                                // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                                .minusDays(day)
                                // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                                .withHour(8 + random.nextInt(10))
                                // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                                .withMinute(random.nextInt(60))
                                // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                                .withSecond(0)
                                // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                                .withNano(0)
                // 行注释：结束当前方法调用，把上面准备好的参数一次性提交执行。
                );
                // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
                record.setEnergyGenerated(generated);
                // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
                record.setEnergyConsumed(consumed);
                // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
                record.setEfficiency(efficiency);
                // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
                record.setTemperature(temperature);
                // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
                record.setHumidity(humidity);
                // 行注释：向列表中追加一项数据，用于表格、图表或返回结果。
                records.add(record);
            // 行注释：结束当前代码块，表示这段逻辑处理完成。
            }
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
        monitoringRepository.saveAll(records);
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： buildMonitoringRecord
     * - 作用：根据设备类型生成一条能源监测演示记录。
     * - 说明：太阳能、风能、生物质能使用不同的发电基准值，让演示数据更接近真实业务场景。
     */
    // 行注释：定义 buildMonitoringRecord 方法，统一创建能源监测演示数据。
    // 方法用法：初始化 30 天历史数据或补充当天数据时调用，避免首页和图表缺少采集记录。
    private EnergyMonitoring buildMonitoringRecord(EnergyDevice device, int day, Random random) {
        // 行注释：根据设备类型选择发电基准值，体现不同能源设备的产能差异。
        double generationBase = switch (device.getDeviceType()) {
            // 行注释：太阳能设备使用较高发电基准值。
            case "solar" -> 48;
            // 行注释：风能设备使用中等发电基准值。
            case "wind" -> 28;
            // 行注释：生物质能设备使用相对稳定的发电基准值。
            case "biomass" -> 22;
            // 行注释：其他设备使用默认发电基准值。
            default -> 20;
        // 行注释：结束 switch 表达式，得到当前设备的发电基准。
        };

        // 行注释：生成发电量演示值，并统一保留两位小数。
        BigDecimal generated = scaled(generationBase + random.nextDouble() * 16);
        // 行注释：按发电量比例生成消耗量演示值，体现设备运行自身耗能。
        BigDecimal consumed = scaled(generated.doubleValue() * (0.20 + random.nextDouble() * 0.25));
        // 行注释：生成设备效率演示值，用于首页平均效率和趋势图统计。
        BigDecimal efficiency = scaled(72 + random.nextDouble() * 22);
        // 行注释：生成温度演示值，用于监测记录表格展示环境参数。
        BigDecimal temperature = scaled(16 + random.nextDouble() * 18);
        // 行注释：生成湿度演示值，用于监测记录表格展示环境参数。
        BigDecimal humidity = scaled(42 + random.nextDouble() * 30);

        // 行注释：创建能源监测实体，准备写入 energy_monitoring 表。
        EnergyMonitoring record = new EnergyMonitoring();
        // 行注释：关联当前设备，保证监测记录可以回查设备名称和类型。
        record.setDevice(device);
        // 行注释：设置采集时间，day 为 0 表示今天，day 越大表示越早的历史记录。
        record.setTimestamp(
                // 行注释：以当前时间为基础生成演示采集时间。
                LocalDateTime.now()
                        // 行注释：根据 day 参数向前推对应天数。
                        .minusDays(day)
                        // 行注释：设置白天采集小时，让演示数据看起来像正常运行时采集。
                        .withHour(8 + random.nextInt(10))
                        // 行注释：随机设置分钟，避免所有演示记录时间完全相同。
                        .withMinute(random.nextInt(60))
                        // 行注释：秒数固定为 0，让时间展示更整齐。
                        .withSecond(0)
                        // 行注释：纳秒固定为 0，避免前端展示过长时间精度。
                        .withNano(0)
        // 行注释：结束当前方法调用，把上面准备好的参数一次性提交执行。
        );
        // 行注释：写入发电量，供首页统计、表格和图表使用。
        record.setEnergyGenerated(generated);
        // 行注释：写入消耗量，供首页统计、表格和图表使用。
        record.setEnergyConsumed(consumed);
        // 行注释：写入效率，供平均效率和效率趋势图使用。
        record.setEfficiency(efficiency);
        // 行注释：写入温度，供监测记录表格展示。
        record.setTemperature(temperature);
        // 行注释：写入湿度，供监测记录表格展示。
        record.setHumidity(humidity);
        // 行注释：返回组装好的监测记录，交给调用方批量保存。
        return record;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： seedRecommendations
     * - 作用：支撑当前模块流程的业务辅助方法。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 seedRecommendations 方法，完成 DataSeeder 中对应的一步业务处理。
    // 方法用法：Spring Boot 启动时调用或识别 seedRecommendations，用于注册系统配置、跨域、拦截器或异常处理。
    private void seedRecommendations() {
        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        List<EnergyRecommendation> recommendations = new ArrayList<>();
        // 行注释：向列表中追加一项数据，用于表格、图表或返回结果。
        recommendations.add(recommendation(
                // 行注释：传入字符串参数 “efficiency”，作为当前方法调用的业务标识或显示文本。
                "efficiency",
                // 行注释：传入中文建议说明，用于节能管理页面展示和答辩讲解。
                "低效率设备建议每两周清洁和校准一次。",
                // 行注释：传入字符串参数 “high”，作为当前方法调用的业务标识或显示文本。
                "high",
                // 行注释：传入字符串参数 “32.50”，作为当前方法调用的业务标识或显示文本。
                "32.50",
                // 行注释：传入字符串参数 “pending”，作为当前方法调用的业务标识或显示文本。
                "pending"
        // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
        ));
        // 行注释：向列表中追加一项数据，用于表格、图表或返回结果。
        recommendations.add(recommendation(
                // 行注释：传入字符串参数 “utilization”，作为当前方法调用的业务标识或显示文本。
                "utilization",
                // 行注释：传入中文建议说明，用于节能管理页面展示和答辩讲解。
                "灌溉高峰前提前为储能设备充电，保持供能稳定。",
                // 行注释：传入字符串参数 “medium”，作为当前方法调用的业务标识或显示文本。
                "medium",
                // 行注释：传入字符串参数 “18.40”，作为当前方法调用的业务标识或显示文本。
                "18.40",
                // 行注释：传入字符串参数 “pending”，作为当前方法调用的业务标识或显示文本。
                "pending"
        // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
        ));
        // 行注释：向列表中追加一项数据，用于表格、图表或返回结果。
        recommendations.add(recommendation(
                // 行注释：传入字符串参数 “storage”，作为当前方法调用的业务标识或显示文本。
                "storage",
                // 行注释：传入中文建议说明，用于节能管理页面展示和答辩讲解。
                "扩展电池容量，并在夜间低谷时段放电。",
                // 行注释：传入字符串参数 “medium”，作为当前方法调用的业务标识或显示文本。
                "medium",
                // 行注释：传入字符串参数 “25.10”，作为当前方法调用的业务标识或显示文本。
                "25.10",
                // 行注释：传入字符串参数 “implemented”，作为当前方法调用的业务标识或显示文本。
                "implemented"
        // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
        ));
        // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
        recommendationRepository.saveAll(recommendations);
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： seedCarbonData
     * - 作用：支撑当前模块流程的业务辅助方法。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 seedCarbonData 方法，完成 DataSeeder 中对应的一步业务处理。
    // 方法用法：Spring Boot 启动时调用或识别 seedCarbonData，用于注册系统配置、跨域、拦截器或异常处理。
    private void seedCarbonData() {
        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        Random random = new Random(99);
        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        List<CarbonEmission> emissions = new ArrayList<>();

        // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
        for (int day = 0; day < 30; day++) {
            // 行注释：向列表中追加一项数据，用于表格、图表或返回结果。
            emissions.add(emission("solar", 45 + random.nextDouble() * 8, 0.05, day));
            // 行注释：向列表中追加一项数据，用于表格、图表或返回结果。
            emissions.add(emission("wind", 30 + random.nextDouble() * 7, 0.02, day));
            // 行注释：向列表中追加一项数据，用于表格、图表或返回结果。
            emissions.add(emission("biomass", 20 + random.nextDouble() * 6, 0.23, day));
            // 行注释：向列表中追加一项数据，用于表格、图表或返回结果。
            emissions.add(emission("grid", 12 + random.nextDouble() * 5, 0.85, day));
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
        carbonEmissionRepository.saveAll(emissions);
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： device
     * - 作用：支撑当前模块流程的业务辅助方法。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 device 方法，完成 DataSeeder 中对应的一步业务处理。
    // 方法用法：Spring Boot 启动时调用或识别 device，用于注册系统配置、跨域、拦截器或异常处理。
    private EnergyDevice device(String name, String type, String capacity, String location, LocalDate installationDate) {
        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        EnergyDevice device = new EnergyDevice();
        // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
        device.setDeviceName(name);
        // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
        device.setDeviceType(type);
        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        device.setCapacity(new BigDecimal(capacity));
        // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
        device.setLocation(location);
        // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
        device.setInstallationDate(installationDate);
        // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
        device.setStatus("active");
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return device;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： recommendation
     * - 作用：支撑当前模块流程的业务辅助方法。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：声明 recommendation 方法，承接当前模块的接口请求或内部业务处理。
    // 方法用法：Spring Boot 启动时调用或识别 recommendation，用于注册系统配置、跨域、拦截器或异常处理。
    private EnergyRecommendation recommendation(String type,
                                                // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                                                String description,
                                                // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                                                String priority,
                                                // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                                                String savings,
                                                // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                                                String status) {
        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        EnergyRecommendation recommendation = new EnergyRecommendation();
        // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
        recommendation.setRecommendationType(type);
        // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
        recommendation.setDescription(description);
        // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
        recommendation.setPriority(priority);
        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        recommendation.setPotentialSavings(new BigDecimal(savings));
        // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
        recommendation.setStatus(status);
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return recommendation;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： emission
     * - 作用：支撑当前模块流程的业务辅助方法。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 emission 方法，完成 DataSeeder 中对应的一步业务处理。
    // 方法用法：Spring Boot 启动时调用或识别 emission，用于注册系统配置、跨域、拦截器或异常处理。
    private CarbonEmission emission(String source, double amount, double factor, int day) {
        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        CarbonEmission emission = new CarbonEmission();
        // 行注释：声明变量 energy，保存本行计算或查询得到的结果，供后续逻辑使用。
        BigDecimal energy = scaled(amount);
        // 行注释：声明变量 emissionFactor，保存本行计算或查询得到的结果，供后续逻辑使用。
        BigDecimal emissionFactor = BigDecimal.valueOf(factor);
        // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
        emission.setEnergySource(source);
        // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
        emission.setEnergyAmount(energy);
        // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
        emission.setEmissionFactor(emissionFactor);
        // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
        emission.setCarbonEmission(energy.multiply(emissionFactor).setScale(2, RoundingMode.HALF_UP));
        // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
        emission.setTimestamp(LocalDateTime.now().minusDays(day).withHour(12).withMinute(0).withSecond(0).withNano(0));
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return emission;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    /**
     * 答辩讲解:
     * - 方法： scaled
     * - 作用：数值辅助方法，用于避免业务计算中的空值和精度问题。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 scaled 方法，完成 DataSeeder 中对应的一步业务处理。
    // 方法用法：Spring Boot 启动时调用或识别 scaled，用于注册系统配置、跨域、拦截器或异常处理。
    private BigDecimal scaled(double value) {
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP);
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }
// 行注释：结束当前代码块，表示这段逻辑处理完成。
}



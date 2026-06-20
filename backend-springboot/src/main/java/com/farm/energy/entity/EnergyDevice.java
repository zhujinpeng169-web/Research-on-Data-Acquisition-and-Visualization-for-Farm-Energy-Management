// 逐行注释标记：已按业务语义处理，仅增加中文注释，不改变程序逻辑
/**
 * 答辩注释:
 * - 文件： EnergyDevice
 * - 层级：实体层
 * - 职责：将 Java 对象映射到 MySQL 表，表示持久化领域模型。
 * - 此注释仅用于说明，不会改变运行行为。
 */
// 行注释：声明 EnergyDevice 所在的包路径，体现它在项目中的模块位置。
package com.farm.energy.entity;

// 行注释：引入当前文件要使用的类或注解，保证后续代码可以编译。
import jakarta.persistence.Column;
// 行注释：引入当前文件要使用的类或注解，保证后续代码可以编译。
import jakarta.persistence.Entity;
// 行注释：引入当前文件要使用的类或注解，保证后续代码可以编译。
import jakarta.persistence.GeneratedValue;
// 行注释：引入当前文件要使用的类或注解，保证后续代码可以编译。
import jakarta.persistence.GenerationType;
// 行注释：引入当前文件要使用的类或注解，保证后续代码可以编译。
import jakarta.persistence.Id;
// 行注释：引入当前文件要使用的类或注解，保证后续代码可以编译。
import jakarta.persistence.PrePersist;
// 行注释：引入当前文件要使用的类或注解，保证后续代码可以编译。
import jakarta.persistence.Table;

// 行注释：引入高精度数字类型，用于能源、碳排等小数计算。
import java.math.BigDecimal;
// 行注释：引入时间类型，用于记录日期、创建时间或预测日期。
import java.time.LocalDate;
// 行注释：引入时间类型，用于记录日期、创建时间或预测日期。
import java.time.LocalDateTime;

// 行注释：声明这是 JPA 实体类，对应数据库中的一张业务表。
@Entity
// 行注释：指定该实体映射到数据库表 energy_devices。
@Table(name = "energy_devices")
// 行注释：定义 EnergyDevice 实体，承载该模块的主要代码。
// 类注释：这是 能源监测 模块的实体类，负责把 Java 对象和 MySQL 数据表记录对应起来。
public class EnergyDevice {

    // 行注释：声明该字段是数据库主键，用来唯一标识一条记录。
    @Id
    // 行注释：声明主键由数据库自增生成，新增记录时无需手动填写。
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // 行注释：声明字段 id，保存当前对象需要的数据或依赖。
    private Long id;

    // 行注释：指定字段和数据库列的映射关系，列名是 device_name。
    @Column(name = "device_name", nullable = false)
    // 行注释：声明字段 deviceName，保存当前对象需要的数据或依赖。
    private String deviceName;

    // 行注释：指定字段和数据库列的映射关系，列名是 device_type。
    @Column(name = "device_type", nullable = false)
    // 行注释：声明字段 deviceType，保存当前对象需要的数据或依赖。
    private String deviceType;

    // 行注释：声明字段 capacity，保存当前对象需要的数据或依赖。
    private BigDecimal capacity;

    // 行注释：声明字段 location，保存当前对象需要的数据或依赖。
    private String location;

    // 行注释：指定字段和数据库列的映射关系，列名是 installation_date。
    @Column(name = "installation_date")
    // 行注释：声明字段 installationDate，保存当前对象需要的数据或依赖。
    private LocalDate installationDate;

    // 行注释：声明字段 status，保存当前对象需要的数据或依赖。
    private String status;

    // 行注释：指定字段和数据库列的映射关系，列名是 created_at。
    @Column(name = "created_at", updatable = false)
    // 行注释：声明字段 createdAt，保存当前对象需要的数据或依赖。
    private LocalDateTime createdAt;

    // 行注释：声明保存实体前自动执行该方法，用于补默认值。
    @PrePersist
    // 行注释：定义 onCreate 方法，完成 EnergyDevice 中对应的一步业务处理。
    // 方法用法：新增实体保存到数据库前自动调用，用来补充创建时间、默认状态等字段。
    public void onCreate() {
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (createdAt == null) {
            // 行注释：给变量 createdAt 赋值，更新当前业务流程中的临时状态。
            createdAt = LocalDateTime.now();
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (status == null || status.isBlank()) {
            // 行注释：给变量 status 赋值，更新当前业务流程中的临时状态。
            status = "active";
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：定义 getId 查询方法，给前端返回当前模块数据。
    // 方法用法：读取 id 字段时调用，常用于接口返回、业务判断或页面展示。
    public Long getId() {
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return id;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：定义 setId 赋值方法，把前端或数据库数据写入字段。
    // 方法用法：写入 id 字段时调用，常用于接收前端参数或组装数据库实体。
    public void setId(Long id) {
        // 行注释：把构造方法传入的 id 保存到成员变量，后续方法会继续调用它。
        this.id = id;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：定义 getDeviceName 查询方法，给前端返回当前模块数据。
    // 方法用法：读取 device name 字段时调用，常用于接口返回、业务判断或页面展示。
    public String getDeviceName() {
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return deviceName;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：定义 setDeviceName 赋值方法，把前端或数据库数据写入字段。
    // 方法用法：写入 device name 字段时调用，常用于接收前端参数或组装数据库实体。
    public void setDeviceName(String deviceName) {
        // 行注释：把构造方法传入的 deviceName 保存到成员变量，后续方法会继续调用它。
        this.deviceName = deviceName;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：定义 getDeviceType 查询方法，给前端返回当前模块数据。
    // 方法用法：读取 device type 字段时调用，常用于接口返回、业务判断或页面展示。
    public String getDeviceType() {
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return deviceType;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：定义 setDeviceType 赋值方法，把前端或数据库数据写入字段。
    // 方法用法：写入 device type 字段时调用，常用于接收前端参数或组装数据库实体。
    public void setDeviceType(String deviceType) {
        // 行注释：把构造方法传入的 deviceType 保存到成员变量，后续方法会继续调用它。
        this.deviceType = deviceType;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：定义 getCapacity 查询方法，给前端返回当前模块数据。
    // 方法用法：读取 capacity 字段时调用，常用于接口返回、业务判断或页面展示。
    public BigDecimal getCapacity() {
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return capacity;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：定义 setCapacity 赋值方法，把前端或数据库数据写入字段。
    // 方法用法：写入 capacity 字段时调用，常用于接收前端参数或组装数据库实体。
    public void setCapacity(BigDecimal capacity) {
        // 行注释：把构造方法传入的 capacity 保存到成员变量，后续方法会继续调用它。
        this.capacity = capacity;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：定义 getLocation 查询方法，给前端返回当前模块数据。
    // 方法用法：读取 location 字段时调用，常用于接口返回、业务判断或页面展示。
    public String getLocation() {
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return location;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：定义 setLocation 赋值方法，把前端或数据库数据写入字段。
    // 方法用法：写入 location 字段时调用，常用于接收前端参数或组装数据库实体。
    public void setLocation(String location) {
        // 行注释：把构造方法传入的 location 保存到成员变量，后续方法会继续调用它。
        this.location = location;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：定义 getInstallationDate 查询方法，给前端返回当前模块数据。
    // 方法用法：读取 installation date 字段时调用，常用于接口返回、业务判断或页面展示。
    public LocalDate getInstallationDate() {
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return installationDate;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：定义 setInstallationDate 赋值方法，把前端或数据库数据写入字段。
    // 方法用法：写入 installation date 字段时调用，常用于接收前端参数或组装数据库实体。
    public void setInstallationDate(LocalDate installationDate) {
        // 行注释：把构造方法传入的 installationDate 保存到成员变量，后续方法会继续调用它。
        this.installationDate = installationDate;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：定义 getStatus 查询方法，给前端返回当前模块数据。
    // 方法用法：读取 status 字段时调用，常用于接口返回、业务判断或页面展示。
    public String getStatus() {
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return status;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：定义 setStatus 赋值方法，把前端或数据库数据写入字段。
    // 方法用法：写入 status 字段时调用，常用于接收前端参数或组装数据库实体。
    public void setStatus(String status) {
        // 行注释：把构造方法传入的 status 保存到成员变量，后续方法会继续调用它。
        this.status = status;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：定义 getCreatedAt 查询方法，给前端返回当前模块数据。
    // 方法用法：读取 created at 字段时调用，常用于接口返回、业务判断或页面展示。
    public LocalDateTime getCreatedAt() {
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return createdAt;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：定义 setCreatedAt 赋值方法，把前端或数据库数据写入字段。
    // 方法用法：写入 created at 字段时调用，常用于接收前端参数或组装数据库实体。
    public void setCreatedAt(LocalDateTime createdAt) {
        // 行注释：把构造方法传入的 createdAt 保存到成员变量，后续方法会继续调用它。
        this.createdAt = createdAt;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }
// 行注释：结束当前代码块，表示这段逻辑处理完成。
}



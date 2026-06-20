// 逐行注释标记：已按业务语义处理，仅增加中文注释，不改变程序逻辑
/**
 * 答辩注释:
 * - 文件： CarbonEmission
 * - 层级：实体层
 * - 职责：将 Java 对象映射到 MySQL 表，表示持久化领域模型。
 * - 此注释仅用于说明，不会改变运行行为。
 */
// 行注释：声明 CarbonEmission 所在的包路径，体现它在项目中的模块位置。
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
import java.time.LocalDateTime;

// 行注释：声明这是 JPA 实体类，对应数据库中的一张业务表。
@Entity
// 行注释：指定该实体映射到数据库表 carbon_emissions。
@Table(name = "carbon_emissions")
// 行注释：定义 CarbonEmission 实体，承载该模块的主要代码。
// 类注释：这是 碳排放管理 模块的实体类，负责把 Java 对象和 MySQL 数据表记录对应起来。
public class CarbonEmission {

    // 行注释：声明该字段是数据库主键，用来唯一标识一条记录。
    @Id
    // 行注释：声明主键由数据库自增生成，新增记录时无需手动填写。
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // 行注释：声明字段 id，保存当前对象需要的数据或依赖。
    private Long id;

    // 行注释：声明字段 timestamp，保存当前对象需要的数据或依赖。
    private LocalDateTime timestamp;

    // 行注释：指定字段和数据库列的映射关系，列名是 energy_source。
    @Column(name = "energy_source")
    // 行注释：声明字段 energySource，保存当前对象需要的数据或依赖。
    private String energySource;

    // 行注释：指定字段和数据库列的映射关系，列名是 energy_amount。
    @Column(name = "energy_amount")
    // 行注释：声明字段 energyAmount，保存当前对象需要的数据或依赖。
    private BigDecimal energyAmount;

    // 行注释：指定字段和数据库列的映射关系，列名是 carbon_emission。
    @Column(name = "carbon_emission")
    // 行注释：声明字段 carbonEmission，保存当前对象需要的数据或依赖。
    private BigDecimal carbonEmission;

    // 行注释：指定字段和数据库列的映射关系，列名是 emission_factor。
    @Column(name = "emission_factor")
    // 行注释：声明字段 emissionFactor，保存当前对象需要的数据或依赖。
    private BigDecimal emissionFactor;

    // 行注释：声明保存实体前自动执行该方法，用于补默认值。
    @PrePersist
    // 行注释：定义 onCreate 方法，完成 CarbonEmission 中对应的一步业务处理。
    // 方法用法：新增实体保存到数据库前自动调用，用来补充创建时间、默认状态等字段。
    public void onCreate() {
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (timestamp == null) {
            // 行注释：给变量 timestamp 赋值，更新当前业务流程中的临时状态。
            timestamp = LocalDateTime.now();
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

    // 行注释：定义 getTimestamp 查询方法，给前端返回当前模块数据。
    // 方法用法：读取 timestamp 字段时调用，常用于接口返回、业务判断或页面展示。
    public LocalDateTime getTimestamp() {
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return timestamp;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：定义 setTimestamp 赋值方法，把前端或数据库数据写入字段。
    // 方法用法：写入 timestamp 字段时调用，常用于接收前端参数或组装数据库实体。
    public void setTimestamp(LocalDateTime timestamp) {
        // 行注释：把构造方法传入的 timestamp 保存到成员变量，后续方法会继续调用它。
        this.timestamp = timestamp;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：定义 getEnergySource 查询方法，给前端返回当前模块数据。
    // 方法用法：读取 energy source 字段时调用，常用于接口返回、业务判断或页面展示。
    public String getEnergySource() {
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return energySource;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：定义 setEnergySource 赋值方法，把前端或数据库数据写入字段。
    // 方法用法：写入 energy source 字段时调用，常用于接收前端参数或组装数据库实体。
    public void setEnergySource(String energySource) {
        // 行注释：把构造方法传入的 energySource 保存到成员变量，后续方法会继续调用它。
        this.energySource = energySource;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：定义 getEnergyAmount 查询方法，给前端返回当前模块数据。
    // 方法用法：读取 energy amount 字段时调用，常用于接口返回、业务判断或页面展示。
    public BigDecimal getEnergyAmount() {
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return energyAmount;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：定义 setEnergyAmount 赋值方法，把前端或数据库数据写入字段。
    // 方法用法：写入 energy amount 字段时调用，常用于接收前端参数或组装数据库实体。
    public void setEnergyAmount(BigDecimal energyAmount) {
        // 行注释：把构造方法传入的 energyAmount 保存到成员变量，后续方法会继续调用它。
        this.energyAmount = energyAmount;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：定义 getCarbonEmission 查询方法，给前端返回当前模块数据。
    // 方法用法：读取 carbon emission 字段时调用，常用于接口返回、业务判断或页面展示。
    public BigDecimal getCarbonEmission() {
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return carbonEmission;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：定义 setCarbonEmission 赋值方法，把前端或数据库数据写入字段。
    // 方法用法：写入 carbon emission 字段时调用，常用于接收前端参数或组装数据库实体。
    public void setCarbonEmission(BigDecimal carbonEmission) {
        // 行注释：把构造方法传入的 carbonEmission 保存到成员变量，后续方法会继续调用它。
        this.carbonEmission = carbonEmission;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：定义 getEmissionFactor 查询方法，给前端返回当前模块数据。
    // 方法用法：读取 emission factor 字段时调用，常用于接口返回、业务判断或页面展示。
    public BigDecimal getEmissionFactor() {
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return emissionFactor;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：定义 setEmissionFactor 赋值方法，把前端或数据库数据写入字段。
    // 方法用法：写入 emission factor 字段时调用，常用于接收前端参数或组装数据库实体。
    public void setEmissionFactor(BigDecimal emissionFactor) {
        // 行注释：把构造方法传入的 emissionFactor 保存到成员变量，后续方法会继续调用它。
        this.emissionFactor = emissionFactor;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }
// 行注释：结束当前代码块，表示这段逻辑处理完成。
}



// 逐行注释标记：已按业务语义处理，仅增加中文注释，不改变程序逻辑
/**
 * 答辩注释:
 * - 文件： EnergyForecast
 * - 层级：实体层
 * - 职责：将 Java 对象映射到 MySQL 表，表示持久化领域模型。
 * - 此注释仅用于说明，不会改变运行行为。
 */
// 行注释：声明 EnergyForecast 所在的包路径，体现它在项目中的模块位置。
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
// 行注释：指定该实体映射到数据库表 energy_forecasts。
@Table(name = "energy_forecasts")
// 行注释：定义 EnergyForecast 实体，承载该模块的主要代码。
// 类注释：这是 能源预测 模块的实体类，负责把 Java 对象和 MySQL 数据表记录对应起来。
public class EnergyForecast {

    // 行注释：声明该字段是数据库主键，用来唯一标识一条记录。
    @Id
    // 行注释：声明主键由数据库自增生成，新增记录时无需手动填写。
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // 行注释：声明字段 id，保存当前对象需要的数据或依赖。
    private Long id;

    // 行注释：指定字段和数据库列的映射关系，列名是 forecast_date。
    @Column(name = "forecast_date")
    // 行注释：声明字段 forecastDate，保存当前对象需要的数据或依赖。
    private LocalDate forecastDate;

    // 行注释：指定字段和数据库列的映射关系，列名是 forecast_type。
    @Column(name = "forecast_type")
    // 行注释：声明字段 forecastType，保存当前对象需要的数据或依赖。
    private String forecastType;

    // 行注释：指定字段和数据库列的映射关系，列名是 predicted_generation。
    @Column(name = "predicted_generation")
    // 行注释：声明字段 predictedGeneration，保存当前对象需要的数据或依赖。
    private BigDecimal predictedGeneration;

    // 行注释：指定字段和数据库列的映射关系，列名是 predicted_consumption。
    @Column(name = "predicted_consumption")
    // 行注释：声明字段 predictedConsumption，保存当前对象需要的数据或依赖。
    private BigDecimal predictedConsumption;

    // 行注释：指定字段和数据库列的映射关系，列名是 confidence_level。
    @Column(name = "confidence_level")
    // 行注释：声明字段 confidenceLevel，保存当前对象需要的数据或依赖。
    private BigDecimal confidenceLevel;

    // 行注释：指定字段和数据库列的映射关系，列名是 created_at。
    @Column(name = "created_at")
    // 行注释：声明字段 createdAt，保存当前对象需要的数据或依赖。
    private LocalDateTime createdAt;

    // 行注释：声明保存实体前自动执行该方法，用于补默认值。
    @PrePersist
    // 行注释：定义 onCreate 方法，完成 EnergyForecast 中对应的一步业务处理。
    // 方法用法：新增实体保存到数据库前自动调用，用来补充创建时间、默认状态等字段。
    public void onCreate() {
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (createdAt == null) {
            // 行注释：给变量 createdAt 赋值，更新当前业务流程中的临时状态。
            createdAt = LocalDateTime.now();
        // 行注释：结束当前代码块，表示这段逻辑处理完成。
        }
        // 行注释：根据当前条件判断下一步流程，例如鉴权、参数校验或异常处理。
        if (forecastType == null || forecastType.isBlank()) {
            // 行注释：给变量 forecastType 赋值，更新当前业务流程中的临时状态。
            forecastType = "daily";
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

    // 行注释：定义 getForecastDate 查询方法，给前端返回当前模块数据。
    // 方法用法：读取 forecast date 字段时调用，常用于接口返回、业务判断或页面展示。
    public LocalDate getForecastDate() {
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return forecastDate;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：定义 setForecastDate 赋值方法，把前端或数据库数据写入字段。
    // 方法用法：写入 forecast date 字段时调用，常用于接收前端参数或组装数据库实体。
    public void setForecastDate(LocalDate forecastDate) {
        // 行注释：把构造方法传入的 forecastDate 保存到成员变量，后续方法会继续调用它。
        this.forecastDate = forecastDate;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：定义 getForecastType 查询方法，给前端返回当前模块数据。
    // 方法用法：读取 forecast type 字段时调用，常用于接口返回、业务判断或页面展示。
    public String getForecastType() {
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return forecastType;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：定义 setForecastType 赋值方法，把前端或数据库数据写入字段。
    // 方法用法：写入 forecast type 字段时调用，常用于接收前端参数或组装数据库实体。
    public void setForecastType(String forecastType) {
        // 行注释：把构造方法传入的 forecastType 保存到成员变量，后续方法会继续调用它。
        this.forecastType = forecastType;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：定义 getPredictedGeneration 查询方法，给前端返回当前模块数据。
    // 方法用法：读取 predicted generation 字段时调用，常用于接口返回、业务判断或页面展示。
    public BigDecimal getPredictedGeneration() {
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return predictedGeneration;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：定义 setPredictedGeneration 赋值方法，把前端或数据库数据写入字段。
    // 方法用法：写入 predicted generation 字段时调用，常用于接收前端参数或组装数据库实体。
    public void setPredictedGeneration(BigDecimal predictedGeneration) {
        // 行注释：把构造方法传入的 predictedGeneration 保存到成员变量，后续方法会继续调用它。
        this.predictedGeneration = predictedGeneration;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：定义 getPredictedConsumption 查询方法，给前端返回当前模块数据。
    // 方法用法：读取 predicted consumption 字段时调用，常用于接口返回、业务判断或页面展示。
    public BigDecimal getPredictedConsumption() {
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return predictedConsumption;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：定义 setPredictedConsumption 赋值方法，把前端或数据库数据写入字段。
    // 方法用法：写入 predicted consumption 字段时调用，常用于接收前端参数或组装数据库实体。
    public void setPredictedConsumption(BigDecimal predictedConsumption) {
        // 行注释：把构造方法传入的 predictedConsumption 保存到成员变量，后续方法会继续调用它。
        this.predictedConsumption = predictedConsumption;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：定义 getConfidenceLevel 查询方法，给前端返回当前模块数据。
    // 方法用法：读取 confidence level 字段时调用，常用于接口返回、业务判断或页面展示。
    public BigDecimal getConfidenceLevel() {
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return confidenceLevel;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：定义 setConfidenceLevel 赋值方法，把前端或数据库数据写入字段。
    // 方法用法：写入 confidence level 字段时调用，常用于接收前端参数或组装数据库实体。
    public void setConfidenceLevel(BigDecimal confidenceLevel) {
        // 行注释：把构造方法传入的 confidenceLevel 保存到成员变量，后续方法会继续调用它。
        this.confidenceLevel = confidenceLevel;
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



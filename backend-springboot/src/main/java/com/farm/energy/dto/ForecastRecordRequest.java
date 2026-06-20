// 逐行注释标记：已按业务语义处理，仅增加中文注释，不改变程序逻辑
/**
 * 答辩注释:
 * - 文件： ForecastRecordRequest
 * - 层级：DTO 层
 * - 职责：定义接口请求/响应的数据传输结构，用于参数绑定。
 * - 此注释仅用于说明，不会改变运行行为。
 */
// 行注释：声明 ForecastRecordRequest 所在的包路径，体现它在项目中的模块位置。
package com.farm.energy.dto;

// 行注释：引入当前文件要使用的类或注解，保证后续代码可以编译。
import com.fasterxml.jackson.annotation.JsonAlias;

// 行注释：引入高精度数字类型，用于能源、碳排等小数计算。
import java.math.BigDecimal;
// 行注释：引入时间类型，用于记录日期、创建时间或预测日期。
import java.time.LocalDate;

// 行注释：定义 ForecastRecordRequest 请求参数对象，承载该模块的主要代码。
// 类注释：这是 能源预测 模块的数据传输类，负责承载前端提交的请求参数，供 Controller 和 Service 使用。
public class ForecastRecordRequest {
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    @JsonAlias("forecast_date")
    // 行注释：声明字段 forecastDate，保存当前对象需要的数据或依赖。
    private LocalDate forecastDate;

    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    @JsonAlias("forecast_type")
    // 行注释：声明字段 forecastType，保存当前对象需要的数据或依赖。
    private String forecastType;

    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    @JsonAlias("predicted_generation")
    // 行注释：声明字段 predictedGeneration，保存当前对象需要的数据或依赖。
    private BigDecimal predictedGeneration;

    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    @JsonAlias("predicted_consumption")
    // 行注释：声明字段 predictedConsumption，保存当前对象需要的数据或依赖。
    private BigDecimal predictedConsumption;

    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    @JsonAlias("confidence_level")
    // 行注释：声明字段 confidenceLevel，保存当前对象需要的数据或依赖。
    private BigDecimal confidenceLevel;

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
// 行注释：结束当前代码块，表示这段逻辑处理完成。
}




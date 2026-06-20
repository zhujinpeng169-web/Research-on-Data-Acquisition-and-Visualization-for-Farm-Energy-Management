// 逐行注释标记：已按业务语义处理，仅增加中文注释，不改变程序逻辑
/**
 * 答辩注释:
 * - 文件： MonitoringDataRequest
 * - 层级：DTO 层
 * - 职责：定义接口请求/响应的数据传输结构，用于参数绑定。
 * - 此注释仅用于说明，不会改变运行行为。
 */
// 行注释：声明 MonitoringDataRequest 所在的包路径，体现它在项目中的模块位置。
package com.farm.energy.dto;

// 行注释：引入当前文件要使用的类或注解，保证后续代码可以编译。
import com.fasterxml.jackson.annotation.JsonAlias;

// 行注释：引入高精度数字类型，用于能源、碳排等小数计算。
import java.math.BigDecimal;

// 行注释：定义 MonitoringDataRequest 请求参数对象，承载该模块的主要代码。
// 类注释：这是 能源监测 模块的数据传输类，负责承载前端提交的请求参数，供 Controller 和 Service 使用。
public class MonitoringDataRequest {
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    @JsonAlias("device_id")
    // 行注释：声明字段 deviceId，保存当前对象需要的数据或依赖。
    private Long deviceId;

    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    @JsonAlias("energy_generated")
    // 行注释：声明字段 energyGenerated，保存当前对象需要的数据或依赖。
    private BigDecimal energyGenerated;

    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    @JsonAlias("energy_consumed")
    // 行注释：声明字段 energyConsumed，保存当前对象需要的数据或依赖。
    private BigDecimal energyConsumed;

    // 行注释：声明字段 efficiency，保存当前对象需要的数据或依赖。
    private BigDecimal efficiency;
    // 行注释：声明字段 temperature，保存当前对象需要的数据或依赖。
    private BigDecimal temperature;
    // 行注释：声明字段 humidity，保存当前对象需要的数据或依赖。
    private BigDecimal humidity;

    // 行注释：定义 getDeviceId 查询方法，给前端返回当前模块数据。
    // 方法用法：读取 device id 字段时调用，常用于接口返回、业务判断或页面展示。
    public Long getDeviceId() {
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return deviceId;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：定义 setDeviceId 赋值方法，把前端或数据库数据写入字段。
    // 方法用法：写入 device id 字段时调用，常用于接收前端参数或组装数据库实体。
    public void setDeviceId(Long deviceId) {
        // 行注释：把构造方法传入的 deviceId 保存到成员变量，后续方法会继续调用它。
        this.deviceId = deviceId;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：定义 getEnergyGenerated 查询方法，给前端返回当前模块数据。
    // 方法用法：读取 energy generated 字段时调用，常用于接口返回、业务判断或页面展示。
    public BigDecimal getEnergyGenerated() {
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return energyGenerated;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：定义 setEnergyGenerated 赋值方法，把前端或数据库数据写入字段。
    // 方法用法：写入 energy generated 字段时调用，常用于接收前端参数或组装数据库实体。
    public void setEnergyGenerated(BigDecimal energyGenerated) {
        // 行注释：把构造方法传入的 energyGenerated 保存到成员变量，后续方法会继续调用它。
        this.energyGenerated = energyGenerated;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：定义 getEnergyConsumed 查询方法，给前端返回当前模块数据。
    // 方法用法：读取 energy consumed 字段时调用，常用于接口返回、业务判断或页面展示。
    public BigDecimal getEnergyConsumed() {
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return energyConsumed;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：定义 setEnergyConsumed 赋值方法，把前端或数据库数据写入字段。
    // 方法用法：写入 energy consumed 字段时调用，常用于接收前端参数或组装数据库实体。
    public void setEnergyConsumed(BigDecimal energyConsumed) {
        // 行注释：把构造方法传入的 energyConsumed 保存到成员变量，后续方法会继续调用它。
        this.energyConsumed = energyConsumed;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：定义 getEfficiency 查询方法，给前端返回当前模块数据。
    // 方法用法：读取 efficiency 字段时调用，常用于接口返回、业务判断或页面展示。
    public BigDecimal getEfficiency() {
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return efficiency;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：定义 setEfficiency 赋值方法，把前端或数据库数据写入字段。
    // 方法用法：写入 efficiency 字段时调用，常用于接收前端参数或组装数据库实体。
    public void setEfficiency(BigDecimal efficiency) {
        // 行注释：把构造方法传入的 efficiency 保存到成员变量，后续方法会继续调用它。
        this.efficiency = efficiency;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：定义 getTemperature 查询方法，给前端返回当前模块数据。
    // 方法用法：读取 temperature 字段时调用，常用于接口返回、业务判断或页面展示。
    public BigDecimal getTemperature() {
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return temperature;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：定义 setTemperature 赋值方法，把前端或数据库数据写入字段。
    // 方法用法：写入 temperature 字段时调用，常用于接收前端参数或组装数据库实体。
    public void setTemperature(BigDecimal temperature) {
        // 行注释：把构造方法传入的 temperature 保存到成员变量，后续方法会继续调用它。
        this.temperature = temperature;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：定义 getHumidity 查询方法，给前端返回当前模块数据。
    // 方法用法：读取 humidity 字段时调用，常用于接口返回、业务判断或页面展示。
    public BigDecimal getHumidity() {
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return humidity;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：定义 setHumidity 赋值方法，把前端或数据库数据写入字段。
    // 方法用法：写入 humidity 字段时调用，常用于接收前端参数或组装数据库实体。
    public void setHumidity(BigDecimal humidity) {
        // 行注释：把构造方法传入的 humidity 保存到成员变量，后续方法会继续调用它。
        this.humidity = humidity;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }
// 行注释：结束当前代码块，表示这段逻辑处理完成。
}



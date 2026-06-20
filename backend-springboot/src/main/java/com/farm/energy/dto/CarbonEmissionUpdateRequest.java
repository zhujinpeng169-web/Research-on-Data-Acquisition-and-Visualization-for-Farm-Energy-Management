// 逐行注释标记：已按业务语义处理，仅增加中文注释，不改变程序逻辑
/**
 * 答辩注释:
 * - 文件： CarbonEmissionUpdateRequest
 * - 层级：DTO 层
 * - 职责：定义接口请求/响应的数据传输结构，用于参数绑定。
 * - 此注释仅用于说明，不会改变运行行为。
 */
// 行注释：声明 CarbonEmissionUpdateRequest 所在的包路径，体现它在项目中的模块位置。
package com.farm.energy.dto;

// 行注释：引入当前文件要使用的类或注解，保证后续代码可以编译。
import com.fasterxml.jackson.annotation.JsonAlias;

// 行注释：引入高精度数字类型，用于能源、碳排等小数计算。
import java.math.BigDecimal;
// 行注释：引入时间类型，用于记录日期、创建时间或预测日期。
import java.time.LocalDateTime;

// 行注释：定义 CarbonEmissionUpdateRequest 请求参数对象，承载该模块的主要代码。
// 类注释：这是 碳排放管理 模块的数据传输类，负责承载前端提交的请求参数，供 Controller 和 Service 使用。
public class CarbonEmissionUpdateRequest {
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    @JsonAlias("energy_source")
    // 行注释：声明字段 energySource，保存当前对象需要的数据或依赖。
    private String energySource;

    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    @JsonAlias("energy_amount")
    // 行注释：声明字段 energyAmount，保存当前对象需要的数据或依赖。
    private BigDecimal energyAmount;

    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    @JsonAlias("emission_factor")
    // 行注释：声明字段 emissionFactor，保存当前对象需要的数据或依赖。
    private BigDecimal emissionFactor;

    // 行注释：声明字段 timestamp，保存当前对象需要的数据或依赖。
    private LocalDateTime timestamp;

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
// 行注释：结束当前代码块，表示这段逻辑处理完成。
}




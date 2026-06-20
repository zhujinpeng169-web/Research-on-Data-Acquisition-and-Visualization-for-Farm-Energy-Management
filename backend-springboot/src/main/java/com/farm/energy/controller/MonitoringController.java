// 逐行注释标记：已按业务语义处理，仅增加中文注释，不改变程序逻辑
/**
 * 答辩注释:
 * - 文件： MonitoringController
 * - 层级：控制器层
 * - 职责：暴露 REST 接口，接收请求参数，执行角色校验，并调用服务层。
 * - 此注释仅用于说明，不会改变运行行为。
 */
// 行注释：声明 MonitoringController 所在的包路径，体现它在项目中的模块位置。
package com.farm.energy.controller;

// 行注释：引入前端请求参数对象，便于 Spring 自动接收表单或 JSON 数据。
import com.farm.energy.dto.DeviceCreateRequest;
// 行注释：引入前端请求参数对象，便于 Spring 自动接收表单或 JSON 数据。
import com.farm.energy.dto.DeviceUpdateRequest;
// 行注释：引入前端请求参数对象，便于 Spring 自动接收表单或 JSON 数据。
import com.farm.energy.dto.MonitoringDataRequest;
// 行注释：引入前端请求参数对象，便于 Spring 自动接收表单或 JSON 数据。
import com.farm.energy.dto.MonitoringDataUpdateRequest;
// 行注释：引入鉴权上下文或安全工具，用于识别当前登录用户。
import com.farm.energy.security.AuthContext;
// 行注释：引入业务服务类，把具体业务处理交给服务层完成。
import com.farm.energy.service.MonitoringService;
// 行注释：引入业务服务类，把具体业务处理交给服务层完成。
import com.farm.energy.service.OperationLogService;
// 行注释：引入请求对象，用于读取请求头、路径和登录上下文。
import jakarta.servlet.http.HttpServletRequest;
// 行注释：引入 DELETE 接口注解，用于声明删除类接口地址。
import org.springframework.web.bind.annotation.DeleteMapping;
// 行注释：引入 GET 接口注解，用于声明查询类接口地址。
import org.springframework.web.bind.annotation.GetMapping;
// 行注释：引入当前文件要使用的类或注解，保证后续代码可以编译。
import org.springframework.web.bind.annotation.PathVariable;
// 行注释：引入 POST 接口注解，用于声明提交类接口地址。
import org.springframework.web.bind.annotation.PostMapping;
// 行注释：引入 PUT 接口注解，用于声明更新类接口地址。
import org.springframework.web.bind.annotation.PutMapping;
// 行注释：引入请求体注解，用于把前端 JSON 转成 Java 对象。
import org.springframework.web.bind.annotation.RequestBody;
// 行注释：引入路由前缀注解，用于统一设置接口地址。
import org.springframework.web.bind.annotation.RequestMapping;
// 行注释：引入当前文件要使用的类或注解，保证后续代码可以编译。
import org.springframework.web.bind.annotation.RequestParam;
// 行注释：引入 REST 控制器注解，让接口直接返回 JSON。
import org.springframework.web.bind.annotation.RestController;

// 行注释：引入 Map 结构，用于组装接口返回的键值数据。
import java.util.LinkedHashMap;
// 行注释：引入 Map 结构，用于组装接口返回的键值数据。
import java.util.Map;

// 行注释：声明这是 REST 接口控制器，返回 JSON 数据给前端。
@RestController
// 行注释：给当前控制器统一添加接口前缀 /api/monitoring，前端请求都会以它开头。
@RequestMapping("/api/monitoring")
// 行注释：定义 MonitoringController 控制器，承载该模块的主要代码。
// 类注释：这是 能源监测 模块的控制器类，负责接收前端 HTTP 请求、调用 Service，并把结果以 JSON 返回。
public class MonitoringController {

    // 行注释：声明字段 monitoringService，保存当前对象需要的数据或依赖。
    private final MonitoringService monitoringService;
    // 行注释：注入操作日志服务，用于记录登录、修改、删除等关键行为。
    private final OperationLogService operationLogService;

    // 行注释：声明 MonitoringController 方法，承接当前模块的接口请求或内部业务处理。
    // 方法用法：Spring 创建 MonitoringController 对象时调用这个构造方法，用来注入本类需要的依赖。
    public MonitoringController(MonitoringService monitoringService, OperationLogService operationLogService) {
        // 行注释：把构造方法传入的 monitoringService 保存到成员变量，后续方法会继续调用它。
        this.monitoringService = monitoringService;
        // 行注释：把构造方法传入的 operationLogService 保存到成员变量，后续方法会继续调用它。
        this.operationLogService = operationLogService;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：声明 GET 查询接口 /devices，用于前端读取数据。
    @GetMapping("/devices")
    /**
     * 答辩讲解:
     * - 方法： getDevices
     * - 作用：加载并返回前端视图所需的模块数据。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 getDevices 查询方法，给前端返回当前模块数据。
    // 方法用法：前端查询 Monitoring 模块数据时调用，用来读取数据库并返回 JSON。
    public Map<String, Object> getDevices(@RequestParam(name = "include_inactive", defaultValue = "false") boolean includeInactive) {
        // 行注释：组装接口返回数据，Spring 会自动转换成 JSON。
        return Map.of("devices", monitoringService.listDevices(includeInactive));
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：声明 GET 查询接口 /devices/{id}，用于前端读取数据。
    @GetMapping("/devices/{id}")
    /**
     * 答辩讲解:
     * - 方法： getDevice
     * - 作用：加载并返回前端视图所需的模块数据。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 getDevice 查询方法，给前端返回当前模块数据。
    // 方法用法：前端查询 Monitoring 模块数据时调用，用来读取数据库并返回 JSON。
    public Map<String, Object> getDevice(@PathVariable Long id) {
        // 行注释：组装接口返回数据，Spring 会自动转换成 JSON。
        return Map.of("device", monitoringService.getDevice(id));
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：声明设备新增接口 /devices，用于前端提交新的能源设备。
    @PostMapping("/devices")
    /**
     * 答辩讲解:
     * - 方法： createDevice
     * - 作用：校验请求参数并持久化新增业务记录。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 createDevice 新增方法，把前端提交的数据保存到数据库。
    // 方法用法：前端点击新增并提交表单时调用，用来创建 Monitoring 模块的新记录。
    public Map<String, Object> createDevice(HttpServletRequest request, @RequestBody DeviceCreateRequest body) {
        // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
        AuthContext.requireRole(request, "manager", "admin");
        // 行注释：声明变量 device，保存本行计算或查询得到的结果，供后续逻辑使用。
        Map<String, Object> device = monitoringService.createDevice(body);
        // 行注释：记录本次业务操作，方便后台审计和答辩说明安全性。
        operationLogService.log(
                // 行注释：传入字符串参数 “monitoring_device”，作为当前方法调用的业务标识或显示文本。
                "monitoring_device",
                // 行注释：传入字符串参数 “create”，作为当前方法调用的业务标识或显示文本。
                "create",
                // 行注释：发起接口请求或读取对象数据，获取页面需要的业务信息。
                String.valueOf(device.get("id")),
                // 行注释：从请求上下文中取出当前用户名，用于操作日志记录。
                AuthContext.username(request),
                // 行注释：从请求上下文中取出当前用户角色，用于权限判断或日志记录。
                AuthContext.role(request),
                // 行注释：发起接口请求或读取对象数据，获取页面需要的业务信息。
                "Create device " + device.get("device_name")
        // 行注释：结束当前方法调用，把上面准备好的参数一次性提交执行。
        );
        // 行注释：组装接口返回数据，Spring 会自动转换成 JSON。
        return Map.of("device", device, "message", "Device created");
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：声明 PUT 更新接口 /devices/{id}，用于前端修改已有数据。
    @PutMapping("/devices/{id}")
    /**
     * 答辩讲解:
     * - 方法： updateDevice
     * - 作用：在输入规范化后，对现有记录执行部分字段更新。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：声明 updateDevice 方法，承接当前模块的接口请求或内部业务处理。
    // 方法用法：前端编辑已有记录并保存时调用，用来更新 Monitoring 模块的数据。
    public Map<String, Object> updateDevice(HttpServletRequest request,
                                            // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                                            @PathVariable Long id,
                                            // 行注释：说明该参数来自前端 JSON 请求体，Spring 会自动封装成对象。
                                            @RequestBody DeviceUpdateRequest body) {
        // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
        AuthContext.requireRole(request, "manager", "admin");
        // 行注释：声明变量 device，保存本行计算或查询得到的结果，供后续逻辑使用。
        Map<String, Object> device = monitoringService.updateDevice(id, body);
        // 行注释：记录本次业务操作，方便后台审计和答辩说明安全性。
        operationLogService.log(
                // 行注释：传入字符串参数 “monitoring_device”，作为当前方法调用的业务标识或显示文本。
                "monitoring_device",
                // 行注释：传入字符串参数 “update”，作为当前方法调用的业务标识或显示文本。
                "update",
                // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                String.valueOf(id),
                // 行注释：从请求上下文中取出当前用户名，用于操作日志记录。
                AuthContext.username(request),
                // 行注释：从请求上下文中取出当前用户角色，用于权限判断或日志记录。
                AuthContext.role(request),
                // 行注释：发起接口请求或读取对象数据，获取页面需要的业务信息。
                "Update device " + device.get("device_name")
        // 行注释：结束当前方法调用，把上面准备好的参数一次性提交执行。
        );
        // 行注释：组装接口返回数据，Spring 会自动转换成 JSON。
        return Map.of("device", device, "message", "Device updated");
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：声明 DELETE 删除接口 /devices/{id}，用于前端删除指定记录。
    @DeleteMapping("/devices/{id}")
    /**
     * 答辩讲解:
     * - 方法： deleteDevice
     * - 作用：删除或逻辑移除目标记录，并维护数据一致性。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 deleteDevice 删除方法，删除前端指定的数据记录。
    // 方法用法：前端确认删除时调用，用来删除 Monitoring 模块的指定记录。
    public Map<String, Object> deleteDevice(HttpServletRequest request, @PathVariable Long id) {
        // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
        AuthContext.requireRole(request, "manager", "admin");
        // 行注释：调用 JPA 删除数据或清理过期记录。
        monitoringService.deleteDevice(id);
        // 行注释：记录本次业务操作，方便后台审计和答辩说明安全性。
        operationLogService.log(
                // 行注释：传入字符串参数 “monitoring_device”，作为当前方法调用的业务标识或显示文本。
                "monitoring_device",
                // 行注释：传入字符串参数 “delete”，作为当前方法调用的业务标识或显示文本。
                "delete",
                // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                String.valueOf(id),
                // 行注释：从请求上下文中取出当前用户名，用于操作日志记录。
                AuthContext.username(request),
                // 行注释：从请求上下文中取出当前用户角色，用于权限判断或日志记录。
                AuthContext.role(request),
                // 行注释：传入字符串参数 “Delete device”，作为当前方法调用的业务标识或显示文本。
                "Delete device"
        // 行注释：结束当前方法调用，把上面准备好的参数一次性提交执行。
        );
        // 行注释：组装接口返回数据，Spring 会自动转换成 JSON。
        return Map.of("message", "Device deleted");
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：声明 GET 查询接口 /data，用于前端读取数据。
    @GetMapping("/data")
    /**
     * 答辩讲解:
     * - 方法： listData
     * - 作用：查询并返回集合数据，用于界面表格/图表渲染。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：声明 listData 方法，承接当前模块的接口请求或内部业务处理。
    // 方法用法：前端打开列表页或刷新表格时调用，用来返回 Monitoring 模块的列表数据。
    public Map<String, Object> listData(@RequestParam(name = "device_id", required = false) Long deviceId,
                                        // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                                        @RequestParam(name = "limit", required = false) Integer limit) {
        // 行注释：组装接口返回数据，Spring 会自动转换成 JSON。
        return Map.of("data", monitoringService.listMonitoringData(deviceId, limit));
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：声明 GET 查询接口 /data/{id}，用于前端读取数据。
    @GetMapping("/data/{id}")
    /**
     * 答辩讲解:
     * - 方法： getData
     * - 作用：加载并返回前端视图所需的模块数据。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 getData 查询方法，给前端返回当前模块数据。
    // 方法用法：前端查询 Monitoring 模块数据时调用，用来读取数据库并返回 JSON。
    public Map<String, Object> getData(@PathVariable Long id) {
        // 行注释：组装接口返回数据，Spring 会自动转换成 JSON。
        return Map.of("record", monitoringService.getMonitoringRecord(id));
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：声明监测数据新增接口 /data，用于前端提交新的能源采集记录。
    @PostMapping("/data")
    /**
     * 答辩讲解:
     * - 方法： saveMonitoringData
     * - 作用：按模块校验规则将输入数据持久化到数据库。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 saveMonitoringData 方法，完成 MonitoringController 中对应的一步业务处理。
    // 方法用法：前端访问 Monitoring 模块接口时调用，用来完成对应业务请求并返回 JSON。
    public Map<String, Object> saveMonitoringData(HttpServletRequest request, @RequestBody MonitoringDataRequest body) {
        // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
        AuthContext.requireRole(request, "manager", "admin");
        // 行注释：声明变量 record，保存本行计算或查询得到的结果，供后续逻辑使用。
        Map<String, Object> record = monitoringService.saveMonitoringData(body);
        // 行注释：记录本次业务操作，方便后台审计和答辩说明安全性。
        operationLogService.log(
                // 行注释：传入字符串参数 “monitoring_data”，作为当前方法调用的业务标识或显示文本。
                "monitoring_data",
                // 行注释：传入字符串参数 “create”，作为当前方法调用的业务标识或显示文本。
                "create",
                // 行注释：发起接口请求或读取对象数据，获取页面需要的业务信息。
                String.valueOf(record.get("id")),
                // 行注释：从请求上下文中取出当前用户名，用于操作日志记录。
                AuthContext.username(request),
                // 行注释：从请求上下文中取出当前用户角色，用于权限判断或日志记录。
                AuthContext.role(request),
                // 行注释：传入字符串参数 “Create monitoring record”，作为当前方法调用的业务标识或显示文本。
                "Create monitoring record"
        // 行注释：结束当前方法调用，把上面准备好的参数一次性提交执行。
        );
        // 行注释：组装接口返回数据，Spring 会自动转换成 JSON。
        return Map.of("record", record, "message", "Monitoring data created");
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：声明 PUT 更新接口 /data/{id}，用于前端修改已有数据。
    @PutMapping("/data/{id}")
    /**
     * 答辩讲解:
     * - 方法： updateMonitoringData
     * - 作用：在输入规范化后，对现有记录执行部分字段更新。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：声明 updateMonitoringData 方法，承接当前模块的接口请求或内部业务处理。
    // 方法用法：前端编辑已有记录并保存时调用，用来更新 Monitoring 模块的数据。
    public Map<String, Object> updateMonitoringData(HttpServletRequest request,
                                                    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                                                    @PathVariable Long id,
                                                    // 行注释：说明该参数来自前端 JSON 请求体，Spring 会自动封装成对象。
                                                    @RequestBody MonitoringDataUpdateRequest body) {
        // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
        AuthContext.requireRole(request, "manager", "admin");
        // 行注释：声明变量 record，保存本行计算或查询得到的结果，供后续逻辑使用。
        Map<String, Object> record = monitoringService.updateMonitoringData(id, body);
        // 行注释：记录本次业务操作，方便后台审计和答辩说明安全性。
        operationLogService.log(
                // 行注释：传入字符串参数 “monitoring_data”，作为当前方法调用的业务标识或显示文本。
                "monitoring_data",
                // 行注释：传入字符串参数 “update”，作为当前方法调用的业务标识或显示文本。
                "update",
                // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                String.valueOf(id),
                // 行注释：从请求上下文中取出当前用户名，用于操作日志记录。
                AuthContext.username(request),
                // 行注释：从请求上下文中取出当前用户角色，用于权限判断或日志记录。
                AuthContext.role(request),
                // 行注释：传入字符串参数 “Update monitoring record”，作为当前方法调用的业务标识或显示文本。
                "Update monitoring record"
        // 行注释：结束当前方法调用，把上面准备好的参数一次性提交执行。
        );
        // 行注释：组装接口返回数据，Spring 会自动转换成 JSON。
        return Map.of("record", record, "message", "Monitoring data updated");
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：声明 DELETE 删除接口 /data/{id}，用于前端删除指定记录。
    @DeleteMapping("/data/{id}")
    /**
     * 答辩讲解:
     * - 方法： deleteMonitoringData
     * - 作用：删除或逻辑移除目标记录，并维护数据一致性。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 deleteMonitoringData 删除方法，删除前端指定的数据记录。
    // 方法用法：前端确认删除时调用，用来删除 Monitoring 模块的指定记录。
    public Map<String, Object> deleteMonitoringData(HttpServletRequest request, @PathVariable Long id) {
        // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
        AuthContext.requireRole(request, "manager", "admin");
        // 行注释：调用 JPA 删除数据或清理过期记录。
        monitoringService.deleteMonitoringData(id);
        // 行注释：记录本次业务操作，方便后台审计和答辩说明安全性。
        operationLogService.log(
                // 行注释：传入字符串参数 “monitoring_data”，作为当前方法调用的业务标识或显示文本。
                "monitoring_data",
                // 行注释：传入字符串参数 “delete”，作为当前方法调用的业务标识或显示文本。
                "delete",
                // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                String.valueOf(id),
                // 行注释：从请求上下文中取出当前用户名，用于操作日志记录。
                AuthContext.username(request),
                // 行注释：从请求上下文中取出当前用户角色，用于权限判断或日志记录。
                AuthContext.role(request),
                // 行注释：传入字符串参数 “Delete monitoring record”，作为当前方法调用的业务标识或显示文本。
                "Delete monitoring record"
        // 行注释：结束当前方法调用，把上面准备好的参数一次性提交执行。
        );
        // 行注释：组装接口返回数据，Spring 会自动转换成 JSON。
        return Map.of("message", "Monitoring data deleted");
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：声明 GET 查询接口 /realtime，用于前端读取数据。
    @GetMapping("/realtime")
    /**
     * 答辩讲解:
     * - 方法： getRealtimeData
     * - 作用：加载并返回前端视图所需的模块数据。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 getRealtimeData 查询方法，给前端返回当前模块数据。
    // 方法用法：前端查询 Monitoring 模块数据时调用，用来读取数据库并返回 JSON。
    public Map<String, Object> getRealtimeData() {
        // 行注释：创建对象实例，用来承载实体数据、列表或返回结果。
        Map<String, Object> response = new LinkedHashMap<>();
        // 行注释：向返回结果中加入一个字段，方便前端按 key 读取。
        response.put("data", monitoringService.getRealtimeData(50));
        // 行注释：返回处理结果，最终会交给前端或上层方法使用。
        return response;
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：声明 GET 查询接口 /history/{deviceId}，用于前端读取数据。
    @GetMapping("/history/{deviceId}")
    /**
     * 答辩讲解:
     * - 方法： getHistory
     * - 作用：加载并返回前端视图所需的模块数据。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：声明 getHistory 方法，承接当前模块的接口请求或内部业务处理。
    // 方法用法：前端查询 Monitoring 模块数据时调用，用来读取数据库并返回 JSON。
    public Map<String, Object> getHistory(@PathVariable Long deviceId,
                                          // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                                          @RequestParam(required = false) String startDate,
                                          // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
                                          @RequestParam(required = false) String endDate) {
        // 行注释：组装接口返回数据，Spring 会自动转换成 JSON。
        return Map.of("data", monitoringService.getHistory(deviceId, startDate, endDate));
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：声明 GET 查询接口 /statistics，用于前端读取数据。
    @GetMapping("/statistics")
    /**
     * 答辩讲解:
     * - 方法： getStatistics
     * - 作用：加载并返回前端视图所需的模块数据。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 getStatistics 查询方法，给前端返回当前模块数据。
    // 方法用法：前端查询 Monitoring 模块数据时调用，用来读取数据库并返回 JSON。
    public Map<String, Object> getStatistics() {
        // 行注释：组装接口返回数据，Spring 会自动转换成 JSON。
        return Map.of("statistics", monitoringService.getTodayStatistics());
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }

    // 行注释：声明 GET 查询接口 /statistics/by-type，用于前端读取数据。
    @GetMapping("/statistics/by-type")
    /**
     * 答辩讲解:
     * - 方法： getStatisticsByType
     * - 作用：加载并返回前端视图所需的模块数据。
     * - 说明： 此注释仅用于解释设计意图，不影响运行逻辑。
     */
    // 行注释：定义 getStatisticsByType 查询方法，给前端返回当前模块数据。
    // 方法用法：前端查询 Monitoring 模块数据时调用，用来读取数据库并返回 JSON。
    public Map<String, Object> getStatisticsByType() {
        // 行注释：组装接口返回数据，Spring 会自动转换成 JSON。
        return Map.of("statistics", monitoringService.getStatisticsByType());
    // 行注释：结束当前代码块，表示这段逻辑处理完成。
    }
// 行注释：结束当前代码块，表示这段逻辑处理完成。
}



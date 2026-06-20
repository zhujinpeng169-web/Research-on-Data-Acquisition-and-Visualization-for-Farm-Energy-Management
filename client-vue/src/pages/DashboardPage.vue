<!-- 逐行注释标记：已按业务语义处理，仅增加中文注释，不改变页面逻辑 -->
<!--
  <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
  答辩注释:
  <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
  - 文件： DashboardPage
  <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
  - 层级：前端页面层
  <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
  - 职责：渲染模块界面，并编排 API 调用、状态更新与增删改查交互。
  <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
  - 此注释仅用于说明，页面行为保持不变。
-->
<!-- 行注释：声明 Vue 模板区域，下面写页面显示出来的结构。 -->
<template>
  <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
  <div>
    <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
    <h1 class="page-title">控制台</h1>

    <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
    <a-row :gutter="16">
      <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
      <a-col :span="6">
        <!-- 行注释：渲染内容卡片，用于承载当前模块信息。 -->
        <a-card>
          <!-- 行注释：显示统计指标“今日总发电量”，把后端统计结果展示给用户。 -->
          <a-statistic :title="statisticsTitle('总发电量')" :value="statistics.total_generated" :precision="2" suffix="kWh" />
        <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
        </a-card>
      <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
      </a-col>
      <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
      <a-col :span="6">
        <!-- 行注释：渲染内容卡片，用于承载当前模块信息。 -->
        <a-card>
          <!-- 行注释：显示统计指标“今日总消耗量”，把后端统计结果展示给用户。 -->
          <a-statistic :title="statisticsTitle('总消耗量')" :value="statistics.total_consumed" :precision="2" suffix="kWh" />
        <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
        </a-card>
      <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
      </a-col>
      <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
      <a-col :span="6">
        <!-- 行注释：渲染内容卡片，用于承载当前模块信息。 -->
        <a-card>
          <!-- 行注释：显示统计指标“平均效率”，把后端统计结果展示给用户。 -->
          <a-statistic :title="statisticsTitle('平均效率')" :value="statistics.avg_efficiency" :precision="2" suffix="%" />
        <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
        </a-card>
      <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
      </a-col>
      <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
      <a-col :span="6">
        <!-- 行注释：渲染内容卡片，用于承载当前模块信息。 -->
        <a-card>
          <!-- 行注释：显示统计指标“采集数据点”，把后端统计结果展示给用户。 -->
          <a-statistic :title="statisticsTitle('采集数据点')" :value="statistics.data_points" />
        <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
        </a-card>
      <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
      </a-col>
    <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
    </a-row>

    <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
    <a-row :gutter="16" class="section-gap">
      <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
      <a-col :span="24">
        <!-- 行注释：渲染“实时监测数据”卡片，用于承载当前模块信息。 -->
        <a-card title="实时监测数据" :loading="loading">
          <!-- 行注释：渲染数据表格，把后端列表数据按列展示。 -->
          <a-table :columns="columns" :data-source="realtimeData" row-key="id" :pagination="false" />
        <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
        </a-card>
      <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
      </a-col>
    <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
    </a-row>

    <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
    <a-row v-if="recommendations.length" :gutter="16" class="section-gap">
      <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
      <a-col :span="24">
        <!-- 行注释：渲染“节能建议”卡片，用于承载当前模块信息。 -->
        <a-card title="节能建议">
          <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
          <a-alert
            v-for="(item, index) in recommendations"
            :key="item.id || index"
            :message="item.description"
            :description="`潜在节省: ${item.potential_savings || 0} kWh`"
            :type="item.priority === 'high' ? 'warning' : 'info'"
            show-icon
            style="margin-bottom: 12px"
          />
        <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
        </a-card>
      <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
      </a-col>
    <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
    </a-row>
  <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
  </div>
<!-- 行注释：结束模板区域，页面结构到这里完成。 -->
</template>

<!-- 行注释：进入脚本区域，下面编写数据请求和页面交互逻辑。 -->
<script setup>
// 行注释：引入当前页面或组件需要的依赖。
import { onBeforeUnmount, onMounted, ref } from 'vue';
// 行注释：引入 dayjs，用来把后端 ISO 时间格式转换成页面易读的中文日期格式。
import dayjs from 'dayjs';
// 行注释：引入当前页面或组件需要的依赖。
import api from '../api/http';

// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const loading = ref(true);
// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const statistics = ref({
  // 行注释：设置样式属性，控制页面元素的展示效果。
  total_generated: 0,
  // 行注释：设置样式属性，控制页面元素的展示效果。
  total_consumed: 0,
  // 行注释：设置样式属性，控制页面元素的展示效果。
  avg_efficiency: 0,
  // 行注释：设置样式属性，控制页面元素的展示效果。
  data_points: 0,
  // 行注释：保存本次统计实际使用的日期，便于当天无数据时说明展示的是最新统计日。
  stat_date: null,
  // 行注释：标记统计数据是否来自今天，用来动态显示“今日”或“最新统计日”。
  is_today: true
// 行注释：结束当前脚本代码块。
});
// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const realtimeData = ref([]);
// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const recommendations = ref([]);

// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const columns = [
  // 行注释：设置样式属性，控制页面元素的展示效果。
  { title: '设备名称', dataIndex: 'device_name_label', key: 'device_name_label' },
  // 行注释：设置样式属性，控制页面元素的展示效果。
  { title: '设备类型', dataIndex: 'device_type_label', key: 'device_type_label' },
  // 行注释：设置样式属性，控制页面元素的展示效果。
  { title: '发电量(kWh)', dataIndex: 'energy_generated', key: 'energy_generated' },
  // 行注释：设置样式属性，控制页面元素的展示效果。
  { title: '消耗量(kWh)', dataIndex: 'energy_consumed', key: 'energy_consumed' },
  // 行注释：设置样式属性，控制页面元素的展示效果。
  { title: '效率(%)', dataIndex: 'efficiency', key: 'efficiency' },
  // 行注释：设置样式属性，控制页面元素的展示效果。
  { title: '时间', dataIndex: 'timestamp_label', key: 'timestamp_label' }
// 行注释：结束数组配置，完成列表、菜单或图表数据的组装。
];

// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
let timer = null;

// 行注释：定义设备名称中文映射，兼容旧数据库中已经存在的英文演示数据。
const deviceNameMap = {
  // 行注释：把旧演示数据中的英文名称转换为中文名称，方便表格直接展示。
  'Solar Panel A': '光伏板A',
  // 行注释：把旧演示数据中的英文名称转换为中文名称，方便表格直接展示。
  'Solar Panel B': '光伏板B',
  // 行注释：把旧演示数据中的英文名称转换为中文名称，方便表格直接展示。
  'Wind Turbine A': '风力发电机A',
  // 行注释：把旧演示数据中的英文名称转换为中文名称，方便表格直接展示。
  'Biomass Unit A': '生物质发电单元A'
// 行注释：结束对象配置，完成英文演示名称到中文名称的对应关系。
};

// 行注释：定义设备类型中文映射，内部仍保留英文编码用于后端计算。
const deviceTypeMap = {
  // 行注释：把太阳能设备编码显示为中文。
  solar: '太阳能',
  // 行注释：把风能设备编码显示为中文。
  wind: '风能',
  // 行注释：把生物质能设备编码显示为中文。
  biomass: '生物质能',
  // 行注释：把电网能源编码显示为中文。
  grid: '电网'
// 行注释：结束对象配置，完成设备类型编码到中文名称的对应关系。
};

// 行注释：定义 formatDeviceName 方法，把设备名称统一转成中文展示。
// 方法用法：表格渲染监测数据前调用，避免旧英文演示数据直接显示在页面上。
const formatDeviceName = (value) => deviceNameMap[value] || value || '-';

// 行注释：定义 formatDeviceType 方法，把设备类型编码统一转成中文展示。
// 方法用法：表格渲染设备类型前调用，保留后端编码的同时让页面展示中文。
const formatDeviceType = (value) => deviceTypeMap[value] || value || '-';

// 行注释：定义 formatTimestamp 方法，把后端时间转换成“年-月-日 时:分:秒”。
// 方法用法：表格渲染时间列前调用，让答辩演示时的时间格式更直观。
const formatTimestamp = (value) => (value ? dayjs(value).format('YYYY-MM-DD HH:mm:ss') : '-');

// 行注释：定义 statisticsTitle 方法，根据统计日期动态显示“今日”或“最新统计日”。
// 方法用法：统计卡片渲染标题时调用，避免当天无数据时仍写“今日”造成误解。
const statisticsTitle = (name) => (statistics.value.is_today === false ? `最新统计日${name}` : `今日${name}`);

// 行注释：定义 localizeMonitoringRow 方法，给后端监测记录补充中文展示字段。
// 方法用法：接口返回实时监测数据后调用，统一转换设备名称、设备类型和时间。
const localizeMonitoringRow = (item) => ({
  // 行注释：保留原始记录字段，保证后续逻辑仍能读取后端原数据。
  ...item,
  // 行注释：新增中文设备名称字段，供表格“设备名称”列显示。
  device_name_label: formatDeviceName(item.device_name),
  // 行注释：新增中文设备类型字段，供表格“设备类型”列显示。
  device_type_label: formatDeviceType(item.device_type),
  // 行注释：新增格式化时间字段，供表格“时间”列显示。
  timestamp_label: formatTimestamp(item.timestamp)
// 行注释：结束对象组装，返回页面可以直接展示的一行数据。
});

// 答辩讲解:
// - 函数： fetchData
// - 作用：拉取后端最新数据并同步响应式状态用于渲染。
// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
// 方法用法：页面初始化或刷新时调用，用来集中请求后端数据并更新页面状态。
const fetchData = async () => {
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  try {
    // 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
    const [statsRes, realtimeRes, recRes] = await Promise.all([
      // 行注释：发起接口请求或读取对象数据，获取页面需要的业务信息。
      api.get('/monitoring/statistics'),
      // 行注释：发起接口请求或读取对象数据，获取页面需要的业务信息。
      api.get('/monitoring/realtime'),
      // 行注释：发起接口请求或读取对象数据，获取页面需要的业务信息。
      api.get('/optimization/recommendations')
    // 行注释：结束数组配置，完成列表、菜单或图表数据的组装。
    ]);

    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    statistics.value = statsRes.statistics || statistics.value;
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    realtimeData.value = (realtimeRes.data || []).map(localizeMonitoringRow).slice(0, 10);
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    recommendations.value = (recRes.recommendations || []).slice(0, 5);
  // 行注释：结束 try 代码块并进入异常处理流程。
  } catch (error) {
    // 错误提示已由 axios 拦截器统一处理。
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  } finally {
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    loading.value = false;
  // 行注释：结束当前脚本代码块。
  }
// 行注释：结束当前脚本代码块。
};

// 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
onMounted(async () => {
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  await fetchData();
  // 行注释：给变量 timer 赋值，更新当前业务流程中的临时状态。
  timer = setInterval(fetchData, 30000);
// 行注释：结束当前脚本代码块。
});

// 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
onBeforeUnmount(() => {
  // 行注释：根据条件控制前端流程，例如权限判断、跳转或错误处理。
  if (timer) {
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    clearInterval(timer);
  // 行注释：结束当前脚本代码块。
  }
// 行注释：结束当前脚本代码块。
});
// 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
</script>



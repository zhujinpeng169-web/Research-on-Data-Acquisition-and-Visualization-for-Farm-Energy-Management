<!-- 逐行注释标记：已按业务语义处理，仅增加中文注释，不改变页面逻辑 -->
<!--
  <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
  答辩注释:
  <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
  - 文件： CarbonPage
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
    <h1 class="page-title">碳排放监测</h1>

    <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
    <a-row :gutter="16">
      <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
      <a-col :span="8"><a-card><a-statistic title="总碳排放" :value="statistics.total_emission" :precision="2" suffix="kg CO2" /></a-card></a-col>
      <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
      <a-col :span="8"><a-card><a-statistic title="平均排放" :value="statistics.avg_emission" :precision="2" suffix="kg CO2" /></a-card></a-col>
      <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
      <a-col :span="8"><a-card><a-statistic title="记录数量" :value="statistics.record_count" /></a-card></a-col>
    <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
    </a-row>

    <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
    <a-row :gutter="16" class="section-gap">
      <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
      <a-col :span="12">
        <!-- 行注释：渲染“`碳中和进度(目标 ${progress.target || 100}%)`”卡片，用于承载当前模块信息。 -->
        <a-card :title="`碳中和进度(目标 ${progress.target || 100}%)`">
          <!-- 行注释：渲染进度图，用于展示效率、碳中和等百分比指标。 -->
          <a-progress type="circle" :percent="Number(progress.progress || 0)" />
          <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
          <div style="margin-top: 20px">
            <!-- 行注释：把响应式变量渲染到页面上，数据变化时页面会自动更新。 -->
            <p>可再生能源占比 {{ progress.renewable_percentage || 0 }}%</p>
            <!-- 行注释：把响应式变量渲染到页面上，数据变化时页面会自动更新。 -->
            <p>减排百分比 {{ progress.emission_reduction_percentage || 0 }}%</p>
          <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
          </div>
        <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
        </a-card>
      <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
      </a-col>

      <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
      <a-col :span="12">
        <!-- 行注释：渲染“碳排放来源分布”卡片，用于承载当前模块信息。 -->
        <a-card title="碳排放来源分布">
          <!-- 行注释：渲染 ECharts 图表，把趋势、占比或预测结果可视化。 -->
          <v-chart :option="pieOption" autoresize style="height: 300px" />
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
        <!-- 行注释：渲染“碳排放趋势”卡片，用于承载当前模块信息。 -->
        <a-card title="碳排放趋势">
          <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
          <template #extra>
            <!-- 行注释：渲染下拉选择框，让用户选择筛选或分类选项。 -->
            <a-select v-model:value="period" style="width: 120px">
              <!-- 行注释：渲染下拉选择框，让用户选择筛选或分类选项。 -->
              <a-select-option value="week">近7天</a-select-option>
              <!-- 行注释：渲染下拉选择框，让用户选择筛选或分类选项。 -->
              <a-select-option value="month">近30天</a-select-option>
            <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
            </a-select>
          <!-- 行注释：结束模板区域，页面结构到这里完成。 -->
          </template>
          <!-- 行注释：渲染 ECharts 图表，把趋势、占比或预测结果可视化。 -->
          <v-chart :option="trendOption" autoresize style="height: 320px" />
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
        <!-- 行注释：渲染“减排策略建议”卡片，用于承载当前模块信息。 -->
        <a-card title="减排策略建议" :loading="loading">
          <!-- 行注释：渲染数据表格，把后端列表数据按列展示。 -->
          <a-table :columns="strategyColumns" :data-source="strategies" row-key="strategy" :pagination="false" />
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
        <!-- 行注释：渲染“碳排放记录”卡片，用于承载当前模块信息。 -->
        <a-card title="碳排放记录" :loading="loading">
          <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
          <template #extra>
            <!-- 行注释：渲染操作按钮，触发新增、保存、删除或查询动作。 -->
            <a-button v-if="writable" type="primary" @click="openCreate">新增记录</a-button>
          <!-- 行注释：结束模板区域，页面结构到这里完成。 -->
          </template>
          <!-- 行注释：渲染数据表格，把后端列表数据按列展示。 -->
          <a-table :columns="emissionColumns" :data-source="emissions" row-key="id" :pagination="{ pageSize: 10 }">
            <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
            <template #bodyCell="{ column, record }">
              <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
              <template v-if="column.key === 'action'">
                <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
                <a-space>
                  <!-- 行注释：渲染操作按钮，触发新增、保存、删除或查询动作。 -->
                  <a-button size="small" @click="openEdit(record)">编辑</a-button>
                  <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
                  <a-popconfirm title="确认删除该记录吗？" @confirm="deleteItem(record.id)">
                    <!-- 行注释：渲染操作按钮，触发新增、保存、删除或查询动作。 -->
                    <a-button size="small" danger>删除</a-button>
                  <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
                  </a-popconfirm>
                <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
                </a-space>
              <!-- 行注释：结束模板区域，页面结构到这里完成。 -->
              </template>
            <!-- 行注释：结束模板区域，页面结构到这里完成。 -->
            </template>
          <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
          </a-table>
        <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
        </a-card>
      <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
      </a-col>
    <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
    </a-row>

    <!-- 行注释：渲染弹窗表单，用于新增或编辑业务数据。 -->
    <a-modal
      v-model:open="modalOpen"
      :title="editingId ? '编辑排放记录' : '新增排放记录'"
      :confirm-loading="submitting"
      @ok="submit"
      destroy-on-close
    >
      <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
      <a-form layout="vertical">
        <!-- 行注释：渲染表单项“能源来源”，接收用户输入。 -->
        <a-form-item label="能源来源" required>
          <!-- 行注释：渲染下拉选择框，让用户选择筛选或分类选项。 -->
          <a-select v-model:value="form.energy_source">
            <!-- 行注释：渲染下拉选择框，让用户选择筛选或分类选项。 -->
            <a-select-option value="solar">solar</a-select-option>
            <!-- 行注释：渲染下拉选择框，让用户选择筛选或分类选项。 -->
            <a-select-option value="wind">wind</a-select-option>
            <!-- 行注释：渲染下拉选择框，让用户选择筛选或分类选项。 -->
            <a-select-option value="biomass">biomass</a-select-option>
            <!-- 行注释：渲染下拉选择框，让用户选择筛选或分类选项。 -->
            <a-select-option value="grid">grid</a-select-option>
            <!-- 行注释：渲染下拉选择框，让用户选择筛选或分类选项。 -->
            <a-select-option value="diesel">diesel</a-select-option>
            <!-- 行注释：渲染下拉选择框，让用户选择筛选或分类选项。 -->
            <a-select-option value="natural_gas">natural_gas</a-select-option>
          <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
          </a-select>
        <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
        </a-form-item>
        <!-- 行注释：渲染表单项“能源用量(kWh)”，接收用户输入。 -->
        <a-form-item label="能源用量(kWh)" required>
          <!-- 行注释：渲染输入框，接收用户填写的数据。 -->
          <a-input-number v-model:value="form.energy_amount" :min="0" style="width: 100%" />
        <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
        </a-form-item>
        <!-- 行注释：渲染表单项“排放因子(可选)”，接收用户输入。 -->
        <a-form-item label="排放因子(可选)">
          <!-- 行注释：渲染输入框，接收用户填写的数据。 -->
          <a-input-number v-model:value="form.emission_factor" :min="0" style="width: 100%" />
        <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
        </a-form-item>
        <!-- 行注释：渲染表单项“时间”，接收用户输入。 -->
        <a-form-item v-if="editingId" label="时间">
          <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
          <a-date-picker
            v-model:value="form.timestamp"
            show-time
            format="YYYY-MM-DD HH:mm:ss"
            style="width: 100%"
          />
        <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
        </a-form-item>
      <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
      </a-form>
    <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
    </a-modal>
  <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
  </div>
<!-- 行注释：结束模板区域，页面结构到这里完成。 -->
</template>

<!-- 行注释：进入脚本区域，下面编写数据请求和页面交互逻辑。 -->
<script setup>
// 行注释：引入当前页面或组件需要的依赖。
import { computed, onMounted, reactive, ref, watch } from 'vue';
// 行注释：引入当前页面或组件需要的依赖。
import dayjs from 'dayjs';
// 行注释：引入消息提示组件，用于向用户显示登录过期或操作失败。
import { message } from 'ant-design-vue';
// 行注释：引入当前页面或组件需要的依赖。
import VChart from 'vue-echarts';
// 行注释：引入当前页面或组件需要的依赖。
import '../components/chart-setup';
// 行注释：引入当前页面或组件需要的依赖。
import api from '../api/http';
// 行注释：引入当前页面或组件需要的依赖。
import { canWrite } from '../auth/session';

// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const loading = ref(true);
// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const period = ref('week');
// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const writable = computed(() => canWrite());

// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const statistics = ref({ total_emission: 0, avg_emission: 0, record_count: 0 });
// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const breakdown = ref([]);
// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const trends = ref([]);
// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const strategies = ref([]);
// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const progress = ref({});
// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const emissions = ref([]);

// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const strategyColumns = [
  // 行注释：设置样式属性，控制页面元素的展示效果。
  { title: '减排策略', dataIndex: 'strategy', key: 'strategy' },
  // 行注释：设置样式属性，控制页面元素的展示效果。
  { title: '描述', dataIndex: 'description', key: 'description' },
  // 行注释：设置样式属性，控制页面元素的展示效果。
  { title: '优先级', dataIndex: 'priority', key: 'priority' },
  // 行注释：设置样式属性，控制页面元素的展示效果。
  { title: '潜在减排(kg CO2)', dataIndex: 'potential_reduction', key: 'potential_reduction' }
// 行注释：结束数组配置，完成列表、菜单或图表数据的组装。
];

// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const emissionColumns = computed(() => {
  // 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
  const base = [
    // 行注释：设置样式属性，控制页面元素的展示效果。
    { title: 'ID', dataIndex: 'id', key: 'id' },
    // 行注释：设置样式属性，控制页面元素的展示效果。
    { title: '时间', dataIndex: 'timestamp', key: 'timestamp' },
    // 行注释：设置样式属性，控制页面元素的展示效果。
    { title: '能源来源', dataIndex: 'energy_source', key: 'energy_source' },
    // 行注释：设置样式属性，控制页面元素的展示效果。
    { title: '用量(kWh)', dataIndex: 'energy_amount', key: 'energy_amount' },
    // 行注释：设置样式属性，控制页面元素的展示效果。
    { title: '碳排放(kg CO2)', dataIndex: 'carbon_emission', key: 'carbon_emission' },
    // 行注释：设置样式属性，控制页面元素的展示效果。
    { title: '排放因子', dataIndex: 'emission_factor', key: 'emission_factor' }
  // 行注释：结束数组配置，完成列表、菜单或图表数据的组装。
  ];
  // 行注释：根据条件控制前端流程，例如权限判断、跳转或错误处理。
  if (writable.value) {
    // 行注释：设置样式属性，控制页面元素的展示效果。
    base.push({ title: '操作', key: 'action', width: 180 });
  // 行注释：结束当前脚本代码块。
  }
  // 行注释：返回处理结果，交给调用方或路由守卫继续使用。
  return base;
// 行注释：结束当前脚本代码块。
});

// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const modalOpen = ref(false);
// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const submitting = ref(false);
// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const editingId = ref(null);
// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const form = reactive({
  // 行注释：设置样式属性，控制页面元素的展示效果。
  energy_source: 'grid',
  // 行注释：设置样式属性，控制页面元素的展示效果。
  energy_amount: 0,
  // 行注释：设置样式属性，控制页面元素的展示效果。
  emission_factor: null,
  // 行注释：设置样式属性，控制页面元素的展示效果。
  timestamp: null
// 行注释：结束当前脚本代码块。
});

// 答辩讲解:
// - 函数： resetForm
// - 作用：在用户操作前将表单/筛选状态重置为安全默认值。
// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
// 方法用法：打开新增或提交后调用，用来把表单恢复到默认初始状态。
const resetForm = () => {
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  form.energy_source = 'grid';
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  form.energy_amount = 0;
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  form.emission_factor = null;
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  form.timestamp = null;
// 行注释：结束当前脚本代码块。
};

// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const pieOption = computed(() => ({
  // 行注释：设置样式属性，控制页面元素的展示效果。
  tooltip: { trigger: 'item' },
  // 行注释：设置样式属性，控制页面元素的展示效果。
  legend: { orient: 'vertical', left: 'left' },
  // 行注释：设置样式属性，控制页面元素的展示效果。
  series: [
    // 行注释：开始对象配置，把相关参数集中传给组件或接口。
    {
      // 行注释：设置样式属性，控制页面元素的展示效果。
      type: 'pie',
      // 行注释：设置样式属性，控制页面元素的展示效果。
      radius: '65%',
      // 行注释：设置样式属性，控制页面元素的展示效果。
      data: breakdown.value.map((item) => ({ name: item.energy_source, value: Number(item.total_emission || 0) }))
    // 行注释：结束当前脚本代码块。
    }
  // 行注释：结束数组配置，完成列表、菜单或图表数据的组装。
  ]
// 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
}));

// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const trendOption = computed(() => ({
  // 行注释：设置样式属性，控制页面元素的展示效果。
  tooltip: { trigger: 'axis' },
  // 行注释：设置样式属性，控制页面元素的展示效果。
  xAxis: { type: 'category', data: trends.value.map((item) => item.date) },
  // 行注释：设置样式属性，控制页面元素的展示效果。
  yAxis: { type: 'value' },
  // 行注释：设置样式属性，控制页面元素的展示效果。
  series: [
    // 行注释：开始对象配置，把相关参数集中传给组件或接口。
    {
      // 行注释：设置样式属性，控制页面元素的展示效果。
      name: '碳排放',
      // 行注释：设置样式属性，控制页面元素的展示效果。
      type: 'line',
      // 行注释：设置样式属性，控制页面元素的展示效果。
      smooth: true,
      // 行注释：设置样式属性，控制页面元素的展示效果。
      data: trends.value.map((item) => Number(item.carbon_emission || 0))
    // 行注释：结束当前脚本代码块。
    }
  // 行注释：结束数组配置，完成列表、菜单或图表数据的组装。
  ]
// 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
}));

// 答辩讲解:
// - 函数： fetchData
// - 作用：拉取后端最新数据并同步响应式状态用于渲染。
// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
// 方法用法：页面初始化或刷新时调用，用来集中请求后端数据并更新页面状态。
const fetchData = async () => {
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  try {
    // 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
    const [statsRes, breakdownRes, trendsRes, strategiesRes, progressRes, emissionsRes] = await Promise.all([
      // 行注释：发起接口请求或读取对象数据，获取页面需要的业务信息。
      api.get(`/carbon/statistics?period=${period.value}`),
      // 行注释：发起接口请求或读取对象数据，获取页面需要的业务信息。
      api.get(`/carbon/breakdown?period=${period.value}`),
      // 行注释：发起接口请求或读取对象数据，获取页面需要的业务信息。
      api.get('/carbon/trends'),
      // 行注释：发起接口请求或读取对象数据，获取页面需要的业务信息。
      api.get('/carbon/reduction-strategies'),
      // 行注释：发起接口请求或读取对象数据，获取页面需要的业务信息。
      api.get('/carbon/carbon-neutral-progress'),
      // 行注释：发起接口请求或读取对象数据，获取页面需要的业务信息。
      api.get('/carbon/emissions?limit=100')
    // 行注释：结束数组配置，完成列表、菜单或图表数据的组装。
    ]);

    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    statistics.value = statsRes.statistics || statistics.value;
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    breakdown.value = breakdownRes.breakdown || [];
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    trends.value = trendsRes.data || [];
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    strategies.value = strategiesRes.strategies || [];
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    progress.value = progressRes || {};
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    emissions.value = emissionsRes.emissions || [];
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

// 答辩讲解:
// - 函数： openCreate
// - 作用：准备本地表单状态并打开弹窗供用户操作。
// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
// 方法用法：用户点击新增按钮时调用，用来重置表单并打开新增弹窗。
const openCreate = () => {
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  editingId.value = null;
  // 行注释：设置实体字段值，准备保存到数据库或返回给前端。
  resetForm();
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  modalOpen.value = true;
// 行注释：结束当前脚本代码块。
};

// 答辩讲解:
// - 函数： openEdit
// - 作用：准备本地表单状态并打开弹窗供用户操作。
// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
// 方法用法：用户点击编辑按钮时调用，用来把当前行数据回填到表单并打开编辑弹窗。
const openEdit = (row) => {
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  editingId.value = row.id;
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  form.energy_source = row.energy_source || 'grid';
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  form.energy_amount = Number(row.energy_amount || 0);
  // 行注释：设置样式属性，控制页面元素的展示效果。
  form.emission_factor = row.emission_factor == null ? null : Number(row.emission_factor);
  // 行注释：设置样式属性，控制页面元素的展示效果。
  form.timestamp = row.timestamp ? dayjs(row.timestamp) : null;
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  modalOpen.value = true;
// 行注释：结束当前脚本代码块。
};

// 答辩讲解:
// - 函数： submit
// - 作用：校验表单输入并向后端提交新增/更新请求。
// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
// 方法用法：用户点击弹窗或表单确认按钮时调用，用来校验输入并提交新增或编辑请求。
const submit = async () => {
  // 行注释：根据条件控制前端流程，例如权限判断、跳转或错误处理。
  if (!form.energy_source) {
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    message.warning('请选择能源来源');
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    return;
  // 行注释：结束当前脚本代码块。
  }

  // 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
  const payload = {
    // 行注释：设置样式属性，控制页面元素的展示效果。
    energy_source: form.energy_source,
    // 行注释：设置样式属性，控制页面元素的展示效果。
    energy_amount: form.energy_amount,
    // 行注释：设置样式属性，控制页面元素的展示效果。
    emission_factor: form.emission_factor,
    // 行注释：设置样式属性，控制页面元素的展示效果。
    timestamp: form.timestamp ? form.timestamp.format('YYYY-MM-DDTHH:mm:ss') : null
  // 行注释：结束当前脚本代码块。
  };

  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  submitting.value = true;
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  try {
    // 行注释：根据条件控制前端流程，例如权限判断、跳转或错误处理。
    if (editingId.value) {
      // 行注释：发起接口请求或读取对象数据，获取页面需要的业务信息。
      await api.put(`/carbon/emissions/${editingId.value}`, payload);
      // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
      message.success('排放记录已更新');
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    } else {
      // 行注释：发起接口请求或读取对象数据，获取页面需要的业务信息。
      await api.post('/carbon/emissions', payload);
      // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
      message.success('排放记录已创建');
    // 行注释：结束当前脚本代码块。
    }
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    modalOpen.value = false;
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    await fetchData();
  // 行注释：结束 try 代码块并进入异常处理流程。
  } catch (error) {
    // 行注释：根据条件控制前端流程，例如权限判断、跳转或错误处理。
    if (error?.response?.status === 404) {
      // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
      message.warning('排放记录不存在，已刷新列表');
      // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
      await fetchData();
      // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
      modalOpen.value = false;
    // 行注释：结束当前脚本代码块。
    }
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  } finally {
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    submitting.value = false;
  // 行注释：结束当前脚本代码块。
  }
// 行注释：结束当前脚本代码块。
};

// 答辩讲解:
// - 函数： deleteItem
// - 作用：删除后端中的选中记录并刷新表格数据。
// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
// 方法用法：用户确认删除时调用，用来请求后端删除记录并刷新列表。
const deleteItem = async (id) => {
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  try {
    // 行注释：发起接口请求或读取对象数据，获取页面需要的业务信息。
    await api.delete(`/carbon/emissions/${id}`);
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    message.success('排放记录已删除');
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    await fetchData();
  // 行注释：结束 try 代码块并进入异常处理流程。
  } catch (error) {
    // 行注释：根据条件控制前端流程，例如权限判断、跳转或错误处理。
    if (error?.response?.status === 404) {
      // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
      message.warning('排放记录不存在，已刷新列表');
      // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
      await fetchData();
    // 行注释：结束当前脚本代码块。
    }
  // 行注释：结束当前脚本代码块。
  }
// 行注释：结束当前脚本代码块。
};

// 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
onMounted(fetchData);
// 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
watch(period, fetchData);
// 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
</script>



<!-- 逐行注释标记：已按业务语义处理，仅增加中文注释，不改变页面逻辑 -->
<!--
  <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
  答辩注释:
  <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
  - 文件： ForecastPage
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
    <h1 class="page-title">能源预测与计划</h1>

    <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
    <a-row :gutter="16">
      <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
      <a-col :span="24">
        <!-- 行注释：渲染“预测设置”卡片，用于承载当前模块信息。 -->
        <a-card title="预测设置">
          <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
          <span style="margin-right: 12px">预测天数:</span>
          <!-- 行注释：渲染输入框，接收用户填写的数据。 -->
          <a-input-number v-model:value="days" :min="1" :max="30" style="margin-right: 12px" />
          <!-- 行注释：渲染操作按钮，触发新增、保存、删除或查询动作。 -->
          <a-button type="primary" :loading="loading" @click="fetchData">生成预测</a-button>
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
        <!-- 行注释：渲染“能源需求预测趋势”卡片，用于承载当前模块信息。 -->
        <a-card title="能源需求预测趋势">
          <!-- 行注释：渲染 ECharts 图表，把趋势、占比或预测结果可视化。 -->
          <v-chart :option="forecastOption" autoresize style="height: 340px" />
        <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
        </a-card>
      <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
      </a-col>
    <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
    </a-row>

    <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
    <a-row :gutter="16" class="section-gap">
      <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
      <a-col :span="12">
        <!-- 行注释：渲染“预测数据详情”卡片，用于承载当前模块信息。 -->
        <a-card title="预测数据详情" :loading="loading">
          <!-- 行注释：渲染数据表格，把后端列表数据按列展示。 -->
          <a-table :columns="forecastColumns" :data-source="forecasts" row-key="date" :pagination="{ pageSize: 10 }" />
        <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
        </a-card>
      <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
      </a-col>
      <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
      <a-col :span="12">
        <!-- 行注释：渲染“能源需求计划建议”卡片，用于承载当前模块信息。 -->
        <a-card title="能源需求计划建议" :loading="loading">
          <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
          <template v-if="demandPlan">
            <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
            <h3>计划目标</h3>
            <!-- 行注释：把响应式变量渲染到页面上，数据变化时页面会自动更新。 -->
            <p>日常储备目标: {{ demandPlan.daily_target }} kWh</p>
            <!-- 行注释：把响应式变量渲染到页面上，数据变化时页面会自动更新。 -->
            <p>高峰期准备 {{ demandPlan.peak_preparation }} kWh</p>
            <!-- 行注释：把响应式变量渲染到页面上，数据变化时页面会自动更新。 -->
            <p>最低储备 {{ demandPlan.minimum_reserve }} kWh</p>

            <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
            <h3 style="margin-top: 24px">建议措施</h3>
            <!-- 行注释：渲染内容卡片，用于承载当前模块信息。 -->
            <a-card
              v-for="(item, index) in demandPlan.recommendations || []"
              :key="index"
              size="small"
              style="margin-bottom: 12px; background: #f7f9fc"
            >
              <!-- 行注释：把响应式变量渲染到页面上，数据变化时页面会自动更新。 -->
              <h4 style="margin: 0 0 8px">{{ item.title }}</h4>
              <!-- 行注释：把响应式变量渲染到页面上，数据变化时页面会自动更新。 -->
              <p style="margin: 0 0 8px">{{ item.description }}</p>
              <!-- 行注释：把响应式变量渲染到页面上，数据变化时页面会自动更新。 -->
              <span>优先级: {{ item.priority }}</span>
            <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
            </a-card>
          <!-- 行注释：结束模板区域，页面结构到这里完成。 -->
          </template>
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
        <!-- 行注释：渲染“预测记录管理”卡片，用于承载当前模块信息。 -->
        <a-card title="预测记录管理" :loading="loading">
          <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
          <template #extra>
            <!-- 行注释：渲染操作按钮，触发新增、保存、删除或查询动作。 -->
            <a-button v-if="writable" type="primary" @click="openCreate">新增记录</a-button>
          <!-- 行注释：结束模板区域，页面结构到这里完成。 -->
          </template>
          <!-- 行注释：渲染数据表格，把后端列表数据按列展示。 -->
          <a-table :columns="recordColumns" :data-source="records" row-key="id" :pagination="{ pageSize: 10 }">
            <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
            <template #bodyCell="{ column, record }">
              <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
              <template v-if="column.key === 'action'">
                <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
                <a-space>
                  <!-- 行注释：渲染操作按钮，触发新增、保存、删除或查询动作。 -->
                  <a-button size="small" @click="openEdit(record)">编辑</a-button>
                  <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
                  <a-popconfirm title="确认删除该预测记录吗？" @confirm="deleteItem(record.id)">
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
      :title="editingId ? '编辑预测记录' : '新增预测记录'"
      :confirm-loading="submitting"
      @ok="submit"
      destroy-on-close
    >
      <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
      <a-form layout="vertical">
        <!-- 行注释：渲染表单项“预测日期”，接收用户输入。 -->
        <a-form-item label="预测日期" required>
          <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
          <a-date-picker v-model:value="form.forecast_date" style="width: 100%" />
        <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
        </a-form-item>
        <!-- 行注释：渲染表单项“预测类型”，接收用户输入。 -->
        <a-form-item label="预测类型">
          <!-- 行注释：渲染下拉选择框，让用户选择筛选或分类选项。 -->
          <a-select v-model:value="form.forecast_type">
            <!-- 行注释：渲染下拉选择框，让用户选择筛选或分类选项。 -->
            <a-select-option value="daily">daily</a-select-option>
            <!-- 行注释：渲染下拉选择框，让用户选择筛选或分类选项。 -->
            <a-select-option value="weekly">weekly</a-select-option>
            <!-- 行注释：渲染下拉选择框，让用户选择筛选或分类选项。 -->
            <a-select-option value="monthly">monthly</a-select-option>
          <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
          </a-select>
        <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
        </a-form-item>
        <!-- 行注释：渲染表单项“预测发电(kWh)”，接收用户输入。 -->
        <a-form-item label="预测发电(kWh)">
          <!-- 行注释：渲染输入框，接收用户填写的数据。 -->
          <a-input-number v-model:value="form.predicted_generation" :min="0" style="width: 100%" />
        <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
        </a-form-item>
        <!-- 行注释：渲染表单项“预测消耗(kWh)”，接收用户输入。 -->
        <a-form-item label="预测消耗(kWh)">
          <!-- 行注释：渲染输入框，接收用户填写的数据。 -->
          <a-input-number v-model:value="form.predicted_consumption" :min="0" style="width: 100%" />
        <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
        </a-form-item>
        <!-- 行注释：渲染表单项“置信度(0-1)”，接收用户输入。 -->
        <a-form-item label="置信度(0-1)">
          <!-- 行注释：渲染输入框，接收用户填写的数据。 -->
          <a-input-number v-model:value="form.confidence_level" :min="0" :max="1" :step="0.01" style="width: 100%" />
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
import { computed, onMounted, reactive, ref } from 'vue';
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
const writable = computed(() => canWrite());
// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const days = ref(7);
// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const forecasts = ref([]);
// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const demandPlan = ref(null);
// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const records = ref([]);

// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const forecastColumns = [
  // 行注释：设置样式属性，控制页面元素的展示效果。
  { title: '预测日期', dataIndex: 'date', key: 'date' },
  // 行注释：设置样式属性，控制页面元素的展示效果。
  { title: '预测发电量(kWh)', dataIndex: 'predicted_generation', key: 'predicted_generation' },
  // 行注释：设置样式属性，控制页面元素的展示效果。
  { title: '预测消耗量(kWh)', dataIndex: 'predicted_consumption', key: 'predicted_consumption' },
  // 行注释：开始对象配置，把相关参数集中传给组件或接口。
  {
    // 行注释：设置样式属性，控制页面元素的展示效果。
    title: '置信度',
    // 行注释：设置样式属性，控制页面元素的展示效果。
    dataIndex: 'confidence_level',
    // 行注释：设置样式属性，控制页面元素的展示效果。
    key: 'confidence_level',
    // 行注释：设置样式属性，控制页面元素的展示效果。
    customRender: ({ text }) => `${Math.round(Number(text || 0) * 100)}%`
  // 行注释：结束当前脚本代码块。
  }
// 行注释：结束数组配置，完成列表、菜单或图表数据的组装。
];

// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const recordColumns = computed(() => {
  // 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
  const base = [
    // 行注释：设置样式属性，控制页面元素的展示效果。
    { title: 'ID', dataIndex: 'id', key: 'id' },
    // 行注释：设置样式属性，控制页面元素的展示效果。
    { title: '预测日期', dataIndex: 'forecast_date', key: 'forecast_date' },
    // 行注释：设置样式属性，控制页面元素的展示效果。
    { title: '预测类型', dataIndex: 'forecast_type', key: 'forecast_type' },
    // 行注释：设置样式属性，控制页面元素的展示效果。
    { title: '预测发电(kWh)', dataIndex: 'predicted_generation', key: 'predicted_generation' },
    // 行注释：设置样式属性，控制页面元素的展示效果。
    { title: '预测消耗(kWh)', dataIndex: 'predicted_consumption', key: 'predicted_consumption' },
    // 行注释：设置样式属性，控制页面元素的展示效果。
    { title: '置信度', dataIndex: 'confidence_level', key: 'confidence_level' },
    // 行注释：设置样式属性，控制页面元素的展示效果。
    { title: '创建时间', dataIndex: 'created_at', key: 'created_at' }
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
  forecast_date: null,
  // 行注释：设置样式属性，控制页面元素的展示效果。
  forecast_type: 'daily',
  // 行注释：设置样式属性，控制页面元素的展示效果。
  predicted_generation: 0,
  // 行注释：设置样式属性，控制页面元素的展示效果。
  predicted_consumption: 0,
  // 行注释：设置样式属性，控制页面元素的展示效果。
  confidence_level: 0.8
// 行注释：结束当前脚本代码块。
});

// 答辩讲解:
// - 函数： resetForm
// - 作用：在用户操作前将表单/筛选状态重置为安全默认值。
// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
// 方法用法：打开新增或提交后调用，用来把表单恢复到默认初始状态。
const resetForm = () => {
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  form.forecast_date = null;
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  form.forecast_type = 'daily';
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  form.predicted_generation = 0;
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  form.predicted_consumption = 0;
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  form.confidence_level = 0.8;
// 行注释：结束当前脚本代码块。
};

// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const forecastOption = computed(() => ({
  // 行注释：设置样式属性，控制页面元素的展示效果。
  tooltip: { trigger: 'axis' },
  // 行注释：设置样式属性，控制页面元素的展示效果。
  legend: { data: ['预测发电', '预测消耗'] },
  // 行注释：设置样式属性，控制页面元素的展示效果。
  xAxis: { type: 'category', data: forecasts.value.map((item) => item.date) },
  // 行注释：设置样式属性，控制页面元素的展示效果。
  yAxis: { type: 'value' },
  // 行注释：设置样式属性，控制页面元素的展示效果。
  series: [
    // 行注释：开始对象配置，把相关参数集中传给组件或接口。
    {
      // 行注释：设置样式属性，控制页面元素的展示效果。
      name: '预测发电',
      // 行注释：设置样式属性，控制页面元素的展示效果。
      type: 'line',
      // 行注释：设置样式属性，控制页面元素的展示效果。
      smooth: true,
      // 行注释：设置样式属性，控制页面元素的展示效果。
      areaStyle: {},
      // 行注释：设置样式属性，控制页面元素的展示效果。
      data: forecasts.value.map((item) => Number(item.predicted_generation || 0))
    // 行注释：结束当前回调或对象结构，完成这一段逻辑封装。
    },
    // 行注释：开始对象配置，把相关参数集中传给组件或接口。
    {
      // 行注释：设置样式属性，控制页面元素的展示效果。
      name: '预测消耗',
      // 行注释：设置样式属性，控制页面元素的展示效果。
      type: 'line',
      // 行注释：设置样式属性，控制页面元素的展示效果。
      smooth: true,
      // 行注释：设置样式属性，控制页面元素的展示效果。
      areaStyle: {},
      // 行注释：设置样式属性，控制页面元素的展示效果。
      data: forecasts.value.map((item) => Number(item.predicted_consumption || 0))
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
  loading.value = true;
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  try {
    // 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
    const [forecastRes, demandRes, recordsRes] = await Promise.all([
      // 行注释：发起接口请求或读取对象数据，获取页面需要的业务信息。
      api.get(`/forecast/energy?days=${days.value}`),
      // 行注释：发起接口请求或读取对象数据，获取页面需要的业务信息。
      api.get('/forecast/demand-plan'),
      // 行注释：发起接口请求或读取对象数据，获取页面需要的业务信息。
      api.get('/forecast/records?limit=100')
    // 行注释：结束数组配置，完成列表、菜单或图表数据的组装。
    ]);

    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    forecasts.value = forecastRes.forecasts || [];
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    demandPlan.value = demandRes.demand_plan || null;
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    records.value = recordsRes.records || [];
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
  // 行注释：设置样式属性，控制页面元素的展示效果。
  form.forecast_date = row.forecast_date ? dayjs(row.forecast_date) : null;
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  form.forecast_type = row.forecast_type || 'daily';
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  form.predicted_generation = Number(row.predicted_generation || 0);
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  form.predicted_consumption = Number(row.predicted_consumption || 0);
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  form.confidence_level = Number(row.confidence_level || 0.8);
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
  if (!form.forecast_date) {
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    message.warning('请选择预测日期');
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    return;
  // 行注释：结束当前脚本代码块。
  }

  // 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
  const payload = {
    // 行注释：设置样式属性，控制页面元素的展示效果。
    forecast_date: form.forecast_date.format('YYYY-MM-DD'),
    // 行注释：设置样式属性，控制页面元素的展示效果。
    forecast_type: form.forecast_type,
    // 行注释：设置样式属性，控制页面元素的展示效果。
    predicted_generation: form.predicted_generation,
    // 行注释：设置样式属性，控制页面元素的展示效果。
    predicted_consumption: form.predicted_consumption,
    // 行注释：设置样式属性，控制页面元素的展示效果。
    confidence_level: form.confidence_level
  // 行注释：结束当前脚本代码块。
  };

  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  submitting.value = true;
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  try {
    // 行注释：根据条件控制前端流程，例如权限判断、跳转或错误处理。
    if (editingId.value) {
      // 行注释：发起接口请求或读取对象数据，获取页面需要的业务信息。
      await api.put(`/forecast/records/${editingId.value}`, payload);
      // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
      message.success('预测记录已更新');
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    } else {
      // 行注释：发起接口请求或读取对象数据，获取页面需要的业务信息。
      await api.post('/forecast/records', payload);
      // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
      message.success('预测记录已创建');
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
      message.warning('预测记录不存在，已刷新列表');
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
    await api.delete(`/forecast/records/${id}`);
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    message.success('预测记录已删除');
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    await fetchData();
  // 行注释：结束 try 代码块并进入异常处理流程。
  } catch (error) {
    // 行注释：根据条件控制前端流程，例如权限判断、跳转或错误处理。
    if (error?.response?.status === 404) {
      // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
      message.warning('预测记录不存在，已刷新列表');
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
</script>



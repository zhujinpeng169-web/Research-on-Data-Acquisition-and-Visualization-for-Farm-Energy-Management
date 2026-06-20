<!-- 逐行注释标记：已按业务语义处理，仅增加中文注释，不改变页面逻辑 -->
<!--
  <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
  答辩注释:
  <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
  - 文件： ReportsPage
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
    <h1 class="page-title">报告中心</h1>

    <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
    <a-row :gutter="16">
      <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
      <a-col :span="24">
        <!-- 行注释：渲染“生成新报告”卡片，用于承载当前模块信息。 -->
        <a-card title="生成新报告">
          <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
          <span style="margin-right: 12px">报告类型:</span>
          <!-- 行注释：渲染下拉选择框，让用户选择筛选或分类选项。 -->
          <a-select v-model:value="reportType" style="width: 220px; margin-right: 20px">
            <!-- 行注释：渲染下拉选择框，让用户选择筛选或分类选项。 -->
            <a-select-option value="comprehensive">综合报告</a-select-option>
            <!-- 行注释：渲染下拉选择框，让用户选择筛选或分类选项。 -->
            <a-select-option value="energy">能源报告</a-select-option>
            <!-- 行注释：渲染下拉选择框，让用户选择筛选或分类选项。 -->
            <a-select-option value="carbon">碳排放报告</a-select-option>
            <!-- 行注释：渲染下拉选择框，让用户选择筛选或分类选项。 -->
            <a-select-option value="optimization">优化建议报告</a-select-option>
          <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
          </a-select>

          <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
          <span style="margin-right: 12px">报告周期:</span>
          <!-- 行注释：渲染下拉选择框，让用户选择筛选或分类选项。 -->
          <a-select v-model:value="period" style="width: 160px; margin-right: 20px">
            <!-- 行注释：渲染下拉选择框，让用户选择筛选或分类选项。 -->
            <a-select-option value="week">近7天</a-select-option>
            <!-- 行注释：渲染下拉选择框，让用户选择筛选或分类选项。 -->
            <a-select-option value="month">近30天</a-select-option>
            <!-- 行注释：渲染下拉选择框，让用户选择筛选或分类选项。 -->
            <a-select-option value="year">近1年</a-select-option>
          <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
          </a-select>

          <!-- 行注释：渲染操作按钮，触发新增、保存、删除或查询动作。 -->
          <a-button type="primary" :loading="loading" :disabled="!writable" @click="generatePdf">
            <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
            生成 PDF 报告
          <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
          </a-button>
        <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
        </a-card>
      <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
      </a-col>
    <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
    </a-row>

    <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
    <a-row :gutter="16" class="section-gap">
      <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
      <a-col :span="8">
        <!-- 行注释：渲染“能源使用报告”卡片，用于承载当前模块信息。 -->
        <a-card title="能源使用报告" :loading="!energyReport">
          <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
          <a-descriptions v-if="energyReport" :column="1" size="small">
            <!-- 行注释：把响应式变量渲染到页面上，数据变化时页面会自动更新。 -->
            <a-descriptions-item label="总发电量">{{ energyReport.total_generated }} kWh</a-descriptions-item>
            <!-- 行注释：把响应式变量渲染到页面上，数据变化时页面会自动更新。 -->
            <a-descriptions-item label="总消耗量">{{ energyReport.total_consumed }} kWh</a-descriptions-item>
            <!-- 行注释：把响应式变量渲染到页面上，数据变化时页面会自动更新。 -->
            <a-descriptions-item label="平均效率">{{ energyReport.avg_efficiency }}%</a-descriptions-item>
            <!-- 行注释：把响应式变量渲染到页面上，数据变化时页面会自动更新。 -->
            <a-descriptions-item label="数据点数">{{ energyReport.data_points }}</a-descriptions-item>
            <!-- 行注释：把响应式变量渲染到页面上，数据变化时页面会自动更新。 -->
            <a-descriptions-item label="统计周期">{{ energyReport.period }}</a-descriptions-item>
          <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
          </a-descriptions>
        <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
        </a-card>
      <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
      </a-col>

      <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
      <a-col :span="8">
        <!-- 行注释：渲染“碳排放报告”卡片，用于承载当前模块信息。 -->
        <a-card title="碳排放报告" :loading="!carbonReport">
          <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
          <a-descriptions v-if="carbonReport" :column="1" size="small">
            <!-- 行注释：把响应式变量渲染到页面上，数据变化时页面会自动更新。 -->
            <a-descriptions-item label="总碳排放">{{ carbonReport.total_emission }} kg CO2</a-descriptions-item>
            <!-- 行注释：把响应式变量渲染到页面上，数据变化时页面会自动更新。 -->
            <a-descriptions-item label="总能源使用">{{ carbonReport.total_energy }} kWh</a-descriptions-item>
            <!-- 行注释：把响应式变量渲染到页面上，数据变化时页面会自动更新。 -->
            <a-descriptions-item label="统计周期">{{ carbonReport.period }}</a-descriptions-item>
            <!-- 行注释：把响应式变量渲染到页面上，数据变化时页面会自动更新。 -->
            <a-descriptions-item label="排放源数量">{{ (carbonReport.breakdown || []).length }}</a-descriptions-item>
          <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
          </a-descriptions>
        <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
        </a-card>
      <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
      </a-col>

      <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
      <a-col :span="8">
        <!-- 行注释：渲染“优化建议报告”卡片，用于承载当前模块信息。 -->
        <a-card title="优化建议报告" :loading="!optimizationReport">
          <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
          <a-descriptions v-if="optimizationReport" :column="1" size="small">
            <!-- 行注释：把响应式变量渲染到页面上，数据变化时页面会自动更新。 -->
            <a-descriptions-item label="总建议数">{{ optimizationReport.total_recommendations }}</a-descriptions-item>
            <!-- 行注释：把响应式变量渲染到页面上，数据变化时页面会自动更新。 -->
            <a-descriptions-item label="潜在节省">{{ optimizationReport.total_potential_savings }} kWh</a-descriptions-item>
            <!-- 行注释：把响应式变量渲染到页面上，数据变化时页面会自动更新。 -->
            <a-descriptions-item label="高优先级">{{ optimizationReport.high_priority }}</a-descriptions-item>
            <!-- 行注释：把响应式变量渲染到页面上，数据变化时页面会自动更新。 -->
            <a-descriptions-item label="中优先级">{{ optimizationReport.medium_priority }}</a-descriptions-item>
            <!-- 行注释：把响应式变量渲染到页面上，数据变化时页面会自动更新。 -->
            <a-descriptions-item label="低优先级">{{ optimizationReport.low_priority }}</a-descriptions-item>
          <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
          </a-descriptions>
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
        <!-- 行注释：渲染“历史报告列表”卡片，用于承载当前模块信息。 -->
        <a-card title="历史报告列表">
          <!-- 行注释：渲染数据表格，把后端列表数据按列展示。 -->
          <a-table :columns="reportColumns" :data-source="reportsList" row-key="id" :pagination="{ pageSize: 10 }">
            <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
            <template #bodyCell="{ column, record }">
              <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
              <template v-if="column.key === 'status'">
                <!-- 行注释：把响应式变量渲染到页面上，数据变化时页面会自动更新。 -->
                <a-tag :color="record.status === 'completed' ? 'green' : 'orange'">{{ record.status }}</a-tag>
              <!-- 行注释：结束模板区域，页面结构到这里完成。 -->
              </template>
              <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
              <template v-if="column.key === 'action'">
                <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
                <a-space>
                  <!-- 行注释：渲染操作按钮，触发新增、保存、删除或查询动作。 -->
                  <a-button size="small" @click="download(record.file_path)">下载</a-button>
                  <!-- 行注释：渲染操作按钮，触发新增、保存、删除或查询动作。 -->
                  <a-button v-if="writable" size="small" @click="openEdit(record)">编辑</a-button>
                  <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
                  <a-popconfirm v-if="writable" title="确认删除该报告记录吗？" @confirm="deleteItem(record.id)">
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
      title="编辑报告记录"
      :confirm-loading="submitting"
      @ok="submit"
      destroy-on-close
    >
      <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
      <a-form layout="vertical">
        <!-- 行注释：渲染表单项“报告类型”，接收用户输入。 -->
        <a-form-item label="报告类型">
          <!-- 行注释：渲染下拉选择框，让用户选择筛选或分类选项。 -->
          <a-select v-model:value="editForm.report_type">
            <!-- 行注释：渲染下拉选择框，让用户选择筛选或分类选项。 -->
            <a-select-option value="comprehensive">comprehensive</a-select-option>
            <!-- 行注释：渲染下拉选择框，让用户选择筛选或分类选项。 -->
            <a-select-option value="energy">energy</a-select-option>
            <!-- 行注释：渲染下拉选择框，让用户选择筛选或分类选项。 -->
            <a-select-option value="carbon">carbon</a-select-option>
            <!-- 行注释：渲染下拉选择框，让用户选择筛选或分类选项。 -->
            <a-select-option value="optimization">optimization</a-select-option>
          <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
          </a-select>
        <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
        </a-form-item>
        <!-- 行注释：渲染表单项“报告周期”，接收用户输入。 -->
        <a-form-item label="报告周期">
          <!-- 行注释：渲染下拉选择框，让用户选择筛选或分类选项。 -->
          <a-select v-model:value="editForm.report_period">
            <!-- 行注释：渲染下拉选择框，让用户选择筛选或分类选项。 -->
            <a-select-option value="week">week</a-select-option>
            <!-- 行注释：渲染下拉选择框，让用户选择筛选或分类选项。 -->
            <a-select-option value="month">month</a-select-option>
            <!-- 行注释：渲染下拉选择框，让用户选择筛选或分类选项。 -->
            <a-select-option value="year">year</a-select-option>
          <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
          </a-select>
        <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
        </a-form-item>
        <!-- 行注释：渲染表单项“状态”，接收用户输入。 -->
        <a-form-item label="状态">
          <!-- 行注释：渲染下拉选择框，让用户选择筛选或分类选项。 -->
          <a-select v-model:value="editForm.status">
            <!-- 行注释：渲染下拉选择框，让用户选择筛选或分类选项。 -->
            <a-select-option value="completed">completed</a-select-option>
            <!-- 行注释：渲染下拉选择框，让用户选择筛选或分类选项。 -->
            <a-select-option value="processing">processing</a-select-option>
            <!-- 行注释：渲染下拉选择框，让用户选择筛选或分类选项。 -->
            <a-select-option value="failed">failed</a-select-option>
          <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
          </a-select>
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
import { onMounted, reactive, ref, watch, computed } from 'vue';
// 行注释：引入消息提示组件，用于向用户显示登录过期或操作失败。
import { message } from 'ant-design-vue';
// 行注释：引入当前页面或组件需要的依赖。
import api from '../api/http';
// 行注释：引入登录状态工具，用于读取 token 或清空会话。
import { authState, canWrite } from '../auth/session';

// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const loading = ref(false);
// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const writable = computed(() => canWrite());
// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const reportType = ref('comprehensive');
// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const period = ref('month');

// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const reportsList = ref([]);
// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const energyReport = ref(null);
// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const carbonReport = ref(null);
// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const optimizationReport = ref(null);

// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const reportColumns = [
  // 行注释：设置样式属性，控制页面元素的展示效果。
  { title: '报告ID', dataIndex: 'id', key: 'id' },
  // 行注释：设置样式属性，控制页面元素的展示效果。
  { title: '报告类型', dataIndex: 'report_type', key: 'report_type' },
  // 行注释：设置样式属性，控制页面元素的展示效果。
  { title: '报告周期', dataIndex: 'report_period', key: 'report_period' },
  // 行注释：设置样式属性，控制页面元素的展示效果。
  { title: '状态', dataIndex: 'status', key: 'status' },
  // 行注释：设置样式属性，控制页面元素的展示效果。
  { title: '生成时间', dataIndex: 'generated_at', key: 'generated_at' },
  // 行注释：设置样式属性，控制页面元素的展示效果。
  { title: '操作', key: 'action', width: 220 }
// 行注释：结束数组配置，完成列表、菜单或图表数据的组装。
];

// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const modalOpen = ref(false);
// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const submitting = ref(false);
// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const editingId = ref(null);
// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const editForm = reactive({
  // 行注释：设置样式属性，控制页面元素的展示效果。
  report_type: 'comprehensive',
  // 行注释：设置样式属性，控制页面元素的展示效果。
  report_period: 'month',
  // 行注释：设置样式属性，控制页面元素的展示效果。
  status: 'completed'
// 行注释：结束当前脚本代码块。
});

// 答辩讲解:
// - 函数： fetchReportsList
// - 作用：拉取后端最新数据并同步响应式状态用于渲染。
// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
// 方法用法：页面交互或状态变化时调用 fetchReportsList，用于完成当前前端模块的一段处理逻辑。
const fetchReportsList = async () => {
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  try {
    // 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
    const res = await api.get('/reports/list');
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    reportsList.value = res.reports || [];
  // 行注释：结束 try 代码块并进入异常处理流程。
  } catch (error) {
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    reportsList.value = [];
  // 行注释：结束当前脚本代码块。
  }
// 行注释：结束当前脚本代码块。
};

// 答辩讲解:
// - 函数： fetchReportData
// - 作用：拉取后端最新数据并同步响应式状态用于渲染。
// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
// 方法用法：页面交互或状态变化时调用 fetchReportData，用于完成当前前端模块的一段处理逻辑。
const fetchReportData = async () => {
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  try {
    // 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
    const [energyRes, carbonRes, optimizationRes] = await Promise.all([
      // 行注释：发起接口请求或读取对象数据，获取页面需要的业务信息。
      api.get(`/reports/energy?period=${period.value}`),
      // 行注释：发起接口请求或读取对象数据，获取页面需要的业务信息。
      api.get(`/reports/carbon?period=${period.value}`),
      // 行注释：发起接口请求或读取对象数据，获取页面需要的业务信息。
      api.get('/reports/optimization')
    // 行注释：结束数组配置，完成列表、菜单或图表数据的组装。
    ]);

    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    energyReport.value = energyRes.report;
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    carbonReport.value = carbonRes.report;
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    optimizationReport.value = optimizationRes.report;
  // 行注释：结束 try 代码块并进入异常处理流程。
  } catch (error) {
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    energyReport.value = null;
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    carbonReport.value = null;
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    optimizationReport.value = null;
  // 行注释：结束当前脚本代码块。
  }
// 行注释：结束当前脚本代码块。
};

// 答辩讲解:
// - 函数： generatePdf
// - 作用：用于页面交互与状态流转的前端辅助函数。
// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
// 方法用法：页面交互或状态变化时调用 generatePdf，用于完成当前前端模块的一段处理逻辑。
const generatePdf = async () => {
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  loading.value = true;
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  try {
    // 行注释：发起接口请求或读取对象数据，获取页面需要的业务信息。
    await api.post('/reports/generate-pdf', {
      // 行注释：设置样式属性，控制页面元素的展示效果。
      report_type: reportType.value,
      // 行注释：设置样式属性，控制页面元素的展示效果。
      period: period.value
    // 行注释：结束当前脚本代码块。
    });
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    message.success('报告生成成功');
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    await fetchReportsList();
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
// - 函数： download
// - 作用：请求受保护文件流并触发浏览器端下载。
// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
// 方法用法：页面交互或状态变化时调用 download，用于完成当前前端模块的一段处理逻辑。
const download = async (filename) => {
  // 行注释：根据条件控制前端流程，例如权限判断、跳转或错误处理。
  if (!filename) {
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    message.warning('报告文件名为空');
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    return;
  // 行注释：结束当前脚本代码块。
  }

  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  try {
    // 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
    const response = await fetch(`/api/reports/download/${encodeURIComponent(filename)}`, {
      // 行注释：设置样式属性，控制页面元素的展示效果。
      headers: {
        // 行注释：判断本地是否已有登录 token，有 token 才能访问受保护接口。
        Authorization: `Bearer ${authState.token}`
      // 行注释：结束当前脚本代码块。
      }
    // 行注释：结束当前脚本代码块。
    });

    // 行注释：根据条件控制前端流程，例如权限判断、跳转或错误处理。
    if (!response.ok) {
      // 行注释：设置样式属性，控制页面元素的展示效果。
      throw new Error(`download failed: ${response.status}`);
    // 行注释：结束当前脚本代码块。
    }

    // 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
    const blob = await response.blob();
    // 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
    const blobUrl = window.URL.createObjectURL(blob);
    // 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
    const link = document.createElement('a');
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    link.href = blobUrl;
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    link.download = filename;
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    document.body.appendChild(link);
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    link.click();
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    link.remove();
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    window.URL.revokeObjectURL(blobUrl);
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    message.success('报告下载已开始');
  // 行注释：结束 try 代码块并进入异常处理流程。
  } catch (error) {
    // 行注释：显示错误提示，让用户知道当前操作为什么失败。
    message.error('报告下载失败，请重新登录后重试');
  // 行注释：结束当前脚本代码块。
  }
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
  editForm.report_type = row.report_type || 'comprehensive';
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  editForm.report_period = row.report_period || 'month';
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  editForm.status = row.status || 'completed';
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
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  submitting.value = true;
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  try {
    // 行注释：发起接口请求或读取对象数据，获取页面需要的业务信息。
    await api.put(`/reports/${editingId.value}`, {
      // 行注释：设置样式属性，控制页面元素的展示效果。
      report_type: editForm.report_type,
      // 行注释：设置样式属性，控制页面元素的展示效果。
      report_period: editForm.report_period,
      // 行注释：设置样式属性，控制页面元素的展示效果。
      status: editForm.status
    // 行注释：结束当前脚本代码块。
    });
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    message.success('报告记录已更新');
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    modalOpen.value = false;
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    await fetchReportsList();
  // 行注释：结束 try 代码块并进入异常处理流程。
  } catch (error) {
    // 行注释：根据条件控制前端流程，例如权限判断、跳转或错误处理。
    if (error?.response?.status === 404) {
      // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
      message.warning('报告记录不存在，已刷新列表');
      // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
      await fetchReportsList();
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
    await api.delete(`/reports/${id}`);
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    message.success('报告记录已删除');
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    await fetchReportsList();
  // 行注释：结束 try 代码块并进入异常处理流程。
  } catch (error) {
    // 行注释：根据条件控制前端流程，例如权限判断、跳转或错误处理。
    if (error?.response?.status === 404) {
      // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
      message.warning('报告记录不存在，已刷新列表');
      // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
      await fetchReportsList();
    // 行注释：结束当前脚本代码块。
    }
  // 行注释：结束当前脚本代码块。
  }
// 行注释：结束当前脚本代码块。
};

// 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
onMounted(async () => {
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  try {
    // 行注释：并行请求多个接口，减少页面等待时间。
    await Promise.all([fetchReportsList(), fetchReportData()]);
  // 行注释：结束 try 代码块并进入异常处理流程。
  } catch (error) {
    // 错误提示已由 axios 拦截器统一处理。
  // 行注释：结束当前脚本代码块。
  }
// 行注释：结束当前脚本代码块。
});

// 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
watch(period, fetchReportData);
// 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
</script>



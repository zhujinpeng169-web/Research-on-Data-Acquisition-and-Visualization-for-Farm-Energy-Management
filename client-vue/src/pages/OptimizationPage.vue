<!-- 逐行注释标记：已按业务语义处理，仅增加中文注释，不改变页面逻辑 -->
<!--
  <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
  答辩注释:
  <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
  - 文件： OptimizationPage
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
    <h1 class="page-title">节能管理</h1>

    <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
    <a-row :gutter="16">
      <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
      <a-col :span="6"><a-card><a-statistic title="总发电量" :value="analysis.pattern.totalGenerated" :precision="2" suffix="kWh" /></a-card></a-col>
      <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
      <a-col :span="6"><a-card><a-statistic title="总消耗量" :value="analysis.pattern.totalConsumed" :precision="2" suffix="kWh" /></a-card></a-col>
      <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
      <a-col :span="6"><a-card><a-statistic title="能源盈余" :value="analysis.pattern.surplus" :precision="2" suffix="kWh" /></a-card></a-col>
      <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
      <a-col :span="6"><a-card><a-statistic title="利用率" :value="analysis.pattern.utilizationRate" :precision="2" suffix="%" /></a-card></a-col>
    <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
    </a-row>

    <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
    <a-row :gutter="16" class="section-gap">
      <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
      <a-col :span="12">
        <!-- 行注释：渲染“平均效率”卡片，用于承载当前模块信息。 -->
        <a-card title="平均效率">
          <!-- 行注释：渲染进度图，用于展示效率、碳中和等百分比指标。 -->
          <a-progress type="circle" :percent="Number(analysis.pattern.avgEfficiency || 0)" />
        <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
        </a-card>
      <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
      </a-col>
      <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
      <a-col :span="12">
        <!-- 行注释：渲染“节能统计”卡片，用于承载当前模块信息。 -->
        <a-card title="节能统计">
          <!-- 行注释：把响应式变量渲染到页面上，数据变化时页面会自动更新。 -->
          <p>总建议数: {{ savings.total_recommendations || 0 }}</p>
          <!-- 行注释：把响应式变量渲染到页面上，数据变化时页面会自动更新。 -->
          <p>潜在总节省 {{ savings.total_potential_savings || 0 }} kWh</p>
          <!-- 行注释：把响应式变量渲染到页面上，数据变化时页面会自动更新。 -->
          <p>已实现节省 {{ savings.realized_savings || 0 }} kWh</p>
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
        <!-- 行注释：渲染“能源分配建议”卡片，用于承载当前模块信息。 -->
        <a-card title="能源分配建议">
          <!-- 行注释：渲染 ECharts 图表，把趋势、占比或预测结果可视化。 -->
          <v-chart :option="allocationOption" autoresize style="height: 320px" />
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
        <!-- 行注释：渲染“节能建议列表”卡片，用于承载当前模块信息。 -->
        <a-card title="节能建议列表" :loading="loading">
          <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
          <template #extra>
            <!-- 行注释：渲染操作按钮，触发新增、保存、删除或查询动作。 -->
            <a-button v-if="writable" type="primary" @click="openCreate">新增建议</a-button>
          <!-- 行注释：结束模板区域，页面结构到这里完成。 -->
          </template>
          <!-- 行注释：渲染数据表格，把后端列表数据按列展示。 -->
          <a-table :columns="recommendationColumns" :data-source="recommendations" row-key="id" :pagination="{ pageSize: 10 }">
            <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
            <template #bodyCell="{ column, record }">
              <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
              <template v-if="column.key === 'priority'">
                <!-- 行注释：把响应式变量渲染到页面上，数据变化时页面会自动更新。 -->
                <a-tag :color="priorityColor(record.priority)">{{ record.priority }}</a-tag>
              <!-- 行注释：结束模板区域，页面结构到这里完成。 -->
              </template>
              <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
              <template v-if="column.key === 'status'">
                <!-- 行注释：把响应式变量渲染到页面上，数据变化时页面会自动更新。 -->
                <a-tag :color="record.status === 'implemented' ? 'green' : 'orange'">{{ record.status }}</a-tag>
              <!-- 行注释：结束模板区域，页面结构到这里完成。 -->
              </template>
              <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
              <template v-if="column.key === 'action'">
                <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
                <a-space>
                  <!-- 行注释：渲染操作按钮，触发新增、保存、删除或查询动作。 -->
                  <a-button size="small" @click="openEdit(record)">编辑</a-button>
                  <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
                  <a-popconfirm title="确认删除该建议吗？" @confirm="deleteItem(record.id)">
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
      :title="editingId ? '编辑建议' : '新增建议'"
      :confirm-loading="submitting"
      @ok="submit"
      destroy-on-close
    >
      <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
      <a-form layout="vertical">
        <!-- 行注释：渲染表单项“建议类型”，接收用户输入。 -->
        <a-form-item label="建议类型" required>
          <!-- 行注释：渲染下拉选择框，让用户选择筛选或分类选项。 -->
          <a-select v-model:value="form.recommendation_type">
            <!-- 行注释：渲染下拉选择框，让用户选择筛选或分类选项。 -->
            <a-select-option value="efficiency">efficiency</a-select-option>
            <!-- 行注释：渲染下拉选择框，让用户选择筛选或分类选项。 -->
            <a-select-option value="utilization">utilization</a-select-option>
            <!-- 行注释：渲染下拉选择框，让用户选择筛选或分类选项。 -->
            <a-select-option value="storage">storage</a-select-option>
          <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
          </a-select>
        <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
        </a-form-item>
        <!-- 行注释：渲染表单项“建议描述”，接收用户输入。 -->
        <a-form-item label="建议描述" required>
          <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
          <a-textarea v-model:value="form.description" :rows="4" />
        <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
        </a-form-item>
        <!-- 行注释：渲染表单项“潜在节省(kWh)”，接收用户输入。 -->
        <a-form-item label="潜在节省(kWh)">
          <!-- 行注释：渲染输入框，接收用户填写的数据。 -->
          <a-input-number v-model:value="form.potential_savings" :min="0" style="width: 100%" />
        <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
        </a-form-item>
        <!-- 行注释：渲染表单项“优先级”，接收用户输入。 -->
        <a-form-item label="优先级">
          <!-- 行注释：渲染下拉选择框，让用户选择筛选或分类选项。 -->
          <a-select v-model:value="form.priority">
            <!-- 行注释：渲染下拉选择框，让用户选择筛选或分类选项。 -->
            <a-select-option value="high">high</a-select-option>
            <!-- 行注释：渲染下拉选择框，让用户选择筛选或分类选项。 -->
            <a-select-option value="medium">medium</a-select-option>
            <!-- 行注释：渲染下拉选择框，让用户选择筛选或分类选项。 -->
            <a-select-option value="low">low</a-select-option>
          <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
          </a-select>
        <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
        </a-form-item>
        <!-- 行注释：渲染表单项“状态”，接收用户输入。 -->
        <a-form-item label="状态">
          <!-- 行注释：渲染下拉选择框，让用户选择筛选或分类选项。 -->
          <a-select v-model:value="form.status">
            <!-- 行注释：渲染下拉选择框，让用户选择筛选或分类选项。 -->
            <a-select-option value="pending">pending</a-select-option>
            <!-- 行注释：渲染下拉选择框，让用户选择筛选或分类选项。 -->
            <a-select-option value="implemented">implemented</a-select-option>
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
import { computed, onMounted, reactive, ref } from 'vue';
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
const analysis = ref({
  // 行注释：设置样式属性，控制页面元素的展示效果。
  pattern: { totalGenerated: 0, totalConsumed: 0, surplus: 0, utilizationRate: 0, avgEfficiency: 0 }
// 行注释：结束当前脚本代码块。
});
// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const recommendations = ref([]);
// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const allocation = ref([]);
// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const savings = ref({});

// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const writable = computed(() => canWrite());

// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const recommendationColumns = computed(() => {
  // 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
  const base = [
    // 行注释：设置样式属性，控制页面元素的展示效果。
    { title: '建议类型', dataIndex: 'recommendation_type', key: 'recommendation_type' },
    // 行注释：设置样式属性，控制页面元素的展示效果。
    { title: '描述', dataIndex: 'description', key: 'description' },
    // 行注释：设置样式属性，控制页面元素的展示效果。
    { title: '潜在节省(kWh)', dataIndex: 'potential_savings', key: 'potential_savings' },
    // 行注释：设置样式属性，控制页面元素的展示效果。
    { title: '优先级', dataIndex: 'priority', key: 'priority' },
    // 行注释：设置样式属性，控制页面元素的展示效果。
    { title: '状态', dataIndex: 'status', key: 'status' }
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
  recommendation_type: 'efficiency',
  // 行注释：设置样式属性，控制页面元素的展示效果。
  description: '',
  // 行注释：设置样式属性，控制页面元素的展示效果。
  potential_savings: 0,
  // 行注释：设置样式属性，控制页面元素的展示效果。
  priority: 'medium',
  // 行注释：设置样式属性，控制页面元素的展示效果。
  status: 'pending'
// 行注释：结束当前脚本代码块。
});

// 答辩讲解:
// - 函数： resetForm
// - 作用：在用户操作前将表单/筛选状态重置为安全默认值。
// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
// 方法用法：打开新增或提交后调用，用来把表单恢复到默认初始状态。
const resetForm = () => {
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  form.recommendation_type = 'efficiency';
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  form.description = '';
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  form.potential_savings = 0;
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  form.priority = 'medium';
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  form.status = 'pending';
// 行注释：结束当前脚本代码块。
};

// 答辩讲解:
// - 函数： fetchData
// - 作用：拉取后端最新数据并同步响应式状态用于渲染。
// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
// 方法用法：页面初始化或刷新时调用，用来集中请求后端数据并更新页面状态。
const fetchData = async () => {
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  try {
    // 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
    const [analysisRes, recommendationsRes, allocationRes, savingsRes] = await Promise.all([
      // 行注释：发起接口请求或读取对象数据，获取页面需要的业务信息。
      api.get('/optimization/analysis'),
      // 行注释：发起接口请求或读取对象数据，获取页面需要的业务信息。
      api.get('/optimization/recommendations'),
      // 行注释：发起接口请求或读取对象数据，获取页面需要的业务信息。
      api.get('/optimization/allocation'),
      // 行注释：发起接口请求或读取对象数据，获取页面需要的业务信息。
      api.get('/optimization/savings-statistics')
    // 行注释：结束数组配置，完成列表、菜单或图表数据的组装。
    ]);

    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    analysis.value = analysisRes || analysis.value;
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    recommendations.value = recommendationsRes.recommendations || [];
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    allocation.value = allocationRes.allocation || [];
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    savings.value = savingsRes.statistics || {};
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
  form.recommendation_type = row.recommendation_type;
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  form.description = row.description;
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  form.potential_savings = Number(row.potential_savings || 0);
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  form.priority = row.priority || 'medium';
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  form.status = row.status || 'pending';
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
  if (!form.recommendation_type || !form.description) {
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    message.warning('请填写建议类型和描述');
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    return;
  // 行注释：结束当前脚本代码块。
  }

  // 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
  const payload = {
    // 行注释：设置样式属性，控制页面元素的展示效果。
    recommendation_type: form.recommendation_type,
    // 行注释：设置样式属性，控制页面元素的展示效果。
    description: form.description,
    // 行注释：设置样式属性，控制页面元素的展示效果。
    potential_savings: form.potential_savings,
    // 行注释：设置样式属性，控制页面元素的展示效果。
    priority: form.priority,
    // 行注释：设置样式属性，控制页面元素的展示效果。
    status: form.status
  // 行注释：结束当前脚本代码块。
  };

  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  submitting.value = true;
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  try {
    // 行注释：根据条件控制前端流程，例如权限判断、跳转或错误处理。
    if (editingId.value) {
      // 行注释：发起接口请求或读取对象数据，获取页面需要的业务信息。
      await api.put(`/optimization/recommendations/${editingId.value}`, payload);
      // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
      message.success('建议已更新');
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    } else {
      // 行注释：发起接口请求或读取对象数据，获取页面需要的业务信息。
      await api.post('/optimization/recommendations', payload);
      // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
      message.success('建议已创建');
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
      message.warning('建议不存在，已刷新列表');
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
    await api.delete(`/optimization/recommendations/${id}`);
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    message.success('建议已删除');
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    await fetchData();
  // 行注释：结束 try 代码块并进入异常处理流程。
  } catch (error) {
    // 行注释：根据条件控制前端流程，例如权限判断、跳转或错误处理。
    if (error?.response?.status === 404) {
      // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
      message.warning('建议不存在，已刷新列表');
      // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
      await fetchData();
    // 行注释：结束当前脚本代码块。
    }
  // 行注释：结束当前脚本代码块。
  }
// 行注释：结束当前脚本代码块。
};

// 答辩讲解:
// - 函数： priorityColor
// - 作用：用于页面交互与状态流转的前端辅助函数。
// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
// 方法用法：表格渲染标签时调用，根据状态或角色返回对应颜色。
const priorityColor = (priority) => {
  // 行注释：根据条件控制前端流程，例如权限判断、跳转或错误处理。
  if (priority === 'high') return 'red';
  // 行注释：根据条件控制前端流程，例如权限判断、跳转或错误处理。
  if (priority === 'medium') return 'orange';
  // 行注释：返回处理结果，交给调用方或路由守卫继续使用。
  return 'green';
// 行注释：结束当前脚本代码块。
};

// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const allocationOption = computed(() => ({
  // 行注释：设置样式属性，控制页面元素的展示效果。
  tooltip: { trigger: 'axis' },
  // 行注释：设置样式属性，控制页面元素的展示效果。
  legend: { data: ['当前发电量', '优化潜力'] },
  // 行注释：设置样式属性，控制页面元素的展示效果。
  grid: { left: 50, right: 20, top: 40, bottom: 30 },
  // 行注释：设置样式属性，控制页面元素的展示效果。
  xAxis: { type: 'category', data: allocation.value.map((item) => item.device_type) },
  // 行注释：设置样式属性，控制页面元素的展示效果。
  yAxis: { type: 'value' },
  // 行注释：设置样式属性，控制页面元素的展示效果。
  series: [
    // 行注释：设置样式属性，控制页面元素的展示效果。
    { name: '当前发电量', type: 'bar', data: allocation.value.map((item) => Number(item.current_generation || 0)) },
    // 行注释：设置样式属性，控制页面元素的展示效果。
    { name: '优化潜力', type: 'bar', data: allocation.value.map((item) => Number(item.optimization_potential || 0)) }
  // 行注释：结束数组配置，完成列表、菜单或图表数据的组装。
  ]
// 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
}));

// 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
onMounted(fetchData);
// 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
</script>



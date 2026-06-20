<!-- 逐行注释标记：已按业务语义处理，仅增加中文注释，不改变页面逻辑 -->
<!--
  <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
  答辩注释:
  <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
  - 文件： MonitoringPage
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
    <h1 class="page-title">能源监测</h1>

    <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
    <a-row :gutter="16">
      <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
      <a-col :span="24">
        <!-- 行注释：渲染“能源设备列表”卡片，用于承载当前模块信息。 -->
        <a-card title="能源设备列表" :loading="loading">
          <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
          <template #extra>
            <!-- 行注释：渲染操作按钮，触发新增、保存、删除或查询动作。 -->
            <a-button v-if="writable" type="primary" @click="openDeviceCreate">新增设备</a-button>
          <!-- 行注释：结束模板区域，页面结构到这里完成。 -->
          </template>
          <!-- 行注释：渲染数据表格，把后端列表数据按列展示。 -->
          <a-table
            :columns="deviceColumns"
            :data-source="devices"
            row-key="id"
            :pagination="{ pageSize: 10 }"
          >
            <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
            <template #bodyCell="{ column, record }">
              <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
              <template v-if="column.key === 'status'">
                <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
                <a-tag :color="record.status === 'active' ? 'green' : 'default'">
                  <!-- 行注释：把响应式变量渲染到页面上，数据变化时页面会自动更新。 -->
                  {{ record.status_label || formatStatus(record.status) }}
                <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
                </a-tag>
              <!-- 行注释：结束模板区域，页面结构到这里完成。 -->
              </template>
              <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
              <template v-if="column.key === 'action'">
                <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
                <a-space>
                  <!-- 行注释：渲染操作按钮，触发新增、保存、删除或查询动作。 -->
                  <a-button size="small" @click="openDeviceEdit(record)">编辑</a-button>
                  <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
                  <a-popconfirm title="确认删除该设备吗？" @confirm="deleteDevice(record.id)">
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

    <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
    <a-row :gutter="16" class="section-gap">
      <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
      <a-col :span="24">
        <!-- 行注释：渲染“监测数据记录”卡片，用于承载当前模块信息。 -->
        <a-card title="监测数据记录" :loading="loading">
          <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
          <template #extra>
            <!-- 行注释：渲染操作按钮，触发新增、保存、删除或查询动作。 -->
            <a-button v-if="writable" type="primary" @click="openRecordCreate">新增数据</a-button>
          <!-- 行注释：结束模板区域，页面结构到这里完成。 -->
          </template>
          <!-- 行注释：渲染数据表格，把后端列表数据按列展示。 -->
          <a-table
            :columns="recordColumns"
            :data-source="records"
            row-key="id"
            :pagination="{ pageSize: 10 }"
          >
            <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
            <template #bodyCell="{ column, record }">
              <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
              <template v-if="column.key === 'action'">
                <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
                <a-space>
                  <!-- 行注释：渲染操作按钮，触发新增、保存、删除或查询动作。 -->
                  <a-button size="small" @click="openRecordEdit(record)">编辑</a-button>
                  <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
                  <a-popconfirm title="确认删除该记录吗？" @confirm="deleteRecord(record.id)">
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

    <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
    <a-row :gutter="16" class="section-gap">
      <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
      <a-col :span="12">
        <!-- 行注释：渲染“实时发电与消耗对比”卡片，用于承载当前模块信息。 -->
        <a-card title="实时发电与消耗对比">
          <!-- 行注释：渲染 ECharts 图表，把趋势、占比或预测结果可视化。 -->
          <v-chart :option="barOption" autoresize style="height: 300px" />
        <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
        </a-card>
      <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
      </a-col>
      <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
      <a-col :span="12">
        <!-- 行注释：渲染“按设备类型统计发电占比”卡片，用于承载当前模块信息。 -->
        <a-card title="按设备类型统计发电占比">
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
        <!-- 行注释：渲染“设备效率趋势”卡片，用于承载当前模块信息。 -->
        <a-card title="设备效率趋势">
          <!-- 行注释：渲染 ECharts 图表，把趋势、占比或预测结果可视化。 -->
          <v-chart :option="lineOption" autoresize style="height: 320px" />
        <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
        </a-card>
      <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
      </a-col>
    <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
    </a-row>

    <!-- 行注释：渲染弹窗表单，用于新增或编辑业务数据。 -->
    <a-modal
      v-model:open="deviceModalOpen"
      :title="deviceEditingId ? '编辑设备' : '新增设备'"
      :confirm-loading="deviceSubmitting"
      @ok="submitDevice"
      destroy-on-close
    >
      <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
      <a-form layout="vertical">
        <!-- 行注释：渲染表单项“设备名称”，接收用户输入。 -->
        <a-form-item label="设备名称" required>
          <!-- 行注释：渲染输入框，接收用户填写的数据。 -->
          <a-input v-model:value="deviceForm.device_name" />
        <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
        </a-form-item>
        <!-- 行注释：渲染表单项“设备类型”，接收用户输入。 -->
        <a-form-item label="设备类型" required>
          <!-- 行注释：渲染下拉选择框，让用户选择筛选或分类选项。 -->
          <a-select v-model:value="deviceForm.device_type">
            <!-- 行注释：渲染下拉选择框，让用户选择筛选或分类选项。 -->
            <a-select-option value="solar">太阳能</a-select-option>
            <!-- 行注释：渲染下拉选择框，让用户选择筛选或分类选项。 -->
            <a-select-option value="wind">风能</a-select-option>
            <!-- 行注释：渲染下拉选择框，让用户选择筛选或分类选项。 -->
            <a-select-option value="biomass">生物质能</a-select-option>
            <!-- 行注释：渲染下拉选择框，让用户选择筛选或分类选项。 -->
            <a-select-option value="grid">电网</a-select-option>
          <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
          </a-select>
        <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
        </a-form-item>
        <!-- 行注释：渲染表单项“容量(kW)”，接收用户输入。 -->
        <a-form-item label="容量(kW)">
          <!-- 行注释：渲染输入框，接收用户填写的数据。 -->
          <a-input-number v-model:value="deviceForm.capacity" :min="0" style="width: 100%" />
        <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
        </a-form-item>
        <!-- 行注释：渲染表单项“位置”，接收用户输入。 -->
        <a-form-item label="位置">
          <!-- 行注释：渲染输入框，接收用户填写的数据。 -->
          <a-input v-model:value="deviceForm.location" />
        <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
        </a-form-item>
        <!-- 行注释：渲染表单项“安装日期”，接收用户输入。 -->
        <a-form-item label="安装日期">
          <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
          <a-date-picker v-model:value="deviceForm.installation_date" style="width: 100%" />
        <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
        </a-form-item>
        <!-- 行注释：渲染表单项“状态”，接收用户输入。 -->
        <a-form-item v-if="deviceEditingId" label="状态">
          <!-- 行注释：渲染下拉选择框，让用户选择筛选或分类选项。 -->
          <a-select v-model:value="deviceForm.status">
            <!-- 行注释：渲染下拉选择框，让用户选择筛选或分类选项。 -->
            <a-select-option value="active">运行中</a-select-option>
            <!-- 行注释：渲染下拉选择框，让用户选择筛选或分类选项。 -->
            <a-select-option value="inactive">停用</a-select-option>
            <!-- 行注释：渲染下拉选择框，让用户选择筛选或分类选项。 -->
            <a-select-option value="deleted">已删除</a-select-option>
          <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
          </a-select>
        <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
        </a-form-item>
      <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
      </a-form>
    <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
    </a-modal>

    <!-- 行注释：渲染弹窗表单，用于新增或编辑业务数据。 -->
    <a-modal
      v-model:open="recordModalOpen"
      :title="recordEditingId ? '编辑监测记录' : '新增监测记录'"
      :confirm-loading="recordSubmitting"
      @ok="submitRecord"
      destroy-on-close
    >
      <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
      <a-form layout="vertical">
        <!-- 行注释：渲染表单项“设备”，接收用户输入。 -->
        <a-form-item label="设备" required>
          <!-- 行注释：渲染下拉选择框，让用户选择筛选或分类选项。 -->
          <a-select v-model:value="recordForm.device_id">
            <!-- 行注释：渲染下拉选择框，让用户选择筛选或分类选项。 -->
            <a-select-option v-for="device in activeDevices" :key="device.id" :value="device.id">
              <!-- 行注释：把响应式变量渲染到页面上，数据变化时页面会自动更新。 -->
              {{ device.device_name_label || device.device_name }} ({{ device.device_type_label || formatDeviceType(device.device_type) }})
            <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
            </a-select-option>
          <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
          </a-select>
        <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
        </a-form-item>
        <!-- 行注释：渲染表单项“发电量(kWh)”，接收用户输入。 -->
        <a-form-item label="发电量(kWh)">
          <!-- 行注释：渲染输入框，接收用户填写的数据。 -->
          <a-input-number v-model:value="recordForm.energy_generated" :min="0" style="width: 100%" />
        <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
        </a-form-item>
        <!-- 行注释：渲染表单项“消耗量(kWh)”，接收用户输入。 -->
        <a-form-item label="消耗量(kWh)">
          <!-- 行注释：渲染输入框，接收用户填写的数据。 -->
          <a-input-number v-model:value="recordForm.energy_consumed" :min="0" style="width: 100%" />
        <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
        </a-form-item>
        <!-- 行注释：渲染表单项“效率(%)”，接收用户输入。 -->
        <a-form-item label="效率(%)">
          <!-- 行注释：渲染输入框，接收用户填写的数据。 -->
          <a-input-number v-model:value="recordForm.efficiency" :min="0" :max="100" style="width: 100%" />
        <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
        </a-form-item>
        <!-- 行注释：渲染表单项“温度(°C)”，接收用户输入。 -->
        <a-form-item label="温度(°C)">
          <!-- 行注释：渲染输入框，接收用户填写的数据。 -->
          <a-input-number v-model:value="recordForm.temperature" style="width: 100%" />
        <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
        </a-form-item>
        <!-- 行注释：渲染表单项“湿度(%)”，接收用户输入。 -->
        <a-form-item label="湿度(%)">
          <!-- 行注释：渲染输入框，接收用户填写的数据。 -->
          <a-input-number v-model:value="recordForm.humidity" :min="0" :max="100" style="width: 100%" />
        <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
        </a-form-item>
        <!-- 行注释：渲染表单项“时间”，接收用户输入。 -->
        <a-form-item v-if="recordEditingId" label="时间">
          <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
          <a-date-picker
            v-model:value="recordForm.timestamp"
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
import { computed, onBeforeUnmount, onMounted, reactive, ref } from 'vue';
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
const devices = ref([]);
// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const records = ref([]);
// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const statistics = ref([]);
// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const realtimeData = ref([]);

// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const writable = computed(() => canWrite());
// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const activeDevices = computed(() => devices.value.filter((item) => item.status === 'active'));

// 行注释：定义设备名称中文映射，兼容旧数据库中已经存在的英文演示数据。
const deviceNameMap = {
  // 行注释：把旧演示数据中的英文名称转换为中文名称，方便设备表格展示。
  'Solar Panel A': '光伏板A',
  // 行注释：把旧演示数据中的英文名称转换为中文名称，方便设备表格展示。
  'Solar Panel B': '光伏板B',
  // 行注释：把旧演示数据中的英文名称转换为中文名称，方便设备表格展示。
  'Wind Turbine A': '风力发电机A',
  // 行注释：把旧演示数据中的英文名称转换为中文名称，方便设备表格展示。
  'Biomass Unit A': '生物质发电单元A'
// 行注释：结束对象配置，完成英文设备名称到中文设备名称的对应关系。
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

// 行注释：定义状态中文映射，让设备状态在页面上以中文显示。
const statusMap = {
  // 行注释：active 表示设备正在运行。
  active: '运行中',
  // 行注释：inactive 表示设备暂时停用。
  inactive: '停用',
  // 行注释：deleted 表示设备已经被逻辑删除。
  deleted: '已删除'
// 行注释：结束对象配置，完成状态编码到中文名称的对应关系。
};

// 行注释：定义 formatDeviceName 方法，把设备名称统一转成中文展示。
// 方法用法：设备列表、监测记录和图表渲染前调用，避免旧英文演示数据直接显示。
const formatDeviceName = (value) => deviceNameMap[value] || value || '-';

// 行注释：定义 formatDeviceType 方法，把设备类型编码统一转成中文展示。
// 方法用法：设备列表、下拉框和图表渲染前调用，让页面展示中文类型。
const formatDeviceType = (value) => deviceTypeMap[value] || value || '-';

// 行注释：定义 formatStatus 方法，把设备状态编码统一转成中文展示。
// 方法用法：状态标签渲染前调用，让老师能直接看懂设备运行状态。
const formatStatus = (value) => statusMap[value] || value || '-';

// 行注释：定义 formatTimestamp 方法，把后端时间转换成“年-月-日 时:分:秒”。
// 方法用法：监测记录表格渲染时间列前调用，让时间格式更适合演示。
const formatTimestamp = (value) => (value ? dayjs(value).format('YYYY-MM-DD HH:mm:ss') : '-');

// 行注释：定义 localizeDeviceRow 方法，给设备记录补充中文展示字段。
// 方法用法：接口返回设备列表后调用，保留原始编码同时新增中文名称、类型和状态。
const localizeDeviceRow = (item) => ({
  // 行注释：保留后端原始字段，保证编辑、删除等业务逻辑仍使用原编码。
  ...item,
  // 行注释：新增中文设备名称字段，供设备列表直接显示。
  device_name_label: formatDeviceName(item.device_name),
  // 行注释：新增中文设备类型字段，供设备列表直接显示。
  device_type_label: formatDeviceType(item.device_type),
  // 行注释：新增中文状态字段，供状态标签直接显示。
  status_label: formatStatus(item.status)
// 行注释：结束对象组装，返回页面可以直接展示的一行设备数据。
});

// 行注释：定义 localizeMonitoringRow 方法，给监测记录补充中文展示字段。
// 方法用法：接口返回监测数据后调用，统一转换设备名称、设备类型和采集时间。
const localizeMonitoringRow = (item) => ({
  // 行注释：保留后端原始字段，保证编辑监测记录时仍可提交原始数据。
  ...item,
  // 行注释：新增中文设备名称字段，供监测记录表格和图表显示。
  device_name_label: formatDeviceName(item.device_name),
  // 行注释：新增中文设备类型字段，供图表和表格显示。
  device_type_label: formatDeviceType(item.device_type),
  // 行注释：新增格式化时间字段，供监测记录表格显示。
  timestamp_label: formatTimestamp(item.timestamp)
// 行注释：结束对象组装，返回页面可以直接展示的一行监测数据。
});

// 行注释：定义 localizeStatisticsRow 方法，给分类统计补充中文设备类型字段。
// 方法用法：接口返回按类型统计数据后调用，让饼图图例显示中文类型。
const localizeStatisticsRow = (item) => ({
  // 行注释：保留后端原始统计字段，保证图表数值计算不受影响。
  ...item,
  // 行注释：新增中文设备类型字段，供饼图名称和图例显示。
  device_type_label: formatDeviceType(item.device_type)
// 行注释：结束对象组装，返回页面可以直接展示的一条统计数据。
});

// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const deviceColumns = computed(() => {
  // 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
  const base = [
    // 行注释：设置样式属性，控制页面元素的展示效果。
    { title: '设备ID', dataIndex: 'id', key: 'id' },
    // 行注释：设置样式属性，控制页面元素的展示效果。
    { title: '设备名称', dataIndex: 'device_name_label', key: 'device_name_label' },
    // 行注释：设置样式属性，控制页面元素的展示效果。
    { title: '设备类型', dataIndex: 'device_type_label', key: 'device_type_label' },
    // 行注释：设置样式属性，控制页面元素的展示效果。
    { title: '容量(kW)', dataIndex: 'capacity', key: 'capacity' },
    // 行注释：设置样式属性，控制页面元素的展示效果。
    { title: '位置', dataIndex: 'location', key: 'location' },
    // 行注释：设置样式属性，控制页面元素的展示效果。
    { title: '安装日期', dataIndex: 'installation_date', key: 'installation_date' },
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
const recordColumns = computed(() => {
  // 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
  const base = [
    // 行注释：设置样式属性，控制页面元素的展示效果。
    { title: '记录ID', dataIndex: 'id', key: 'id' },
    // 行注释：设置样式属性，控制页面元素的展示效果。
    { title: '设备', dataIndex: 'device_name_label', key: 'device_name_label' },
    // 行注释：设置样式属性，控制页面元素的展示效果。
    { title: '发电(kWh)', dataIndex: 'energy_generated', key: 'energy_generated' },
    // 行注释：设置样式属性，控制页面元素的展示效果。
    { title: '消耗(kWh)', dataIndex: 'energy_consumed', key: 'energy_consumed' },
    // 行注释：设置样式属性，控制页面元素的展示效果。
    { title: '效率(%)', dataIndex: 'efficiency', key: 'efficiency' },
    // 行注释：设置样式属性，控制页面元素的展示效果。
    { title: '温度(°C)', dataIndex: 'temperature', key: 'temperature' },
    // 行注释：设置样式属性，控制页面元素的展示效果。
    { title: '湿度(%)', dataIndex: 'humidity', key: 'humidity' },
    // 行注释：设置样式属性，控制页面元素的展示效果。
    { title: '时间', dataIndex: 'timestamp_label', key: 'timestamp_label' }
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
let timer = null;

// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const deviceModalOpen = ref(false);
// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const deviceSubmitting = ref(false);
// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const deviceEditingId = ref(null);
// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const deviceForm = reactive({
  // 行注释：设置样式属性，控制页面元素的展示效果。
  device_name: '',
  // 行注释：设置样式属性，控制页面元素的展示效果。
  device_type: 'solar',
  // 行注释：设置样式属性，控制页面元素的展示效果。
  capacity: null,
  // 行注释：设置样式属性，控制页面元素的展示效果。
  location: '',
  // 行注释：设置样式属性，控制页面元素的展示效果。
  installation_date: null,
  // 行注释：设置样式属性，控制页面元素的展示效果。
  status: 'active'
// 行注释：结束当前脚本代码块。
});

// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const recordModalOpen = ref(false);
// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const recordSubmitting = ref(false);
// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const recordEditingId = ref(null);
// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const recordForm = reactive({
  // 行注释：设置样式属性，控制页面元素的展示效果。
  device_id: null,
  // 行注释：设置样式属性，控制页面元素的展示效果。
  energy_generated: 0,
  // 行注释：设置样式属性，控制页面元素的展示效果。
  energy_consumed: 0,
  // 行注释：设置样式属性，控制页面元素的展示效果。
  efficiency: 0,
  // 行注释：设置样式属性，控制页面元素的展示效果。
  temperature: 0,
  // 行注释：设置样式属性，控制页面元素的展示效果。
  humidity: 0,
  // 行注释：设置样式属性，控制页面元素的展示效果。
  timestamp: null
// 行注释：结束当前脚本代码块。
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
    const [devicesRes, recordsRes, statsRes, realtimeRes] = await Promise.all([
      // 行注释：发起接口请求或读取对象数据，获取页面需要的业务信息。
      api.get('/monitoring/devices?include_inactive=true'),
      // 行注释：发起接口请求或读取对象数据，获取页面需要的业务信息。
      api.get('/monitoring/data?limit=100'),
      // 行注释：发起接口请求或读取对象数据，获取页面需要的业务信息。
      api.get('/monitoring/statistics/by-type'),
      // 行注释：发起接口请求或读取对象数据，获取页面需要的业务信息。
      api.get('/monitoring/realtime')
    // 行注释：结束数组配置，完成列表、菜单或图表数据的组装。
    ]);

    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    devices.value = (devicesRes.devices || []).map(localizeDeviceRow);
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    records.value = (recordsRes.data || []).map(localizeMonitoringRow);
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    statistics.value = (statsRes.statistics || []).map(localizeStatisticsRow);
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    realtimeData.value = (realtimeRes.data || []).map(localizeMonitoringRow).slice(0, 20);
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
// - 函数： resetDeviceForm
// - 作用：在用户操作前将表单/筛选状态重置为安全默认值。
// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
// 方法用法：页面交互或状态变化时调用 resetDeviceForm，用于完成当前前端模块的一段处理逻辑。
const resetDeviceForm = () => {
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  deviceForm.device_name = '';
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  deviceForm.device_type = 'solar';
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  deviceForm.capacity = null;
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  deviceForm.location = '';
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  deviceForm.installation_date = null;
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  deviceForm.status = 'active';
// 行注释：结束当前脚本代码块。
};

// 答辩讲解:
// - 函数： openDeviceCreate
// - 作用：准备本地表单状态并打开弹窗供用户操作。
// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
// 方法用法：页面交互或状态变化时调用 openDeviceCreate，用于完成当前前端模块的一段处理逻辑。
const openDeviceCreate = () => {
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  deviceEditingId.value = null;
  // 行注释：设置实体字段值，准备保存到数据库或返回给前端。
  resetDeviceForm();
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  deviceModalOpen.value = true;
// 行注释：结束当前脚本代码块。
};

// 答辩讲解:
// - 函数： openDeviceEdit
// - 作用：准备本地表单状态并打开弹窗供用户操作。
// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
// 方法用法：页面交互或状态变化时调用 openDeviceEdit，用于完成当前前端模块的一段处理逻辑。
const openDeviceEdit = (row) => {
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  deviceEditingId.value = row.id;
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  deviceForm.device_name = row.device_name;
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  deviceForm.device_type = row.device_type;
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  deviceForm.capacity = row.capacity;
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  deviceForm.location = row.location;
  // 行注释：设置样式属性，控制页面元素的展示效果。
  deviceForm.installation_date = row.installation_date ? dayjs(row.installation_date) : null;
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  deviceForm.status = row.status || 'active';
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  deviceModalOpen.value = true;
// 行注释：结束当前脚本代码块。
};

// 答辩讲解:
// - 函数： submitDevice
// - 作用：用于页面交互与状态流转的前端辅助函数。
// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
// 方法用法：页面交互或状态变化时调用 submitDevice，用于完成当前前端模块的一段处理逻辑。
const submitDevice = async () => {
  // 行注释：根据条件控制前端流程，例如权限判断、跳转或错误处理。
  if (!deviceForm.device_name || !deviceForm.device_type) {
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    message.warning('请填写设备名称和类型');
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    return;
  // 行注释：结束当前脚本代码块。
  }

  // 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
  const payload = {
    // 行注释：设置样式属性，控制页面元素的展示效果。
    device_name: deviceForm.device_name,
    // 行注释：设置样式属性，控制页面元素的展示效果。
    device_type: deviceForm.device_type,
    // 行注释：设置样式属性，控制页面元素的展示效果。
    capacity: deviceForm.capacity,
    // 行注释：设置样式属性，控制页面元素的展示效果。
    location: deviceForm.location,
    // 行注释：设置样式属性，控制页面元素的展示效果。
    installation_date: deviceForm.installation_date ? deviceForm.installation_date.format('YYYY-MM-DD') : null,
    // 行注释：设置样式属性，控制页面元素的展示效果。
    status: deviceForm.status
  // 行注释：结束当前脚本代码块。
  };

  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  deviceSubmitting.value = true;
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  try {
    // 行注释：根据条件控制前端流程，例如权限判断、跳转或错误处理。
    if (deviceEditingId.value) {
      // 行注释：发起接口请求或读取对象数据，获取页面需要的业务信息。
      await api.put(`/monitoring/devices/${deviceEditingId.value}`, payload);
      // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
      message.success('设备已更新');
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    } else {
      // 行注释：发起接口请求或读取对象数据，获取页面需要的业务信息。
      await api.post('/monitoring/devices', payload);
      // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
      message.success('设备已创建');
    // 行注释：结束当前脚本代码块。
    }
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    deviceModalOpen.value = false;
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    await fetchData();
  // 行注释：结束 try 代码块并进入异常处理流程。
  } catch (error) {
    // 行注释：根据条件控制前端流程，例如权限判断、跳转或错误处理。
    if (error?.response?.status === 404) {
      // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
      message.warning('设备不存在，已刷新列表');
      // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
      await fetchData();
      // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
      deviceModalOpen.value = false;
    // 行注释：结束当前脚本代码块。
    }
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  } finally {
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    deviceSubmitting.value = false;
  // 行注释：结束当前脚本代码块。
  }
// 行注释：结束当前脚本代码块。
};

// 答辩讲解:
// - 函数： deleteDevice
// - 作用：删除后端中的选中记录并刷新表格数据。
// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
// 方法用法：页面交互或状态变化时调用 deleteDevice，用于完成当前前端模块的一段处理逻辑。
const deleteDevice = async (id) => {
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  try {
    // 行注释：发起接口请求或读取对象数据，获取页面需要的业务信息。
    await api.delete(`/monitoring/devices/${id}`);
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    message.success('设备已删除');
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    await fetchData();
  // 行注释：结束 try 代码块并进入异常处理流程。
  } catch (error) {
    // 行注释：根据条件控制前端流程，例如权限判断、跳转或错误处理。
    if (error?.response?.status === 404) {
      // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
      message.warning('设备不存在，已刷新列表');
      // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
      await fetchData();
    // 行注释：结束当前脚本代码块。
    }
  // 行注释：结束当前脚本代码块。
  }
// 行注释：结束当前脚本代码块。
};

// 答辩讲解:
// - 函数： resetRecordForm
// - 作用：在用户操作前将表单/筛选状态重置为安全默认值。
// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
// 方法用法：页面交互或状态变化时调用 resetRecordForm，用于完成当前前端模块的一段处理逻辑。
const resetRecordForm = () => {
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  recordForm.device_id = activeDevices.value[0]?.id || null;
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  recordForm.energy_generated = 0;
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  recordForm.energy_consumed = 0;
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  recordForm.efficiency = 0;
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  recordForm.temperature = 0;
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  recordForm.humidity = 0;
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  recordForm.timestamp = null;
// 行注释：结束当前脚本代码块。
};

// 答辩讲解:
// - 函数： openRecordCreate
// - 作用：准备本地表单状态并打开弹窗供用户操作。
// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
// 方法用法：页面交互或状态变化时调用 openRecordCreate，用于完成当前前端模块的一段处理逻辑。
const openRecordCreate = () => {
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  recordEditingId.value = null;
  // 行注释：设置实体字段值，准备保存到数据库或返回给前端。
  resetRecordForm();
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  recordModalOpen.value = true;
// 行注释：结束当前脚本代码块。
};

// 答辩讲解:
// - 函数： openRecordEdit
// - 作用：准备本地表单状态并打开弹窗供用户操作。
// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
// 方法用法：页面交互或状态变化时调用 openRecordEdit，用于完成当前前端模块的一段处理逻辑。
const openRecordEdit = (row) => {
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  recordEditingId.value = row.id;
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  recordForm.device_id = row.device_id;
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  recordForm.energy_generated = Number(row.energy_generated || 0);
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  recordForm.energy_consumed = Number(row.energy_consumed || 0);
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  recordForm.efficiency = Number(row.efficiency || 0);
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  recordForm.temperature = Number(row.temperature || 0);
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  recordForm.humidity = Number(row.humidity || 0);
  // 行注释：设置样式属性，控制页面元素的展示效果。
  recordForm.timestamp = row.timestamp ? dayjs(row.timestamp) : null;
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  recordModalOpen.value = true;
// 行注释：结束当前脚本代码块。
};

// 答辩讲解:
// - 函数： submitRecord
// - 作用：用于页面交互与状态流转的前端辅助函数。
// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
// 方法用法：页面交互或状态变化时调用 submitRecord，用于完成当前前端模块的一段处理逻辑。
const submitRecord = async () => {
  // 行注释：根据条件控制前端流程，例如权限判断、跳转或错误处理。
  if (!recordForm.device_id) {
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    message.warning('请选择设备');
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    return;
  // 行注释：结束当前脚本代码块。
  }

  // 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
  const payload = {
    // 行注释：设置样式属性，控制页面元素的展示效果。
    device_id: recordForm.device_id,
    // 行注释：设置样式属性，控制页面元素的展示效果。
    energy_generated: recordForm.energy_generated,
    // 行注释：设置样式属性，控制页面元素的展示效果。
    energy_consumed: recordForm.energy_consumed,
    // 行注释：设置样式属性，控制页面元素的展示效果。
    efficiency: recordForm.efficiency,
    // 行注释：设置样式属性，控制页面元素的展示效果。
    temperature: recordForm.temperature,
    // 行注释：设置样式属性，控制页面元素的展示效果。
    humidity: recordForm.humidity,
    // 行注释：设置样式属性，控制页面元素的展示效果。
    timestamp: recordForm.timestamp ? recordForm.timestamp.format('YYYY-MM-DDTHH:mm:ss') : null
  // 行注释：结束当前脚本代码块。
  };

  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  recordSubmitting.value = true;
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  try {
    // 行注释：根据条件控制前端流程，例如权限判断、跳转或错误处理。
    if (recordEditingId.value) {
      // 行注释：发起接口请求或读取对象数据，获取页面需要的业务信息。
      await api.put(`/monitoring/data/${recordEditingId.value}`, payload);
      // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
      message.success('监测记录已更新');
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    } else {
      // 行注释：发起接口请求或读取对象数据，获取页面需要的业务信息。
      await api.post('/monitoring/data', payload);
      // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
      message.success('监测记录已创建');
    // 行注释：结束当前脚本代码块。
    }
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    recordModalOpen.value = false;
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    await fetchData();
  // 行注释：结束 try 代码块并进入异常处理流程。
  } catch (error) {
    // 行注释：根据条件控制前端流程，例如权限判断、跳转或错误处理。
    if (error?.response?.status === 404) {
      // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
      message.warning('记录不存在，已刷新列表');
      // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
      await fetchData();
      // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
      recordModalOpen.value = false;
    // 行注释：结束当前脚本代码块。
    }
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  } finally {
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    recordSubmitting.value = false;
  // 行注释：结束当前脚本代码块。
  }
// 行注释：结束当前脚本代码块。
};

// 答辩讲解:
// - 函数： deleteRecord
// - 作用：删除后端中的选中记录并刷新表格数据。
// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
// 方法用法：页面交互或状态变化时调用 deleteRecord，用于完成当前前端模块的一段处理逻辑。
const deleteRecord = async (id) => {
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  try {
    // 行注释：发起接口请求或读取对象数据，获取页面需要的业务信息。
    await api.delete(`/monitoring/data/${id}`);
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    message.success('监测记录已删除');
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    await fetchData();
  // 行注释：结束 try 代码块并进入异常处理流程。
  } catch (error) {
    // 行注释：根据条件控制前端流程，例如权限判断、跳转或错误处理。
    if (error?.response?.status === 404) {
      // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
      message.warning('记录不存在，已刷新列表');
      // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
      await fetchData();
    // 行注释：结束当前脚本代码块。
    }
  // 行注释：结束当前脚本代码块。
  }
// 行注释：结束当前脚本代码块。
};

// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const barOption = computed(() => {
  // 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
  const data = realtimeData.value.slice(0, 8);
  // 行注释：返回处理结果，交给调用方或路由守卫继续使用。
  return {
    // 行注释：设置样式属性，控制页面元素的展示效果。
    tooltip: { trigger: 'axis' },
    // 行注释：设置样式属性，控制页面元素的展示效果。
    legend: { data: ['发电量', '消耗量'] },
    // 行注释：设置样式属性，控制页面元素的展示效果。
    grid: { left: 40, right: 20, top: 40, bottom: 30 },
    // 行注释：设置样式属性，控制页面元素的展示效果。
    xAxis: { type: 'category', data: data.map((item) => item.device_name_label) },
    // 行注释：设置样式属性，控制页面元素的展示效果。
    yAxis: { type: 'value' },
    // 行注释：设置样式属性，控制页面元素的展示效果。
    series: [
      // 行注释：设置样式属性，控制页面元素的展示效果。
      { name: '发电量', type: 'bar', data: data.map((item) => Number(item.energy_generated || 0)) },
      // 行注释：设置样式属性，控制页面元素的展示效果。
      { name: '消耗量', type: 'bar', data: data.map((item) => Number(item.energy_consumed || 0)) }
    // 行注释：结束数组配置，完成列表、菜单或图表数据的组装。
    ]
  // 行注释：结束当前脚本代码块。
  };
// 行注释：结束当前脚本代码块。
});

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
      name: '发电占比',
      // 行注释：设置样式属性，控制页面元素的展示效果。
      type: 'pie',
      // 行注释：设置样式属性，控制页面元素的展示效果。
      radius: '60%',
      // 行注释：设置样式属性，控制页面元素的展示效果。
      data: statistics.value.map((item) => ({
        // 行注释：设置样式属性，控制页面元素的展示效果。
        name: item.device_type_label,
        // 行注释：设置样式属性，控制页面元素的展示效果。
        value: Number(item.total_generated || 0)
      // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
      }))
    // 行注释：结束当前脚本代码块。
    }
  // 行注释：结束数组配置，完成列表、菜单或图表数据的组装。
  ]
// 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
}));

// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const lineOption = computed(() => {
  // 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
  const data = realtimeData.value.slice(0, 10);
  // 行注释：返回处理结果，交给调用方或路由守卫继续使用。
  return {
    // 行注释：设置样式属性，控制页面元素的展示效果。
    tooltip: { trigger: 'axis' },
    // 行注释：设置样式属性，控制页面元素的展示效果。
    xAxis: { type: 'category', data: data.map((item) => item.device_name_label) },
    // 行注释：设置样式属性，控制页面元素的展示效果。
    yAxis: { type: 'value' },
    // 行注释：设置样式属性，控制页面元素的展示效果。
    series: [
      // 行注释：开始对象配置，把相关参数集中传给组件或接口。
      {
        // 行注释：设置样式属性，控制页面元素的展示效果。
        name: '效率',
        // 行注释：设置样式属性，控制页面元素的展示效果。
        type: 'line',
        // 行注释：设置样式属性，控制页面元素的展示效果。
        smooth: true,
        // 行注释：设置样式属性，控制页面元素的展示效果。
        data: data.map((item) => Number(item.efficiency || 0))
      // 行注释：结束当前脚本代码块。
      }
    // 行注释：结束数组配置，完成列表、菜单或图表数据的组装。
    ]
  // 行注释：结束当前脚本代码块。
  };
// 行注释：结束当前脚本代码块。
});

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



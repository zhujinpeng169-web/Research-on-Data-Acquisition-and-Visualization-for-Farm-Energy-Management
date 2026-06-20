<!-- 逐行注释标记：已按业务语义处理，仅增加中文注释，不改变页面逻辑 -->
<!--
  <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
  答辩注释:
  <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
  - 文件： UserManagementPage
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
    <h1 class="page-title">用户管理</h1>

    <!-- 行注释：渲染“系统用户”卡片，用于承载当前模块信息。 -->
    <a-card title="系统用户" :loading="loading">
      <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
      <template #extra>
        <!-- 行注释：渲染操作按钮，触发新增、保存、删除或查询动作。 -->
        <a-button type="primary" @click="openCreate">新增用户</a-button>
      <!-- 行注释：结束模板区域，页面结构到这里完成。 -->
      </template>

      <!-- 行注释：渲染数据表格，把后端列表数据按列展示。 -->
      <a-table :columns="columns" :data-source="users" row-key="id" :pagination="{ pageSize: 10 }">
        <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
        <template #bodyCell="{ column, record }">
          <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
          <template v-if="column.key === 'role'">
            <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
            <a-tag :color="record.role === 'admin' ? 'red' : record.role === 'manager' ? 'blue' : 'default'">
              <!-- 行注释：把响应式变量渲染到页面上，数据变化时页面会自动更新。 -->
              {{ record.role }}
            <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
            </a-tag>
          <!-- 行注释：结束模板区域，页面结构到这里完成。 -->
          </template>
          <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
          <template v-if="column.key === 'status'">
            <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
            <a-tag :color="record.status === 'active' ? 'green' : 'orange'">
              <!-- 行注释：把响应式变量渲染到页面上，数据变化时页面会自动更新。 -->
              {{ record.status }}
            <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
            </a-tag>
          <!-- 行注释：结束模板区域，页面结构到这里完成。 -->
          </template>
          <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
          <template v-if="column.key === 'action'">
            <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
            <a-space>
              <!-- 行注释：渲染操作按钮，触发新增、保存、删除或查询动作。 -->
              <a-button size="small" @click="openEdit(record)">编辑</a-button>
              <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
              <a-popconfirm title="确认删除该用户吗？" @confirm="deleteItem(record.id)">
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

    <!-- 行注释：渲染弹窗表单，用于新增或编辑业务数据。 -->
    <a-modal
      v-model:open="modalOpen"
      :title="editingId ? '编辑用户' : '新增用户'"
      :confirm-loading="submitting"
      @ok="submit"
      destroy-on-close
    >
      <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
      <a-form layout="vertical">
        <!-- 行注释：渲染表单项“用户名”，接收用户输入。 -->
        <a-form-item label="用户名" required>
          <!-- 行注释：渲染输入框，接收用户填写的数据。 -->
          <a-input v-model:value="form.username" :disabled="Boolean(editingId)" />
        <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
        </a-form-item>
        <!-- 行注释：渲染表单项“显示名称”，接收用户输入。 -->
        <a-form-item label="显示名称">
          <!-- 行注释：渲染输入框，接收用户填写的数据。 -->
          <a-input v-model:value="form.display_name" />
        <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
        </a-form-item>
        <!-- 行注释：渲染表单项“角色”，接收用户输入。 -->
        <a-form-item label="角色">
          <!-- 行注释：渲染下拉选择框，让用户选择筛选或分类选项。 -->
          <a-select v-model:value="form.role">
            <!-- 行注释：渲染下拉选择框，让用户选择筛选或分类选项。 -->
            <a-select-option value="admin">admin</a-select-option>
            <!-- 行注释：渲染下拉选择框，让用户选择筛选或分类选项。 -->
            <a-select-option value="manager">manager</a-select-option>
            <!-- 行注释：渲染下拉选择框，让用户选择筛选或分类选项。 -->
            <a-select-option value="viewer">viewer</a-select-option>
          <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
          </a-select>
        <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
        </a-form-item>
        <!-- 行注释：渲染表单项“状态”，接收用户输入。 -->
        <a-form-item label="状态">
          <!-- 行注释：渲染下拉选择框，让用户选择筛选或分类选项。 -->
          <a-select v-model:value="form.status">
            <!-- 行注释：渲染下拉选择框，让用户选择筛选或分类选项。 -->
            <a-select-option value="active">active</a-select-option>
            <!-- 行注释：渲染下拉选择框，让用户选择筛选或分类选项。 -->
            <a-select-option value="disabled">disabled</a-select-option>
          <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
          </a-select>
        <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
        </a-form-item>
        <!-- 行注释：渲染表单项“editingId ? '新密码(可选)' : '密码'”，接收用户输入。 -->
        <a-form-item :label="editingId ? '新密码(可选)' : '密码'" :required="!editingId">
          <!-- 行注释：渲染输入框，接收用户填写的数据。 -->
          <a-input-password v-model:value="form.password" />
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
import { onMounted, reactive, ref } from 'vue';
// 行注释：引入消息提示组件，用于向用户显示登录过期或操作失败。
import { message } from 'ant-design-vue';
// 行注释：引入当前页面或组件需要的依赖。
import api from '../api/http';

// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const loading = ref(true);
// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const users = ref([]);

// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const columns = [
  // 行注释：设置样式属性，控制页面元素的展示效果。
  { title: 'ID', dataIndex: 'id', key: 'id' },
  // 行注释：设置样式属性，控制页面元素的展示效果。
  { title: '用户名', dataIndex: 'username', key: 'username' },
  // 行注释：设置样式属性，控制页面元素的展示效果。
  { title: '显示名称', dataIndex: 'display_name', key: 'display_name' },
  // 行注释：设置样式属性，控制页面元素的展示效果。
  { title: '角色', dataIndex: 'role', key: 'role' },
  // 行注释：设置样式属性，控制页面元素的展示效果。
  { title: '状态', dataIndex: 'status', key: 'status' },
  // 行注释：设置样式属性，控制页面元素的展示效果。
  { title: '创建时间', dataIndex: 'created_at', key: 'created_at' },
  // 行注释：设置样式属性，控制页面元素的展示效果。
  { title: '最后登录', dataIndex: 'last_login_at', key: 'last_login_at' },
  // 行注释：设置样式属性，控制页面元素的展示效果。
  { title: '操作', key: 'action', width: 180 }
// 行注释：结束数组配置，完成列表、菜单或图表数据的组装。
];

// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const modalOpen = ref(false);
// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const submitting = ref(false);
// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const editingId = ref(null);
// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const form = reactive({
  // 行注释：设置样式属性，控制页面元素的展示效果。
  username: '',
  // 行注释：设置样式属性，控制页面元素的展示效果。
  display_name: '',
  // 行注释：设置样式属性，控制页面元素的展示效果。
  role: 'viewer',
  // 行注释：设置样式属性，控制页面元素的展示效果。
  status: 'active',
  // 行注释：设置样式属性，控制页面元素的展示效果。
  password: ''
// 行注释：结束当前脚本代码块。
});

// 答辩讲解:
// - 函数： resetForm
// - 作用：在用户操作前将表单/筛选状态重置为安全默认值。
// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
// 方法用法：打开新增或提交后调用，用来把表单恢复到默认初始状态。
const resetForm = () => {
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  form.username = '';
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  form.display_name = '';
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  form.role = 'viewer';
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  form.status = 'active';
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  form.password = '';
// 行注释：结束当前脚本代码块。
};

// 答辩讲解:
// - 函数： fetchUsers
// - 作用：拉取后端最新数据并同步响应式状态用于渲染。
// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
// 方法用法：页面交互或状态变化时调用 fetchUsers，用于完成当前前端模块的一段处理逻辑。
const fetchUsers = async () => {
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  try {
    // 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
    const res = await api.get('/users');
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    users.value = res.users || [];
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
  form.username = row.username;
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  form.display_name = row.display_name || '';
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  form.role = row.role || 'viewer';
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  form.status = row.status || 'active';
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  form.password = '';
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
  if (!form.username) {
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    message.warning('请输入用户名');
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    return;
  // 行注释：结束当前脚本代码块。
  }
  // 行注释：根据条件控制前端流程，例如权限判断、跳转或错误处理。
  if (!editingId.value && (!form.password || form.password.length < 6)) {
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    message.warning('新用户密码至少 6 位');
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    return;
  // 行注释：结束当前脚本代码块。
  }

  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  submitting.value = true;
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  try {
    // 行注释：根据条件控制前端流程，例如权限判断、跳转或错误处理。
    if (editingId.value) {
      // 行注释：发起接口请求或读取对象数据，获取页面需要的业务信息。
      await api.put(`/users/${editingId.value}`, {
        // 行注释：设置样式属性，控制页面元素的展示效果。
        display_name: form.display_name,
        // 行注释：设置样式属性，控制页面元素的展示效果。
        role: form.role,
        // 行注释：设置样式属性，控制页面元素的展示效果。
        status: form.status,
        // 行注释：设置样式属性，控制页面元素的展示效果。
        password: form.password || undefined
      // 行注释：结束当前脚本代码块。
      });
      // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
      message.success('用户已更新');
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    } else {
      // 行注释：发起接口请求或读取对象数据，获取页面需要的业务信息。
      await api.post('/users', {
        // 行注释：设置样式属性，控制页面元素的展示效果。
        username: form.username,
        // 行注释：设置样式属性，控制页面元素的展示效果。
        display_name: form.display_name || form.username,
        // 行注释：设置样式属性，控制页面元素的展示效果。
        role: form.role,
        // 行注释：设置样式属性，控制页面元素的展示效果。
        password: form.password
      // 行注释：结束当前脚本代码块。
      });
      // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
      message.success('用户已创建');
    // 行注释：结束当前脚本代码块。
    }
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    modalOpen.value = false;
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    await fetchUsers();
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
  // 行注释：发起接口请求或读取对象数据，获取页面需要的业务信息。
  await api.delete(`/users/${id}`);
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  message.success('用户已删除');
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  await fetchUsers();
// 行注释：结束当前脚本代码块。
};

// 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
onMounted(fetchUsers);
// 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
</script>



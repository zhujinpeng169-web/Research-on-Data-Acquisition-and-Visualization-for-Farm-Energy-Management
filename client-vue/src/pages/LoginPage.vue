<!-- 逐行注释标记：已按业务语义处理，仅增加中文注释，不改变页面逻辑 -->
<!--
  <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
  答辩注释:
  <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
  - 文件： LoginPage
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
  <div class="login-page">
    <!-- 行注释：渲染“农场能源管理系统登录”卡片，用于承载当前模块信息。 -->
    <a-card class="login-card" title="农场能源管理系统登录">
      <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
      <a-form layout="vertical" :model="form" @finish="submit">
        <!-- 行注释：渲染表单项“用户名”，接收用户输入。 -->
        <a-form-item label="用户名" name="username" :rules="[{ required: true, message: '请输入用户名' }]">
          <!-- 行注释：渲染输入框，接收用户填写的数据。 -->
          <a-input v-model:value="form.username" placeholder="admin / manager / viewer" />
        <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
        </a-form-item>
        <!-- 行注释：渲染表单项“密码”，接收用户输入。 -->
        <a-form-item label="密码" name="password" :rules="[{ required: true, message: '请输入密码'}]">
          <!-- 行注释：渲染输入框，接收用户填写的数据。 -->
          <a-input-password v-model:value="form.password" placeholder="请输入密码" />
        <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
        </a-form-item>
        <!-- 行注释：渲染操作按钮，触发新增、保存、删除或查询动作。 -->
        <a-button type="primary" html-type="submit" :loading="loading" block>
          <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
          登录
        <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
        </a-button>
      <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
      </a-form>

      <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
      <a-alert style="margin-top: 12px" type="info" show-icon>
        <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
        <template #message>默认账号</template>
        <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
        <template #description>
          <!-- 行注释：声明页面结构或组件属性，组成用户看到的前端界面。 -->
          admin/admin123，manager/manager123，viewer/viewer123
        <!-- 行注释：结束模板区域，页面结构到这里完成。 -->
        </template>
      <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
      </a-alert>
    <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
    </a-card>
  <!-- 行注释：结束当前标签，表示该区域的页面结构收尾。 -->
  </div>
<!-- 行注释：结束模板区域，页面结构到这里完成。 -->
</template>

<!-- 行注释：进入脚本区域，下面编写数据请求和页面交互逻辑。 -->
<script setup>
// 行注释：引入当前页面或组件需要的依赖。
import { reactive, ref } from 'vue';
// 行注释：引入当前页面或组件需要的依赖。
import { useRoute, useRouter } from 'vue-router';
// 行注释：引入消息提示组件，用于向用户显示登录过期或操作失败。
import { message } from 'ant-design-vue';
// 行注释：引入当前页面或组件需要的依赖。
import api from '../api/http';
// 行注释：引入当前页面或组件需要的依赖。
import { setSession } from '../auth/session';

// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const router = useRouter();
// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const route = useRoute();
// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const loading = ref(false);
// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const form = reactive({
  // 行注释：设置样式属性，控制页面元素的展示效果。
  username: '',
  // 行注释：设置样式属性，控制页面元素的展示效果。
  password: ''
// 行注释：结束当前脚本代码块。
});

// 答辩讲解:
// - 函数： submit
// - 作用：校验表单输入并向后端提交新增/更新请求。
// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
// 方法用法：用户点击弹窗或表单确认按钮时调用，用来校验输入并提交新增或编辑请求。
const submit = async () => {
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  loading.value = true;
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  try {
    // 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
    const res = await api.post('/auth/login', {
      // 行注释：设置样式属性，控制页面元素的展示效果。
      username: form.username,
      // 行注释：设置样式属性，控制页面元素的展示效果。
      password: form.password
    // 行注释：结束当前脚本代码块。
    });

    // 行注释：设置实体字段值，准备保存到数据库或返回给前端。
    setSession(res.token, res.user);
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    message.success('登录成功');

    // 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
    const redirect = typeof route.query.redirect === 'string' ? route.query.redirect : '/';
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    await router.replace(redirect);
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
</script>

<!-- 行注释：进入样式区域，下面控制当前页面的布局和外观。 -->
<style scoped>
/* 行注释：选中页面元素或组件区域，准备编写它的样式。 */
.login-page {
  /* 行注释：设置宽高，让组件在页面中保持稳定尺寸。 */
  min-height: 100vh;
  /* 行注释：设置布局方式，控制页面元素如何排列。 */
  display: grid;
  /* 行注释：设置当前样式属性，控制页面展示效果。 */
  place-items: center;
  /* 行注释：设置背景颜色或渐变，提升页面视觉表现。 */
  background: linear-gradient(135deg, #f0f7ff 0%, #effaf5 100%);
/* 行注释：结束当前样式规则。 */
}

/* 行注释：选中页面元素或组件区域，准备编写它的样式。 */
.login-card {
  /* 行注释：设置宽高，让组件在页面中保持稳定尺寸。 */
  width: 420px;
/* 行注释：结束当前样式规则。 */
}
/* 行注释：设置当前样式属性，控制页面展示效果。 */
</style>



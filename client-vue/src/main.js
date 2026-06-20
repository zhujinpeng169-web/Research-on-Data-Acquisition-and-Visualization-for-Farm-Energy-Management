// 逐行注释标记：已按业务语义处理，仅增加中文注释，不改变程序逻辑
/**
 * 答辩注释:
 * - 文件： main
 * - 层级：前端启动层
 * - 职责：初始化 Vue 应用外壳、全局插件及应用级样式/布局行为。
 * - 此注释仅用于说明，不会改变运行行为。
 */
// 行注释：引入当前页面或组件需要的依赖。
import { createApp } from 'vue';
// 行注释：引入当前页面或组件需要的依赖。
import Antd from 'ant-design-vue';
// 行注释：引入当前页面或组件需要的依赖。
import zhCN from 'ant-design-vue/es/locale/zh_CN';
// 行注释：引入当前页面或组件需要的依赖。
import 'ant-design-vue/dist/reset.css';
// 行注释：引入当前页面或组件需要的依赖。
import App from './App.vue';
// 行注释：引入当前页面或组件需要的依赖。
import router from './router';
// 行注释：引入当前页面或组件需要的依赖。
import './style.css';

// 行注释：创建 Vue 应用实例，作为整个前端系统的入口。
createApp(App)
  // 行注释：挂载路由插件，让页面可以根据地址切换。
  .use(router)
  // 行注释：挂载 Ant Design Vue 组件库，并设置中文语言包。
  .use(Antd, { locale: zhCN })
  // 行注释：把 Vue 应用挂载到 index.html 中的 #app 节点。
  .mount('#app');



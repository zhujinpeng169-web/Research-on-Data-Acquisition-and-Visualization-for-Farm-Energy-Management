// 逐行注释标记：已按业务语义处理，仅增加中文注释，不改变程序逻辑
/**
 * 答辩注释:
 * - 文件： index
 * - 层级：前端路由层
 * - 职责：定义路由映射及前端导航守卫，实现登录与角色权限控制。
 * - 此注释仅用于说明，不会改变运行行为。
 */
// 行注释：引入当前页面或组件需要的依赖。
import { createRouter, createWebHistory } from 'vue-router';
// 行注释：引入消息提示组件，用于向用户显示登录过期或操作失败。
import { message } from 'ant-design-vue';
// 行注释：引入当前页面或组件需要的依赖。
import { hasRole, isLoggedIn } from '../auth/session';
// 行注释：引入当前页面或组件需要的依赖。
import DashboardPage from '../pages/DashboardPage.vue';
// 行注释：引入当前页面或组件需要的依赖。
import MonitoringPage from '../pages/MonitoringPage.vue';
// 行注释：引入当前页面或组件需要的依赖。
import OptimizationPage from '../pages/OptimizationPage.vue';
// 行注释：引入当前页面或组件需要的依赖。
import CarbonPage from '../pages/CarbonPage.vue';
// 行注释：引入当前页面或组件需要的依赖。
import ForecastPage from '../pages/ForecastPage.vue';
// 行注释：引入当前页面或组件需要的依赖。
import ReportsPage from '../pages/ReportsPage.vue';
// 行注释：引入当前页面或组件需要的依赖。
import LoginPage from '../pages/LoginPage.vue';
// 行注释：引入当前页面或组件需要的依赖。
import UserManagementPage from '../pages/UserManagementPage.vue';
// 行注释：引入当前页面或组件需要的依赖。
import AuditLogsPage from '../pages/AuditLogsPage.vue';

// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const routes = [
  // 行注释：设置对象属性，作为请求配置、路由配置或组件状态使用。
  { path: '/login', component: LoginPage, meta: { public: true, layout: false } },
  // 行注释：设置对象属性，作为请求配置、路由配置或组件状态使用。
  { path: '/', component: DashboardPage },
  // 行注释：设置对象属性，作为请求配置、路由配置或组件状态使用。
  { path: '/monitoring', component: MonitoringPage },
  // 行注释：设置对象属性，作为请求配置、路由配置或组件状态使用。
  { path: '/optimization', component: OptimizationPage },
  // 行注释：设置对象属性，作为请求配置、路由配置或组件状态使用。
  { path: '/carbon', component: CarbonPage },
  // 行注释：设置对象属性，作为请求配置、路由配置或组件状态使用。
  { path: '/forecast', component: ForecastPage },
  // 行注释：设置对象属性，作为请求配置、路由配置或组件状态使用。
  { path: '/reports', component: ReportsPage },
  // 行注释：设置对象属性，作为请求配置、路由配置或组件状态使用。
  { path: '/users', component: UserManagementPage, meta: { roles: ['admin'] } },
  // 行注释：设置对象属性，作为请求配置、路由配置或组件状态使用。
  { path: '/audit', component: AuditLogsPage, meta: { roles: ['admin'] } }
// 行注释：结束数组配置，完成列表、菜单或图表数据的组装。
];

// 行注释：创建前端路由实例，管理各页面之间的跳转。
const router = createRouter({
  // 行注释：使用浏览器历史模式，让地址栏路径更接近真实页面地址。
  history: createWebHistory(),
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  routes
// 行注释：结束当前脚本代码块。
});

// 行注释：设置路由守卫，进入页面前检查登录状态和角色权限。
router.beforeEach((to) => {
  // 行注释：根据条件控制前端流程，例如权限判断、跳转或错误处理。
  if (to.meta.public) {
    // 行注释：判断用户是否已登录，决定是否放行页面访问。
    if (to.path === '/login' && isLoggedIn()) {
      // 行注释：返回处理结果，交给调用方或路由守卫继续使用。
      return '/';
    // 行注释：结束当前脚本代码块。
    }
    // 行注释：返回处理结果，交给调用方或路由守卫继续使用。
    return true;
  // 行注释：结束当前脚本代码块。
  }

  // 行注释：判断用户是否已登录，决定是否放行页面访问。
  if (!isLoggedIn()) {
    // 行注释：返回处理结果，交给调用方或路由守卫继续使用。
    return `/login?redirect=${encodeURIComponent(to.fullPath)}`;
  // 行注释：结束当前脚本代码块。
  }

  // 行注释：判断当前用户角色是否满足页面权限要求。
  if (!hasRole(to.meta.roles || [])) {
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    message.warning('当前角色无权访问此页面');
    // 行注释：返回处理结果，交给调用方或路由守卫继续使用。
    return '/';
  // 行注释：结束当前脚本代码块。
  }

  // 行注释：返回处理结果，交给调用方或路由守卫继续使用。
  return true;
// 行注释：结束当前脚本代码块。
});

// 行注释：导出 index 模块，供其他文件直接引用。
export default router;



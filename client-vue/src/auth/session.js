// 逐行注释标记：已按业务语义处理，仅增加中文注释，不改变程序逻辑
/**
 * 答辩注释:
 * - 文件： session
 * - 层级：前端鉴权状态层
 * - 职责：在浏览器存储中维护本地会话状态与角色能力辅助函数。
 * - 此注释仅用于说明，不会改变运行行为。
 */
// 行注释：引入当前页面或组件需要的依赖。
import { reactive } from 'vue';

// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const TOKEN_KEY = 'farm_energy_token';
// 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
const USER_KEY = 'farm_energy_user';

// 答辩讲解:
// - 函数： safeParseUser
// - 作用：存储辅助方法：在应用启动时安全恢复持久化会话数据。
// 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
// 方法用法：页面交互或状态变化时调用 safeParseUser，用于完成当前前端模块的一段处理逻辑。
function safeParseUser(raw) {
  // 行注释：根据条件控制前端流程，例如权限判断、跳转或错误处理。
  if (!raw) return null;
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  try {
    // 行注释：返回处理结果，交给调用方或路由守卫继续使用。
    return JSON.parse(raw);
  // 行注释：结束 try 代码块并进入异常处理流程。
  } catch {
    // 行注释：返回处理结果，交给调用方或路由守卫继续使用。
    return null;
  // 行注释：结束当前脚本代码块。
  }
// 行注释：结束当前脚本代码块。
}

// 答辩讲解:
// - 函数： readToken
// - 作用：存储辅助方法：在应用启动时安全恢复持久化会话数据。
// 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
// 方法用法：页面交互或状态变化时调用 readToken，用于完成当前前端模块的一段处理逻辑。
function readToken() {
  // 行注释：返回处理结果，交给调用方或路由守卫继续使用。
  return localStorage.getItem(TOKEN_KEY) || '';
// 行注释：结束当前脚本代码块。
}

// 答辩讲解:
// - 函数： readUser
// - 作用：存储辅助方法：在应用启动时安全恢复持久化会话数据。
// 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
// 方法用法：页面交互或状态变化时调用 readUser，用于完成当前前端模块的一段处理逻辑。
function readUser() {
  // 行注释：返回处理结果，交给调用方或路由守卫继续使用。
  return safeParseUser(localStorage.getItem(USER_KEY));
// 行注释：结束当前脚本代码块。
}

// 行注释：声明变量 authState，保存本行计算或查询得到的结果，供后续逻辑使用。
export const authState = reactive({
  // 行注释：设置对象属性，作为请求配置、路由配置或组件状态使用。
  token: readToken(),
  // 行注释：设置对象属性，作为请求配置、路由配置或组件状态使用。
  user: readUser()
// 行注释：结束当前脚本代码块。
});

// 行注释：设置实体字段值，准备保存到数据库或返回给前端。
export function setSession(token, user) {
  // 行注释：判断本地是否已有登录 token，有 token 才能访问受保护接口。
  authState.token = token || '';
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  authState.user = user || null;
  // 行注释：判断本地是否已有登录 token，有 token 才能访问受保护接口。
  if (authState.token) {
    // 行注释：判断本地是否已有登录 token，有 token 才能访问受保护接口。
    localStorage.setItem(TOKEN_KEY, authState.token);
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  } else {
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    localStorage.removeItem(TOKEN_KEY);
  // 行注释：结束当前脚本代码块。
  }
  // 行注释：根据条件控制前端流程，例如权限判断、跳转或错误处理。
  if (authState.user) {
    // 行注释：调用 setter 方法写入字段值，准备保存或返回业务数据。
    localStorage.setItem(USER_KEY, JSON.stringify(authState.user));
  // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
  } else {
    // 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
    localStorage.removeItem(USER_KEY);
  // 行注释：结束当前脚本代码块。
  }
// 行注释：结束当前脚本代码块。
}

// 行注释：清空本地登录状态，防止继续使用过期 token。
export function clearSession() {
  // 行注释：设置实体字段值，准备保存到数据库或返回给前端。
  setSession('', null);
// 行注释：结束当前脚本代码块。
}

// 行注释：判断用户是否已登录，决定是否放行页面访问。
export function isLoggedIn() {
  // 行注释：判断本地是否已有登录 token，有 token 才能访问受保护接口。
  return Boolean(authState.token);
// 行注释：结束当前脚本代码块。
}

// 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
export function currentRole() {
  // 行注释：返回处理结果，交给调用方或路由守卫继续使用。
  return authState.user?.role || 'viewer';
// 行注释：结束当前脚本代码块。
}

// 行注释：承接上方业务语句，继续完成当前流程的数据组装或调用。
export function canWrite() {
  // 行注释：声明变量或响应式状态，支撑页面数据和交互流程。
  const role = currentRole();
  // 行注释：返回处理结果，交给调用方或路由守卫继续使用。
  return role === 'admin' || role === 'manager';
// 行注释：结束当前脚本代码块。
}

// 行注释：判断当前用户角色是否满足页面权限要求。
export function hasRole(roles = []) {
  // 行注释：根据条件控制前端流程，例如权限判断、跳转或错误处理。
  if (!roles || roles.length === 0) return true;
  // 行注释：返回处理结果，交给调用方或路由守卫继续使用。
  return roles.includes(currentRole());
// 行注释：结束当前脚本代码块。
}



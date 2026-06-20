// 逐行注释标记：已按业务语义处理，仅增加中文注释，不改变程序逻辑
/**
 * 答辩注释:
 * - 文件： http
 * - 层级：前端 API 客户端层
 * - 职责：封装 HTTP 默认配置与拦截器，实现令牌注入和统一错误处理。
 * - 此注释仅用于说明，不会改变运行行为。
 */
// 行注释：引入 Axios，用于前端统一发送 HTTP 请求。
import axios from 'axios';
// 行注释：引入消息提示组件，用于向用户显示登录过期或操作失败。
import { message } from 'ant-design-vue';
// 行注释：引入登录状态工具，用于读取 token 或清空会话。
import { authState, clearSession } from '../auth/session';

// 行注释：创建统一的接口请求实例，所有页面都通过它访问后端。
const api = axios.create({
  // 行注释：设置后端接口统一前缀，默认请求当前站点下的 /api。
  baseURL: import.meta.env.VITE_API_BASE_URL || '/api',
  // 行注释：设置请求超时时间，避免接口长时间无响应卡住页面。
  timeout: 15000,
  // 行注释：设置对象属性，作为请求配置、路由配置或组件状态使用。
  headers: {
    // 行注释：声明请求体是 JSON，方便后端用 @RequestBody 接收。
    'Content-Type': 'application/json'
  // 行注释：结束当前脚本代码块。
  }
// 行注释：结束当前脚本代码块。
});

// 行注释：注册请求拦截器，每次请求发送前先处理 token。
api.interceptors.request.use((config) => {
  // 行注释：判断本地是否已有登录 token，有 token 才能访问受保护接口。
  if (authState.token) {
    // 行注释：判断本地是否已有登录 token，有 token 才能访问受保护接口。
    config.headers.Authorization = `Bearer ${authState.token}`;
  // 行注释：结束当前脚本代码块。
  }
  // 行注释：返回处理结果，交给调用方或路由守卫继续使用。
  return config;
// 行注释：结束当前脚本代码块。
});

// 行注释：注册响应拦截器，统一处理成功数据和错误状态。
api.interceptors.response.use(
  // 行注释：只把后端 JSON 的 data 部分返回给页面，减少重复取值。
  (response) => response.data,
  // 行注释：定义回调函数，当前事件或请求完成后会执行这里的处理逻辑。
  (error) => {
    // 行注释：取出 HTTP 状态码，用来判断是否登录过期或接口失败。
    const status = error?.response?.status;
    // 行注释：取出后端返回的错误消息，优先展示真实失败原因。
    const serverMessage = error?.response?.data?.message;
    // 行注释：记录当前请求地址，区分登录失败和普通接口未登录。
    const requestUrl = error?.config?.url || '';

    // 行注释：判断接口是否返回未授权，通常表示 token 无效或已过期。
    if (status === 401) {
      // 行注释：判断当前失败请求是不是登录接口，避免误跳转。
      if (requestUrl.includes('/auth/login')) {
        // 行注释：显示错误提示，让用户知道当前操作为什么失败。
        message.error(serverMessage || '用户名或密码错误');
        // 行注释：继续抛出请求错误，方便页面自己的 catch 逻辑处理。
        return Promise.reject(error);
      // 行注释：结束当前脚本代码块。
      }
      // 行注释：清空本地登录状态，防止继续使用过期 token。
      clearSession();
      // 行注释：根据条件控制前端流程，例如权限判断、跳转或错误处理。
      if (!window.location.pathname.startsWith('/login')) {
        // 行注释：跳转到登录页，让用户重新登录后继续使用系统。
        window.location.href = '/login';
      // 行注释：结束当前脚本代码块。
      }
      // 行注释：显示错误提示，让用户知道当前操作为什么失败。
      message.error(serverMessage || '登录已过期，请重新登录');
      // 行注释：继续抛出请求错误，方便页面自己的 catch 逻辑处理。
      return Promise.reject(error);
    // 行注释：结束当前脚本代码块。
    }

    // 行注释：显示错误提示，让用户知道当前操作为什么失败。
    message.error(serverMessage || 'Request failed, please retry');
    // 行注释：继续抛出请求错误，方便页面自己的 catch 逻辑处理。
    return Promise.reject(error);
  // 行注释：结束当前脚本代码块。
  }
// 行注释：结束当前方法调用，把上面准备好的参数一次性提交执行。
);

// 行注释：导出 http 模块，供其他文件直接引用。
export default api;



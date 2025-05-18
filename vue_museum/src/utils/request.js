import axios from 'axios';

/**
 * 此文件用于与后端交互
 */

const service = axios.create({
  baseURL: 'http://localhost:8081/api', // 后端 API 的基础路径
  timeout: 5000, // 请求超时时间
});

export default service;

// 请求拦截器
service.interceptors.request.use(
    config => {
      /**调试用 */
      console.log("Now Fecting Url = "+config.url+" --With Data: "+JSON.stringify(config.data));
      // 登录接口不附带 Authorization 头
      if (config.url.endsWith('/login')) {
        return config;
      }
      // 在发送请求之前做些什么，例如添加 token
      const token = localStorage.getItem('jwt');
      if (token) config.headers.Authorization = `Bearer ${token}`;
      console.log("开始请求后端,保存有token: "+token);
      return config;
    },
    error => {
      // 对请求错误做些什么
      console.log("请求错误");
      return Promise.reject(error);
    }
  );
  
  // 响应拦截器
  service.interceptors.response.use(
    response => {
      // 对响应数据做点什么
      console.log("成功从后端请求到数据: "+JSON.stringify(response.data)); //这句在数据量大时请去除
      return response;
    },
    error => {
      // 对响应错误做点什么
      if (error.response?.status === 401) {
        localStorage.removeItem('jwt');
        localStorage.removeItem('username');
        localStorage.removeItem('avatar');        
      //   // 跳登录页、清 Token 等
      //   window.location.hash = '/login';
      //   localStorage.removeItem('jwt');
      //   console.log("响应拦截器重定向至登录页");
      }
      console.log("响应出错: "+JSON.stringify(error.response.data));
      return error.response;
    }
  );
  
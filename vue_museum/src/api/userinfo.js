import request from '@/utils/request';

export function getUserList(params) {
  console.log("now fetching /users");

  return request({
    url: '/users',
    method: 'get',
    params
  }).then(response => {   //注意此处的response是在拦截器中解析好的数据，即response.data
    const data = response;
    console.log(data);
    // 类型校验
    if (!data || !Array.isArray(data)) {
      throw new Error("响应数据格式不正确，应为 [...]");
    }
    return data; // 返回数组，简化前端调用
  }).catch(error => {
    console.error("获取用户列表失败:", error);
    throw error; // 继续向上传递，前端组件可以 catch
  });
}

import request from '@/utils/request';

export function getUserList(params) {

  return request({
    url: '/users',
    method: 'get',
    params
  }).then(response => {
    const data = response.data;
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

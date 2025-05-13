import request from '@/utils/request';
import { message } from 'ant-design-vue';


/**获取评论区数据map */
export async function getComments(url, params) {
    return request({
        url: url,
        method: 'post',
        data:params
    }).then(response => {
        const data = response.data;
        // 类型校验
        if (!data || !Array.isArray(data.comments)) {
            throw new Error("响应数据格式不正确");
        }
        return data;
    }).catch(error => {
        console.error("获取文物评论信息失败:", error);
        message.error("获取文物评论信息失败");
        return {};
    });
}
/**上传发布的评论 */
export async function AddComments(url, params) {
    return request({
        url: url,
        method: 'post',
        data:params
    }).then(response => {
        if(response.status !== 200)
            throw new Error("响应异常");
        return true;
    }).catch(error => {
        console.error("发表评论信息失败:", error);
        message.error("发表评论信息失败");
        return false;
    });
}
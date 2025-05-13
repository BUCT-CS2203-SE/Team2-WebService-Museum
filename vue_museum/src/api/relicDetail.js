import request from '@/utils/request';
import { message } from 'ant-design-vue';


/**获取选择框数据map */
export async function getDetail(url, params) {
    return request({
        url: url,
        method: 'post',
        data:params
    }).then(response => {
        const data = response.data;
        // 类型校验
        if (!data || !Array.isArray(data.related)) {
            throw new Error("响应数据格式不正确");
        }
        return data;
    }).catch(error => {
        console.error("获取文物具体信息失败:", error);
        message.error("获取文物具体信息失败");
        return {};
    });
}
/**上传收藏信息 */
export async function isFavorite(url, params) {
    return request({
        url: url,
        method: 'post',
        data:params
    }).then(response => {
        if(response.status !== 200)
            throw new Error("响应异常");
        return true;
    }).catch(error => {
        console.error("获取文物具体信息失败:", error);
        message.error("修改失败");
        return false;
    });
}
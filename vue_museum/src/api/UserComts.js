import request from '@/utils/request';
import { message } from 'ant-design-vue';


/**获取收藏表格数据 */
export async function getComtsDetail(url, params) {
    return request({
        url: url,
        method: 'post',
        data: params
    }).then(response => {
        const data = response.data;
        // 类型校验
        if (!data || !Array.isArray(data)) {
            throw new Error("响应数据格式不正确");
        }
        data.forEach(element => {    //转变数据形式
            element.time = new Date(element.time).toLocaleString();
        })
        return data;
    }).catch(error => {
        console.error("获取评论信息失败:", error);
        message.error("获取评论信息失败");
        throw error("请求失败")
    });
}
/**取消收藏信息 */
export async function delComts(url, params) {
    return request({
        url: url,
        method: 'post',
        data: params
    }).then(response => {
        if (response.status !== 200 || response.data.ans !== true)
            throw new Error("响应异常");
        return true;
    }).catch(error => {
        console.error("删除评论信息失败:", error);
        message.error("删除失败");
        return false;
    });
}
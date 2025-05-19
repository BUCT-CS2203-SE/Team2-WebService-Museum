import request from '@/utils/request';
import { message } from 'ant-design-vue';


/**获取收藏表格数据 */
export async function getBroswerDetail(url, params) {
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
        data.forEach((element, index) => {
        // 序号从 1 开始，如果希望从 0 开始就去掉 +1
        element.seq = index + 1;                        // 新增序号字段
        element.time = new Date(element.time)           // 原有时间转换
                            .toLocaleString();
        });
        return data;
    }).catch(error => {
        console.error("浏览信息失败:", error);
        message.error("浏览信息失败");
        throw error("请求失败")
    });
}
/**取消收藏信息 */
export async function delBrowser(url, params) {
    return request({
        url: url,
        method: 'post',
        data: params
    }).then(response => {
        if (response.status !== 200 || response.data.ans !== true)
            throw new Error("响应异常");
        return true;
    }).catch(error => {
        console.error("删除浏览信息失败:", error);
        message.error("删除失败");
        return false;
    });
}
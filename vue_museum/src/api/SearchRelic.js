import request from '@/utils/request';
import { message } from 'ant-design-vue';


/**获取选择框数据list[map],配套SelectInput使用 */
export async function getcatasETcList(url, params) {
    return request({
        url: url,
        method: 'get',
        params
    }).then(response => {
        const data = response.data;
        // 类型校验
        if (!data || !Array.isArray(data)) {
            throw new Error("响应数据格式不正确，应为 [...]");
        }
        const result = [];
        const formatted = data.map(item => ({
            value: item,
            label: item
        }));
        result.value = [
            { value: 'null', label: '全部' },
            ...formatted
        ];
        return result;
    }).catch(error => {
        console.error("获取文物选择框列表失败:", error);
        message.error("获取文物选择框列表失败");
        return [{ value: 'null', label: '全部' }];
    });
}

/**文物查询函数，返回map */
export async function SearchRelic(url, params) {
    return request({
        url: url,
        method: 'post',
        data: params
    }).then(response => {
        const result = response.data;
        // 类型校验
        if (
            !result ||
            typeof result.total !== 'number' ||
            !Array.isArray(result.data)
        ) {
            throw new Error('响应数据格式不正确，应为 { total: number, data: Array }');
        }
        return result;
    }).catch(error => {
        console.error("获取文物列表失败:", error);
        message.error("获取文物列表失败");
        return { total: 0, data: [] };
    });
}
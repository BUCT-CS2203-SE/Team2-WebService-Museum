import request from '@/utils/request';
import { message } from 'ant-design-vue';
import { ref } from 'vue';

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
        const result = ref([]);
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
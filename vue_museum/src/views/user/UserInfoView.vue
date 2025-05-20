<template>
  <!-- 模板部分保持不变 -->
  <a-card title="个人信息管理" :bordered="false" class="user-profile-card">
    <!-- 头像上传区域 -->
    <div class="avatar-upload">
      <a-upload
        name="avatar"
        list-type="picture-card"
        class="avatar-uploader"
        :show-upload-list="false"
        :before-upload="handleManualUpload"
      >
        <img v-if="formState.avatar" :src="formState.avatar" alt="头像" />
        <div v-else>
          <loading-outlined v-if="loading"></loading-outlined>
          <plus-outlined v-else></plus-outlined>
          <div class="ant-upload-text">上传头像</div>
        </div>
      </a-upload>
    </div>

    <!-- 用户信息表单 -->
    <a-form
      :model="formState"
      :rules="rules"
      :label-col="{ span: 6 }"
      :wrapper-col="{ span: 14 }"
      @finish="onFinish"
      @finishFailed="onFinishFailed"
    >
      <a-form-item label="账号" name="username">
        <a-input v-model:value="formState.username" disabled />
      </a-form-item>

      <a-form-item label="密码" name="password" has-feedback>
        <a-input-password v-model:value="formState.password" />
      </a-form-item>

    <a-form-item
      label="确认密码"
      name="confirmPassword"
      :dependencies="['password']" 
      has-feedback
    >
      <a-input-password v-model:value="formState.confirmPassword" />
    </a-form-item>

     
      <a-form-item label="电子邮箱" name="email">
        <a-input v-model:value="formState.email" />
      </a-form-item>

      <a-form-item :wrapper-col="{ span: 14, offset: 6 }">
        <a-button type="primary" html-type="submit">保存修改</a-button>
        <a-button style="margin-left: 10px" @click="resetForm">重置</a-button>
      </a-form-item>
    </a-form>
  </a-card>
</template>

<script setup>
import { reactive, ref } from 'vue';
import { PlusOutlined, LoadingOutlined } from '@ant-design/icons-vue';
import { message } from 'ant-design-vue';
import api from "../../api/Api.js"
import axios from "@/utils/request";//测试时要注释掉，正式使用时取消注释


// 头像上传状态
const loading = ref(false);

// 表单数据
const formState = reactive({
  username: localStorage.getItem('username'), 
  password: null,
  confirmPassword: null,
  email: null,
  avatar: localStorage.getItem('avatar'),
});

// 表单验证规则（保持不变）
import { computed } from 'vue'

const rules = computed(() => ({
  password: [
    { message: '请输入密码', trigger: 'blur' },
    { min: 1, max: 18, message: '密码长度1-18位', trigger: 'blur' },
  ],
  confirmPassword: [
    // 只有在密码框有内容时，才加 required 规则
    ...(formState.password
      ? [{ required: true, message: '请确认密码', trigger: 'blur' }]
      : []),
    {
      validator(_, value) {
        // 如果密码没填，直接通过（因为此时并不要求必填）
        if (!formState.password) {
          return Promise.resolve()
        }
        // 密码框有值，再校验两次输入是否一致
        if (value === formState.password) {
          return Promise.resolve()
        }
        return Promise.reject(new Error('两次输入的密码不一致!'))
      },
      // 确保 change/blur 和 submit 都能触发
      trigger: ['blur','change'],
    },
  ],
  email: [
    { message: '请输入电子邮箱', trigger: 'blur' },
    { type: 'email', message: '请输入正确的邮箱格式', trigger: 'blur' },
  ],
}))



// 头像上传状态变化（ 实际项目中应该上传到服务器）
const handleManualUpload = async (file) => {
  const isJpgOrPng = file.type === 'image/jpeg' || file.type === 'image/png';
  const isLt2M = file.size / 1024 / 1024 < 2;

  if (!isJpgOrPng) {
    message.error('只能上传 JPG/PNG 格式的图片!');
    return false;
  }
  if (!isLt2M) {
    message.error('图片大小不能超过 2MB!');
    return false;
  }

  loading.value = true;

  const formData = new FormData();
  formData.append('file', file);
  formData.append('username', formState.username); // 供后端重命名使用

  try {
    console.log(formData);
    const response = await axios.post(api.url.user.upgradeAvatar, formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    });

    const url = response.data.url;
    if (url) {
      formState.avatar = url;
      localStorage.setItem('avatar',url);
      message.success('头像上传成功！');
    } else {
      message.error('服务器未返回文件地址');
    }
  } catch (error) {
    console.error('上传失败', error);
    message.error('头像上传失败: ' + (error.response?.data?.message || error.message));
  } finally {
    loading.value = false;
  }

  return false; // 阻止默认上传行为
};


// 表单提交（ 改为调用API提交数据）
// 这个表单提交函数是用API接口时用的，上面那个是测试使用
const onFinish = async (values) => {
  try {
    const submitData = {
      username: values.username,
      password: values.password,
      
      email: values.email,
    };
    if(!values.password && !values.email) {
      message.warn("未修改表单信息");
      return ;
    }
    const res = await axios.post(api.url.user.sendInfo, submitData);
    if(res.data.ans) message.success('个人信息更新成功');
    else{message.error(res.data.message); return ;}
  } catch (error) {
    message.error('更新失败: ' + (error.response?.data?.message || error.message));
  }
};

// 表单提交失败（保持不变）
const onFinishFailed = (errorInfo) => {
  console.log('验证失败:', errorInfo);
  message.error('请检查表单填写是否正确');
};

// 重置表单（重置为从接口获取的最新数据）
const resetForm = async () => {
  try {
    formState.confirmPassword = '';
    formState.email = '';
    formState.password = '';
    message.info('已重置表单');
  } catch (error) {
    message.error('重置失败');
  }
};
</script>

<style scoped>
/* 样式部分保持不变 */
.user-profile-card {
  max-width: 800px;
  margin: 20px auto;
  padding: 20px;
}

/* 覆盖 picture-card 容器为 120×120 的正方形 */
.avatar-uploader ::v-deep .ant-upload-select-picture-card {
  width: 120px;
  height: 120px;
  padding: 0;
}

/* 让 img 充满容器又不会超出，并圆角 */
.avatar-uploader ::v-deep .ant-upload-select-picture-card img {
  width: 100%;
  height: 100%;
  max-width: 110%;
  max-height: 110%;
  object-fit: cover;
  border-radius: 50%;
}
</style>

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
        :before-upload="beforeUpload"
        @change="handleAvatarChange"
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
        dependencies="['password']"
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
import { reactive, ref, onMounted } from 'vue';
import { PlusOutlined, LoadingOutlined } from '@ant-design/icons-vue';
import { message } from 'ant-design-vue';
import api from "../api/Api.js"
import axios from "@/utils/request";//测试时要注释掉，正式使用时取消注释


// 头像上传状态
const loading = ref(false);

// 表单数据
const formState = reactive({
  username: '', 
  password: '',
  confirmPassword: '',
  email: '',
  avatar: '',
});


// 表单验证规则（保持不变）
const rules = {
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 1, max: 18, message: '密码长度1-18位', trigger: 'blur' },
  ],
  confirmPassword: [
    { required: true, message: '请确认密码', trigger: 'blur' },
    ({ getFieldValue }) => ({
      validator(_, value) {
        if (!value || getFieldValue('password') === value) {
          return Promise.resolve();
        }
        return Promise.reject('两次输入的密码不一致!');
      },
      trigger: 'blur',
    }),
  ],
 
  email: [
    { required: true, message: '请输入电子邮箱', trigger: 'blur' },
    { type: 'email', message: '请输入正确的邮箱格式', trigger: 'blur' },
  ],
};

//  添加获取用户信息的函数
const fetchUserProfile = async () => {
  try {
    loading.value = true;
    const response = await axios.get(api.url.user.getInfo,{params:{username:"user"}}); 
    const userData = response.data;
    
    // 将接口数据映射到表单
    Object.assign(formState, {
      username: userData.username,
      
      email: userData.email,
      avatar: userData.avatar || 'https://randomuser.me/api/portraits/men/1.jpg', // 默认头像
    });
    
  } catch (error) {
    console.error('获取用户信息失败:', error);
    message.error('获取用户信息失败');
  } finally {
    loading.value = false;
  }
};

 // 组件挂载时获取用户数据
 onMounted(() => {
   fetchUserProfile();
 });

// 头像上传前的处理（保持不变）
const beforeUpload = (file) => {
  const isJpgOrPng = file.type === 'image/jpeg' || file.type === 'image/png';
  if (!isJpgOrPng) {
    message.error('只能上传JPG/PNG格式的图片!');
  }
  const isLt2M = file.size / 1024 / 1024 < 2;
  if (!isLt2M) {
    message.error('图片大小不能超过2MB!');
  }
  return isJpgOrPng && isLt2M;
};

// 头像上传状态变化（ 实际项目中应该上传到服务器）
const handleAvatarChange = (info) => {
  if (info.file.status === 'uploading') {
    loading.value = true;
    return;
  }
  if (info.file.status === 'done') {
    // 这里应该调用API上传头像
    // 模拟上传成功后更新头像
    formState.avatar = URL.createObjectURL(api.url.user.upgradeAvatar);
    loading.value = false;
    message.success('头像上传成功');
  }
  if (info.file.status === 'error') {
    loading.value = false;
    message.error('上传失败');
  }
};

// 表单提交（ 改为调用API提交数据）
// 这个表单提交函数是用API接口时用的，上面那个是测试使用
const onFinish = async (values) => {
  try {
    const submitData = {
      username: values.username,
      password: values.password,
      
      email: values.email,
      avatar: values.avatar
    };
    
    await axios.put(api.url.user.sendInfo, submitData);
    message.success('个人信息更新成功');
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
    await fetchUserProfile();
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

.avatar-upload {
  display: flex;
  justify-content: center;
  margin-bottom: 20px;
}

.avatar-uploader :deep(.ant-upload) {
  width: 128px;
  height: 128px;
}

.avatar-uploader :deep(.ant-upload img) {
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: 50%;
}
</style>

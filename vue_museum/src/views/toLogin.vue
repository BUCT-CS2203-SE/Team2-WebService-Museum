<template>
  <div class="login-container">
    <div class="animated-bg"></div>
    <transition v-if="showLo" id="denglu" >
      <div class="login-card">
        <div v-if="useAcc" style="color: black;font-size: 28px;">账号登录</div>
        <div v-else style="color: black;font-size: 28px;">邮箱登录</div>
        <form @submit.prevent="onSubmit">
          <div v-if="useAcc" class="input-group">
            <input v-model="u" type="text" placeholder="账号" required />
            <span class="underline"></span>
          </div>
          <div v-else class="input-group">
            <input v-model="uemail" ref="emailEl"  @input="validateLoginEmail" type="text" placeholder="邮箱"  required />
            <span class="underline"></span>
          </div>          
          <div class="input-group">
            <input v-model="p" type="password" placeholder="密码" required />
            <span class="underline"></span>
          </div>
          <a v-if="useAcc" @click.prevent="switchWay" class="youx">使用邮箱登录</a>
          <a v-else @click.prevent="switchWay" class="youx">使用账号登录</a>
          <button type="submit" class="login-button">登录</button>
          <div class="bot">
            <a @click.prevent="switchLogin_Forgot" class="bolab">忘记密码？</a>
            <div class="box">
              <a @click.prevent="switchLogin_Regest"  class="bolab">立即注册</a>
              <div>还没有账号？</div>
            </div>
          </div>
        </form>
      </div>
      </transition>
      <transition v-if="showRe">
      <div class="login-card">
        <div style="color: black;font-size: 28px;">账号注册</div>
        <form @submit.prevent="onRegest">
          <div class="input-group">
            <input v-model="uemail" ref="emailEl"  @input="validateLoginEmail" type="text" placeholder="邮箱" required />
            <span class="underline"></span>
          </div>
          <div class="input-group">
            <input v-model="newu" type="text" placeholder="账号" required />
            <span class="underline"></span>
          </div>
          <div class="input-group">
            <input v-model="newp" type="password" placeholder="密码" required />
            <span class="underline"></span>
          </div>
          <div class="input-group">
            <input v-model="renewp" ref="rpwdEl" @input="eraseError" type="password" placeholder="请再次输入密码" required />
            <span class="underline"></span>
          </div>
          <button type="submit" class="login-button">注册</button>
          <div class="bot">
            <a class="bolab"></a>
            <div class="box">
              <a @click.prevent="switchLogin_Regest" class="bolab">立即登录</a>
              <div>已有账号？</div>
            </div>
          </div>
        </form>
      </div>      
    </transition>
    <transition v-if="showForgot">
      <div class="login-card">
        <div style="color: black;font-size: 28px;">忘记密码</div>
        <form @submit.prevent="onFindAc">
          <div class="input-group">
            <input v-model="uemail" type="text" ref="emailEl"  @input="validateEmail" placeholder="邮箱" required />
            <span class="underline"></span>
          </div>
          <div class="qianbox">
            <div class="input-group">
              <input v-model="conp" type="test" placeholder="验证码" required />
              <span class="underline"></span>
            </div>
            <countDownButton
                :disabledInitially = "canclick"
                :emailInfo="uemail" />  
          </div>
          <div class="input-group">
            <input v-model="np" type="password" placeholder="新密码" required />
            <span class="underline"></span>
          </div>
          <button type="submit" class="login-button">重置密码</button>
          <div class="bot">
            <a class="bolab"></a>
            <div class="box">
              <a @click.prevent="switchLogin_Forgot" class="bolab">立即登录</a>
              <div>记起密码？</div>
            </div>
          </div>
        </form>
      </div>
      </transition>
  </div>
</template>


<script setup>
import { ref, onMounted } from 'vue';
import service from '@/utils/request';
import { useRouter } from 'vue-router';
import countDownButton from '@/components/countDownButton.vue';

const useAcc = ref(true); //采用何种方式登录，默认账号
function switchWay(){
    useAcc.value = !useAcc.value;
}

//登录逻辑
const u = ref(''); //账号登录页账号
const p = ref(''); //账号登录页密码
const router = useRouter();

/**邮箱普通验证 */
function validateLoginEmail(event) {
    const el = event.target;
    // 先清除旧的自定义消息
    el.setCustomValidity('');
    // 自定义正则校验逻辑
    const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    const isok = re.test(uemail.value.trim());
    console.log(uemail.value+isok)
    if (!isok) {
        // 格式不匹配时设置自定义提示
        el.setCustomValidity('邮箱格式不正确，请输入形如 user@example.com 的地址');
    }
    // 立即弹出提示框
    el.reportValidity();
}

async function onSubmit() {
    //注意函数的使用
    let ac = u.value;
    if(!useAcc.value) ac = uemail.value;
    const data = await service.post('/login', {way:useAcc.value, user: ac.trim(), password: p.value.trim() });
    if(data.data.status === 401) {alert(data.data.message);return ;}
    alert("登录成功!");
    localStorage.setItem('jwt', data.data.token);
    // 登录动作中
    console.log("保存成功："+data.data.token);
    //登陆成功跳转路径
    router.push('/');
}
/***************************************注册******************************************* */
const newu = ref(''); //新账号
const newp = ref(''); //新密码
const renewp =ref(''); //再次输入的密码
const rpwdEl = ref(null);
async function onRegest(){
    if(newp.value != renewp.value) {
        rpwdEl.value.setCustomValidity("请输入相同的密码!");
        rpwdEl.value.reportValidity();
        return ;
    }
    const response =  await service.post('/regest',{account:newu.value.trim(),password:newp.value.trim(),email:uemail.value.trim()});
    alert(response.data.message);
    if(response.status === 400) return;
    switchLogin_Regest();
}
/***************************************忘记密码******************************************* */
const conp = ref(''); //验证码
const np = ref(''); //新密码
async function onFindAc() {
  const resp = await service.post('/forget',{email:uemail.value.trim(),code:conp.value.trim(),newpwd:np.value.trim()});
  alert(resp.data.message);
  if(resp.status == 400) return;
  switchLogin_Forgot();
}

onMounted(() => {
  // 触发首次动画
});

//页面切换逻辑
const showLo = ref(true);
const showForgot = ref(false);
const showRe = ref(false);
function switchLogin_Forgot(){
    showLo.value = !showLo.value;
    showForgot.value = !showForgot.value;
}
function switchLogin_Regest(){
    showLo.value = !showLo.value;
    showRe.value = !showRe.value;
}

//密码重置逻辑
const uemail = ref(''); //邮箱
const canclick = ref(true);
const emailEl = ref(null); //错误消息
/**绑定按钮的邮箱验证 */
function validateEmail(event) {
    const el = event.target;
    // 先清除旧的自定义消息
    el.setCustomValidity('');
    // 自定义正则校验逻辑
    const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    const isok = re.test(uemail.value.trim());
    console.log(uemail.value+isok)
    if (!isok) {
        // 格式不匹配时设置自定义提示
        el.setCustomValidity('邮箱格式不正确，请输入形如 user@example.com 的地址');
    } else {
        console.log(uemail.value +' '+ isok);
        canclick.value = false; //可以发送验证码
    }
    // 立即弹出提示框
    el.reportValidity();
}
/**清除输入框中的错误 */
function eraseError(event){
    event.target.setCustomValidity('');
}

</script>

<style scoped>
a {
    cursor: pointer;
}
.login-container {
  position: relative;
  width: 100vw;
  height: 100vh;
  overflow: hidden;
  display: flex;
  justify-content: center;
  align-items: center;
  background: #111;
}

@keyframes waveStreak {
  0%   { background-position: 0% 50%; }
  50%  { background-position: 100% 50%; }
  100% { background-position: 0% 50%; }
}

.animated-bg {
  position: absolute;
  inset: 0;
  background: repeating-linear-gradient(
    135deg,
    #8b6e4f,      /* 古铜褐 */
    #8b6e4f 12%,  /* 保持 12% 宽度 */
    #e5dac0 12%,  /* 象牙白 */
    #a7a7a6 24%,  /* 再 12% */
    #696969 24%,  /* 深石板灰 */
    #696969 36%   /* 再 12%，形成三色带 */
  ); /* 重复条带 :contentReference[oaicite:0]{index=0} */
  background-size: 200% 200%;
  animation: waveStreak 20s ease-in-out infinite;
  z-index: 0;
}


@keyframes gradientShift {
  0% { background-position: 0% 50%; }
  50% { background-position: 100% 50%; }
  100% { background-position: 0% 50%; }
}

.login-card {
  position: relative;
  z-index: 1;
  width: 320px;
  padding: 30px;
  background: rgba(255,255,255,0.1);
  border-radius: 12px;
  backdrop-filter: blur(40px);
  box-shadow: 0 8px 32px rgba(0,0,0,0.5);
  color: #fff;
  text-align: center;
}

.youx{
    display: flex;
    flex-direction: row-reverse;
    margin-top: -14px;
    margin-bottom: -8px;
    color: rgb(131, 34, 222);
    text-decoration-line: none;
}

.input-group {
  position: relative;
  margin: 30px 0;
}

.input-group input {
  width: 100%;
  padding: 6px 0;
  background: transparent;
  border: none;
  border-bottom: 2px solid rgba(255,255,255,0.5);
  color: #000000;
  font-size: 20px;
  outline: none;
}

.input-group input::placeholder {
  color: #dfdfdf;               /* 字体颜色 */
  opacity: 1;                /* Firefox 默认降低透明度，显式设置为 1 */
  font-size: 20px;           /* 字体大小 */
  font-family: 'Arial', sans-serif; /* 字体 */
  font-style: italic;        /* 斜体 */
  letter-spacing: 0.5px;     /* 字符间距 */
}


.input-group input:focus + label,
.input-group input:not(:placeholder-shown) + label {
  transform: translateY(-20px);
  font-size: 12px;
  color: #fff;
}

.input-group .underline {
  position: absolute;
  bottom: -2px;
  left: 0;
  width: 0;
  height: 2px;
  background: #ffeb3b;
  transition: 0.4s ease;
}

.input-group input:focus ~ .underline {
  width: 100%;
}

.login-button {
  margin-top: 20px;
  width: 100%;
  padding: 12px;
  background: #ffeb3b;
  border: none;
  border-radius: 6px;
  font-size: 16px;
  color: #111;
  cursor: pointer;
  transition: background 0.3s ease;
}

.login-button:hover {
  background: #ffe033;
}

.bot {
  margin-top: 8px;
  display: grid;
  grid-template-columns: 1fr 2fr;
}
.box{
  display: flex;
  flex-direction: row-reverse;
}
.bolab {
  color: rgb(131, 34, 222);
  text-decoration-line: none;
}

.qianbox {
  display: grid;
  grid-template-columns: 2fr 1fr;
  gap: 8px;
}
</style>

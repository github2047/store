<script>
import {defineComponent, reactive, ref} from 'vue'
import { useMessage} from 'naive-ui'
import { useRouter } from 'vue-router'
import user from "../util/api/user";
import store from "../util/store";
import {defaultFormInstValue} from "../util/api/types";
import merchant from "../util/api/merchant";

export default defineComponent({
    name: 'Login',
    setup() {
        const router = useRouter()
        const formRef = ref(null)
        const rPasswordFormItemRef = ref(null)
        const message = useMessage()
        const loginModel = reactive({
            username: null,
            password: null
        })
        const regModel = ref({
            username: null,
            password: null,
            payPwd: null
        })
        const loginLoading = ref(false),regLoading=ref(false)

        const regFrm = ref(defaultFormInstValue())
        const regRules = ref({
            username: [
                {
                    required: true,
                    trigger: 'blur',
                    validator: (rule, value) => {
                        return new Promise((resolve, reject) => {
                            if (!value) {
                                reject(Error('请输入登录邮箱'))
                            } else {
                                user.emailExists(value).then(b => {
                                    if (b) {
                                        reject(Error('登录邮箱已经存在了'))
                                    } else {
                                        resolve(null)
                                    }
                                }).catch(e => reject(e))
                            }
                        })
                    }
                }
            ],
            password: [
                {
                    required: true,
                    message:'请输入登录密码'
                }
            ],
            payPwd: [
                {
                    required: true,
                    message: '请输入支付密码'
                }
            ]
        })
        function handleLogin() {
            const {username, password} = loginModel
            user.login(username, password).then(user => {
                if (user) {
                    // console.log(user.id, user.username)
                    store.loginUser = user;
                    router.push('/main?from=login')
                }
            }).catch(e => {
                console.log(e);
                message.info(e.message)
            })
        }
        function handleReg() {
            regFrm.value.validate((errors) => {
                if (errors) {
                    return;
                }
                user.reg(regModel.value).then(user => {
                    if (user) {
                        store.loginUser = user;
                        router.push('/main?from=login')
                    }
                }).catch(e => {
                    console.log(e);
                    message.info(e.message)
                })
            });
        }

        return {
            loginLoading,regLoading,
            loginModel,
            regModel,
            handleLogin,
            handleReg,
            regFrm,regRules
        }
    }
})
</script>

<template>
    <div id="login-page" class="main-bg">
        <div class="header">
            <div class="content-wrap row">
                <div class="logo">
                    <img src="./../assets/pay-logo.svg" id="app-logo" alt="">
                </div>
                <div class="site-text">魔法支付</div>
            </div>
        </div>
        <div class="page-wrap">
            <n-tabs default-value="sign_in" size="large" justify-content="space-evenly">
                <n-tab-pane name="sign_in" tab="登录">
                    <n-form :model="loginModel">
                        <div class="form-item">
                            <n-input v-model:value="loginModel.username" autofocus placeholder="请输入邮箱/手机号码">
                                <template #prefix>
                                    <n-icon>
                                        <svg t="1634382855022" class="icon" viewBox="0 0 1024 1024" version="1.1"
                                             xmlns="http://www.w3.org/2000/svg" p-id="7342" width="128" height="128">
                                            <path
                                                d="M492.642454 544.492238c138.576752 0 250.922119-112.345367 250.922119-250.924119 0-138.576752-112.344367-250.922119-250.922119-250.922119-138.578752 0-250.924119 112.345367-250.924119 250.923119 0 138.577752 112.345367 250.923119 250.924119 250.923119m329.666273 152.226433C763.454107 634.645126 743.657571 613.336625 635.847097 607.485762H349.42681c-107.810473 5.851863-127.607009 27.159363-186.461629 89.231909-62.510535 64.169496-112.295368 145.404592-88.694922 246.144231 26.184386 65.876456 96.935728 81.138098 163.83616 81.138098h509.062069c66.899432 0 137.699773-15.261642 163.885159-81.138098 23.599447-100.739639-26.185386-181.974735-88.74492-246.144231"
                                                p-id="7343"></path>
                                        </svg>
                                    </n-icon>
                                </template>
                            </n-input>
                        </div>
                        <div class="form-item">
                            <n-input type="password" show-password-on="click" v-model:value="loginModel.password" placeholder="请输入登录密码">
                                <template #prefix>
                                    <n-icon>
                                        <svg t="1634382912230" class="icon" viewBox="0 0 1024 1024" version="1.1"
                                             xmlns="http://www.w3.org/2000/svg" p-id="9150" width="128" height="128">
                                            <path
                                                d="M780.8 354.58H665.6v-42.89c0-72.31-19.85-193.3-153.6-193.3-138.87 0-153.6 135.05-153.6 193.3v42.89H243.2v-42.89C243.2 122.25 348.79 0 512 0s268.8 122.25 268.8 311.69v42.89z m-192 314.84c0-43.52-34.58-78.65-76.8-78.65s-76.8 35.13-76.8 78.65c0 29.46 15.4 54.47 38.44 67.82v89.64c0 21.74 17.25 39.7 38.4 39.7s38.4-17.96 38.4-39.7v-89.64c23-13.35 38.36-38.36 38.36-67.82zM896 512v393.61c0 65.26-51.87 118.39-115.2 118.39H243.2c-63.291 0-115.2-53.13-115.2-118.39V512c0-65.22 51.87-118.39 115.2-118.39h537.6c63.33 0 115.2 53.17 115.2 118.39z"
                                                p-id="9151"></path>
                                        </svg>
                                    </n-icon>
                                </template>
                            </n-input>
                        </div>
                    </n-form>
                    <n-button :loading="loginLoading" type="primary" block @click="handleLogin">登录</n-button>
                </n-tab-pane>
                <n-tab-pane name="signup" tab="注册">
                    <n-form ref="regFrm" :model="regModel" :rules="regRules" :show-label="false">
                        <n-form-item-row class="form-item" path="username">
                            <n-input v-model:value="regModel.username" autofocus placeholder="请输入邮箱">
                                <template #prefix>
                                    <n-icon>
                                        <svg t="1634382855022" class="icon" viewBox="0 0 1024 1024" version="1.1"
                                             xmlns="http://www.w3.org/2000/svg" p-id="7342" width="128" height="128">
                                            <path
                                                d="M492.642454 544.492238c138.576752 0 250.922119-112.345367 250.922119-250.924119 0-138.576752-112.344367-250.922119-250.922119-250.922119-138.578752 0-250.924119 112.345367-250.924119 250.923119 0 138.577752 112.345367 250.923119 250.924119 250.923119m329.666273 152.226433C763.454107 634.645126 743.657571 613.336625 635.847097 607.485762H349.42681c-107.810473 5.851863-127.607009 27.159363-186.461629 89.231909-62.510535 64.169496-112.295368 145.404592-88.694922 246.144231 26.184386 65.876456 96.935728 81.138098 163.83616 81.138098h509.062069c66.899432 0 137.699773-15.261642 163.885159-81.138098 23.599447-100.739639-26.185386-181.974735-88.74492-246.144231"
                                                p-id="7343"></path>
                                        </svg>
                                    </n-icon>
                                </template>
                            </n-input>
                        </n-form-item-row>
                        <n-form-item-row class="form-item" path="password">
                            <n-input type="password" show-password-on="click" v-model:value="regModel.password" placeholder="请输入登录密码">
                                <template #prefix>
                                    <n-icon>
                                        <svg t="1634382912230" class="icon" viewBox="0 0 1024 1024" version="1.1"
                                             xmlns="http://www.w3.org/2000/svg" p-id="9150" width="128" height="128">
                                            <path
                                                d="M780.8 354.58H665.6v-42.89c0-72.31-19.85-193.3-153.6-193.3-138.87 0-153.6 135.05-153.6 193.3v42.89H243.2v-42.89C243.2 122.25 348.79 0 512 0s268.8 122.25 268.8 311.69v42.89z m-192 314.84c0-43.52-34.58-78.65-76.8-78.65s-76.8 35.13-76.8 78.65c0 29.46 15.4 54.47 38.44 67.82v89.64c0 21.74 17.25 39.7 38.4 39.7s38.4-17.96 38.4-39.7v-89.64c23-13.35 38.36-38.36 38.36-67.82zM896 512v393.61c0 65.26-51.87 118.39-115.2 118.39H243.2c-63.291 0-115.2-53.13-115.2-118.39V512c0-65.22 51.87-118.39 115.2-118.39h537.6c63.33 0 115.2 53.17 115.2 118.39z"
                                                p-id="9151"></path>
                                        </svg>
                                    </n-icon>
                                </template>
                            </n-input>
                        </n-form-item-row>
                        <n-form-item-row class="form-item" path="payPwd">
                            <n-input type="password" show-password-on="click" v-model:value="regModel.payPwd" placeholder="请输入支付密码">
                                <template #prefix>
                                    <n-icon>
                                        <svg t="1634382912230" class="icon" viewBox="0 0 1024 1024" version="1.1"
                                             xmlns="http://www.w3.org/2000/svg" p-id="9150" width="128" height="128">
                                            <path
                                                d="M780.8 354.58H665.6v-42.89c0-72.31-19.85-193.3-153.6-193.3-138.87 0-153.6 135.05-153.6 193.3v42.89H243.2v-42.89C243.2 122.25 348.79 0 512 0s268.8 122.25 268.8 311.69v42.89z m-192 314.84c0-43.52-34.58-78.65-76.8-78.65s-76.8 35.13-76.8 78.65c0 29.46 15.4 54.47 38.44 67.82v89.64c0 21.74 17.25 39.7 38.4 39.7s38.4-17.96 38.4-39.7v-89.64c23-13.35 38.36-38.36 38.36-67.82zM896 512v393.61c0 65.26-51.87 118.39-115.2 118.39H243.2c-63.291 0-115.2-53.13-115.2-118.39V512c0-65.22 51.87-118.39 115.2-118.39h537.6c63.33 0 115.2 53.17 115.2 118.39z"
                                                p-id="9151"></path>
                                        </svg>
                                    </n-icon>
                                </template>
                            </n-input>
                        </n-form-item-row>
                    </n-form>
                    <div style="margin-top: 20px">
                        <n-button :loading="regLoading" type="primary" block @click="handleReg">注册</n-button>
                    </div>
                </n-tab-pane>
            </n-tabs>
        </div>
    </div>
</template>

<style scoped lang="less">
#login-page {

}

.page-wrap {
    width: 400px;
    background-color: rgba(255, 255, 255, 0.8);
    padding: 20px 20px 40px;
    border-radius: 5px;
    position: absolute;
    top: 24%;
    left: 50%;
    transform: translateX(-50%);
    box-sizing: border-box;
}

@media screen and (max-width: 500px) {
    .page-wrap {
        width: 90%;
    }
}
</style>

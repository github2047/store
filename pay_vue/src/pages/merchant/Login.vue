<template>
    <div class="app-login-page">
        <div class="login-wrap">
            <div class="left-panel"></div>
            <div class="right-login">
                <n-tabs default-value="login" size="large" justify-content="space-evenly">
                    <n-tab-pane name="login" tab="登录">
                        <n-form class="frm-content" ref="loginFrm" :rules="rules.login" :model="loginModel">
                            <n-form-item-row label="登录账号" path="account">
                                <n-input placeholder="请输入登录账号/商户编号" v-model:value="loginModel.account"/>
                            </n-form-item-row>
                            <n-form-item-row label="登录密码" path="password">
                                <n-input placeholder="请输入登录密码" v-model:value="loginModel.password" type="password"/>
                            </n-form-item-row>
                        </n-form>
                        <div style="margin-top: 10px">
                            <n-button type="info" :loading="dataModel.loginLoading" block @click="handleLogin">登录</n-button>
                        </div>
                    </n-tab-pane>
                    <n-tab-pane name="signup" tab="注册">
                        <n-form class="frm-content" ref="regFrm" :rules="rules.reg" :model="regModel"
                                style="margin-top: 20px">
                            <n-form-item-row label="登录账号" path="account">
                                <n-input placeholder="请输入登录账号" v-model:value="regModel.account"/>
                            </n-form-item-row>
                            <n-form-item-row label="应用名称" path="appName">
                                <n-input placeholder="请输入应用名称" v-model:value="regModel.appName"/>
                            </n-form-item-row>
                            <n-form-item-row label="登录密码" path="password">
                                <n-input placeholder="请输入登录密码" v-model:value="regModel.password" type="password"/>
                            </n-form-item-row>
                        </n-form>

                        <div style="margin-top: 10px">
                            <n-button type="primary" :loading="dataModel.regLoading" block @click="handleReg">注册</n-button>
                        </div>
                    </n-tab-pane>
                </n-tabs>
            </div>
        </div>
    </div>
</template>

<script lang="ts">
import {defineComponent, ref} from 'vue'
import {FormInst, useMessage} from 'naive-ui'
import merchant, {LoginParam, RegParam} from './../../util/api/merchant'
import {useRouter} from "vue-router";
import store from "../../util/store";
import {defaultFormInstValue} from "../../util/api/types";


export default defineComponent({
    name: "MerchantLogin",
    setup(props) {
        const message = useMessage()
        const router = useRouter()


        const callback = () => {
            console.log(arguments)
        }
        const handleUpload = () => {
            console.log(arguments)
        }
        const handleFinish = (file: any) => {
            console.log(file)
        }

        const rules = ref({
            login: {
                account: [
                    {
                        required: true,
                        message: '请输入登录账号'
                    }
                ],
                password: [
                    {
                        required: true,
                        message: '请输入登录密码'
                    }
                ]
            },
            reg: {
                account: [
                    {
                        required: true,
                        trigger: 'blur',
                        validator: (rule: any, value: string) => {
                            return new Promise((resolve, reject) => {
                                if (!value) {
                                    reject(Error('请输入登录账号'))
                                } else {
                                    merchant.accountExists(value).then(b => {
                                        if (b) {
                                            reject(Error('登录账号已经存在了'))
                                        } else {
                                            resolve(null)
                                        }
                                    }).catch(e => reject(e))
                                }
                            })
                        }
                    }
                ],
                appName: [
                    {
                        required: true,
                        trigger: 'blur',
                        validator: (rule: any, value: string) => {
                            return new Promise((resolve, reject) => {
                                if (!value) {
                                    reject(Error('请输入应用名称'))
                                } else {
                                    merchant.appNameExists(value).then(b => {
                                        if (b) {
                                            reject(Error('应用名称已经存在了'))
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
                        message: '请输入登录密码'
                    }
                ]
            }
        })
        const loginFrm = ref<FormInst>(defaultFormInstValue())
        const regFrm = ref<FormInst>(defaultFormInstValue())
        const regModel = ref<RegParam>({
            account: '',
            appName: '',
            password: ''
        })
        const loginModel = ref<LoginParam>({
            account: '',
            password: ''
        })
        const dataModel = ref({
            loginLoading:false,
            regLoading:false
        })
        const handleLogin = () => {
            loginFrm.value.validate((errors) => {
                if (errors) {
                    return;
                }
                dataModel.value.loginLoading = true
                merchant.login(loginModel.value).then(ret => {
                    store.merchantInfo = ret
                    router.push('/merchant?from=e-login')
                }).catch(e => message.info(e.message))
                    .finally(()=>dataModel.value.loginLoading = false)
            })
        }
        const handleReg = () => {
            regFrm.value.validate((errors) => {
                if (errors) {
                    return;
                }
                dataModel.value.regLoading = true
                merchant.create(regModel.value).then(ret => {
                    store.merchantInfo = ret
                    router.push('/merchant?from=e-login')
                }).catch(e => message.info(e.message))
                    .finally(()=>dataModel.value.regLoading = false)
            })
        }
        return {
            loginModel, regModel,dataModel,
            callback, handleUpload, handleFinish, loginFrm, regFrm,
            rules, handleLogin, handleReg,
            postUrl: 'https://api.lubanwj.com/basic/upload/image'
        }
    }
})
</script>

<style scoped lang="less">
.app-login-page {
    height: 100vh;
    background: url('../../assets/e-login-bg.jpg') no-repeat;
    background-size: cover;
}

.login-wrap {
    width: 800px;
    height: 450px;
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
    display: flex;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
    overflow: hidden;

    .left-panel {
        width: 400px;
        background: url("../../assets/e-login-left_03.jpg") no-repeat;
        background-size: cover;
        border-radius: 5px 0 0 5px;
    }

    .right-login {
        background-color: #fff;
        flex: 1;
        padding: 20px 50px;
        border-radius: 0 5px 5px 0;
    }
}

.frm-content {
    margin-top: 50px
}
</style>
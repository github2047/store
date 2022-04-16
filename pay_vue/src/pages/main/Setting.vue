<template>

    <n-alert type="info" >如果不修改密码则不要填写对应的新密码</n-alert>
    <div class="page-main-setting" style="width: 600px;margin:40px auto">
        <n-spin :show="data.loading">
            <n-form :model="model" class="pay-form" label-placement="left"
                    :label-width="160" :rules="rules" ref="formRef">
                <n-form-item label="原始登录密码" path="oriLoginPwd">
                    <n-input v-model:value="model.oriLoginPwd" placeholder="请输入原始登录密码"/>
                </n-form-item>
                <n-form-item label="新登录密码">
                    <n-input v-model:value="model.loginPwd" placeholder="请输入新登录密码"/>
                </n-form-item>
                <n-form-item label="新支付密码">
                    <n-input v-model:value="model.payPwd" placeholder="请输入新的支付密码"/>
                </n-form-item>
                <div style="margin-left: 160px;">
                    <n-button style="width:160px" type="info"  @click="validateAndSave">修改</n-button>
                </div>
            </n-form>
        </n-spin>
    </div>
</template>

<script lang="ts">
import {ref, defineComponent} from 'vue'
import {FormInst, useMessage} from "naive-ui";
import {defaultFormInstValue} from "../../util/api/types";

export default defineComponent({
    name: "Setting",
    setup() {
        const data = ref({
            loading: false
        }), model = ref({
            oriLoginPwd: '',
            loginPwd: '',
            payPwd: ''
        }),rules = {
            oriLoginPwd: [
                {
                    required: true,
                    message:'请输入原始登录密码',
                    trigger: ['input', 'blur']
                }
            ],
        },formRef = ref<FormInst>(defaultFormInstValue());
        const message = useMessage();
        return {
            formRef,
            data, model,rules,
            validateAndSave(){
                formRef.value.validate((errors: any) => {
                    if (!errors) {
                        message.success('Valid')
                    } else {
                        console.log(errors)
                        message.error('Invalid')
                    }
                })
            }
        }
    }
})
</script>

<style scoped>

</style>
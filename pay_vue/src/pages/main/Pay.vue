<template>
    <div class="page-main-pay">
        <n-spin :show="data.loading">
            <n-form :model="model" class="pay-form" :rules="rules" ref="formRef" label-placement="left"
                    :label-width="160">
                <n-form-item label="收款人用户名" path="username">
                    <n-input v-model:value="model.username" placeholder="请输入收款人邮箱或者电话"/>
                </n-form-item>
                <n-form-item label="收款人用户名">
                    <n-spin :show="data.queryLoading">
                        <span>{{ data.username }}</span>
                    </n-spin>
                </n-form-item>
                <n-form-item label="转账金额" path="amount">
                    <n-input-number v-model:value="model.amount" min="0" placeholder="请输入转账金额"/>
                </n-form-item>
                <n-form-item label="备注">
                    <n-input v-model:value="model.remark" placeholder="请输入转账备注"/>
                </n-form-item>
                <div style="margin-left: 160px;">
                    <n-button style="width:120px" type="info">验证</n-button>
                </div>
            </n-form>
        </n-spin>
    </div>
</template>

<script>
import {defineComponent, ref} from 'vue'
import user from "../../util/api/user";

export default defineComponent({
    name: "Pay",
    setup() {
        const model = ref({
            username: null,
            amount: null,
            remark: null,
        })
        const data = ref({
            username: '',
            loading: false,
            queryLoading: false
        })
        const rules = {
            amount: [
                {
                    required: true,
                    validator(rule, value) {
                        if (!value) {
                            return new Error('请填写转账金额')
                        } else if (Number(value) < 0) {
                            return new Error('转账金额不能小于0')
                        }
                        return true
                    },
                    trigger: ['input', 'blur']
                }
            ],
            username: [
                {
                    required: true,
                    trigger: 'blur',
                    validator: (rule, value) => {
                        return new Promise((resolve, reject) => {
                            if (!value) {
                                reject(new Error('请输入转账用户手机或者邮箱地址'))
                            } else {
                                data.value.queryLoading = true
                                user.query(value).then(ret => {
                                    if (ret) {
                                        resolve();
                                        data.value.username = ret.username
                                    } else {
                                        reject(new Error('转账的用户不存在'))
                                    }
                                }).catch(e => {
                                    console.log(e)
                                    reject(e)
                                }).finally(()=>{
                                    data.value.queryLoading = false
                                })
                            }
                        })
                    }
                }
            ]
        }
        return {
            model, data, rules
        }
    }
})
</script>

<style scoped>
.pay-form {
    max-width: 600px;
    margin: 50px auto;
}
</style>
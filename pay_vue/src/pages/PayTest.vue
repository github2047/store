<template>
    <div class="container">
        <div v-if="showPay">
            <div v-if="status == 'waiting'" class="text-center">
                <a class="pay-btn" @click="checkPayStatus"
                   :href="payUrl" target="_blank">开始支付</a>
            </div>
            <div v-else-if="status == 'no'" class="text-center">
                <n-spin size="large" description="等待支付完成..."/>
            </div>
            <template v-else>
                <n-result status="success" title="支付成功">
                    <template #footer>
                        <n-button @click="payAgain">再来一次</n-button>
                    </template>
                </n-result>
            </template>
        </div>
        <div v-else class="text-center">
            <n-button type="info" size="large" @click="create">创建订单</n-button>
        </div>
    </div>
</template>

<script>
import {defineComponent, ref} from "vue";
import {useMessage} from "naive-ui";

export default defineComponent({
    name: "PayTest",
    setup(props) {
        const showPay = ref(false),
            payUrl = ref(''),
            orderNo = ref(''),
            status = ref('waiting')

        const msg = useMessage()
        const request = (req) => {
            return fetch('http://localhost:8089' + req).then(res => res.json());
        }
        let timer = null;
        return {
            showPay, payUrl, status,
            payAgain() {
                showPay.value = false
            },
            checkPayStatus() {
                timer = setInterval(() => {
                    fetch('http://localhost:8089/status?no=' + orderNo.value).then(res => res.text()).then(ret => {
                        status.value = ret.toString()
                        if (ret == 'yes') {
                            clearInterval(timer)
                        }
                    })
                }, 5000)
            },
            create() {
                status.value = 'waiting'
                showPay.value = false
                request('/create').then(ret => {
                    if (ret.code == 0) {
                        showPay.value = true
                        orderNo.value = ret.data.out_trade_no
                        payUrl.value = ret.message
                        console.log(ret)
                    } else {
                        msg.info(ret.message)
                    }
                }).catch(e => msg.error(e.message))
            }
        }
    }
})
</script>

<style scoped lang="less">
.container {
    margin: 50px auto;
}

.pay-btn {
    display: inline-block;
    font-weight: 400;
    line-height: 1.5;
    text-align: center;
    text-decoration: none;
    vertical-align: middle;
    cursor: pointer;
    -webkit-user-select: none;
    -moz-user-select: none;
    user-select: none;
    background-color: #2080f0;
    border: 1px solid #2080f0;
    font-size: 1rem;
    border-radius: 3px;
    transition: color .15s ease-in-out, background-color .15s ease-in-out, border-color .15s ease-in-out, box-shadow .15s ease-in-out;
    color: #fff;
    padding: 5px 18px;
    font-weight: 600;

    &:hover {
        background-color: #4098fc;
    }
}
</style>
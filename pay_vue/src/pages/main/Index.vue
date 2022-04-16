<template>
    <div class="page-main-index">
        <div class="info">
            <n-grid x-gap="12" cols="7" item-responsive>
                <n-grid-item span="4">
                    <n-card title="账户信息">
                        <div class="card-content text-center">
                            <n-grid :cols="3">
                                <n-gi>
                                    <div class="block-item">
                                        <div class="value">{{ numberFormat(userInfo.balance / 100.0) }}元</div>
                                        <div class="title">
                                            <span>余额</span>
                                            <n-button style="margin-left: 10px;" text type="info" @click="charge">充值
                                            </n-button>
                                        </div>
                                    </div>
                                </n-gi>
                                <n-gi>
                                    <div class="block-item">
                                        <div class="value">0.00元</div>
                                        <div class="title">待确认</div>
                                    </div>
                                </n-gi>
                                <n-gi>
                                    <div class="block-item">
                                        <div class="value">20笔</div>
                                        <div class="title">交易次数</div>
                                    </div>
                                </n-gi>
                            </n-grid>
                        </div>
                    </n-card>
                </n-grid-item>
                <n-grid-item span="3">
                    <n-card title="系统消息">
                        <div class="card-content">
                            <sys-notice :query-count="5"/>
                        </div>
                    </n-card>
                </n-grid-item>
            </n-grid>
        </div>
        <div class="data-list" style="margin-top: 20px;">
            <n-card :bordered="false" title="最近交易记录" header-style="padding:20px 0;" content-style="padding: 0;">
                <PayRecord/>
            </n-card>
        </div>
    </div>
</template>

<script lang="ts">
import PayRecord from '../../components/PayRecord.vue'
import HelloWorld from "../../components/HelloWorld.vue"
import SysNotice from "../../components/SysNotice.vue"
import store from "../../util/store";
import {useRouter} from "vue-router";
import {useMessage} from "naive-ui";
import user from "../../util/api/user";
import {ref} from "vue";
import {numberFormat} from "../../util/utils";

export default {
    name: "Index",
    components: {SysNotice, HelloWorld, PayRecord},
    setup() {
        const userData = store.loginUser
        const router = useRouter()
        if (!userData) {
            router.replace('/login')
        }
        const msg = useMessage();
        const userInfo = ref(userData)
        return {
            numberFormat,
            userInfo,
            charge() {
                const money = Number(prompt('请输入要充值的金额:', ''))
                if (isNaN(money) || money <= 0) {
                    msg.error('充值金额数据不正确')
                    return;
                }
                user.charge(userData.id, money).then(ret => {
                    store.loginUser = ret
                    userInfo.value = ret
                    msg.success('充值成功')
                }).catch(e => msg.error(e.message))
            }
        }
    }
}
</script>

<style scoped lang="less">
.block-item {
    .value {
        font-size: 20px;
        color: #3c80f0;
    }
}

.card-content {
    height: 70px;
}
</style>
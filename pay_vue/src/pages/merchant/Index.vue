<template>
    <div id="app-main">
        <div class="nav">
            <div class="container">
                <div class="row">
                    <div class="logo float-left">
                        <img src="./../../assets/pay-logo.svg" id="app-logo" class="float-left">
                        <span class="site-text">魔法支付</span>
                    </div>
                    <div class="float-right logout-text">
                        <span class="link-text" @click="logout">退出登录</span>
                    </div>
                </div>
            </div>
        </div>
        <div class="content container">
            <div class="page-main-index">
                <div class="info">
                    <n-grid x-gap="12" cols="10" item-responsive>
                        <n-grid-item span="3">
                            <n-card title="基本信息">
                                <div class="card-content">
                                    <div class="block-item">
                                        <div class="title">商户编号</div>
                                        <div class="value">{{ info.id }}</div>
                                    </div>
                                    <div class="block-item">
                                        <div class="title">应用名称</div>
                                        <div class="value">{{ info.appName }}</div>
                                    </div>
                                    <div class="block-item">
                                        <div class="title">
                                            <span>应用图标</span>
                                        </div>
                                        <div class="value">
                                            <span style="vertical-align: middle;display: inline-block">
                                                <img :src="info.appIcon" alt=""></span>
                                            <span style="margin-left: 10px;" @click="showReplace"
                                                  class="link-text under-line">替换</span>
                                        </div>
                                    </div>
                                    <div class="block-item">
                                        <div class="title">当前资金</div>
                                        <div class="value">{{ (info.balance / 100.0).toFixed(2) }}元</div>
                                    </div>
                                </div>
                            </n-card>
                        </n-grid-item>
                        <n-grid-item span="3">
                            <n-card title="开发信息">
                                <div class="card-content">
                                    <div class="block-item">
                                        <div class="title">安全秘钥</div>
                                        <div class="value">
                                            <merchant-cert-key/>
                                        </div>
                                    </div>
                                    <div class="block-item">
                                        <div class="title">支付网关</div>
                                        <div class="value">http://{{ pay_host }}:30083</div>
                                    </div>
                                    <div class="block-item">
                                        <div class="title">应用网关</div>
                                        <div class="value">暂不可用</div>
                                    </div>
                                </div>
                            </n-card>
                        </n-grid-item>
                        <n-grid-item span="4">
                            <n-card title="系统消息">
                                <div class="card-content">
                                    <sys-notice :query-count="10"/>
                                </div>
                            </n-card>
                        </n-grid-item>
                    </n-grid>
                </div>
            </div>
            <div class="pay-records" style="margin-top: 30px">
                <merchant-pay-record/>
            </div>
        </div>
        <n-modal v-model:show="showModal" preset="dialog" title="请输入新的图标网址" :show-icon="false">
            <div style="padding: 20px 0;">
                <n-input v-model:value="replaceIconUrl" placeholder="请填写完整的图标网址" clearable/>
            </div>
            <template #action>
                <div>
                    <n-button @click="showModal=false" size="small" style="margin-right: 10px">算了</n-button>
                    <n-button type="info" size="small" @click="onReplaceHandle">马上替换</n-button>
                </div>
            </template>
        </n-modal>
    </div>
</template>

<script lang="ts">
import {defineComponent, ref, onMounted} from 'vue'
import store from "../../util/store";

import SysNotice from "../../components/SysNotice.vue";
import MerchantPayRecord from "../../components/MerchantPayRecord.vue";
import {useRouter} from "vue-router";
import MerchantCertKey from "../../components/MerchantCertKey.vue";
import merchant from "../../util/api/merchant";
import {useDialog, useMessage} from "naive-ui";


export default defineComponent({
    name: "MerchantIndex",
    components: {MerchantCertKey, MerchantPayRecord, SysNotice},
    setup() {
        const router = useRouter()
        const loginData = store.merchantInfo

        if (!loginData) {
            router.replace('/e-login?login')
        }
        /**
         *
         * @type {Ref<UnwrapRef<Merchant>>}
         */
        const info = ref(loginData)
        const logout = () => {
            store.removeMerchantInfo()
            router.replace('/')
        }
        onMounted(() => {
            merchant.query(loginData.id).then(ret => {
                info.value = ret;
            })
        });
        const dialog = useDialog(), message = useMessage()
        const showModal = ref(false), replaceIconUrl = ref('')

        const onReplaceHandle = () => {
            if (!replaceIconUrl.value) {
                message.error('请填写正确的图标网址')
                return
            }
            merchant.updateIcon(loginData.id, replaceIconUrl.value).then(ret => {
                info.value = ret;
                showModal.value = false
            }).catch(e => message.error(e.message))
        }, showReplace = () => {
            replaceIconUrl.value = ''
            showModal.value = true
        }
        return {
            logout, info, onReplaceHandle, showModal, replaceIconUrl, showReplace,pay_host:window.location.hostname
        }
    }
})
</script>

<style scoped lang="less">
@nav_bg_color: rgba(20, 160, 240);
@nav_icon_size: 50px;

.nav {
    //background-color: @nav_bg_color;
    background-color: rgb(215 215 215 / 0%);
    border-bottom: solid 1px #3333330f;
}

.content {
    margin: 30px auto;
}

.logo {
    padding: 10px 0;
    color: #fff;
}

.site-text {
    margin-left: 10px;
    margin-top: 10px;
    float: left;
    font-size: 20px;
    font-weight: bold;
    color: #666;
}

.logout-text {
    margin-top: 24px;
    font-size: 16px;
}

#app-logo {
    display: block;
    height: @nav_icon_size;
}

.card-content {
    height: 140px;
}

.block-item {
    display: flex;
    text-align: left;
    line-height: 32px;

    .title {
        width: 80px;
        display: flex;
        align-items: center;
        align-content: center;
    }

    &:hover {
        .title {
            color: #000;
        }
    }

    .value {
        flex: 1;

        img {
            width: 36px;
            height: 36px;
            border-radius: 4px;
        }
    }
}
</style>
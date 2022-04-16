<template>
    <div v-if="message" class="message">{{ message }}</div>
    <div v-else class="key-wrap">
        <template v-if="priKey">
            <div class="key-action-c">
                <span v-show="!loading" class="copy-text link-text" @click="onShowModal" style="margin-right: 10px;">查看</span>
                <n-popconfirm @positive-click="createKey" positive-text="重新生成">
                    <template #trigger>
                        <n-button text :loading="loading" class="link-text">{{loading?'生成中...':'重新生成'}}</n-button>
                    </template>
                    <span>重新生成后,原证书将不可以再访问接口,请谨慎操作!</span>
                </n-popconfirm>
            </div>
        </template>
        <div v-else class="gen-key-c">
            <n-button text type="primary" :loading="loading" @click="createKey" size="small">{{loading?'生成中...':'创建证书'}}</n-button>
        </div>
    </div>
    <n-modal style="width: 800px;" v-model:show="showModal" title="查看密钥" preset="card">
        <div class="key-content-wrap">
            <div class="title row">
                <span class="float-left">应用公钥：</span>
                <span class="float-right">
                        <n-button type="info" text @click="copyKey(pubKey,'复制应用公钥成功')">复制公钥</n-button>
                    </span>
            </div>
            <div class="content-value-wrap">
                <div class="content-value">{{pubKey}}</div>
            </div>
        </div>
        <div class="key-content-wrap">
            <div class="title row">
                <span class="float-left">应用私钥：</span>
                <span class="float-right">
                        <n-button type="info" text @click="copyKey(priKey,'复制应用私钥成功')">复制私钥</n-button>
                    </span>
            </div>
            <div class="content-value-wrap">
                <div class="content-value">{{priKey}}</div>
            </div>
        </div>
    </n-modal>
</template>

<script>
import * as copy from 'copy-to-clipboard';
import {ref} from "vue";
import store from "../util/store";
import merchant from "../util/api/merchant";
import {useMessage} from "naive-ui";

export default {
    name: "MerchantCertKey",
    setup() {
        const copied = ref(false), message = ref(''),
            priKey = ref(''),pubKey = ref(''),
            loading = ref(false), showModal = ref(false),
            msg = useMessage()

        const copyKey = (text, notice) => {
            copied.value = copy(text);
            msg.info(notice)
        }

        const loadKey = () => {
            merchant.query(info.id).then(ret => {
                priKey.value = ret.secret
                pubKey.value = ret.pubKey
            }).catch(e => message.value = e.message)
        }
        const info = store.merchantInfo;
        if (!info) {
            message.value = '读取商户信息失败'
        } else {
            loadKey()
        }
        const createKey = () => {
            loading.value = true
            merchant.createKey(info.id).then(()=>{
                msg.success('生成证书成功')
                loadKey()
            }).catch(e => {
                msg.info('生成安全证书失败')
            }).finally(() => loading.value = false)
        }
        return {
            copied, copyKey, message, priKey, loading, createKey,
            showModal,pubKey,
            onShowModal() {
                showModal.value = true
            },
            generateNew() {
                createKey()
            }


        }
    }
}
</script>

<style scoped lang="less">
.key-wrap {
    position: relative;
}

.key-content {
    word-break: break-all;
    overflow: hidden;
    max-height: 120px;
    text-overflow: ellipsis;
    font-size: 12px;
    line-height: 14px;
}

.key-wrap-c {

    position: absolute;
    left: 0;
    right: 0;
    bottom: 0;
    background: linear-gradient(0deg, white, transparent);
}

.key-action-c {
    background: rgba(255, 255, 255, 0.9);
    //text-align: center;
    //opacity: 0;
    //transition: opacity 0.3s;
}

.key-wrap:hover {
    .key-action-c {
        opacity: 1;
    }
}
.key-content-wrap{
    .title{}
    &+.key-content-wrap{
        margin-top: 20px;
    }
}

.content-value-wrap{
    background-color: #dee;
    border-radius: 5px;
    margin: 5px 0;
    padding: 20px;
}
.content-value{
    word-break: break-all;
}
</style>
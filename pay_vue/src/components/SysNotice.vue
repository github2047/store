<template>
    <div class="message-wrap">
        <div v-if="loading">
            <div v-if="error">{{ error }}</div>
            <n-spin v-else size="small"/>
        </div>
        <template v-else>
            <div v-for="m in list" :key="m.id" class="message-item">
                <span class="link-text" @click="showNotice(m)">{{ m.title }}</span>
            </div>
        </template>
        <n-modal v-model:show="showModal" positive-text="知道了">
            <n-card style="width: 800px;" header-style="text-align:center" :title="messageContent.title" :bordered="false" size="huge">
                <div class="message-content" v-html="messageContent.content"></div>
            </n-card>
        </n-modal>
    </div>
</template>

<script>
import {defineComponent, ref,onMounted,defineProps} from 'vue'
import notice from '../util/api/notice'

export default defineComponent({
    name: "SysNotice",
    props:{
        queryCount:{
            type:Number,
            default:5
        }
    },
    setup(props) {
        const list = ref([])
        const loading = ref(true)
        const error = ref('')
        const showModal = ref(false), messageContent = ref({
            title: '',
            content: ''
        })

        notice.query((props.queryCount??5)).then(ret => {
            list.value = ret;
            loading.value = false
        }).catch(e => error.value = '加载通知失败')
        const showNotice = (notice) => {
            messageContent.value = notice
            showModal.value = true

        }
        return {
            loading, error, showModal,showNotice,
            list, messageContent
        }
    }
})
</script>

<style scoped lang="less">
.message-content{

}
</style>
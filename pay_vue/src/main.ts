import {createApp} from 'vue'
import router from './util/routes'
import App from './App.vue'
import {
    create,
    NButton, NIcon, NConfigProvider, NMessageProvider, NModal,
    NForm, NDialog, NCard, NDataTable, NInput, NFormItem, NFormItemRow,
    NSpin, NPagination, NPopconfirm, NGrid, NGridItem, NTabs, NTabPane,
    NAlert, NInputNumber, NResult, NDialogProvider, NCode
} from 'naive-ui'
import './assets/style.less'

const app = createApp(App)
const naive = create({
    components:[
        NButton,NIcon,NConfigProvider,NMessageProvider,NModal,
        NForm,NDialog,NCard,NDataTable,NInput,NFormItem,NFormItemRow,
        NSpin,NPagination,NPopconfirm,NGrid,NGridItem,NTabs,NTabPane,
        NAlert,NInputNumber,NResult,NDialogProvider,NCode
    ]
})
app.use(naive)
app.use(router)
app.mount('#app')

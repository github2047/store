<template>
    <n-data-table
        :bordered="false"
        :columns="columns"
        :data="dataModel.records"
        :pagination="false">
        <template #empty>没有找到支付记录</template>
    </n-data-table>
    <div class="page-wrap row" v-if="dataModel.pages > 1">
        <n-pagination class="float-right" @update-page="queryByPage" v-model:page="dataModel.current" :page-count="dataModel.pages" />
    </div>
</template>

<script lang="ts">
import {IPage, PayRecord} from '../util/api/types'
import {defineComponent, onMounted, ref} from 'vue'
import pay from "../util/api/pay";
import moment from "moment";
import store from "../util/store";

export default defineComponent({
    name: "PayRecord",
    props: {
        pageSize: {
            default: 15
        },
        showPage: {
            default: false
        }
    },
    setup() {
        const dataModel: any = ref<IPage<PayRecord>>(store.defaultIPage()), loading = ref(false)

        const loadData = () => {
            const {current, size} = dataModel.value
            pay.queryRecord(1, current, size).then((ret) => {
                if (!ret) {
                    return;
                }
                dataModel.value.records = ret.records
            }).catch(e => console.log(e))
        }
        onMounted(loadData)
        const dateRender = (row:PayRecord)=>{
            if(!row.payTime) return ''
            return moment(row.payTime).format('YYYY年MM月DD日');
        }

        return {
            queryByPage(page:number){
                dataModel.value.current = page;
                loadData()
            },
            data: [

            ],
            columns: [
                {
                    title: '交易号',
                    width:200,
                    key: 'no'
                },
                {
                    title: '交易名称',
                    key: 'title'
                },
                {
                    title: '对方',
                    key: 'uid',
                    render: (row: PayRecord) => {
                        return row.uid > 0 ? row.uid : '-';
                    }
                },
                {
                    title: '金额',
                    key: 'totalFee',
                    width:100,
                    render: (row: PayRecord) => {
                        return (row.totalFee / 100.0).toFixed(2) + '元'
                    }
                },
                {
                    title: '支付时间',
                    key: 'payTime',
                    width:200,
                    render: (row: PayRecord) => dateRender(row)
                },
                {
                    title: '状态',
                    key: 'status',
                    width:100,
                    render: (row: PayRecord) => {
                        if (row.status == 1) {
                            return '未付款';
                        } else if (row.status == 2) {
                            return '待确认';
                        } else if (row.status == 3) {
                            return '已支付';
                        } else if (row.status == 4) {
                            return '已退款';
                        } else if (row.status == 0) {
                          return '已取消';
                        }

                    }
                }
            ],
            dataModel
        }
    }
})
</script>

<style scoped lang="less">

</style>
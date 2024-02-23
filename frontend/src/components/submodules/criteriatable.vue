<template>
    <div class="criteria-header">
        <div>
            <n-space>
                <div>Only met criteria<n-switch v-model:value="activeMet" @update:value="()=> showDataList()" style="padding-left: 8px" :round="false" /></div>
                <div>Article's evidences<n-switch v-model:value="activeEvidence" @update:value="()=> showDataList()" style="padding-left: 8px;" :round="false" /></div>
            </n-space>
        </div>
        <div class="status">
            <n-space>
                <div>Your work is <button class="status-btn status-btn1" :style="status ? 'background-color: #12990F; color: #ffffff;' : 'background-color: #cccccc;'">{{ status ? 'Completed' : 'Uncompleted'}}</button></div>
                <div>Submit your work <n-button type="info" class="status-btn" size="tiny" @click="submitStatus">Submit</n-button></div>
            </n-space>
        </div>
    </div>
    <div style="width: 100%; margin-top: 12px;">
        <n-data-table :columns="columns" :data="data" :bordered="false" :row-key="rowKeys"/>
    </div>
</template>

<script setup lang="ts">
    import { h, ref, reactive, onMounted } from 'vue';
    import { TypeTable } from "@/types/summary";
    import { EditRegular } from "@vicons/fa";
    import { Dialog } from '@/utils/utils';
    import { DataTableColumns, NIcon, NSpace } from 'naive-ui';
    import { getClassficationTable, submitCritrtiaStatus } from "@/api/request";

    import { useRouter, useRoute } from "vue-router";

    import CriteriaCard from "./criteriaCard.vue";
    import CriteriaDialogTitle from './criteriaDialogTitle.vue'
    import CreateriaDialog from "./criteriadialog.vue";

    const route = useRoute();
    const router = useRouter();
    const data = ref<TypeTable[]>([]);
    const dataAll = ref<TypeTable[]>([]);
    const status = ref<boolean>(false);
    const rowKeys = (row: TypeTable) => row.index;
    const activeMet = ref(true);
    const activeEvidence = ref(false);
    const categoryMerge = ref({});
    const criteriaMerge = ref({});
    const autoRelatedCriteria = ref<Array<any>>([]);  //自动化关联证据项数据
    const mergeRow = (data: any, type: any) => {
        return data.value.reduce((pre: any, cur: any) => {
            if (pre[cur[type]]) {
                pre[cur[type]] += 1;
            } else {
                pre[cur[type]] = 1
            }
            return pre
        }, {})
    };
    const columns: DataTableColumns<TypeTable> = reactive<DataTableColumns<TypeTable>>([
        {
            key: 'category',
            title: (() => {
                return h(
                    NSpace,
                    { style: { fontSize: '16px', fontWeight: 'bold' } },
                    { default: () => "Category" }
                )
            }),
            width: 170,
            minWidth: 170,
            maxWidth: 200,
            rowSpan: (_rowData, _rowIndex) => {
                for (let i in categoryMerge.value) {
                    if (_rowData.category === i) {
                        return categoryMerge.value[i]
                    }
                }
            },
        },
        {
            title: (() => {
                return h(
                    NSpace,
                    { style: { fontSize: '16px', fontWeight: 'bold' } },
                    { default: () => "Criteria" }
                )
            }),
            key: 'criteria',
            filter: 'default',
            width: 130,
            minWidth: 130,
            maxWidth: 200,
            className: 'criteria-style',
            filterOptionValue: null,
            rowSpan: (rowData) => {
                for (let i in criteriaMerge.value) {
                    if (rowData.rule === i) {
                        return criteriaMerge.value[i]
                    }
                }
            },
            render(_row) {
                const tags =  h(
                        CriteriaCard,
                        {
                            key: Math.random(),
                            info: { key: _row.rule, value: _row.strength, active: activeEvidence.value },
                            strength: _row.strength
                        },
                        {
                            default: () => activeEvidence.value
                        }
                    )
                return tags
            },
        },
        {
            title: (() => {
                return h(
                    NSpace,
                    { style: { fontSize: '16px', fontWeight: 'bold' } },
                    { default: () => "Evidence summary" }
                )
            }),
            key: 'evidence',
            resizable: false,
            ellipsis: {
                tooltip: true
            },
            render(_row) {
                // return _row.summary 
                return h('n-ellipsis', { innerHTML: _row.summary })
            },
        },
        {
            title: (() => {
                return h(
                    NSpace,
                    { style: { fontSize: '16px', fontWeight: 'bold' } },
                    { default: () => "Manager" }
                )
            }),
            key: 'comment',
            // resizable: true,
            width: 100,
            render(row) {
                return h('div', null,
                    [
                        h(NIcon,
                            {
                                size: 22,
                                class: ['comment-edit'],
                                style: { cursor: 'pointer', marginLeft: '24px', marginTop: '6px' },
                                onClick: () => {
                                    row.source
                                }
                            },
                            {
                                default: () => h(EditRegular, {
                                    onClick: () => {
                                        Dialog.info({
                                            showIcon: false,
                                            title: () => h(CriteriaDialogTitle, { rule: row.rule, strength: row.strength }),
                                            // title: () => h(NText,{ style:{ 'text-align':'center', 'width':'100%' }},{default: ()=> row.rule+" "+row.strength }),
                                            style: { 'width': '60%', 'min-width': '850px', 'max-width': '950px', 'height': 'auto', 'max-height': '98%', color:'#d2dae2', 'border-radius': '10px' },
                                            content: () => h(CreateriaDialog, {
                                                info: { 'rowData': row, 'variantId': route.params.variantId, 'relateCriteria': autoRelatedCriteria.value },
                                                router: router
                                            })
                                        })
                                    }
                                }, {})
                            }
                        )]
                )
            }
        }
    ]);
    const getCriteriaData = () => {
        let input = new FormData();
        input.append("vd", route.params.variantId.toString());
        input.append("u", '22');
        getClassficationTable(input).then(res => {
            const { data } = res.data;
            console.log(data)
            status.value = data[0].isManual === '2' ? true : false;
            dataAll.value = data;
            showDataList();
            data.forEach((v: any)=> {
                if(v.rule === 'PM2' || v.rule === 'BS1' || v.rule === 'BA1' || v.rule === 'BS2' || v.rule === 'PP3' || v.rule === 'BP4') {
                    autoRelatedCriteria.value.push(v)
                }
            })
        })
    };
    const showDataList = () => {
        data.value = [];
        if(activeEvidence.value && activeMet.value) {
            for (const obj of dataAll.value) {
                if (obj.strength !== "Unmet" && obj.evidences) {
                    data.value.push(obj);
                }
            }
        } else if(activeEvidence.value && !activeMet.value) {
            for (const obj of dataAll.value) {
                if (obj.evidences) {
                    data.value.push(obj);
                }
            }
        } else if(!activeEvidence.value && activeMet.value) {
            for (const obj of dataAll.value) {
                if (obj.strength !== "Unmet") {
                    data.value.push(obj);
                }
            }
        } else {
            data.value = dataAll.value;
        }
        categoryMerge.value = mergeRow(data, 'category');
        criteriaMerge.value = mergeRow(data, 'rule');
    }
    const submitStatus = () => {
        submitCritrtiaStatus(JSON.parse(JSON.stringify({variantId: router.currentRoute.value.params.variantId}))).then(res=> {
            if(res.data.code === 1000) {
                status.value = true;
            }
        })
    }
    onMounted(() => {
        getCriteriaData();
    });
</script>

<style lang="scss">
    .criteria-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        .status {
            text-align: right;
            .status-btn {
                padding: 0 20px;
                margin-left: 6px;
            }
            .status-btn1 {
                height: 22px;
                background-color: #cccccc;
                border-radius: 3px;
                border: none;
                color: #383838;
            }
        }
    }
    .criteria-style {
        text-align: left;
    }
</style>

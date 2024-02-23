<template>
    <div class="pmid-table">
        <table cellpadding="0" cellspacing="0"  class="table-style">
            <thead>
                <th v-for="item in columns" :key="item.key">{{ item.title }}</th>
            </thead>
            <tbody>
                <tr v-for="item in tableData" :key="item.variantId">
                    <td>{{ item.variantId }}</td>
                    <td>{{ item.relevance }}</td>
                    <td>{{ item.evidence }}</td>
                    <td>{{ item.submitter }}</td>
                    <td>{{ item.comment }}</td>
                </tr>
            </tbody>
        </table>
        <div class="table-page">
            <n-pagination v-model:page="page" :page-count="Math.ceil(props.data.length / 10)" />
        </div>
        
    </div>
</template>

<script setup lang='ts'>
    import { ref, computed } from 'vue';
    // import type { DataTableColumns } from 'naive-ui';
    const props = defineProps(['data']);

    const columns = [{
        title: 'VariantId',
        key: 'mutation',
    },{
        title: 'Relevance',
        key: 'relevance',
    },{
        title: 'Evidence',
        key: 'evidence',
    },{
        title: 'Submitter',
        key: 'submitter',
    },{
        title: 'Comment',
        key: 'comment',
    }]
    const page = ref<number>(1);

    const tableData = computed(()=> {
        return props.data.slice((page.value - 1)*10 + 1, page.value * 10 + 1);
    })
    // const data = [
    //     {
    //         key: 0,
    //         mutation: '7-107315505-T-A',
    //         relevance: '4',
    //         evidence: '',
    //         submitter: 'zuo',
    //         comment: ''
    //     },
    //     {
    //         key: 1,
    //         mutation: '7-107315505-T-A',
    //         relevance: '2',
    //         evidence: '',
    //         submitter: '',
    //         comment: ''
    //     },
    //     {
    //         key: 2,
    //         mutation: '7-107315505-T-A',
    //         relevance: '1',
    //         evidence: '',
    //         submitter: '',
    //         comment: ''
    //     },
    //     {
    //         key: 3,
    //         mutation: '7-107315505-T-A',
    //         relevance: '1',
    //         evidence: '',
    //         submitter: '',
    //         comment: ''
    //     },
    //     {
    //         key: 4,
    //         mutation: '7-107315505-T-A',
    //         relevance: '1',
    //         evidence: '',
    //         submitter: 'hai',
    //         comment: ''
    //     },
    //     {
    //         key: 5,
    //         mutation: '7-107315505-T-A',
    //         relevance: '1',
    //         evidence: '',
    //         submitter: '',
    //         comment: ''
    //     },
    //     {
    //         key: 6,
    //         mutation: '7-107315505-T-A',
    //         relevance: '1',
    //         evidence: '',
    //         submitter: '',
    //         comment: ''
    //     },
    //     {
    //         key: 7,
    //         mutation: '7-107315505-T-A',
    //         relevance: '1',
    //         evidence: '',
    //         submitter: '',
    //         comment: ''
    //     },
    //     {
    //         key: 8,
    //         mutation: '7-107315505-T-A',
    //         relevance: '1',
    //         evidence: '',
    //         submitter: 'liang',
    //         comment: ''
    //     },
    //     {
    //         key: 9,
    //         mutation: '7-107315505-T-A',
    //         relevance: '1',
    //         evidence: '',
    //         submitter: '',
    //         comment: ''
    //     }
    // ]
</script>

<style lang='scss' scoped>
    .pmid-table {
        margin-top: 16px;
        .table-style {
            width: 100%;
            border: none;
            border-top: 1px solid #C7C7C7;
            margin-bottom: 10px;
            th {
                border-bottom: 1px solid #C7C7C7;
                padding: 2px;
                color: #808080;
            }
            td {
                width: 20%;
                border-bottom: 1px solid #C7C7C7;
                padding: 2px;
            }
            td:first-child {
                color: #1E5FA8;
            }
        }
        .table-page {
            display: flex;
            flex-direction: row-reverse;
        }
    }
</style>
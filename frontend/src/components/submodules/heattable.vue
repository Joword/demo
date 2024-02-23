<template>
    <n-data-table :columns="useHeatData.heatColumns" :data="data" :row-class-name="rowClassName"/>
</template>

<script setup lang="ts">
    
    import { useData } from "@/store";
    import { HeatRows } from "@/types/info";

    const useHeatData = useData();
    const data: HeatRows[] = [
        {
            key: 0,
            type: 'Synonymous',
            pathogenic: '0',
            likelypathogenic: '5',
            vus: '27',
            likelybenign: '251',
            benign: '9',
            total: '292'
        },{
            key: 1,
            type: 'Missense',
            pathogenic: '208',
            likelypathogenic: '103',
            vus: '366',
            likelybenign: '25',
            benign: '17',
            total: '719'
        },{
            key: 2,
            type: 'Nonsense',
            pathogenic: '69',
            likelypathogenic: '13',
            vus: '3',
            likelybenign: '0',
            benign: '0',
            total: '85'
        },{
            key: 3,
            type: 'Start loss',
            pathogenic: '5',
            likelypathogenic: '0',
            vus: '0',
            likelybenign: '0',
            benign: '0',
            total: '5'
        },{
            key: 4,
            type: 'Stop loss',
            pathogenic: '1',
            likelypathogenic: '0',
            vus: '0',
            likelybenign: '0',
            benign: '0',
            total: '1'
        },{
            key: 5,
            type: 'Frameshift',
            pathogenic: '107',
            likelypathogenic: '33',
            vus: '4',
            likelybenign: '0',
            benign: '0',
            total: '144'
        },{
            key: 6,
            type: 'Inframe indel',
            pathogenic: '6',
            likelypathogenic: '1',
            vus: '9',
            likelybenign: '0',
            benign: '0',
            total: '16'
        },{
            key: 7,
            type: 'Splice junction loss',
            pathogenic: '79',
            likelypathogenic: '29',
            vus: '10',
            likelybenign: '0',
            benign: '0',
            total: '118'
        },{
            key: 8,
            type: 'Non-coding',
            pathogenic: '12',
            likelypathogenic: '5',
            vus: '36',
            likelybenign: '67',
            benign: '7',
            total: '127'
        },{
            key: 9,
            type: 'Total',
            pathogenic: '487',
            likelypathogenic: '189',
            vus: '455',
            likelybenign: '343',
            benign: '33',
            total: '1507'
        }
        
    ];
    const rowClassName = (_row: HeatRows) => {
        switch (_row.type) {
            case 'Stop loss':
                return "stop-level";
            case 'Start loss':
                return "start-level";
            case 'Synonymous':
                return "synonymous-level";
            case 'Missense':
                return "missense-level";
            case 'Nonsense':
                return 'nonsense-level';
            case 'Frameshift':
                return 'frameshift-level';
            case 'Inframe indel':
                return "inframe-level";
            case 'Splice junction loss':
                return "splice-level";
            case 'Non-coding':
                return "noncoding-level";
            case 'Total':
                return "total-level";
            default:
                return "";
        }
    }
</script>

<style scoped>
    .n-data-table .n-data-table-td {
        padding: 0;
    }
    /* total start */
    :deep(.total-level .pathogenic) {
        background-color: rgba(255, 87, 51, 0.45) !important;
        font-family: HanSansCN-Bold;
    }
    :deep(.total-level .lp) {
        background-color: rgba(255, 87, 51, 0.16) !important;
        font-family: HanSansCN-Bold;
    }
    :deep(.total-level .vus) {
        background-color: rgba(242, 154, 94, 0.41) !important;
        font-family: HanSansCN-Bold;
    }
    :deep(.total-level .lb) {
        background-color: rgba(9, 184, 15, 0.13) !important;
        font-family: HanSansCN-Bold;
    }
    :deep(.total-level .benign) {
        background-color: rgba(9, 184, 15, 0.04) !important;
        font-family: HanSansCN-Bold;
    }
    /* total end */

    :deep(.stop-level .pathogenic) {
        background-color: rgba(212, 48, 48, 1) !important;
    }
    :deep(.start-level .pathogenic) {
        background-color: rgba(212, 48, 48, 1) !important;
    }
    :deep(.synonymous-level .lb) {
        background-color: rgba(9, 184, 15, 1) !important;
    }
    :deep(.missense-level .pathogenic) {
        background-color: rgba(255, 87, 51, 0.66) !important;
    }
    :deep(.missense-level .lp) {
        background-color: rgba(255, 87, 51, 0.16) !important;
    }
    :deep(.missense-level .vus) {
        background-color: rgba(255, 111, 0, 0.36) !important;
    }
    :deep(.nonsense-level .pathogenic) {
        background-color: rgba(237, 104, 104, 1) !important;
    }
    :deep(.nonsense-level .lp) {
        background-color: rgba(255, 87, 51, 0.16) !important;
    }
    :deep(.nonsense-level .vus) {
        background-color: rgba(242, 154, 94, 0.07) !important;
    }
    :deep(.frameshift-level .pathogenic) {
        background-color: rgba(237, 104, 104, 1) !important;
    }
    :deep(.frameshift-level .lp) {
        background-color: rgba(255, 87, 51, 0.27) !important;
    }
    :deep(.inframe-level .pathogenic) {
        background-color: rgba(255, 87, 51, 0.66) !important;
    }
    :deep(.inframe-level .lp) {
        background-color: rgba(255, 87, 51, 0.03) !important;
    }
    :deep(.inframe-level .vus) {
        background-color: rgba(255, 111, 0, 0.36) !important;
    }
    :deep(.splice-level .pathogenic) {
        background-color: rgba(255, 87, 51, 0.82) !important;
    }
    :deep(.splice-level .lp) {
        background-color: rgba(255, 87, 51, 0.27) !important;
    }
    :deep(.splice-level .vus) {
        background-color: rgba(242, 154, 94, 0.02) !important;
    }
    :deep(.noncoding-level .pathogenic) {
        background-color: rgba(255, 87, 51, 0.16) !important;
    }
    :deep(.noncoding-level .lp) {
        background-color: rgba(255, 87, 51, 0.03) !important;
    }
    :deep(.noncoding-level .vus) {
        background-color: rgba(242, 154, 94, 0.17) !important;
    }
    :deep(.noncoding-level .lb) {
        background-color: rgba(9, 184, 15, 0.34) !important;
    }
    :deep(.noncoding-level .benign) {
        background-color: rgba(9, 184, 15, 0.04) !important;
    }
    
</style>
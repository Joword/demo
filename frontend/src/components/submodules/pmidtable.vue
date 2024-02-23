<template>
    <n-data-table :bordered="false" :single-line="false" single-column :columns="columns" :data="data"/>
</template>

<script setup lang="ts">

    import { h, reactive } from "vue";
    import { PmidRows } from "@/types/info";
    import { NText, NSwitch } from "naive-ui";
    import { geneStyle, variantStyle, diseaseStyle } from "@/utils/utils";

    import type { DataTableColumns } from 'naive-ui';

    const columns:DataTableColumns<PmidRows> = reactive<DataTableColumns<PmidRows>>([
            {
                title: (():any => {
                        return h(
                            NText,
                            { style: { fontFamily: 'HanSansCN-Bold', fontSize: '18px' } },
                            { default: () => {
                                return h(NSwitch,
                                { round: false, style:{ paddingBottom:'5px',paddingLeft:'2px'}, railStyle:geneStyle },
                                { checked: () => {
                                    return h(NText,{ style:{color:'#7948EA'} },{default:() => "Gene"})
                                }, unchecked:() => "Gene" })
                            }}
                        )
                    }),
                key: 'gene',
                renderFilterIcon: () => {
                    return h(NSwitch,{  },{  });
                }
            },
            {
                title: (():any => {
                        return h(
                            NText,
                            { style: { fontFamily: 'HanSansCN-Bold', fontSize: '18px' }},
                            { default: () => {
                                return h(NSwitch, 
                                {round: false, style:{ paddingBottom:'5px',paddingLeft:'2px', }, railStyle: variantStyle}, 
                                { checked: () => {
                                    return h(NText,{ style:{color:'#00BAAD'} },{default:() => "Variant"})
                                }, unchecked:() => "Variant"})
                            }}
                        )
                    }),
                key: 'variants'
                // children: [
                //     {
                //         title: 'cHGVS',
                //         key: 'chgvs'
                //     },
                //     {
                //         title: 'pHGVS',
                //         key: 'phgvs'
                //     },
                //     {
                //         title: 'pHGVS abbreviation',
                //         key: 'phgvs1'
                //     }
                // ]
            },
            {
                title:(():any => {
                        return h(
                            NText,
                            { style: {fontFamily: 'HanSansCN-Bold', fontSize: '18px'}, name:'diseases' },
                            { default: () => {
                                return h(NSwitch, 
                                {round: false, style:{ paddingBottom:'5px',paddingLeft:'2px' }, railStyle: diseaseStyle}, 
                                { checked: () => {
                                    return h(NText,{ style:{color:'#FF8D1A'} },{default:() => "Disease"})
                                }, unchecked:() => "Disease"})
                            }}
                        )
                    }),
                key: 'phenotype'
            }
        ]);

    const data: PmidRows[] =  [
        {
            key: 0,
            gene: 'Pendrin',
            variants: '["V239D","123123","abccc"]',
            phenotype: 'Hearing loss',
            chgvs: '-',
            phgvs: '-',
            phgvs1: 'V239D'
        },
        {
            key: 1,
            gene: 'TMPRSS3',
            variants: 'D346V',
            phenotype: 'Deafness',
            chgvs: '-',
            phgvs: '-',
            phgvs1: 'D346V'
        },
        {
            key: 2,
            gene: 'OTOANCORIN',
            variants: '1001G>T',
            phenotype: 'Pendred Syndrome',
            chgvs: '1001G>T',
            phgvs: '-',
            phgvs1: '-'
        },
        {
            key: 3,
            gene: 'GJB2',
            variants: '988DELA',
            phenotype: 'Infection or Trauma',
            chgvs: '988DELA',
            phgvs: '-',
            phgvs1: '-'
        },
    ];
</script>

<style scoped>
    .n-data-table .n-data-table-th .n-data-table-th__title-wrapper .n-data-table-th__title{
        text-align: center;
    }
</style>
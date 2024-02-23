import { h } from "vue";
import { defineStore } from "pinia";
import { SwpTable, Phenotype, HeatRows } from "@/types/info";
import { NSpace, NTag, NText } from "naive-ui";
// import { QuestionCircle16Regular } from "@vicons/fluent";

export const useData = defineStore('data', {
    persist: {
        key: 'useDataKey',
        storage: sessionStorage,
        paths: [ 'predictionColumns', 'predictionData', 'phenotypeColumns', 'phenotypeData', 'heatColumns', 'heatTable' ]
    },
    state: () => {
        return {
            showStyle: {},
            regularStyle: { fontSize: '18px' },
            zeroStyle: { fontSize: '18px', color: '#FFFFF' },
            predictionColumns: [ 
                {
                    title:(():any => {
                        return h(
                            NSpace,
                            { style: { fontFamily: 'HanSansCN-Bold', fontSize: '16px' } },
                            { default: () => "Category" }
                        )
                    }),
                    key: 'category',
                    rowSpan: (_rowData: any, rowIndex: number) => {
                        switch (rowIndex) {
                            case 1:
                                return 5;
                            default:
                                return 1;
                        }
                    },
                },
                {
                    title:(():any => {
                        return h(
                            NSpace,
                            { style: { fontFamily: 'HanSansCN-Bold', fontSize: '16px' } },
                            { default: () => "Tools" }
                        )
                    }),
                    key:'tools',
                },
                {
                    title: (():any => {
                        return h(
                            NSpace,
                            { style: { fontFamily: 'HanSansCN-Bold', fontSize: '16px' } },
                            { default: () => "Scores" }
                        )
                    }),
                    key: 'scores',
                    render(row:SwpTable) {
                        return h('div',null,
                            { default: () => row.scores+'('+ row.type +")" })
                    },
                },
                {
                    title: (():any => {
                        return h(
                            NSpace,
                            { style: { fontFamily: 'HanSansCN-Bold', fontSize: '16px' } },
                            { default: () => "Thresthold" }
                        )
                    }),
                    key: 'thresthold'
                }
            ],
            predictionData: [
                {
                    key: 0,
                    category: 'Aggregated',
                    tools: 'Aggregated prediction',
                    scores: '0.98',
                    type: 'Deleterious',
                    thresthold: '',
                    url:''
                },
                {
                    key: 1,
                    category: 'Functional',
                    tools: 'REVEL',
                    scores: '0.94',
                    type: 'Deleterious(Strong)',
                    thresthold: '',
                    url: ''
                },
                {
                    key: 2,
                    category: 'Functional prediction',
                    tools: 'Mut Assesor',
                    scores: '2.36',
                    type: 'Med',
                    thresthold: '',
                    url: ''
                },{
                    key: 3,
                    category: 'Functional prediction',
                    tools: 'SIFT',
                    scores: '0.0001',
                    type: 'Uncertain',
                    thresthold: '',
                    url: ''
                },{
                    key: 4,
                    category: 'Functional prediction',
                    tools: 'Polyphen2',
                    scores: '0.86',
                    type: 'Uncertain',
                    thresthold: '',
                    url: ''
                },{
                    key: 5,
                    category: 'Funtional prediction',
                    tools: 'PrimetaAI',
                    scores: '0.65',
                    type: 'Uncertain',
                    thresthold: '',
                    url: ''
                },{
                    key: 6,
                    category: 'Splicing',
                    tools: 'SpliceAI',
                    scores: '0',
                    type: 'Benign',
                    thresthold: '',
                    url: ''
                },{
                    key: 7,
                    category: 'Conservation',
                    tools: 'GERP++',
                    scores: '5.59',
                    type: 'Deleterious',
                    thresthold: '',
                    url: ''
                },{
                    key: 8,
                    category: 'Pathogenicity',
                    tools: 'MLVP',
                    scores: '0.756',
                    type: 'Damaging',
                    thresthold: '',
                    url: ''
                }
            ],
            phenotypeColumns: [
                {
                    title: (():any => {
                        return h(
                            NSpace,
                            { style: { fontFamily: 'HanSansCN-Bold', fontSize: '16px' } },
                            { default: () => "Phenotype" }
                        )
                    }),
                    key: 'phenotype',
                },
                {
                    title: (():any => {
                        return h(
                            NSpace,
                            { style: { fontFamily: 'HanSansCN-Bold', fontSize: '16px' } },
                            { default: () => "Inheritance" }
                        )
                    }),
                    key: 'inheritance',
                },
                {
                    title: (():any => {
                        return h(
                            NSpace,
                            { style: { fontFamily: 'HanSansCN-Bold', fontSize: '16px' } },
                            { default: () => "Source" }
                        )
                    }),
                    key: 'source',
                    render(row:Phenotype) {
                        const source = row.source.map((target) => {
                            return h(
                                    NTag,
                                    {
                                        style: { marginRight: '10px', cursor: 'pointer', border: '1px solid #2080f0' },
                                        type: 'info',
                                        bordered: false
                                    },
                                    { default: () => target }
                                )
                        })
                        return source;
                    },
                }
            ],
            phenotypeData: [
                {
                    key: 0,
                    phenotype: 'Pendred Syndrome',
                    inheritance: 'AR',
                    source: ['Monarch','Orphanet','ClinGen','GENCC','OMIM'],
                    url: ['','','','',''],
                    msg: 'abc'
                },
                {
                    key: 1,
                    phenotype: 'Hearing Loss, Autosomal Recessive',
                    inheritance: 'AR',
                    source: ['GENCC','OMIM','Monarch'],
                    url: ['','',''],
                    msg: 'bcd'
                },
                {
                    key: 2,
                    phenotype: 'Enlarged Vestibular Aquduct Syndrome',
                    inheritance: 'AR',
                    source: ['OMIM'],
                    url: [''],
                    msg: 'cde'
                },
                {
                    key: 3,
                    phenotype: 'Primary Congenital Hypothyroidism',
                    inheritance: 'AR',
                    source: ['GENCC'],
                    url:[''],
                    msg: 'def'
                },
                {
                    key: 4,
                    phenotype: 'Autosomal Recessive Non-Syndromic Sensorineural Deafness Type DFNB',
                    inheritance: 'AR',
                    source: ['Orphanet'],
                    url: [''],
                    msg: 'efg'
                },
                {
                    key: 5,
                    phenotype: 'Pendred',
                    inheritance: 'AR',
                    source: ['GeneReviews'],
                    url: [''],
                    msg: 'fgi'
                }
            ],
            heatColumns: [
                {
                    title: (():any => {
                        return h(
                            NText,
                            { style: { fontFamily: 'HanSansCN-Bold', fontSize: '18px' } },
                            { default: () => " " }
                        )
                    }),
                    key: 'type',
                    resizable: false,
                    minWidth: 200,
                    maxWidth: 200,
                    render(row: HeatRows) {
                        return h(
                                NText,
                                { style: { fontFamily: 'HanSansCN-Bold', fontSize: '18px' } },
                                { default: () => row.type }
                            )
                    },
                },
                {
                    title: (():any => {
                        return h(
                            NText,
                            { style: { fontFamily: 'HanSansCN-Bold', fontSize: '18px' } },
                            { default: () => "Pathogenic" }
                        )
                    }),
                    key: 'pathogenic',
                    resizable: false,
                    minWidth: 200,
                    maxWidth: 200,
                    align: 'center',
                    titleAlign: 'center',
                    className: 'pathogenic',
                    render(row: HeatRows) {
                        return h(
                            NText,
                            { style: row.pathogenic==='0'?{ fontSize: '18px', color: '#bfbfbf' }:{ fontSize: '18px' } },
                            { default: () => row.pathogenic })
                    },
                },
                {
                    title: (():any => {
                        return h(
                            NText,
                            { style: { fontFamily: 'HanSansCN-Bold', fontSize: '16px' } },
                            { default: () => "Likely pathogenic" }
                        )
                    }),
                    key: 'likelypathogenic',
                    resizable: false,
                    minWidth: 200,
                    maxWidth: 200,
                    align: 'center',
                    titleAlign: 'center',
                    className: 'lp',
                    render(row: HeatRows) {
                        return h(
                            NText,
                            { style: row.likelypathogenic==='0'?{ fontSize: '18px', color: '#bfbfbf' }:{ fontSize: '18px' } },
                            { default: () => row.likelypathogenic })
                    },
                },
                {
                    title: (():any => {
                        return h(
                            NText,
                            { style: { fontFamily: 'HanSansCN-Bold', fontSize: '16px' } },
                            { default: () => "Uncertain significance" }
                        )
                    }),
                    key: 'vus',
                    resizable: false,
                    minWidth: 300,
                    maxWidth: 300,
                    align: 'center',
                    titleAlign: 'center',
                    className: 'vus',
                    render(row: HeatRows) {
                        return h(
                            NText,
                            { style: row.vus==='0'?{ fontSize: '18px', color: '#bfbfbf' }:{ fontSize: '18px' } },
                            { default: () => row.vus })
                    },
                },
                {
                    title: (():any => {
                        return h(
                            NText,
                            { style: { fontFamily: 'HanSansCN-Bold', fontSize: '16px' } },
                            { default: () => "Likely benign" }
                        )
                    }),
                    key: 'likelybenign',
                    resizable: false,
                    minWidth: 200,
                    maxWidth: 200,
                    align: 'center',
                    titleAlign: 'center',
                    className: 'lb',
                    render(row: HeatRows) {
                        return h(
                            NText,
                            { style: row.likelybenign==='0'?{ fontSize: '18px', color: '#bfbfbf' }:{ fontSize: '18px' } },
                            { default: () => row.likelybenign })
                    },
                },
                {
                    title: (():any => {
                        return h(
                            NText,
                            { style: { fontFamily: 'HanSansCN-Bold', fontSize: '16px' } },
                            { default: () => "Benign" }
                        )
                    }),
                    key: 'benign',
                    resizable: false,
                    minWidth: 100,
                    maxWidth: 100,
                    align: 'center',
                    titleAlign: 'center',
                    className: 'benign',
                    render(row: HeatRows) {
                        return h(
                            NText,
                            { style: row.benign==='0'?{ fontSize: '18px', color: '#bfbfbf' }:{ fontSize: '18px' } },
                            { default: () => row.benign })
                    },
                },
                {
                    title: (():any => {
                        return h(
                            NText,
                            { style: { fontFamily: 'HanSansCN-Bold', fontSize: '16px' } },
                            { default: () => "Total" }
                        )
                    }),
                    key: 'total',
                    resizable: false,
                    align: 'center',
                    titleAlign: 'center',
                    className: 'total',
                    render(row: HeatRows) {
                        return h(
                            NText,
                            { style: row.total==='0'?{ fontSize: '18px', color: '#bfbfbf', }:{ fontSize: '18px', fontFamily:'HanSansCN-Bold' } },
                            { default: () => row.total })
                    },
                }
            ],
            heatTable: [
                
            ],
            variantData: [
                
            ]
        }
    }
})
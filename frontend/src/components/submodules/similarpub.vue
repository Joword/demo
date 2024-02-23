<template>
    <n-data-table :bordered="false" :single-line="false" single-column :columns="columns" :data="data"/>
</template>

<script setup lang="ts">
    import { h, reactive } from "vue";
    import { SimilarPubRows } from "@/types/info";
    import { NButton, NText, DataTableColumns } from "naive-ui";

    const columns:DataTableColumns<SimilarPubRows> =  reactive<DataTableColumns<SimilarPubRows>>([
        {
            title: (():any => {
                        return h(
                            NText,
                            { style: { fontFamily: 'HanSansCN-Bold', fontSize: '18px' } },
                            { default: () => "Title" }
                        )
                    }),
            key: 'title',
            width: 500,
            ellipsis: {
                tooltip: true
            }
        },
        {
            title: (():any => {
                        return h(
                            NText,
                            { style: { fontFamily: 'HanSansCN-Bold', fontSize: '18px' } },
                            { default: () => "Author" }
                        )
                    }),
            key: 'author'
        },
        {
            title: (():any => {
                        return h(
                            NText,
                            { style: { fontFamily: 'HanSansCN-Bold', fontSize: '18px' } },
                            { default: () => "Journal" }
                        )
                    }),
            key: 'journal'
        },
        {
            title:(():any => {
                        return h(
                            NText,
                            { style: { fontFamily: 'HanSansCN-Bold', fontSize: '18px' } },
                            { default: () => "Link" }
                        )
                    }),
            key: 'link',
            render(row: SimilarPubRows){
                return h(
                    NButton,
                    {
                        size: 'small',
                        type: 'info',
                        onClick: () => link2Pmid(row),
                        style: { borderRadius: '8px' }
                    },
                    { default: () => "Link" }
                )
            }
        }
    ]);

    const data:SimilarPubRows[] = [
        {
            key: 0,
            title: 'Associations between GJB2, Mitochondrial 12S rRNA, SLC26A4 Mutations, and Hearing Loss among Three Ethnicities',
            author: 'Du W et al.',
            journal: 'Biomed Res Int',
            url: 'https://www.ncbi.nlm.nih.gov/pmc/articles/PMC3996913/'
        },
        {
            key: 0,
            title: 'Analysis of SLC26A4 Gene in Individuals with Non Syndromic Hearing Impairment in Relation with GJB2 Associated Mutations',
            author: 'Rajalakshmi K te al.',
            journal: 'Avicenna J Med Biotechnol',
            url: 'https://www.ncbi.nlm.nih.gov/pmc/articles/PMC10073921/'
        }
    ];

    const link2Pmid = (arg: SimilarPubRows) => {
        window.open(arg.url);
    }
</script>

<style scoped>
    #similar-title:hover {
        color: white
    }
    #similar-title:focus {
        color: white
    }
</style>
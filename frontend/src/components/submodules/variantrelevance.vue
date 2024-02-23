<template>
        <n-data-table :bordered="false" :columns="columns" :data="data" :pagination="pagination"/>
</template>

<script setup lang="ts">
    import { h, ref, reactive } from "vue";
    import { useRouter } from "vue-router";
    import { VariantInfo } from "@/types/info";
    import { NTag, NText, DataTableColumns } from "naive-ui";

    const router = useRouter();
    const pagination = ref({pageSize: 5});

    const showCriteria = (row:VariantInfo) => {
        if (row.status==="unread") {
            row.status = "read";
        }else{ row.status = "unread"; }
    }

    const goVariant = (row:VariantInfo) => {
        router.push({name: 'variant', params: { v: row.variant }});
    }

    const columns:DataTableColumns<VariantInfo> = reactive<DataTableColumns<VariantInfo>>([
        {
            title: (():any => {
                return h(NText,
                    { style: { fontFamily: 'HanSansCN-Bold', fontSize: '18px' } },
                    { default: () => "Variant" }
                )
            }),
            key: "variant",
            render: (row:VariantInfo):any => {
                return h(NTag,
                    { 
                        type:"info", 
                        onClick: () => { goVariant(row) },
                        style: { cursor: "pointer", borderRadius: '8px' },
                     },
                    { default: () => row.variant }
                )
            }
        },
        {
            title: (():any => {
                return h(NText,
                    { style: { fontFamily: 'HanSansCN-Bold', fontSize: '18px' } },
                    { default: () => "Relevance" }
                )
            }),
            key: "relevance",
        },
        {
            title: (():any => {
                return h(NText,
                    { style: { fontFamily: 'HanSansCN-Bold', fontSize: '18px' } },
                    { default: () => "Criteria" }
                )
            }),
            key: "criteria",
            render: (row:VariantInfo):any => {
                return h(NText,
                    null,
                    { 
                        default: () => {
                            if (row.status==="read") {
                                return row.criteria;
                            }else{
                                return h(NTag,
                                        { 
                                            type: "info", 
                                            onClick: () => showCriteria(row),
                                            style: { cursor: "pointer", borderRadius: '8px'
                                        }},
                                        { default: () => row.status }
                                    );
                            }
                        }
                    }
                )
            }
        },
        {
            title: (():any => {
                return h(NText,
                    { style: { fontFamily: 'HanSansCN-Bold', fontSize: '18px' } },
                    { default: () => "Submitter" }
                )
            }),
            key: "submitter",
        },
        {
            title: (():any => {
                return h(NText,
                    { style: { fontFamily: 'HanSansCN-Bold', fontSize: '18px' } },
                    { default: () => "Comment" }
                )
            }),
            key: "comment",
        },
    ]);

    const data:VariantInfo[] = reactive<VariantInfo[]>([
        {
            key: 0,
            variant: "7-107315505-T-A",
            relevance: '4',
            criteria: "PP4,PM3,PS3,PP1",
            submitter: "Wang weifeng",
            comment: "Comment 1",
            status: "read"
        },
        {
            key: 1,
            variant: "7-107323982-G-T",
            relevance: '4',
            criteria: "PP1,PM3,PS3",
            submitter: "XXX",
            comment: "Comment 1",
            status: "read"
        },
        {
            key: 2,
            variant: "7-10730665-A-C",
            relevance: '2',
            criteria: "-",
            submitter: "XXXX",
            comment: "Comment 1",
            status: "unread"
        },
        {
            key: 3,
            variant: "21-43809151-TG-T",
            relevance: '1',
            criteria: "-",
            submitter: "XXX",
            comment: "Comment 1",
            status: "unread"
        },
        {
            key: 4,
            variant: "16-21716533-G-C",
            relevance: '1',
            criteria: "-",
            submitter: "XXX",
            comment: "Comment 1",
            status: "unread"
        },
        {
            key: 5,
            variant: "16-21721408-A-T",
            relevance: '1',
            criteria: "-",
            submitter: "XXX",
            comment: "Comment 1",
            status: "unread"
        },
    ]);

</script>

<style scoped>
    .n-tag:hover {
        background-color: #235BA3;
        color: white;
    }
    .n-tag:focus {
        background-color: #235BA3;
        color: white;
    }
</style>
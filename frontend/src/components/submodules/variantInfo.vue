<template>
  <n-data-table :bordered="false" :columns="columns" :data="props.info" />
</template>

<script setup lang="ts">
    import { h, reactive } from "vue";
    import {useRouter} from "vue-router";
    import { VariantRows } from "@/types/info";
    import {NText, DataTableColumns, NTag} from "naive-ui";

    const router = useRouter();
    const props = defineProps(['info']);
    const toLink = (arg: VariantRows) => {
      router.push({name: 'variant', params: {variantId: arg.variant}})
    }

    const columns:DataTableColumns<VariantRows> =  reactive<DataTableColumns<VariantRows>>([
        {
            title: (():any => {
                        return h(
                            NText,
                            { style: { fontFamily: 'HanSansCN-Bold', fontSize: '18px' } },
                            {default: () => "Variant"}
                        )
                    }),
            key: 'variant',
            ellipsis: {
                tooltip: true
            },
          render(_row) {
            return h(NTag,
                {
                  style: {cursor: 'pointer'},
                  type: 'info',
                  onClick: () => toLink(_row)
                }, {default: () => _row.variant})
          }
        },
        {
            title: (():any => {
                        return h(
                            NText,
                            { style: { fontFamily: 'HanSansCN-Bold', fontSize: '18px' } },
                            { default: () => "cHGVS" }
                        )
                    }),
            key: 'chgvs'
        },
        {
            title: (():any => {
                        return h(
                            NText,
                            { style: { fontFamily: 'HanSansCN-Bold', fontSize: '18px' } },
                            { default: () => "pHGVS" }
                        )
                    }),
            key: 'phgvs'
        },
        {
            title:(():any => {
                        return h(
                            NText,
                            { style: { fontFamily: 'HanSansCN-Bold', fontSize: '18px' } },
                            { default: () => "Consequence" }
                        )
                    }),
            key: 'consequence',
        },
        {
            title:(():any => {
                        return h(
                            NText,
                            { style: { fontFamily: 'HanSansCN-Bold', fontSize: '18px' } },
                            { default: () => "Criteria" }
                        )
                    }),
            key: 'criteria',
        },
        {
            title:(():any => {
                        return h(
                            NText,
                            { style: { fontFamily: 'HanSansCN-Bold', fontSize: '18px' } },
                            { default: () => "Classification" }
                        )
                    }),
            key: 'classification',
        },
    ]);
</script>

<style scoped>

</style>
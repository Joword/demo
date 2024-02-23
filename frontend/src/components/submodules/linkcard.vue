<template>
    <n-tag type="info" style="cursor: pointer;background-color: white;border: 2px solid;border-radius: 5px;margin-right: 10px;" @click="link2Others(props.info)">
        <template #default>
            <b>{{ name }}</b>
        </template>  
        <template #icon>
            <n-icon :component="ExpandUpRight28Regular" style="font-weight: bold;"></n-icon>
        </template>
    </n-tag>
</template>

<script setup lang="ts">
    import { ref } from "vue";
    import { ExpandUpRight28Regular } from "@vicons/fluent";

    const props = defineProps(['info']);
    const name = ref('');
    switch (props.info) {
        case 'clinvar':
            name.value = 'ClinVar';
            break;
        case 'genereviews':
            name.value = 'GeneReviews';
            break;
        case 'gnomAD':
            name.value = 'gnomAD';
            break;
        case 'omim':
            name.value = 'OMIM';
            break;
        case 'clingen':
            name.value = 'ClinGene';
            break;
        case 'gtex':
            name.value = 'GTExPorta';
            break;
        case 'hgnc':
            name.value = 'HGNC';
            break;
    }
    const link2Others = (arg:string) => {
        switch (arg) {
            case 'clinvar':
                window.open('https://www.ncbi.nlm.nih.gov/clinvar/')
                break;
        
        }
    }
</script>

<style scoped>

</style>
<template>
    <div class="home-card" @click="getEvent(props.info)" ref="card">
      <div style="margin-bottom: 10px;">
        <img :src="showData.img" style="width:100%; height: 180px" /></div>
      <div class="home-card-title">{{ showData.title }}</div>
        <div class="home-card-desc" v-html="showData.desc"></div>
    </div>
</template>

<script setup lang="ts">
    import { ref, onMounted } from "vue";
    import { useInformation } from "@/store";
    import { useRouter } from "vue-router";

    import { HomeCard } from "@/types/info";

    const router = useRouter();
    const useInfo = useInformation();
    const props = defineProps(['info']);
    const showData = ref<HomeCard>({ img:'', title:'', desc:'' });

    const getType = () => {
        switch (props.info) {
            case 'chatpaper':
                showData.value.title = useInfo.pdfParser.value;
                showData.value.desc = useInfo.pdfParser.desc;
                showData.value.img = useInfo.pdfParser.img;
                break;
            case 'interpretation':
                showData.value.title = useInfo.interpretation.value;
                showData.value.desc = useInfo.interpretation.desc;
                showData.value.img = useInfo.interpretation.img;
                break;
            case 'acmgClassification':
                showData.value.title = useInfo.acmgClassification.value;
                showData.value.desc = useInfo.acmgClassification.desc;
                showData.value.img = useInfo.acmgClassification.img;
                break;
        }
    };
    const getEvent = (arg: string) => {
        switch (arg) {
            case 'chatpaper':
                router.push({ path: 'paper', query: { pmid: '16460646', pmcid: 'PMC3525152' }});
                break;
            case 'interpretation':
                // router.push({name: 'pmid', params: {pmid: '25394566'}});
                router.push({name: 'variant', params: {variantId: '7-107315505-T-A'}, query: { type: 'article' }});
                break;
            case 'acmgClassification':
                router.push({name: 'variant', params: {variantId: '7-107315505-T-A'}, query: { type: 'classification' }});
                break;
        }
    }

    onMounted(() => {
        getType();
    });
</script>

<style lang="css">
    :root{
        --card-text-color: black;
    }

    .home-card {
        min-height: 330px;
        background-color: white;
        color: #A6A6A6;
        border-radius: 8px;
        padding: 20px;
        cursor: pointer;
    }
    .home-card:hover {
        background-color: white;
        color: var(--card-text-color);
    }
    .home-card-title {
        font-family: HanSansCN-Bold;
        text-align: left;
        font-size: 20px;
    }
    .home-card-desc {
        word-wrap: break-word;
        text-align: left;
        font-size: 16px;
    }
</style>
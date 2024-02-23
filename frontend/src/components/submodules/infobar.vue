<template>
    <div id="info-bar">
        <div style="width:40px"></div>
        <div style="flex: 18;">
            <div id="gene" v-if="props.type==='gene'">
                <div style="color: white;line-height: 40px;text-align: left;">
                    <span style="cursor: pointer;" @click="go2Event('home')">{{ t('commons.home') }}</span>
                    <n-icon :component="AngleRight" size="20" color="white" style="vertical-align: sub;"></n-icon>
                    <span style="cursor: pointer;">{{ props.value }}</span>
                    
                </div>
                <div style="line-height: 60px;color: white;text-align: left;">
                    <b style="font-family: HanSansCN-Bold;font-size: 30px;margin-right: 10px;">{{ props.value }}</b>
                  <span style="font-size: 18px;">Name:&nbsp;{{ props.info.name }} | Cytoband:&nbsp;{{ props.info.chromosome }}</span>
                </div>
                <div style="text-align: left;margin-bottom: 10px;">
                    <LinkCard :info="'clinvar'"></LinkCard>
                    <LinkCard :info="'genereviews'"></LinkCard>
                    <LinkCard :info="'gnomAD'"></LinkCard>
                    <LinkCard :info="'omim'"></LinkCard>
                    <LinkCard :info="'clingen'"></LinkCard>
                    <LinkCard :info="'gtex'"></LinkCard>
                    <LinkCard :info="'hgnc'"></LinkCard>
                </div>
            </div>
            <div id="article" v-else-if="props.type==='article'">
                <div style="color: white;line-height: 40px;text-align: left;">
                    <span style="cursor: pointer;" @click="go2Event('home')">{{ t('commons.home') }}</span>
                    <n-icon :component="AngleRight" size="20" color="white" style="vertical-align: sub;"></n-icon>
                    <span style="cursor: pointer;">{{ t('commons.article') }}</span>
                    <n-icon :component="AngleRight" size="20" color="white" style="vertical-align: sub;"></n-icon>
                    <span style="cursor: pointer;">{{ props.value }}</span>
                </div>
                <div>
                    <div style="line-height: 60px;color: white;text-align: left;">
                        <b style="font-family: HanSansCN-Bold;font-size: 30px;margin-right: 10px;">{{ props.value }}</b>
                        <span style="font-size: 18px;">7-110735505-T>A | p.Val239Asp | NM_000441.2 | dbSNPrs11103325</span>
                    </div>
                </div>
            </div>
        </div>
        <div style="width:40px"></div>
    </div>
</template>

<script setup lang="ts">
    import { useI18n } from "vue-i18n";
    import { useRouter } from "vue-router";
    import { AngleRight } from "@vicons/fa";

    import LinkCard from "@/components/submodules/linkcard.vue";

    const { t } = useI18n();
    const router = useRouter();
    const props = defineProps(['type', 'value', 'info']);

    const go2Event = (arg:string) => {
        switch (arg) {
            case 'home':
                router.push({name:'home'});
                break;
        }
    }

</script>

<style scoped>
    #info-bar {
        display: flex;
        min-height: 80px;
        background-color: #235BA3;
        border-top: 1px solid #225BA5;
        border-image: -webkit-linear-gradient(50deg, #225BA5 20%,#deeeff 50%, #225BA5 75%) 1 2 2 1;
        border-image: -moz-linear-gradient(30deg, #225BA5 20%,#deeeff 50%, #225BA5 75%) 2 2 2 2;
        border-image: linear-gradient(30deg, #225BA5 20%,#deeeff 50%, #225BA5 75%) 2 2 2 2;
    }
</style>
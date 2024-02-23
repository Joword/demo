<template>
    <n-page-header style="background-color: #235BA3; height: 50px; min-width: 1200px;">
        <template #title>
            <n-row id="buttonGroup">
                <div style="width: 300px;padding-top: 10px;margin: 0 20px;">
                    <n-input class="header-input" :placeholder="$t('commons.searchPlaceholder')" clearable>
                        <template #prefix>
                            <n-icon :component="IosSearch" />
                        </template>
                    </n-input>
                </div>
                <n-col class="buttons-group">
                    <n-dropdown :options="myWorkOptions"
                        placement="bottom-start">Mywork</n-dropdown>
                </n-col>
                <n-col class="buttons-group" @click="changeEvent('tools')">Tools</n-col>
                <n-col class="buttons-group" @click="changeEvent('about')">About</n-col>
            </n-row>
        </template>
        <template #avatar>
            <div style="min-width: 40px;"></div>
            <n-image class="pointer" @click="changeEvent('home')" src="/transport-green-logo.png" width="70"
                preview-disabled />
        </template>
        <template #extra>
            <n-space>
                <div style="vertical-align: middle;color: white;font-size: 20px;line-height: 40px;">{{ info.userMessage.name
                }}</div>
                <div style="line-height: 30px;">
                    <n-dropdown :options="languageOptions" placement="bottom-start">
                        <div class="language-icon">
                            <n-icon color="white" :component="Language" />
                        </div>
                    </n-dropdown>
                </div>
                <div style="min-width: 40px;"></div>
            </n-space>
        </template>
    </n-page-header>
</template>

<script setup lang="ts">
import { onMounted } from 'vue'
import { useRouter } from "vue-router";
import { useSchema, informations } from '@/store';
import { Language } from "@vicons/ionicons5";
import { IosSearch } from "@vicons/ionicons4";
//import { getVariantId } from "@/api/request";

//const route = useRoute();
const router = useRouter()
const schema = useSchema();
const info = informations();
//const variantInfo = useInformation();

const myWorkOptions = [
    {
        label: 'My group work',
        key: 'work',
        props: {
            onClick: () => {
                router.push({ name: 'manager', query: { type: 'work' } });
            }
        }
    },
    {
        label: 'My search log',
        key: 'log',
        props: {
            onClick: () => {
                router.push({ name: 'manager', query: { type: 'log' } });
            }
        }
    }
]
const languageOptions = [
    {
        label: 'English',
        key: 'en-US',
        props: {
            onClick: () => {
                schema.setLanguage("en-US")
            }
        }
    },
    {
        label: '简体中文',
        key: 'zh-CN',
        props: {
            onClick: () => {
                schema.setLanguage("zh-CN")
            }
        }
    }
];
const changeEvent = (arg: string) => {
    switch (arg) {
        case "home":
            router.push({ name: 'home' });
            break;
        case "manager":
            router.push({ name: 'manager' });
            break;
        case "tools":
            router.push({ name: 'tools' });
            break;
        case "about":
            router.push({ name: 'about' });
            break;
    }
};
//const getData = () => {
//    if (variantInfo.variantBasicInfo.gene === "") {
//        const data = new FormData();
//        data.append("vd", route.params.variantId.toString());
//        getVariantId(data).then(res => {
//            if (res.data.code === 1000) {
//                variantInfo.variantBasicInfo.gene = res.data.data.gene;
//                variantInfo.variantBasicInfo.variantId = res.data.data.variantId;
//                variantInfo.variantBasicInfo.transcript = res.data.data.transcript;
//                variantInfo.variantBasicInfo.classification = res.data.data.classification;
//                variantInfo.variantBasicInfo.criteria = res.data.data.criteria;
//                variantInfo.variantBasicInfo.chgvs = res.data.data.chgvs.split(":").length > 1 ? res.data.data.chgvs.split(":")[1] : res.data.data.chgvs;
//                variantInfo.variantBasicInfo.phgvs = res.data.data.phgvs.split(":").length > 1 ? res.data.data.phgvs.split(":")[1] : res.data.data.phgvs;
//                variantInfo.variantBasicInfo.rsId = res.data.data.rsId;
//                variantInfo.variantBasicInfo.cytoband = res.data.data.cytoband;
//                variantInfo.variantBasicInfo.chgvsDetail = res.data.data.chgvsSummary;
//                variantInfo.variantBasicInfo.phgvsDetail = res.data.data.phgvsSummary;
//                variantInfo.articleBasicInfo.number = res.data.data.publicationInfoVo.number;
//                variantInfo.articleBasicInfo.pmid = res.data.data.publicationInfoVo.pmids;
//                variantInfo.articleBasicInfo.pmcid = res.data.data.publicationInfoVo.pmcids;
//                variantInfo.articleBasicInfo.source = res.data.data.publicationInfoVo.source;
//                variantInfo.articleBasicInfo.sourceNumber = res.data.data.publicationInfoVo.sourceNumber;
//                variantInfo.articleBasicInfo.summary = res.data.data.publicationInfoVo.summary;
//                variantInfo.clinvarBasicInfo.clinvarId = res.data.data.clinvarObj.clinvarId;
//                variantInfo.clinvarBasicInfo.consequence = res.data.data.clinvarObj.mutation;
//                variantInfo.clinvarBasicInfo.pathogenicity = res.data.data.clinvarObj.pathogenicity;
//                variantInfo.clinvarBasicInfo.pathAbbr = res.data.data.clinvarObj.pathAbbr;
//                variantInfo.clinvarBasicInfo.reviewStar = res.data.data.clinvarObj.reviewStatusStar;
//                variantInfo.clinvarBasicInfo.reviewStatus = res.data.data.clinvarObj.reviewStatus;
//                variantInfo.clinvarBasicInfo.afEsp = res.data.data.clinvarObj.afEsp;
//                variantInfo.clinvarBasicInfo.afExac = res.data.data.clinvarObj.afExac;
//                variantInfo.clinvarBasicInfo.afTgp = res.data.data.clinvarObj.afTgp;
//            }
//        });
//    }
//}

onMounted(() => {
    // getData();
});
</script>

<style lang="scss" scoped>
.buttons-group {
    min-width: 90px;
    min-height: 50px;
    cursor: pointer;
    color: aliceblue;
    font-size: 20px;
    vertical-align: middle;
    line-height: 50px;

}

.buttons-group:hover {
    color: #12cbc4;
}

.language-icon {
    height: 25px;
    width: 40px;
    padding-top: 8px;
}

.language-icon:hover {
    margin-top: 10px;
    height: 30px;
    width: 40px;
    border: 1px solid white;
    color: skyblue;
    border-radius: 3px;
}

.header-input {
    text-align: left;
    background-color: #235BA3;
    border-radius: 8px;
    font-size: 16px;
}

.header-input:hover {
    text-align: left;
    background-color: aliceblue;
    color: #235BA3;
}

.pointer {
    cursor: pointer;
}

.search-log {
    color: aliceblue;
    background-color: #12cbc4;
}

.group-work {
    color: aliceblue
}
</style>
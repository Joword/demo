<template #default>
    <div>
        <div class="pmid-header">
            <n-row>
                <n-col :span="1" style="width: 40px;"></n-col>
                <n-col :span="23" style="text-align: left;font-size: 16px;height: 52px;">
                    <b >{{ t('commons.gene') }}:&nbsp;</b>{{ info.gene }}
                    <b style="margin-left: 10px;">{{ t('commons.variantId') }}:&nbsp;</b>{{ info.variantId }}
                    <span class="info-buttons-group">
                        <span class="info-buttons back-btn" id="buttons-back" @click="backToVariant">
                            <n-icon size="20" class="back-btn-active" :component="DrillBack" />
                            Back
                        </span>
                        <span class="info-buttons" id="buttons-back" :style="{ color: !chatInfo.showOverView ? '#FFFFFF' : '#f1ca17', border: !chatInfo.showOverView ? '1px solid #FFFFFF' : '1px solid #f1ca17' }" @click="overViewEvent">
                            <n-icon size="20" v-if="chatInfo.showOverView" class="info-buttons-active" :component="EyeTracking20Filled" />
                            <n-icon size="20" v-else :component="EyeTracking20Regular" />
                            OverView
                        </span>
                        <span class="info-buttons" id="buttons-chatpaper" :style="{ color: !chatInfo.openChat ? '#FFFFFF' : '#f1ca17', border: !chatInfo.openChat ? '1px solid #FFFFFF' : '1px solid #f1ca17' }" @click="chatPaperEvent">
                            <n-icon size="20" :component="Bot20Regular" v-if="!chatInfo.openChat" />
                            <n-icon size="20" class="info-buttons-active" :component="Bot20Filled" v-else />
                            {{ t('modules.chat') }}</span>
                        <!-- <span class="info-buttons" id="buttons-star"><n-icon size="20" :component="Star" />{{ t('commons.star') }}</span>
                        <span class="info-buttons" id="buttons-share"><n-icon size="20" :component="Share24Regular" />{{ t('commons.share') }}</span> -->
                    </span>
                </n-col>
                <n-col :span="1" style="width: 40px;"></n-col>
            </n-row>
        </div>
        <div class="pmid-content">
            <div class="cont-detail">
                <div class="detail-left" :style="{height: height + 'px', width: chatInfo.showOverView ? '50%' : '100%'}">
                    <n-scrollbar style="height: 100%; padding: 0px;box-sizing: border-box;">
                        <div class="paragraph-1">
                            <span v-if="route.query.pmid">pmid: {{ route.query.pmid }} | </span>  
                            <span v-if="route.query.pmcid">pmcid: {{ route.query.pmcid }}</span>
                            <!-- <span>PMC3525152</span> -->
                        </div>
                        <Criteria></Criteria>
                    </n-scrollbar>
                </div>
                <div class="detail-right" :style="{height: height + 'px'}" v-show="chatInfo.showOverView">
                    <n-spin :show="chatInfo.loading" :rotate="false">
                        <template #icon>
                            <n-icon>
                                <BounceLoader
                                    :loading="chatInfo.loading"
                                    color="#0954AB"
                                    size="60px" />
                            </n-icon>
                        </template>
                        <div :style="{height: height + 'px'}">
                            <n-scrollbar style="height: 100%; padding: 10px; box-sizing: border-box;">
                                <Overview></Overview>
                            </n-scrollbar>
                        </div>
                    </n-spin>
                </div>
            </div>
            <div class="cont-chat" v-show="chatInfo.openChat" :style="{width: chatInfo.showOverView ? '50%' : '100%'}">
                <ChatText :height="height"></ChatText>
            </div>
        </div>
    </div>  
</template>

<script setup lang="ts">
    import { ref, reactive, watch, onBeforeMount, onMounted } from "vue";
    import { useI18n } from "vue-i18n";
    import { BounceLoader } from "vue3-spinner";
    // import { useRouter } from "vue-router";
    import { informations } from "../store";
    import { Bot20Filled, Bot20Regular, EyeTracking20Regular, EyeTracking20Filled } from "@vicons/fluent";
    import { DrillBack } from '@vicons/carbon'
    // import { Send } from "@vicons/tabler";

    import ChatText from "./submodules/chattext.vue";
    import Criteria from "./submodules/criteria.vue";
    import Overview from "./submodules/overview.vue";
    import { useChatInfo } from '@/store';
    import { useRoute, useRouter } from "vue-router";
    // import { getPaperContent } from '@/api/request';
    const { t } = useI18n();
    const route = useRoute();
    const router = useRouter();
    const chatInfo = useChatInfo();
    // const router = useRouter();
    const msg = informations();
    // const showOverView = ref<boolean>(true);
    const showChatPaper = ref<boolean>(false);
    const height = ref(405);
    // const openChat = ref<boolean>(false);
    const paperSpan = ref({'criteria':23,'text':0})
    const tagSpan = ref({'criteria':18})
    const info = reactive({ gene: 'SLC26A4', variantId: '7-107315505-T-A' });

    const backToVariant = () => {
        router.go(-1)
    }

    const overViewEvent = () => {
        chatInfo.isShowOverView(!chatInfo.showOverView);
        // showOverView.value = !showOverView.value;
    }
    const chatPaperEvent = () => {
        showChatPaper.value = !chatInfo.openChat;
        chatInfo.isOpenChat(showChatPaper.value);
        chatInfo.openChatFirst();
        // openChat.value = true;
    };
    // const paperContentData = () => {
    //     getPaperContent({pmid: '16460646', variantId: '7-107315505-T-A', userId: '21'}).then((res) => {
    //         console.log(res);
    //     })
    // }

    watch(() => msg.chatpaper.value, (newValue) => {
        switch (newValue) {
            case true:
                paperSpan.value.criteria = 16;
                paperSpan.value.text = 7;
                tagSpan.value.criteria = 11;
                break;
            case false:
                paperSpan.value.criteria = 23;
                tagSpan.value.criteria = 18;
                break;
        }
    });

    // const backToRoute = () => {
    //     router.push({ name: 'detail' })
    // }

    window.onresize = () => {
        height.value = document.documentElement.clientHeight - 128;
    }
    onBeforeMount(()=> {
        height.value = document.documentElement.clientHeight - 128;
    })
    onMounted(() => {
        // paperContentData();
        if (msg.chatpaper.value) {
            paperSpan.value.criteria = 16;
            paperSpan.value.text = 7;
            msg.chatpaper.color = "black";
            tagSpan.value.criteria = 11;
        }else{
            paperSpan.value.criteria = 23;
            tagSpan.value.criteria = 18
        }
    })
    
</script>

<style scoped lang="scss">
    b {
        font-family: "HanSansCN-Bold";
    }
    .info-buttons {
        padding: 5px;
        border-radius: 5px;
        margin-right: 10px;
        font-family: "HanSansCN-Light";
        cursor: pointer;
        border: 1px solid white;
        .info-buttons-active:hover {
            color: #f1ca17 !important;
        }
    }
    .back-btn:hover {
        border: 1px solid #f1ca17 !important;
        color: #f1ca17 !important;
        .back-btn-active {
            color: #f1ca17 !important;
        }
    }
    .info-buttons .n-icon {
        vertical-align: sub;
        padding-right: 2px;
    }
    .info-buttons:hover {
        border: 1px solid white;
        color: white
    }
    .info-buttons-group {
        float: right;
        color: #F5F5F5;
        user-select: none;
    }
    .pmid-header {
        line-height: 52px;
        background-color: rgba(31,82,148, 1);
        // border-top: 1px solid #A6A6A6;
        border-top: 1px solid rgba(204, 204, 204, .5);
        color: #FFFFFF;
    }
    .pad {
        padding: 16px;
    }
    .pmid-content {
        width: 100%;
        padding: 10px;
        display: flex;
        box-sizing: border-box;
        .cont-detail {
            width: 100%;
            border: 2px solid #FAF7F7;
            border-radius: 8px;
            display: flex;
            align-items: stretch;
            justify-content: space-between;
            .detail-right {
                width: 50%;
                box-sizing: border-box;
            }
            .detail-left {
                width: 50%;
                // @extend .pad;
                text-align: left;
                box-shadow: 8px 0 8px -8px rgba(0,0,0,.5);
                box-sizing: border-box;
                .paragraph-1 {
                    padding: 10px 10px 0 10px;
                    span {
                        font-size: 16px;
                        font-weight: bolder;
                        color: #7EA5CC;
                    }
                    .desc-1 {
                        font-size: 14px;
                        font-weight: bold;
                        line-height: 18px;
                    }
                }
                .paragraph-2 {
                    color: #808080;
                    font-size: 12px;
                    margin: 16px 0;
                }
                .paragraph-3 {
                    img {
                        width: 100%;
                        height: 360px;
                    }
                }
            }
        }
        .cont-chat {
            width: 50%;
            margin-left: 6px;
        }
    }
</style>
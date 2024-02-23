<template>
    <n-spin :show="loading" :rotate="false">
        <template #icon>
            <n-icon>
                <BounceLoader
                    :loading="loading"
                    color="#0954AB"
                    size="60px" />
            </n-icon>
        </template>
        <n-row class="chat-text">
            <!-- <n-col :span="24"> -->
                <n-scrollbar ref="historyRef" class="scroller-conversation" :style="{height: props.height - 65 + 'px', paddingTop: '6px', paddingBottom: '10px', paddingLeft: '6px', paddingRight: '6px', border: '2px solid #FAF7F7', BorderRadius: '8px',  }" @scroll="listeningScroll">
                    <n-alert class="failed-connect-alert" title="connection dropped" type="error" v-if="!isConnection && !isInit" closable>
                        The chat connection has been disconnected.
                    </n-alert>
                    <transition name="fade">
                        <div class="chat-window" style="margin-bottom: 24px;" v-if="initTip">
                            <div class="conversation-com conversation-res">
                                <div class="dialog">
                                    <div class="dialog-header header-left">
                                        <img src="../../../public/chat.png" />
                                    </div>
                                    <div class="dialog-column" style="margin-left: 3px;">
                                        <div class="conversation-arrow-left"></div>
                                        <div style="text-align: left; padding: 8px 6px;">
                                            <div class="init-tip">
                                                <!-- <h4>AIGI智能咨询小助手</h4> -->
                                                <div class="examples">
                                                    <p>Hello! I'm Chat-Paper and I'm your Generative AI assistant for the interpretation of this paper.</p>
                                                    <p>Here some examples of what I can do for you:</p>
                                                    <ul>
                                                        <li>1. <span @click="tipToChat($event)">Summary this paper.</span></li>
                                                        <li>2. <span @click="tipToChat($event)">Which sentence is related to hearing loss.</span></li>
                                                        <li>3. <span @click="tipToChat($event)">Translate this sentence "We conclude that inherited hearing loss is highly heterogeneous in this population, with most extended families acting as genetic isolates in this context. "</span></li>
                                                    </ul>
                                                    <!-- <p>点击提供的示例，或者在输入框中输入你想要咨询的关于文献的相关内容。</p> -->
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="clear-init-tip">
                                        <span @click="clearInitTop">clear</span>
                                    </div>    
                                </div>
                            </div>
                        </div>
                    </transition>
                    <template v-if="chatRecords.length > 0">
                        <div class="chat-window" v-for="i in chatRecords">
                            <div class="conversation-com conversation-ask" v-if="i.way === 'request'">
                                <div class="dialog">
                                    <div class="dialog-column" style="margin-right: 3px; background-color: #F5F5F5;">
                                        <div class="conversation-arrow-right"></div>
                                        <div style="text-align: left; padding: 8px 6px;">
                                            <div style="line-height: 1.3;font-size: 14px;" v-text="i.message"></div>
                                        </div>
                                    </div>
                                    <div class="dialog-header header-right">
                                        <n-icon class="icon-user" :component="UserAvatarFilledAlt" />
                                    </div>
                                </div>
                            </div>
                            <div class="conversation-com conversation-res" v-if="i.way === 'response'">
                                <div class="dialog">
                                    <div class="dialog-header header-left">
                                        <img src="../../../public/chat.png" />
                                    </div>
                                    <div class="dialog-column" style="margin-left: 3px;">
                                        <div class="conversation-arrow-left"></div>
                                        <div style="text-align: left; padding: 8px 6px;">
                                            <div style="line-height: 1.3;font-size: 14px;" class="ellipsis-animation" v-if="i.request">
                                                <i>thinking</i>
                                                <span></span>
                                                <span></span>
                                                <span></span>
                                            </div>
                                            <div style="line-height: 1.3;font-size: 14px; white-space: pre-line;" v-else v-html="i.message"></div>
                                        </div>
                                    </div>    
                                </div>
                            </div>
                        </div>
                    </template>
                    <n-button size="small" circle class="conversation-totop-btn" v-show="chatRecords.length > 0 && showBackToTop" @click="backToConversationTop">
                        <n-icon class="arrow-icon" :component="ArrowUpSharp" />
                    </n-button>
                </n-scrollbar>
                <div class="model-input">
                    <div class="model-btn-group">
                        <n-button size="small" :bordered="false" class="model-btn-main">
                            <n-icon class="hover-icon" :component="MachineLearningModel" />
                        </n-button>
                        <div class="model-btn-attached">
                            <n-button-group vertical style="text-align: left;">
                                <n-button style="justify-content: flex-start;" size="small" :color="gptModel === 'gpt-35-turbo' ? '#235BA3' : ''" @click="selectModel('gpt-35-turbo')" >
                                    <n-icon :class="[gptModel === 'gpt-35-turbo' ? 'hover-icon-active' : 'hover-icon']" :component="MachineLearning" />
                                    gpt-35-turbo
                                </n-button>
                                <n-button style="justify-content: flex-start;" size="small" :color="gptModel === 'gpt-35-16k' ? '#235BA3' : ''" @click="selectModel('gpt-35-16k')">
                                    <n-icon :class="[gptModel === 'gpt-35-16k' ? 'hover-icon-active' : 'hover-icon']" :component="MachineLearning" />
                                    gpt-35-16k
                                </n-button>
                                <n-button style="justify-content: flex-start;" size="small" :color="gptModel === 'gpt-4' ? '#235BA3' : ''" @click="selectModel('gpt-4')">
                                    <n-icon :class="[gptModel === 'gpt-4' ? 'hover-icon-active' : 'hover-icon']" :component="MachineLearning" />
                                    gpt-4
                                </n-button>
                                <n-button style="justify-content: flex-start;" size="small" :color="gptModel === 'gpt-4-32k' ? '#235BA3' : ''" @click="selectModel('gpt-4-32k')">
                                    <n-icon :class="[gptModel === 'gpt-4-32k' ? 'hover-icon-active' : 'hover-icon']" :component="MachineLearning" />
                                    gpt-4-32k
                                </n-button>
                            </n-button-group>
                        </div>
                    </div>
                    <n-input v-model:value="chatTextInput" class="chatbgi-input" :placeholder="gptModel" @keyup.enter="sendChatMsg($event)" :disabled="isDisabled" clearable>
                        <template #prefix>
                            <n-icon class="icon-edit" :component="Edit" />
                        </template>
                        <template #suffix>
                            <n-icon class="pointer" :component="Send" @click="sendMsg" />
                        </template>
                    </n-input>
                </div>
            <!-- </n-col> -->
        </n-row>
    </n-spin>
</template>

<script setup lang="ts">
    import { ref, onMounted, watch, nextTick } from "vue";
    import { BounceLoader } from "vue3-spinner";
    import { Send } from "@vicons/tabler";
    import { ArrowUpSharp } from '@vicons/ionicons5';
    import { UserAvatarFilledAlt, Edit, MachineLearningModel, MachineLearning } from "@vicons/carbon";
    import { Chatparams } from "@/types/chatpaper";
    import { useChatInfo } from '@/store';
    import { IWebSocketApiUrl, getWebSocketApiUrl } from "@/api/chat"

    const props = defineProps(['height']);
    const loading = ref<boolean>(false);
    const initTip = ref<boolean>(true);
    const chatTextInput = ref<string>('');
    const historyRef = ref();
    const isDisabled = ref<boolean>(false);
    const showBackToTop = ref<boolean>(false);
    const gptModel = ref<string>('gpt-4-32k');
    const webSocket = ref<WebSocket | null>(null);
    const isInit = ref<boolean>(true);
    const isSendFirst = ref<boolean>(false);
    const isConnection = ref<boolean>(false);
    const sendParams = ref<Chatparams>({
        sessionId: 'cde879f1-0eac-4d1c-b901-c888680f9518',
        tag: 'J',
        model: 'gpt-4-32k',
        content: {
            question: '',
            pmid: '16460646'
        }
    })
    let textIndex = 0;
    const getChatRecord = ref<string>('');
    const chatRecords = ref<Array<any>>([]);
    const chatInfo = useChatInfo();
    const buildChatConnect = async() => {
        await getWebSocketApiUrl('123').then((res: IWebSocketApiUrl) => {
            webSocket.value = new WebSocket(res.url);
            webSocket.value.onopen = (event: Event) => {
                if(!isConnection.value && !isInit.value) {
                    webSocket.value?.send(JSON.stringify(sendParams.value));
                }
                isInit.value = false;
                isConnection.value = true;
                console.log(event)
            }
            webSocket.value.onmessage = (event: MessageEvent) => {
                console.log(event)
                const data = JSON.parse(event.data).body;
                if(data) {
                    getChatRecord.value = data;
                    chatRecords.value[chatRecords.value.length - 1].request = false;
                    // chatRecords.value[chatRecords.value.length - 1] = {way: 'response', message: data.message, request: false};
                    // isDisabled.value = false;
                }
            }
            webSocket.value.onclose = (event: CloseEvent) => {
                console.log('websocket 断开: ' + event.code + ' ' + event.reason + ' ' + event.wasClean)
                console.log(event)
                isConnection.value = false;
                getChatRecord.value = 'connection dropped.';
                chatRecords.value[chatRecords.value.length - 1].request = false;
                isDisabled.value = false;
            }
            webSocket.value.onerror = (event: Event) => { 
                console.log(event)
                isConnection.value = false;
                getChatRecord.value = 'Can not connect.';
                chatRecords.value[chatRecords.value.length - 1].request = false;
                isDisabled.value = false;
            }
        });
    }
    const sendMsg = async() => {
        if(chatTextInput.value === '') return;
        if(!isSendFirst.value) {
            chatRecords.value = [];
        }
        isDisabled.value = true;
        isSendFirst.value = true;
        chatRecords.value.push({way: 'request', message: chatTextInput.value});
        chatRecords.value.push({way: 'response', message: '', request: true});
        sendParams.value.content.question = chatTextInput.value;
        chatTextInput.value = '';
        if(!isConnection.value) {
            await buildChatConnect();
        }else{
            webSocket.value?.send(JSON.stringify(sendParams.value));
        }
    }
    const sendChatMsg = (event: KeyboardEvent) => {
        if(event.key === 'Enter') {
            sendMsg();
        }
    }
    const backToConversationTop = () => {
        historyRef.value?.scrollTo({ left: 0, top: 0, behavior: 'smooth' });
    }
    const selectModel = (type: string) => {
        gptModel.value = type;
        sendParams.value.model = type;
    }
    let timer:any = null;
    const listeningScroll = () => {
        if(!timer) {
            timer = setTimeout(() => {
                historyRef.value?.$refs?.scrollbarInstRef.containerScrollTop > 100 ? showBackToTop.value = true : showBackToTop.value = false;
                timer = null;
            }, 300);
        }
    }
    const tipToChat = (e: any) => {
        chatTextInput.value = e.target.innerText;
    }
    const clearInitTop = () => {
        initTip.value = false;
    }

    watch(()=> chatInfo.firstOpenChat, (newVal)=> {
        if(newVal) buildChatConnect();
    })
    watch(()=> chatRecords.value, ()=> {
        nextTick(()=> {
            historyRef.value.scrollTo({ left: 0, top: historyRef.value.$refs.scrollbarInstRef.contentRef.scrollHeight, behavior: 'smooth' });
        })
    }, {deep: true})
    watch(()=> getChatRecord.value, ()=> {
        const typeWriter = () => {
            if(textIndex < getChatRecord.value.length) {
                chatRecords.value[chatRecords.value.length - 1]['message'] += getChatRecord.value.charAt(textIndex);
                historyRef.value.scrollTo({ left: 0, top: historyRef.value.$refs.scrollbarInstRef.contentRef.scrollHeight });
                textIndex++;
                setTimeout(typeWriter, 10);
            }else{
                textIndex = 0;
                getChatRecord.value = '';
                isDisabled.value = false;
            }
        }
        typeWriter();
    })
    watch(()=> chatInfo.toChatLabels, ()=> {
        if(chatInfo.toChatLabels) chatTextInput.value += chatInfo.toChatLabels;
    })
    onMounted(()=> {
        // buildChatConnect();
    })
    

</script>

<style lang="scss">
    #close:hover {
        color: grey;
    }
    #refresh:hover {
        color:grey;
    }

    .chat-text {
        .scroller-conversation {
            position: relative;
            .failed-connect-alert {
                width: calc(100% - 10px);
                position: absolute;
                top: 5px;
                left: 5px;
                z-index: 999;
            }
            .conversation-totop-btn {
                position: absolute;
                bottom: 6px;
                right: 6px;
                .arrow-icon:hover {
                    color: #235BA3;
                }
            }
        }
        .chat-window {
            margin: 6px 0;
            box-sizing: border-box;
            .conversation-com {
                display: flex;
                .dialog {
                    max-width: calc(100% - 40px);
                    display: flex;
                    align-items: flex-start;
                    position: relative;
                    .dialog-column {
                        width: calc(100% - 40px);
                        border-radius: 3px;
                        background-color: #E6F4E6;
                        color: #000000;
                        position: relative;
                        .conversation-arrow-left {
                            width: 0;
                            height: 0;
                            border-right: 5px solid #E6F4E6;
                            border-left: 5px solid transparent;
                            border-top: 5px solid transparent;
                            border-bottom: 5px solid transparent;
                            position: absolute;
                            top: 9px;
                            left: -9px;
                        }
                        .conversation-arrow-right {
                            width: 0;
                            height: 0;
                            border-left:5px solid #F5F5F5;
                            border-right: 5px solid transparent;
                            border-top: 5px solid transparent;
                            border-bottom: 5px solid transparent;
                            position: absolute;
                            top: 9px;
                            right: -9px;
                        }
                    }
                    .dialog-header {
                        width: 35px;
                        height: 35px;
                        border-radius: 5px;
                    }
                    .header-right {
                        margin-left: 2px;
                        .icon-user {
                            font-size: 28px;
                            cursor: default;
                            &:hover {
                                color: #000000;
                            }
                        }
                    }
                    .header-left {
                        margin-right: 4px;
                        img {
                            width: 28px;
                            height: 28px;
                            margin-top: 1px;
                        }
                    }
                }
            }
            .conversation-ask {
                flex-direction: row-reverse;
            }
        }
        .model-input {
            width: 100%;
            height: 40px; 
            margin-top: 10px;
            margin-bottom: 0;
            display: flex;
            align-items: center;
            .model-btn-group {
                position: relative;
                .model-btn-attached {
                    display: none;
                    position: absolute;
                    left: 0;
                    bottom: 28px;
                    z-index: 999;
                    background-color: #ffffff;
                    &:hover {
                        display: block;
                    }
                }
                .model-btn-main:hover ~ .model-btn-attached {
                    display: block;
                }
                .hover-icon-active {
                    margin-right: 4px;
                    &:hover {
                        color: #FFFFFF;
                    }
                }
                .hover-icon {
                    margin-right: 4px;
                    &:hover {
                        color: #235BA3;
                    }
                }
            }
        }
    }
    .pointer {
        cursor: pointer;
        &:hover {
            color: #5C6CFF !important;
        }
    }
    .icon-style-pointer {
        cursor: pointer;
        vertical-align: sub;
        float: right;
    }
    .icon-edit:hover {
        color:#18a058;
    }
    .chatbgi-input {
        text-align: left;
        border: 1px grey;
        border-radius: 8px;
        line-height: 40px;
    }
    .chatpaper-logo {
        min-width: 25px;
        vertical-align: middle;
    }
    .chatpaper-button-group {
        width: 650px;
    }
    .chatpaper-title {
        text-align: left;
        font-family: HanSansCN-Bold;
        padding-left: 10px;
        min-width: 50px;margin-bottom: 10px;
    }

    .ellipsis-animation {
        width: 100%;
        height: 100%;
        overflow: hidden;
        position: relative;
    }

    .ellipsis-animation span {
        display: inline-block;
        width: 2px;
        height: 2px;
        margin-left: 3px;
        border-radius: 50%;
        background-color: #000000;
        opacity: 1;
        animation: ellipsis 1.5s infinite;
    }

    .ellipsis-animation span:nth-child(1) {
        animation-delay: 1s;
    }

    .ellipsis-animation span:nth-child(2) {
        animation-delay: 0.5s;
    }

    .ellipsis-animation span:nth-child(3) {
        animation-delay: 0s;
    }

    @keyframes ellipsis {
        0% {
            opacity: 0;
        }
        50% {
            opacity: 1;
        }
        100% {
            opacity: 0;
        }
    }

    .init-tip {
        line-height: 1.3;
        white-space: normal;
        h4 {
            margin: 0;
            line-height: 1.2;
        }
        ul {
            // padding-left: 18px;
            li {
                list-style: none;
                span {
                    text-decoration: underline;
                    cursor: pointer;
                    &:hover {
                        color: #235BA3;
                    }
                }
            } 
        }
    }
    .clear-init-tip {
        position: absolute;
        right: 0;
        bottom: -20px; 
        text-align: right;
        span {
            color: rgba(241, 202, 23, .7); 
            cursor: pointer;
            &:hover {
                color: rgba(241, 202, 23, 1)
            }
        }
    }
    .fade-enter-active,
    .fade-leave-active {
        transition: opacity 1.5s ease;
    }

    .fade-enter-from,
    .fade-leave-to {
        opacity: 0;
    }
    .fade-enter-to,
    .fade-leave-from {
        opacity: 1;
    }
</style>
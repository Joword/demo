import { defineStore } from "pinia";

export const useChatInfo = defineStore("chat", {
    state: () => ({
        loading: false,
        showOverView: true,
        toChatLabels: '',
        openChat: false,
        firstOpenChat: false,
        keyWordSet: [],
        switchStatus: {},
        criteriaRelate: {},
        criteriaSet: []
    }),
    actions: {
        isShowOverView(bool: boolean) {
            this.showOverView = bool;
        },
        getHighLightLabels(labels: string) {
            this.toChatLabels = labels;
        },
        isOpenChat(bool: boolean) { 
            this.openChat = bool;
        },
        openChatFirst() {
            this.firstOpenChat = true;
        },
        getKeyWordSet(arg: any) {
            this.keyWordSet = arg;
        },
        getSwitchStatus(arg: object) {
            this.switchStatus = arg;
        },
        setCriteriaRelate(arg: object) {
            this.criteriaRelate = arg;
        },
        updateCriteriaSet(res: any) {
            this.criteriaSet = res;
        }
    }
})
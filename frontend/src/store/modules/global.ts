import { defineStore } from "pinia";

export const useGlobalInfo = defineStore("global", {
    state: () => ({
        tokenStatus: false,
    }),
    actions: {
        setTokenStatus(status: boolean) {
            this.tokenStatus = status;
        }
    }
})
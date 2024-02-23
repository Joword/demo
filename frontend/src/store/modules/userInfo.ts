import { defineStore } from "pinia";

export const informations = defineStore('user',{
    persist:{
        key: 'userKey',
        storage: localStorage,
        paths: ['userMessage', 'chatpaper']
    },
    state: () => {
        return {
            userMessage: {name: "", organization: "", isLogin: false},
            chatpaper: { value: false, color:'#F5F5F5' }
        }
    }
})

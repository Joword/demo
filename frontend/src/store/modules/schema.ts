import { defineStore } from "pinia";
import { setLocale } from "@/i18n";
import { useStorage } from "@vueuse/core";


const useSchema = defineStore("style", {
    state: () => ({
        language: useStorage("language","en-US")
    }),
    actions: {
        setLanguage(lang: string){
            this.language = lang;
            setLocale(lang);
        }
    }
})

export default useSchema;
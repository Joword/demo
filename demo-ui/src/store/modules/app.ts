import { defineStore } from "pinia";
import { AppState } from "../types";
import { useOsTheme } from "naive-ui";
import { useStorage } from "@vueuse/core";
import { setLocale } from "@/i18n";
import { themeRef } from "@/utils/theme";


const osThemeRef = useOsTheme();
const useAppStore = defineStore("app", {
	state: (): AppState => ({
		theme: useStorage("theme", osThemeRef.value),
		language: useStorage("language", "zh"),
		conversation_id: null,
	}),
	getters: {},
	actions: {
		// setTheme(theme: string | null) {
		//   this.theme = theme;
		// },
		// 切换主题
		toggleTheme() {
			this.theme = this.theme === "dark" ? "light" : "dark";
			themeRef.value = this.theme;
		},
		setLanguage(lang: string) {
			this.language = lang;
			setLocale(lang);
		},
		setConversationID(id: string) {
			this.conversation_id = id
		}
	},
});

export default useAppStore;

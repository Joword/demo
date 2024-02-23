import { createPinia } from "pinia";
import useAppStore from "./modules/app";
import useUserStore from "./modules/user";
import useConversationStore from "./modules/conversation";
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate';

const pinia = createPinia();
pinia.use(piniaPluginPersistedstate);

export { useAppStore, useUserStore, useConversationStore };
export default pinia;

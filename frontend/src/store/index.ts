import { createPinia } from "pinia";
import useSchema from "./modules/schema";
import { informations } from "./modules/userInfo";
import { useSummary } from "./modules/summary";
import { useInformation } from "./modules/info";
import { useData } from "./modules/data";
import { useChatInfo } from "./modules/chat";
import { useGlobalInfo } from "./modules/global";
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate';

const pinia = createPinia();
pinia.use(piniaPluginPersistedstate);

export { useSchema, informations, useSummary, useInformation, useData, useChatInfo, useGlobalInfo };
export default pinia;

import { createPinia } from "pinia";
import { useSummary } from "./modules/summary";
import { useInformation } from "./modules/info";
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate';

const pinia = createPinia();
pinia.use(piniaPluginPersistedstate);

export { useSummary, useInformation };
export default pinia;

import { createApp } from 'vue';
import App from './App.vue';
import pinia from "./store";
import { i18n } from "./i18n";
import router from "./router";
import VXETable from "vxe-table";
import XEUtils from "xe-utils";
import enUS from "vxe-table/es/locale/lang/en-US";

import "uno.css";
import "./style.css";
import "vxe-table/lib/style.css"
import "highlight.js/styles/atom-one-dark.css";
import "highlight.js/lib/common";

import "katex/dist/katex.css";
 import vue3Spinner from 'vue3-spinner'

declare global{
    interface Window {
        $message: any
    }
}

VXETable.setup({
    i18n: (key, args) => XEUtils.toFormatString(XEUtils.get(enUS, key), args)
})
const app = createApp(App);
app.use(router);
app.use(pinia);
app.use(i18n);
app.use(VXETable);
 app.use(vue3Spinner);
app.mount("#app");
import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";
import UnoCSS from "@unocss/vite";
import presetUno from "@unocss/preset-uno";
import Components from "unplugin-vue-components/vite";
import vueJsx from '@vitejs/plugin-vue-jsx';
import { NaiveUiResolver } from "unplugin-vue-components/resolvers";
import { transformerDirectives } from "unocss";
import { fileURLToPath, URL } from "node:url";

// https://vitejs.dev/config/
export default defineConfig({
    base: "/",
    plugins: [
        vue(),
        UnoCSS({
            presets: [
                /* no presets by default */
                presetUno(),
            ],
            /* options */
            transformers: [transformerDirectives()],
        }),
        vueJsx(),
        Components({
            resolvers: [NaiveUiResolver()],
        }),
    ],
    resolve: {
        alias: {
            "@": fileURLToPath(new URL("../src", import.meta.url)),
        },
    },
    css: {
        preprocessorOptions: {
            scss: {
                additionalData: '@import "../src/style/global.scss";'
            }
        }
    }
});

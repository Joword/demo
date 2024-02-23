import { defineConfig } from 'vite';
import vue from '@vitejs/plugin-vue';
// import UnoCss from "@unocss/vite";
// import presetUno from "@unocss/preset-uno";
// import { transformerDirectives } from "unocss";
import Components from "unplugin-vue-components/vite";
import { NaiveUiResolver } from "unplugin-vue-components/resolvers";
import { fileURLToPath, URL } from "node:url";

// https://vitejs.dev/config/
export default defineConfig({
	// base: "./",
	plugins: [
		vue(),
		// UnoCss({
		// 	presets: [
		// 	presetUno(),
		// 	],
		// 	transformers: [transformerDirectives()],
		// }),
        // UnoCss(),
		Components({
			resolvers: [NaiveUiResolver()],
		}),
	],
	define:{
		"process.env": {},
		__VUE_I18N_FULL_INSTALL__: true,
		__VUE_I18N_LEGACY_API__: false,
		__INTLIFY_PROD_DEVTOOLS__: false,
	},
	resolve: {
		alias: {
			"@": fileURLToPath(new URL("../src",import.meta.url))
		}
	},
    css: {
        preprocessorOptions: {
            scss: {
                additionalData: '@import "../src/style/global.scss";'
            }
        }
    }
})

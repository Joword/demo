import { mergeConfig } from "vite";
import baseConfig from "./vite.config.base";

export default mergeConfig({
	mode: "development",
	server: {
		host: "0.0.0.0",
		port: "9566",
		fs: { strict: true },
		proxy: {
			'/test': {
				target: 'http://127.0.0.1:8957',
				changeOrigin: true,
				ws: true,
				rewrite: (path) => path.replace(/^\/test/, ''),
			}
		}
	},
	define: {
		"import.meta.env.VITE_API_PROTOCOL": "'auto'",
		"import.meta.env.HOST_URL": "'http://172.16.218.62:9570/test'"
	},
}, baseConfig)

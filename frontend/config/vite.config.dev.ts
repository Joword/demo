import { mergeConfig } from "vite";
import baseConfig from "./vite.config.base";

export default mergeConfig({
	mode: "development",
	server: {
		host: "0.0.0.0",
		port: "9566",
		fs: {strict: true},
		proxy: {
			'/test': {
                // target: 'http://172.19.205.15:9570',
                target: 'http://172.19.205.21:9570',
				changeOrigin: true,
                logLevel: 'debug',
				ws: true,
				rewrite: (path) => path.replace(/^\/test/, ''),
			}
		}
	},
	define: {
		"import.meta.env.VITE_API_PROTOCOL": "'auto'",
        "import.meta.env.HOST_URL": "'http://172.16.56.33:9570/test'"
	},
}, baseConfig)

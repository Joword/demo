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
                target: 'http://xx.xx.xx.xx:xxxx',
				changeOrigin: true,
                logLevel: 'debug',
				ws: true,
				rewrite: (path) => path.replace(/^\/test/, ''),
			}
		}
	},
	define: {
		"import.meta.env.VITE_API_PROTOCOL": "'auto'",
        "import.meta.env.HOST_URL": "'http://xx.xx.xx.xx:xxxx/test'"
	},
}, baseConfig)

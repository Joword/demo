import { mergeConfig } from "vite";
import baseConfig from "./vite.config.base";

export default mergeConfig(
	{
	mode: "development",
	server: {
		host: "0.0.0.0",
		port: 9173,
		fs: {
		strict: true,
		},
		proxy: {
		"/demoapi": {
			target: "http://172.19.205.18:8000",
			changeOrigin: true,
			ws: true,
			rewrite: (path) => path.replace(/^\/demoapi/, ""),
		},
		},
	},
	},
	baseConfig
);

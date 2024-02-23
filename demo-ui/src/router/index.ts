import { createRouter, createWebHistory } from "vue-router";
import createRouteGuard from "./guard";

const router = createRouter({
	history: createWebHistory(),
	routes: [
		{
			path: "/",
			redirect: "home",
		},
		{
			path: "/home",
			name: "home",
			component: () => import("@/views/home.vue"),
			meta: {
				requiresAuth: false,
				roles: ["superuser", "user"],
			},
		},
		{
			path: "/login",
			name: "login",
			component: () => import("@/views/login.vue"),
			meta: {
				requiresAuth: false,
				roles: ["superuser", "user"],
			},
		},
		{
			path: "/register",
			name: "register",
			component: () => import("@/views/register.vue"),
		},
		{
			path: "/error",
			name: "errorPageWrapper",
			children: [
				{
					path: "/error/403",
					name: "403",
					component: () => import("@/views/403.vue"),
					meta: {
						requiresAuth: false,
						roles: ["superuser", "user"],
					},
				},
			],
		},
	],
	scrollBehavior() {
		return { top: 0 };
	},
});
createRouteGuard(router);
export default router;

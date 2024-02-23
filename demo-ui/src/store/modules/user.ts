import { getUserInfoApi, loginApi, LoginData, logoutApi } from "@/api/user";
import { UserRead } from "@/types/schema";
import { clearCookie } from "@/utils/auth";
import { defineStore } from "pinia";
import { UserState } from "../types";

const useUserStore = defineStore("user", {
	state: (): UserState => ({
		user: null,
		savedUsername: null,
		savedPassword: null,
		isFirstLogin: false
	}),
	getters: {
		userInfo(state: UserState): UserRead | null {
			return state.user;
		},
	},

	actions: {
		// Set user's information
		setInfo(user: UserRead) {
			this.$patch({ user });
		},
		setSavedLoginInfo(username: string, password: string) {
			this.$patch({ savedUsername: username, savedPassword: password });
		},
		// Reset user's information
		resetInfo() {
			this.$reset();
		},

		// Get user's information
		async fetchUserInfo() {
			const result = (await getUserInfoApi()).data;
			if (result.is_superuser || !result.is_verified) {
				this.$state.isFirstLogin = false
			} else {
				result['chat_status'] == 'rookie' ? this.$state.isFirstLogin = true : this.$state.isFirstLogin = false
			}
			this.setInfo(result);
		},

		// Login
		async login(loginForm: LoginData) {
			try {
				await loginApi(loginForm);
				// setToken(res.data.token);
			} catch (err) {
				clearCookie();
				throw err;
			}
		},

		// Logout
		async logout() {
			try {
				await logoutApi();
			} finally {
				this.resetInfo();
				clearCookie();
			}
		},
	},
});

export default useUserStore;

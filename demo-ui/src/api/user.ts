import { UserRead, isAuthorize, UserCreate } from "@/types/schema";
import axios from "axios";
import ApiUrl from "./url";

export interface LoginData {
	username: string;
	password: string;
}

export interface GetRequestCode {
	mode: string;
	email: string;
}

export interface ForgetPassword{
	token: string;
	email: string;
	requestcode: string;
}

export function loginApi(data: LoginData) {
		const formData = new FormData();
		formData.set("username", data.username);
		formData.set("password", data.password);
		return axios.post<any>(ApiUrl.Login, formData, {
				headers: {
						"Content-Type": "multipart/form-data",
				},
		});
}

export function registerApi(userInfo: UserCreate) {
	return axios.post<UserRead>(ApiUrl.Register, userInfo);
  }

export function logoutApi() {
	return axios.post<any>(ApiUrl.Logout);
}

export function getUserInfoApi() {
	return axios.get<UserRead>(ApiUrl.UserInfo);
}

export function getAllUserApi() {
	return axios.get<UserRead[]>(ApiUrl.UserList);
}

export function getUserApi(user: string) {
	return axios.get<UserRead[]>(ApiUrl.UserInfoSingle+`/${user}`);
}

export function deleteUserApi(user_id: number) {
	return axios.delete(ApiUrl.UserList + `/${user_id}`);
}

export function resetUserPasswordApi(user_id: number, old_password: string, new_password: string) {
	return axios.patch(ApiUrl.UserList + `/${user_id}/reset-password`, null, {
		params: { old_password, new_password },
	});
}

export function getRequestCodeApi(getRequestCode:GetRequestCode){
	return axios.post(ApiUrl.emailSend, getRequestCode);
}

export function userAuthorizationApi(authorize:isAuthorize) {
	if (authorize?.user_ids.length>0) {
		for (let i  = 0; i  < authorize?.user_ids.length; i ++) {
			let userId = authorize?.user_ids[i].user_id;
			return axios.post(ApiUrl.Authorization+`/${userId}/limit`,authorize?.user_ids[i]);
		}
	}
	return new Promise((resolve,reject) => {resolve(null)});
}

export function forgetPasswordApi(forgetPassword:ForgetPassword) {
	return axios.post(ApiUrl.forgetPassword, forgetPassword);
}

export function resetPasswordApi(token:string , new_password:string) {
	return axios.patch(ApiUrl.resetPassword + `/${token}`, null, {
		params: {new_password}
	});
}

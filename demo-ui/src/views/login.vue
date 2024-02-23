<template>
	<!-- Login Form -->
	<div class="flex justify-center items-center mt-20">
		<n-image preview-disabled width="230" :src="chatbgiIcon1" />
	</div>
	<div class="flex justify-center items-center mt-5">
		<font class="text-gray-500 inline-block" size=3>{{ $t("commons.siteTag") }}</font>
	</div>
	<div class="flex justify-center items-center mt-15">
		<n-form ref="formRef" :model="formValue" :rules="loginRules" :label-col="{ span: 8 }" :wrapper-col="{ span: 16 }">
			<n-form-item :label="$t('commons.username')" path="username">
				<n-input v-model:value="formValue.username" :placeholder="$t('tips.pleaseEnterUsername')" :input-props="{
					autoComplete: 'username'
				}" />
			</n-form-item>
			<n-form-item :label="$t('commons.password')" path="password" :show-feedback=false>
				<n-input type="password" show-password-on="click" v-model:value="formValue.password"
					:placeholder="$t('tips.pleaseEnterPassword')" :input-props="{ autoComplete: 'current-password' }"
					@keyup.enter="login" />
			</n-form-item>
			<div style="text-align: right;cursor: pointer;color: #0067c3;"><a @click.naive="resetpassword">{{
				$t("commons.foundPassword") }}</a></div>
			<div class="item py-4" style="width: 300px;vertical-align: middle;">
				<font class="text-gray-500 inline-block" size=2>仅限华大内部使用，意见反馈请联系彭继光</font>
			</div>
			<n-form-item :show-feedback=false :show-label=false>
				<div class="w-full" style="display: flex; justify-content: space-between;">
					<div style="text-align: left;">
						<n-button type="primary" @click="register">{{ $t("commons.register") }}</n-button>
					</div>
					<div style="text-align: right;">
						<n-button type="primary" @click="login" :enabled="loading">{{ $t("commons.login") }}</n-button>
					</div>
				</div>
			</n-form-item>
			<!-- <div class="list">
					<div class="cc rowup">
						<div class="item" style="width: 800px;vertical-align: middle;padding-left: 20px;padding-top: 3px;">仅限华大内部使用,&nbsp;意见反馈请联系<a href="mailto:pengjiguang@bgi.com">彭继光</a></div>
					</div>
				</div> -->
		</n-form>
	</div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue';
import { useUserStore } from '@/store';
import { useRouter } from 'vue-router';
import { useI18n } from 'vue-i18n';
import { LoginData, getRequestCodeApi, GetRequestCode } from '@/api/user';
import { Message } from '@/utils/theme';
import { FormValidationError } from 'naive-ui/es/form';
import { FormInst } from 'naive-ui';
import { foundUserPasswordDialog } from '@/utils/renders';
import chatbgiIcon1 from '/logo3.png';

const router = useRouter();
const { t } = useI18n();
const userStore = useUserStore();
const formRef = ref<FormInst>();

const formValue = reactive({
	username: '',
	password: ''
});
const loading = ref(false);
const loginRules = {
	username: { required: true, message: t("tips.pleaseEnterUsername"), trigger: 'blur' },
	password: { required: true, message: t("tips.pleaseEnterPassword"), trigger: 'blur' }
}

const login = async () => {
	if (loading.value) return;
	formRef.value?.validate(async (errors?: Array<FormValidationError>) => {
		if (errors == undefined) {
			loading.value = true;
			try {
				await userStore.login(formValue as LoginData);
				//const { redirect, ...othersQuery } = router.currentRoute.value.query;
				await userStore.fetchUserInfo();
				Message.success(t('tips.loginSuccess'));
				await router.push({
					name: userStore.user?.nickname === "admin" ? 'admin' : 'conversation'
				});
				// TODO: 记住密码
			} catch (e) {
				Message.error(t("errors.loginException"))
			}
			finally {
				loading.value = false;
			}
		}
	});
}

const register = () => {
	router.push({ name: 'register' });
}

if (userStore.user) {
	router.push({ name: 'conversation' });
}

const resetpassword = () => {
	foundUserPasswordDialog(
		async (email: string) => {
			const result = ref(false)
			const codeForm = {
				mode: "Forgetpassword",
				email: email
			};
			await getRequestCodeApi(codeForm as GetRequestCode).then(res => {
				if (res.status === 200) {
					Message.success(t('tips.mailCodeSuccess'));
					result.value = true
				}
			}).catch(e => { console.log(e); });
			return result.value
		},
		() => { Message.info(t("commons.send")) },
	)
}
</script>
<style lang="scss">
@keyframes rowup {
	0% {
		-webkit-transform: translate3d(0, 0, 0);
		transform: translate3d(0, 0, 0);
	}

	100% {
		-webkit-transform: translate3d(-100%, 0, 0);
		transform: translate3d(-100%, 0, 0);
	}
}

.list {
	width: 281px;
	border: 0px solid #c2c2c2;
	margin: 20px auto;
	position: relative;
	height: 30px;
	overflow: hidden;
	border-radius: 3px;
}

.list .rowup {
	-webkit-animation: 8s rowup linear infinite normal;
	animation: 8s rowup linear infinite normal;
	position: relative;
}

a:hover {
	color: #158bb8
}

#resetpwd:hover {
	color: #1661ab
}
</style>
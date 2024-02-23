<template>
	<n-space justify="center">
		<n-form ref="formRef" :model="formValue" :rules="loginRules" style="min-width: 400px;">
			<a href="/login">
				<div class="flex justify-center items-center mt-5 mb-5">
					<n-image preview-disabled width="230" :src="chatbgiIcon1" />
				</div>
			</a>
			<n-form-item :label="$t('commons.email')" path="email">
				<n-input v-model:value="formValue.email" :placeholder="$t('tips.pleaseEnterEmail')" type="email"
					show-password-on="click" @mouseleave.naive="checkUserName" />
			</n-form-item>
			<n-form-item :label="$t('commons.requestCode')" path="requestcode">
				<n-input type="requestCode" v-model:value="formValue.requestcode" :placeholder="$t('tips.enterRequestCode')"
					@keyup.enter="register" />
				<n-grid style="width: 20px;"><n-gi></n-gi></n-grid>
				<n-button type="primary" @click="sendcode" :disabled="isPassCode.isDisabled">{{ isPassCode.status ===
					"waiting"
					?
					$t('commons.requestCodeSend') : $t('commons.requestCodeSent') + isPassCode.text }}</n-button>
			</n-form-item>
			<n-form-item :label="$t('commons.username')" path="username">
				<n-input v-model:value="formValue.username" :placeholder="$t('tips.defaultEmailPrefix')"
					:input-props="{ autoComplete: 'username' }" :disabled="true" />
			</n-form-item>
			<n-form-item :label="$t('commons.nickname')" path="nickname">
				<n-input v-model:value="formValue.nickname" :placeholder="$t('tips.pleaseEnterNickname')" />
			</n-form-item>
			<n-form-item :label="$t('commons.organization')" path="organization">
				<n-input v-model:value="formValue.organization" :placeholder="$t('tips.pleaseEnterOrganization')" />
			</n-form-item>
			<n-form-item :label="$t('commons.password')" path="password">
				<n-input type="password" show-password-on="click" v-model:value="formValue.password"
					:placeholder="$t('tips.pleaseEnterPassword')" />
			</n-form-item>
			<n-form-item :label="$t('commons.passwordAgain')" path="password">
				<n-input type="password" show-password-on="click" v-model:value="formValue.repassword"
					:placeholder="$t('tips.enterPasswordAgain')" @keyup.enter="register" />
			</n-form-item>
			<n-space justify="center">
				<n-form-item wrapper-col="{ span: 16, offset: 8 }">
					<n-button type="primary" @click="register" :enabled="loading" style="min-width: 100px;">{{
						$t("commons.inWaiting") }}</n-button>
				</n-form-item>
			</n-space>
		</n-form>
	</n-space>
</template>
<script setup lang="ts">
import { ref, reactive, onMounted, watch } from 'vue';
import { useI18n } from 'vue-i18n';
import { FormInst } from 'naive-ui';
import { Message } from '@/utils/theme';
import { UserCreate } from '@/types/schema';
import { useRouter } from "vue-router";
import { registerApi, LoginData, loginApi, getRequestCodeApi, GetRequestCode } from '@/api/user';
import chatbgiIcon1 from '/logo3.png';

let countTime = 60;
const loading = ref<boolean>(false);
const isPassCode = reactive({ isDisabled: false, status: 'waiting', text: '', isRequested: false });

const { t } = useI18n();
const formRef = ref<FormInst>();
const router = useRouter();
const autoHeight = ref('120px');

const formValue = reactive({
	username: '',
	password: '',
	repassword: '',
	nickname: '',
	email: '',
	requestcode: "",
	organization: ''
});

const loginRules = {
	username: { required: true, message: t("tips.pleaseEnterUsername"), trigger: 'blur' },
	email: { required: true, message: t("tips.pleaseEnterEmail"), trigger: 'blur' },
	password: { required: true, message: t("tips.pleaseEnterPassword"), trigger: 'blur' },
	requestcode: { required: true, message: t("tips.enterRequestCode"), trigger: 'blur' },
	nickname: { required: true, message: t("tips.pleaseEnterNickname"), trigger: 'blur' }
}
const filtering = (args: any) => {
	const reg = /@genomics.cn|@bgi.com|@mgi-tech.com/;
	const reg1 = /@/;
	return { "bgi": reg.test(args), "emailformat": reg1.test(args) };
}
const register = async () => {
	if (formValue.username === "") {
		Message.error(t('tips.pleaseEnterUsername'));
		formValue.username = "";
	}
	else if (formValue.email === "") {
		Message.error(t('tips.pleaseEnterEmail'));
		formValue.email = "";
	}
	else if (formValue.requestcode === "") {
		Message.error(t('tips.enterRequestCode'));
		formValue.requestcode = "";
	}
	else if (formValue.password !== formValue.repassword) {
		Message.error(t('tips.passwordException'))
		formValue.password = '';
		formValue.repassword = '';
	}
	else {
		if (filtering(formValue.email).bgi == false) {
			Message.warning(t('tips.wrongEmail'));
			formValue.email = "";
		}
		else if (filtering(formValue.email).emailformat == false) {
			Message.error(t('tips.emailFormatFail'));
			formValue.email = "";
		}
		else {
			const registerForm = ref<UserCreate>({
				username: '',
				nickname: '',
				email: '',
				password: '',
				requestcode: '',
				organization: '',
				is_superuser: false
			});
			const useForm = ref<LoginData>({
				username: "",
				password: ""
			})
			registerForm.value.username = formValue.username;
			registerForm.value.email = formValue.email;
			formValue.nickname === "" ? registerForm.value.nickname = formValue.username : registerForm.value.nickname = formValue.nickname;
			registerForm.value.requestcode = formValue.requestcode;
			registerForm.value.password = formValue.password;
			registerForm.value.organization = formValue.organization;
			useForm.value.username = formValue.username;
			useForm.value.password = formValue.password;
			new Promise((resolve, reject) => {
				registerApi(registerForm.value).then(() => {
					Message.success(t("commons.createUserSuccess"));
					loginApi(useForm.value as LoginData).then(() => {
						router.push({ name: 'conversation' });
					});
					resolve(true);
				}).catch(err => { reject(err); });
			})
		}
	}

}
const sendcode = async () => {
	if (countTime == 0) {
		isPassCode.status = "waiting";
		isPassCode.isDisabled = false;
		countTime = 60;
		return;
	}
	else {
		if (formValue.email === "") {
			Message.error(t('commons.isNotNullEmail'));
			isPassCode.status = "waiting";
			isPassCode.isDisabled = false;
			return;
		}
		else if (filtering(formValue.email).emailformat == false) {
			Message.error(t('tips.emailFormatFail'));
			isPassCode.status = "waiting";
			isPassCode.isDisabled = false;
			formValue.email = "";
			return;
		}
		else {
			if (filtering(formValue.email).bgi == false) {
				Message.warning(t('tips.wrongEmail'));
				isPassCode.status = "waiting";
				isPassCode.isDisabled = false;
				formValue.email = "";
				return;
			} else {
				isPassCode.status = "sending";
				isPassCode.isDisabled = true;
				isPassCode.text = "( " + countTime + " )";
				countTime--;
				if (isPassCode.isRequested == false) {
					const codeForm = {
						mode: "",
						email: ""
					};
					codeForm.mode = "register";
					codeForm.email = formValue.email;
					await getRequestCodeApi(codeForm as GetRequestCode).then(res => {
						Message.success(t('tips.mailCodeSuccess'));
						if (res.status === 200) {
							isPassCode.isRequested = true;
							sendcode();
							return;
						}
					}).catch(() => { Message.error(t('tips.mailCodeFailure')) });
				}
				else {
					setTimeout(() => {
						sendcode();
					}, 1000);
				}
			}
		}
	}
}
const checkUserName = () => {
	formValue.username = formValue.email.indexOf("@bgi.com") > -1 || formValue.email.indexOf("@genomics.cn") > -1 || formValue.email.indexOf("@mgi-tech.com") > -1 ? formValue.email.split("@")[0] : "";
}

watch(autoHeight, (newVal, oldVal) => {
	console.log(newVal, oldVal)
	newVal = window.innerHeight - 60 + 'px';
})

onMounted(() => {
	autoHeight.value = window.innerHeight - 60 + 'px';
})

</script>
	
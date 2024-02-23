import {NButton,NInput,NForm,NFormItem,FormRules,} from "naive-ui";
import { ref, h, reactive } from "vue";
import { Dialog, Message } from "@/utils/theme";
import { i18n } from "@/i18n";
import { forgetPasswordApi, ForgetPassword, resetPasswordApi } from '@/api/user';

const t = i18n.global.t as any;

const generateToken = () => {
	const token = ref('');
	const array = new Uint32Array(8);
	crypto.getRandomValues(array);
	token.value = Array.from(array, (val) => val.toString(16)).join('');
	return token
}

const forgetPasswordRules: FormRules = {
	email:
		{
		required: true,
		message: t("tips.pleaseEnterEmail"),
		trigger: 'blur'
		},
	requestcode:
		{
		required: true,
		message: t("tips.enterRequestCode"),
		trigger: 'blur'
		},
	oldpassword:
		{
		required: true,
		message: t("tips.oldPassword"),
		trigger: 'blur'
		},
	password:
		{
		required: true,
		message: t("tips.pleaseEnterPassword"),
		trigger: 'blur'
		},
	reenteredPassword:
		{
		required: true,
		message: t("tips.enterPasswordAgain"),
		}
}

const resetInputDialog = (title: string,placeholder: string,callback: (inp: string) => Promise<any>, success: () => void) => {
const buttonDisabled = ref(false);
const repasswordDisabled = ref(true);
const token = generateToken()
const formValue = reactive({
	email: '',
	requestcode: "",
	password: '',
	repassword: ''
});

const sendcode = async () => {
	if (formValue.email === "") {
	Message.error(t("tips.pleaseEnterEmail"))
	return
	};
	if (/@genomics.cn|@bgi.com|@mgi-tech.com/.test(formValue.email) == false) {
	Message.warning(t('tips.wrongEmail'));
	return
	}
	if (await callback(formValue.email) === true) {
	buttonDisabled.value = true;
	}
}


const d = Dialog.info({
	title: title,
	positiveText: t("commons.verify"),
	content: () => [
	h(NForm, { model: formValue ,rules: forgetPasswordRules, style: { marginTop: '20px' }, }, [
		h(NFormItem, { path: "email", label: t('commons.email') },
		h(NInput, {
			onInput: (e) => (formValue.email = e),
			placeholder: placeholder,
		})
		),
		h(NFormItem, { path: "requestcode", label: t('commons.requestCode') }, [
		h(NInput, {
			onInput: (e) => (formValue.requestcode = e),
			placeholder: t("tips.enterRequestCode"),
		}),
		h(NButton, { style: { marginLeft: '15px' }, disabled: buttonDisabled.value, onClick: sendcode },
			t("commons.send"))
		])
	])
	],
	onPositiveClick: async () => {
	d.loading = true;
	const codeForm = {
		token: token.value,
		email: formValue.email,
		requestcode: formValue.requestcode
	};
	try {
		if (formValue.requestcode === "") {
		Message.error(t("tips.enterRequestCode"))
		throw new Error();
		};
		await forgetPasswordApi(codeForm as ForgetPassword).then(res => {
		if (res.status === 200) {
			const d2 = Dialog.info({
			title: t("commons.inputPassword"),
			positiveText: t("commons.send"),
			content: () => [
				h(NForm, { model: formValue, rules: forgetPasswordRules, style: { marginTop: '20px' } }, [
				h(NFormItem, { path: "password", label: t("tips.pleaseEnterPassword") },
					h(NInput, {
					onInput: (e) => {
						formValue.password = e;
						repasswordDisabled.value = false;
					},
					type: "password",
					showPasswordOn:'click',
					autofocus: true,
					placeholder: t("tips.pleaseEnterPassword"),
					}),
				),
				h(NFormItem, { path: "reenteredPassword", label: t("commons.passwordAgain") },
					h(NInput, {
					onInput: (e) => {
						formValue.repassword = e;
					},
					type: "password",
					showPasswordOn:'click',
					disabled: repasswordDisabled.value,
					placeholder: t("tips.enterPasswordAgain"),
					}),
				)
				])
			],
			onPositiveClick: async () => {
				d2.loading = true
				try {
				if (formValue.password === "") {
					Message.error(t("tips.pleaseEnterPassword"))
					throw new Error();
				}
				if (formValue.repassword === "") {
					Message.error(t("tips.enterPasswordAgain"))
					throw new Error();
				}
				if (formValue.password != formValue.repassword) {
					Message.error(t("tips.passwordException"))
					throw new Error();
				}
				await resetPasswordApi(token.value, formValue.password).then(res2 => {
					if (res2.status === 200) {
					Message.info(t("tips.resetUserPasswordSuccess"))
					}
				})
				} catch (e) {
				d2.loading = false;
				return false;
				}
			}
			})
		}
		})
	} catch (e) {
		d.loading = false;
		return false;
	}
	}
});
};

const foundUserPasswordDialog = (
	callback: (email: string) => Promise<any>,
	success: () => void
) => {
	resetInputDialog(
		t("commons.foundPassword"),
		t("tips.pleaseEnterEmail"),
		callback,
		success
	);
};

export {
	foundUserPasswordDialog,
};
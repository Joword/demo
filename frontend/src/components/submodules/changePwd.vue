<template>
  <n-space justify="center" style="margin: 150px auto;">
    <n-form ref="formRef" :model="formValue" :rules="changeRules" label-placement="left" label-width="auto" require-mark-placement="right-hanging" class="forgot">
      <n-form-item path="email" style="margin-top: 20px;">
        <template #label><span style="float:left;">{{ $t('commons.username') }}</span></template>
        <n-input v-model:value="formValue.username" :placeholder="$t('tips.enterUsername')" type="email" disabled="true" />
      </n-form-item>
      <n-form-item path="username" style="margin-top: 20px;">
        <template #label><span style="float:left;">{{ $t('commons.email') }}</span></template>
        <n-input v-model:value="formValue.email" :placeholder="$t('tips.enterEmail')" type="username" disabled="true" />
      </n-form-item>
      <n-form-item path="password" style="margin-top: 20px;">
        <template #label><span style="float:left;">{{ $t('commons.password') }}</span></template>
        <n-input v-model:value="formValue.password" :placeholder="$t('tips.inputPassword')" type="password" show-password-on="mousedown" />
      </n-form-item>
      <n-form-item path="resetPwd" style="margin-top: 20px;">
        <template #label><span style="float:left;">{{ $t('commons.repassword') }}</span></template>
        <n-input v-model:value="formValue.resetPassword" :placeholder="$t('tips.reinputPassowrd')" type="password" show-password-on="mousedown" />
      </n-form-item>
      <n-space justify="center">
        <n-form-item wrapper-col="{ span: 16, offset: 8 }">
          <n-button type="primary" @click="changePwdSubmit" :enabled="loading" style="min-width: 100px;">{{ $t("commons.reset") }}</n-button>
        </n-form-item>
      </n-space>
    </n-form>
  </n-space>
</template>
<script setup lang="ts">
import {ref, onMounted} from "vue";
import {useI18n} from "vue-i18n";
import {useRouter} from "vue-router";
import {changePwdForm, changePassword} from "@/types/info";
import {changePwdInfo, changePwd} from "@/api/request";
import {Message} from "@/utils/utils";

const {t} = useI18n();
const router = useRouter();
const formValue = ref<changePwdForm>({
  email: '',
  username: '',
  password: '',
  resetPassword: ''
});
const validPwd = ref<changePassword>({
  password: ''
});
const loading = ref(false);
const changeRules = {
  password: {required: true, message: t("tips.inputPassword"), trigger: 'blur'},
  resetPwd: {required: true, message: t("tips.reinputPassowrd"), trigger: 'blur'},
}
const changePwdSubmit = () => {
  if (formValue.value.password === formValue.value.resetPassword) {
    validPwd.value.password = formValue.value.password
    changePwd(validPwd.value).then(res => {
      if (res.data.code === 1000) {
        router.push({name: 'login'});
      }
    })
  } else if (formValue.value.password !== formValue.value.resetPassword) {
    Message.error(t('tips.passwordNotMatch'));
  }
};
onMounted(() => {
  changePwdInfo().then(res => {
    if (res.data.code === 1000) {
      formValue.value.email = res.data.data.email;
      formValue.value.username = res.data.data.username;
    } else {
      Message.error(res.data.msg);
    }
  })
});
</script>

<style scoped lang="css">
.forgot {
  min-width: 400px;
  text-align: left;
  border: 1px solid grey;
  padding: 20px;
  border-radius: 3px;
}
</style>
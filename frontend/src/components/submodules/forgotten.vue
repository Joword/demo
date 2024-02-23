<template>
  <n-space justify="center" style="margin: 150px auto">
    <n-form ref="formRef" :model="formValue" label-placement="left" label-width="auto" require-mark-placement="right-hanging" class="forgot">
      <n-form-item :feedback="inputFeedBack">
        <template #default><b>Forgot password</b></template>
      </n-form-item>
      <n-form-item :label="$t('commons.email')" path="email" style="margin-top: 20px;">
        <n-input v-model:value="formValue.email" :placeholder="$t('tips.enterEmail')" type="email" @keyup.enter="forgotPwd" />
      </n-form-item>
      <n-space justify="center">
        <n-form-item wrapper-col="{ span: 16, offset: 8 }">
          <n-button type="primary" @click="forgotPwd" :enabled="loading" style="min-width: 100px;">{{ $t("commons.reset") }}</n-button>
        </n-form-item>
      </n-space>
    </n-form>
  </n-space>
</template>

<script setup lang="ts">
import {ref, computed} from "vue";
import {emailCodeForm} from "@/types/info";
import {testForgot} from "@/api/request";
import {Message} from "@/utils/utils";

const formValue = ref<emailCodeForm>({
  email: '',
  role: ''
});
const loading = ref(false);
const inputFeedBack = computed(() => {
  return "Enter your email to help us identify you";
})
const forgotPwd = () => {
  testForgot(formValue.value).then(res => {
    switch (res.data.code) {
      case 1000:
        Message.success("the email has been sent, please check your email.");
        break;
        // @ts-ignore
      case 2023:
        Message.error(res.data.msg);
        // @ts-ignore
      case 2022:
        Message.error(res.data.msg);
    }
  })
}
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
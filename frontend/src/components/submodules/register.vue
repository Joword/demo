<template>
  <n-space justify="center" style="margin: 150px auto">
    <n-form ref="formRef" :model="formValue" :rules="registerRules" class="register">
      <n-form-item :label="$t('commons.email')" path="email">
        <n-input v-model:value="formValue.email" :placeholder="$t('tips.enterEmail')" type="email" show-password-on="click" @mouseleave.naive="checkUserName(formValue.email)" @blur="checkUserName(formValue.email)" />
      </n-form-item>
      <n-form-item :label="$t('commons.emailCode')" path="emailCode">
        <n-input type="emailCode" v-model:value="formValue.emailCode" :placeholder="$t('tips.passCode')" @keyup.enter="register" :disabled="passCode.isShow" />
        <n-grid style="width: 20px;">
          <n-gi></n-gi>
        </n-grid>
        <n-button type="primary" @click="sendcode" :disabled="passCode.isDisabled">{{ passCode.status === "sending" ? passCode.text : $t('commons.send') }}</n-button>
      </n-form-item>
      <n-form-item :label="$t('commons.username')" path="username">
        <n-input v-model:value="formValue.username" :placeholder="$t('tips.emailPrefix')" :input-props="{autoComplete: 'username'}" />
      </n-form-item>
      <n-form-item :label="$t('commons.organization')" path="organization">
        <n-input v-model:value="formValue.organization" :placeholder="$t('tips.organization')" />
      </n-form-item>
      <n-form-item :label="$t('commons.password')" path="password">
        <n-input type="password" v-model:value="formValue.password" :placeholder="$t('tips.inputPassword')" />
      </n-form-item>
      <n-form-item :label="$t('commons.repassword')" path="password">
        <n-input type="password" v-model:value="formValue.repassword" :placeholder="$t('tips.reinputPassowrd')" @keyup.enter="register" />
      </n-form-item>
      <n-space justify="center">
        <n-form-item wrapper-col="{ span: 16, offset: 8 }">
          <n-button type="primary" @click="register" :enabled="loading" style="min-width: 100px;">{{ $t("commons.submit") }}</n-button>
        </n-form-item>
      </n-space>
    </n-form>
  </n-space>
</template>

<script setup lang="ts">
import {ref, reactive} from "vue";
import {useI18n} from "vue-i18n";
import {FormInst} from 'naive-ui';
import {useRouter} from "vue-router";
import {Message} from "@/utils/utils";
import {registerApi, testEmailApi, getEmailCode} from "@/api/request";
import {RegisterInfo, validationCode, emailCodeForm} from "@/types/info";

let countTime = 60;

const {t} = useI18n();
const router = useRouter();
const formRef = ref<FormInst>();
const loading = ref<boolean>(false);
const passCode = reactive<validationCode>({
  isDisabled: false,
  isRequested: false,
  isShow: true,
  status: '',
  text: ''
})
const formValue = ref<RegisterInfo>({
  username: '',
  email: '',
  password: '',
  repassword: '',
  organization: '',
  emailCode: ''
});

const registerRules = {
  username: {required: true, message: t("tips.enterUsername"), trigger: 'blur'},
  email: {required: true, message: t("tips.enterEmail"), trigger: 'blur'},
  password: {required: true, message: t("tips.inputPassword"), trigger: 'blur'},
  emailCode: {required: true, message: t("tips.passCode"), trigger: 'blur'},
}

const register = async () => {
  if (formValue.value.username === "") {
    Message.error(t('tips.pleaseEnterUsername'));
    formValue.value.username = "";
  } else if (formValue.value.email === "") {
    Message.error(t('tips.enterEmail'));
    formValue.value.email = "";
  } else if (formValue.value.emailCode === "") {
    Message.error(t('tips.emailMiss'));
    formValue.value.emailCode = "";
  } else if (formValue.value.password !== formValue.value.repassword) {
    Message.error(t('tips.passwordException'))
    formValue.value.password = '';
    formValue.value.repassword = '';
  } else {
    new Promise((resolve, reject) => {
      let data = new FormData();
      data.append("j", JSON.stringify(formValue.value));
      registerApi(formValue.value).then((res) => {
        switch (res.data.code) {
          case 1000:
            router.push({name: 'login'});
            Message.success(t('tips.registerSuccess'));
            break;
            // @ts-ignore
          case 2003:
            Message.error(res.data.msg);
          case 2021:
            Message.error(t('tips.mailCodeOutdate'));
            break;
        }
        resolve(true);
      }).catch(err => {
        reject(err);
      });
    })
  }
}

const checkUserName = (email: string) => {
  const data = new FormData();
  data.append("e", email);
  formValue.value.username = email.split("@")[0];
  if (formValue.value.email.split("@").length == 2) {
    testEmailApi(data).then(res => {
      let code = ref(0);
      if (code.value === 0) {
        switch (res.data.code) {
            // @ts-ignore
          case 2007:
            code.value = 2007;
            break;
          case 2018:
            code.value = 2018;
            break;
        }
      }
      switch (code.value) {
        case 2007:
          Message.error(t('tips.emailExisted'));
          break;
      }
      passCode.isShow = false;
    });
  }
}
const sendcode = async () => {
  passCode.isShow = false;
  if (countTime == 0) {
    passCode.status = "waiting";
    passCode.isDisabled = false;
    countTime = 60;
    return;
  } else {
    if (formValue.value.email === "") {
      Message.error(t('tips.emailNotNull'));
      passCode.status = "waiting";
      passCode.isDisabled = false;
      return;
    } else {
      passCode.status = "sending";
      passCode.isDisabled = true;
      passCode.text = "( " + countTime + " )";
      countTime--;
      if (passCode.isRequested == false) {
        const code = {
          role: "",
          email: ""
        };
        code.role = "regular";
        code.email = formValue.value.email;
        await getEmailCode(code as emailCodeForm).then(res => {
          Message.success(t('tips.mailCodeSuccess'));
          if (res.status === 200) {
            passCode.isRequested = true;
            sendcode();
            return;
          }
        }).catch(() => {
          Message.error(t('tips.mailCodeFailure'))
        });
      } else {
        setTimeout(() => {
          sendcode();
        }, 1000);
      }
    }
  }
}
</script>

<style lang="css" scoped>
.register {
  min-width: 400px;
  text-align: left;
  border: 1px solid grey;
  padding: 20px;
  border-radius: 3px;
}
</style>
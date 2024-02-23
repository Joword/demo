<template>
  <n-space justify="center" style="margin: 150px auto;">
    <n-form :model="formValue" :rules="rules" label-placement="left" label-width="auto" require-mark-placement="right-hanging" class="login">
      <n-form-item :label="$t('commons.username')" path="username" style="margin-top: 20px;">
        <n-input v-model:value="formValue.username" :placeholder="$t('tips.inputName')" type="username" show-password-on="click" />
      </n-form-item>
      <n-form-item :label="$t('commons.password')" path="password">
        <n-input v-model:value="formValue.password" :placeholder="$t('tips.inputPassword')" type="password" @keyup.enter="login" />
      </n-form-item>
      <n-form-item>
        <div style="width:100%;">
          <n-button type="tertiary" @click="forgotPassword" style="float:right" size="small">{{ $t('commons.forgotPassword') }}</n-button>
        </div>
      </n-form-item>
      <n-space justify="center">
        <n-form-item wrapper-col="{ span: 16, offset: 8 }">
          <n-button type="primary" @click="login" :enabled="loading" style="min-width: 100px;">{{ $t("commons.submit") }}</n-button>
        </n-form-item>
      </n-space>
    </n-form>
  </n-space>
</template>

<script setup lang="ts">
import {ref} from "vue";
import {useI18n} from "vue-i18n";
import {useRouter} from "vue-router";
import {loginForm} from "@/types/info";
import {testLogin} from "@/api/request";
import {Message} from "@/utils/utils";

const {t} = useI18n();
const router = useRouter();
const loading = ref<boolean>(false);

const formValue = ref<loginForm>({
  username: '',
  password: ''
});
const rules = {
  username: {required: true, message: t("tips.inputName"), trigger: 'blur'},
  password: {required: true, message: t("tips.inputPassword"), trigger: 'blur'},
}

const login = () => {
    testLogin(formValue.value).then(res => {
        console.log(res);
        if (res.data.code === 1000) {
            switch (res.data.code) {
                // @ts-ignore
                case 1000:
                    router.push({name: 'home'});
                break;
                // @ts-ignore
                case 2002:
                    Message.error(res.data.msg);
                break;
                // @ts-ignore
                case 2022:
                    Message.error(res.data.msg);
                break;
                // @ts-ignore
                case 2004:
                    Message.error(res.data.msg);
                break;
            }
        }
    }).catch(() => {
        Message.error(t('tips.loginError'));
    });
};

const forgotPassword = () => {
  router.push({name: 'forgottenPwd'});
}
</script>

<style lang="css" scoped>
.login {
  min-width: 400px;
  text-align: left;
  border: 1px solid grey;
  padding: 20px;
  border-radius: 3px;
}
</style>